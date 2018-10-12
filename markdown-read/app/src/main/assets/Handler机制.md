 - Handler对Activity finish影响。

在开发的过程中碰到一个棘手的问题，调用Activity.finish函数Acitivity没有执行生命周期的ondestory函数，后面查找半天是因为有一个handler成员，因为它有一个delay消息没有处理，调用Activity.finish，Activity不会马上destory，所以记得在Ativity finish前清理一下handle中的未处理的消息，这样Activity才会顺利的destory

 - Looper

通过调用Looper.prepare()创建Looper()对象并绑定到ThreadLocal变量中。
Looper里面包含了messageQueue。
构造器如下：

```
private Looper()
{
     mQueue = new MessageQueue();
     mRun = true;
     mThread = Thread.currentThread();
}
```

loop()函数

1）从Looper中取出MessageQueue；
2）循环从MessageQueue中取出Message；
3）从Message中取出Target（Handler对象）；
4）调用tartget的dispatchMessage分发消息。

 - Handler对象

重要成员变量：

```
final MessageQueue mQueue;
final Looper  mLooper;
final Callback mCallback;   //用于回调
```

Handler对象在发送消息的时候，将MSG的target变量设为自己。这样在Looper对象循环取出msg的时候就可以调用对应handler的dispatchMessage()。此函数分发消息的优先级如下：
Message在创建的时候调用Obtain设置了Callback。
Handler在创建的时候传入了Callback。
交给Handler子类的HandleMessage处理（通常的做法）。
