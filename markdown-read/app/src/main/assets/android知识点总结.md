#### android知识点总结

### **1. taskAffinity和singleTask，singleInstance**

taskAffinity 是用来指示 Activity 属于哪一个 Task 的，默认情况下 Activity 的 taskAffinity 是包名。在代码设置方式为：


```
		<activity
            android:name=".SecondActivity"
            android:launchMode="singleTask"
            android:taskAffinity="com.test.SecondActivity">
        </activity>
```

一般情况下 taskAffinity 的使用配合 Activity 的启动模式 LaunchMode 来一起使用。

#### **singleTask**

默认情况下，在一个 app 中的所有 Activity 都有一样的 taskAffinity。当一个应用程序加载一个 singleTask 模式的 Activity 时，首先该 Activity 会检查是否存在与它的 taskAffinity 相同的 Task。 

 1. 如果存在，那么检查是否实例化，如果已经实例化，那么销毁在该Activity 以上的 Activity 并调用 onNewIntent 。如果没有实例化，那么该Activity实例化并入栈。
 2. 如果不存在，那么就重新创建 Task，并入栈。

**如果启动的 thisActivity 是 singleTask 模式的话，在它上面再次启动 otherActivity 的话，则 otherActivity 所在的 Task 是 thisActivity 所在的 Task**


![](http://img.blog.csdn.net/20180410103425499?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGlqaV94Yw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

以上图例表示：存在三个 Activity，FirstActivity ，SecondActivity 和  ThirdActivity。其中 SecondActivity 设置了 singleTask 以及和其他两个 Activity 不同的 taskAffinity。他们的跳转流向为：

FirstActivity -------> SecondActivity -------> ThirdActivity

#### **singleInstance**

 1. 当一个应用程序加载一个 singleInstance 模式的 Activity 时，如果该 Activity 没有被实例化，那么就重新创建一个 Task，并入栈，如果已经被实例化，那么就调用该 Activity 的 onNewIntent ；
 2. **singleInstance的 thisActivity 所在的 Task 不允许存在其他Activity**，任何从该 thisActivity 加载的 otherActiivty 都会被放入其它的 Task 中，如果存在与 otherActiivty 相同 affinity 的 Task，则在该 Task 内创建 otherActiivty。如果不存在，则重新生成新的 Task 并入栈。

**如果启动的 thisActivity 是 singleInstance 模式的话，在它上面再次启动 otherActivity 的话，则 otherActivity 所在的 Task 不在是thisActivity 所在的 Task ，singleInstance的Activity所在的Task不允许存在其他Activity**

![](http://img.blog.csdn.net/20180410103440522?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGlqaV94Yw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 

以上图例表示：存在三个 Activity，FirstActivity ，SecondActivity 和  ThirdActivity。其中 SecondActivity 设置了 singleInstance 以及和其他两个 Activity 不同的 taskAffinity。他们的跳转流向为：

FirstActivity -------> SecondActivity -------> ThirdActivity



#### 实例
以上这些学习都是因为最近在做一个微信调起客户端的事情。如果自己的客户端处于运行状态，按下 Home 键后台挂起。此时如果使用微信调起自己的客户端某个页面，不做任何处理的情况下，按下回退或者当前 Activity.finish()，页面都会停留在自己的客户端（因为自己的Application 回退栈不为空），这明显不符合逻辑的。产品的要求是，回退必须回到微信客户端,而且要保证不杀死自己的 Application.我的处理方案就是，设置当前被调起 Activity 的属性为：

```
LaunchMode=""SingleTask"  taskAffinity="com.tencent.mm"
```

(com.tencent.mm 是借助于工具找到的微信包名），就是把自己的 Activity 放到微信默认的 Task 栈里面，这样回退时就会遵循“ Task 只要有 Activity 一定从本 Task 剩余 Activity 回退"的原则，不会回到自己的客户端；而且也不会影响自己客户端本来的 Activity 和Task 逻辑。



