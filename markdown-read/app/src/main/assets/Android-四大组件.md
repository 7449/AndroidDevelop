# 1.Activity的四种启动模式以及相关概念，Activity栈
1.Activity总共有四种启动模式（LaunchMode）
* standard：标准模式，会在当前的任务栈中入栈此activity的实例
* singleTop：栈顶复用模式，首先会检查当前任务栈的栈顶是否为该activity的实例，若是，那么不会创建新的实例且会调用栈顶activity的onNewIntent方法。如果不是，创建此活动的实例并入栈。
* singleTask：栈内复用模式，首先会检查当前任务栈的是否存在该activity的实例，若存在，那么不会创建新的实例且会将该实例之上的其余activity全部出栈，让此activity位于栈顶。如果不存在，创建此活动的实例并入栈。
* singleInstance：单实例模式。会创建一个新的任务栈，当在此任务栈中创建该acitivty的实例。
2. activity栈的概念
* 即任务栈，Activity中的Task就是一组以栈为模式聚集在一起的Activity组件集合。Activity Task有点类似于一个Activity Stack的容器。当App启动时如果不存在当前App的任务栈就会自动创建一个，默认情况下一个App中的所有Activity都是放在一个Task中的。但是如果指定了特殊的启动模式（例如SingInstance启动模式），那么就会出现同一个App的Activity出现在不同的任务栈中的情况，也会有任务栈中包含来自于不同App的Activity。
* 在特殊的应用场景中，Activity的栈和任务管理会有很大的变动，直接影响Activity的栈和任务管理的因素有：LaunchMode、Intent Flag、TaskAffinity
* App在Activity进入后台的情况下，都会通过onSaveInstanceState方法保存当前Activity栈中的信息，一旦App被意外杀死，而Activity栈没有清空的情况下，下次点击进入App，或者App自动被重新拉起的时候，会自动拉起到之前栈中的内容，并保有之前的跳转和回退逻辑。
杀死App 进程有以下几类典型的场景：

代码中杀死当前进程：System.exit(0)（杀死process，但是没有清空Activity栈）；

程序运行中遇到了崩溃问题（杀死process，但是没有清空Activity栈）；

Terminal中杀死进程，adb kill pid（杀死process，且清空Activity栈）；

在手机进程中kill掉正在运行的进程（杀死process，且清空Activity栈）；

其他异常操作，引起系统自动杀死当前进程，如：

后台切换语言（杀死process，但是没有清空Activity栈）；

后台切换字体（杀死process，但是没有清空Activity栈）；

后台关闭当前程序的权限（杀死process，但是没有清空Activity栈）；

但是在回退的过程中，每个页面都是重新创建（onCreate）的，也即页面view是重新绘制的，页面数据时重新获取的。在实际的开发过程中，很有可能会涉及到Stack中不同Activity之间的数据交互或页面跳转，开发者一般默认认为下层栈或底层栈中的Activity内容是存在的，这样遇到App被意外杀死，并重新启动的情况下，就很有可能会造成空指针问题。在实际的开发过程中，要格外留意。

# 2.常见情境下的生命周期分析，异常关闭页面之后的恢复
1. 正常情况下，Acitivity会经历如下生命周期:

- onCreate()：表示Activity 正在创建，这是生命周期的第一个方法，可以做一些初始化操作，比如调用setContentView加载布局，初始化Acitivity所需的数据等。
- onRestart()：表示Activity 正在重新启动。当前Acitivity从不可见重新变为可见状态时会调用onRestart方法。比如按Home键切换到桌面或者打开一个新的Activity，当前Actyvity就会暂停，也就onPause和onStop被执行，再回到这个Activity就会出现这种情况。
- onStart()：表示Activity正在被启动，已经可见，但还没有出现在前台，还无法和用户交互。
- onResume()：表示Activity已经可见了，并且在前台开始活动。注意：onStart和onResume都表示Activity已经可见，但是onStart的时候Activity还在后台，onResume的时候Activity才显示到前台。
- onPause()：表示Activity 正在停止，正常情况下，紧接着onStop就会被调用。此时可以做一些存储数据、停止动画等操作，不能太耗时，否则会影响新的Activity的显示，onPause必须先执行完，新Activity的onResume才会执行。
- onStop：表示Activity***即将停止*，可以做一些稍微重量级的回收工作，同样不能太耗时。
- onDestroy：表示Activity即将被销毁，这是Activity生命周期的最后一个回调，可以做一些回收和最终的资源释放。 
2. 常见的情况分析：
- 第一次启动，回调如下：onCreat->onStart->onResume。 
- 当用户打开新的Activity或者切换到桌面的时候，回调如下：onPause->onStop 。如果新的Activity是透明的 ，那么当前Activity 只会调用onPause，不会调用onStop。 
- 当用户再次回到原Activity时，回调如下：onRestart->onStart->onResume。 
- 当用户按下back键回退时，回调如下：onPause->onStop->onDestory。 
- 当Activity被系统回收后再次打开，生命周期方法回调过程和(1)一样，只是生命周期方法一样，不代表所有过程都一样。
3. 当活动遭遇异常而关闭的时候，系统会调用onSaveInstanceState来保存当前Activity的状态（onStop之前），当系统重建的时候会调用onRestoreInstanceState这个方法，并且把Activity销毁时onSaveInstanceState方法所保存的Bundle对象作为参数同时传递给onRestoreInstanceState和onCreate方法，因为我们可以通过onCreate和onRestoreInstanceState方法来判断Activity是否被重建了，如果被重建了，那么我们就可以取出之前保存的数据并恢复，从上图我们可以看出，onRestoreInstanceState的调用时机是在onStart之后。
* 资源相关的系统配置发生改变导致Activity被杀死并重新创建：这里就比如发生了横竖屏切换。
![image](https://upload-images.jianshu.io/upload_images/3258163-e19437c59f3ab0e9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/583)
当系统配置发生改变后，Activity会被销毁，其onPause，onStop，onDestroy均会被调用，由于Activity是在异常情况下终止的，系统会调用onSaveInstanceState来保存当前Activity的状态。当竖屏切换到横屏时，测试log如下：

![image](https://upload-images.jianshu.io/upload_images/3258163-c5ba9746aa2926f9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/370)

同时，我们知道onSaveInstanceState和onRestoreInstanceState方法当中，系统为我们做了一定得恢复工作。当Activity在异常情况下需要重新创建时，系统会默认为我们保存当前的Activity的视图结构，并且在Activity重启后为我们恢复这些数据。比如文本框中用户输入的数据，ListVIew滚动的位置等。这些View相关的状态系统都能够默认为我们恢复。
* 资源内存不足导致低优先级的Activity被杀死：其数据存储和恢复过程和情况1完全一致。当系统内存不足时，系统就会按照上述优先级去杀死目标Activity所在的进程，并且后续通过onSaveInstanceState和onRestoreInstanceState来存储和恢复数据，如果一个进程中没有四大组件在执行，那么这个进程将很快被系统杀死，比较好的方法是将后台工作放入Service中从而保证进程有一定的优先级，这样就不会轻易地被系统杀死。

# 3.讲解Activity和Window，View的关系
1. Activity是Android应用程序的载体，允许用户在其上创建一个用户界面，并提供用户处理事件的API，如onKeyEvent, onTouchEvent等。在用户级别，程序员可能根愿意理解成为一个界面的载体。但仅仅是个载体，它本身并不负责任何绘制。Activity的内部实现，实际上是聚了一个Window对象。Window是一个抽象类，它的具体是在android_src_home/framework/policies/base/phone/com/android/internal/policy/impl目录下的PhoneWindow.java。当我们调用Acitivity的 setContentView方法的时候实际上是调用的Window对象的setContentView方法，所以我们可以看出Activity中关于界面的绘制实际上全是交给Window对象来做的。绘制类图的话，可以看出Activity聚合了一个Window对象。
下面是PhoneWindow中的setContentView方法的实现：

```
Java代码  收藏代码
@Override  
  
    public void setContentView(View view, ViewGroup.LayoutParams params) {  
  
        if (mContentParent == null) {  
  
            installDecor();  
  
        } else {  
  
            mContentParent.removeAllViews();  
  
        }  
  
        mContentParent.addView(view, params);  
  
        final Callback cb = getCallback();  
  
        if (cb != null) {  
  
            cb.onContentChanged();  
  
        }  
  
    }  
```
Window内部首先判断mContentParent是否为空，然后调用installDecor方法（安装装饰器）在该方法中，首先创建一个DecorView,DecorView是一个扩张FrameLayout的类，是所有窗口的根View。我们在Activity中调用的setConctentView就是放到DecorView中了。这是我们类图的聚合关系如下：Activity--->Window--->DecorView
2. 我们详细分析一下，类对象是如何被创建的，先不考虑Activity的创建（因为 Acitivity的实例由ActivityManager维护，是在另一个进程设计到IPC的通信，后面会讲到），而考虑Window和View的创建。Activity被创建后，系统会调用它的attach方法来将Activity添加到ActivityThread当中。我们找到Activity的attach方法如下：

```
Java代码  收藏代码
final void attach(Context context, ActivityThread aThread,  
  
            Instrumentation instr, IBinder token, int ident,  
  
            Application application, Intent intent, ActivityInfo info,  
  
            CharSequence title, Activity parent, String id,  
  
            Object lastNonConfigurationInstance,  
  
            HashMap<String,Object> lastNonConfigurationChildInstances,  
  
            Configuration config) {  
  
        attachBaseContext(context);  
  
       mWindow= PolicyManager.makeNewWindow(this);  
  
        mWindow.setCallback(this);  
  
        if (info.softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {  
  
            mWindow.setSoftInputMode(info.softInputMode);  
  
        }  
  
        mUiThread = Thread.currentThread();  
  
        mMainThread = aThread;  
  
        mInstrumentation = instr;  
  
        mToken = token;  
  
        mIdent = ident;  
  
        mApplication = application;  
  
        mIntent = intent;  
  
        mComponent = intent.getComponent();  
  
        mActivityInfo = info;  
  
        mTitle = title;  
  
        mParent = parent;  
  
        mEmbeddedID = id;  
  
        mLastNonConfigurationInstance = lastNonConfigurationInstance;  
  
        mLastNonConfigurationChildInstances = lastNonConfigurationChildInstances;  
  
        mWindow.setWindowManager(null, mToken, mComponent.flattenToString());  
  
        if (mParent != null) {  
  
            mWindow.setContainer(mParent.getWindow());  
  
        }  
  
        mWindowManager = mWindow.getWindowManager();  
  
        mCurrentConfig = config;  
  
    }  
```
我们看红色的代码部分，就是创建Window对象的代码。感兴趣的同学可以跟踪去看看具体是如何创建的。其实很简单，其内部实现调用了Policy对象的makeNewWindow方法，其方法直接new了一个PhoneWindow对象如下：

public PhoneWindow makeNewWindow(Context context) {

        returnnew PhoneWindow(context);

 }

这时我们已经可以把流程串起来，Activity创建后系统会调用其attach方法，将其添加到ActivityThread当中，在attach方法中创建了一个window对象。
3. 下面分析View的创建。我们知道Window聚合了DocerView,当用户调用setContentView的时候会把一颗View树仍给DocerView.View树是已经创建好的实例对象了，所以我们研究的是DocerView是个什么东西，它是如何被创建的。

我们回头看看Window实现里边的setContentView方法，我们看上面代码的红色部分setContentView-> installDecor-> generateDecor.

 

generateDecor直接new了一个DecorView对象：  

protected DecorView generateDecor() {


        returnnew DecorView(getContext(), -1);

 }

我们可以去看看DecorView的实现，它是PhoneWindow的一个内部类。实现很简单，它默认会包含一个灰色的标题栏，然后在标题栏下边会包含一个空白区域用来当用户调用setContentView的时候放置用户View，并传递事件，这里不做详细分析，感兴趣同学可以自己研究研究。

当DecorView创建好之后再回到Window中的setContentView方法中来，见上面代码蓝色部分，调用

  mContentParent.addView(view, params);
来将用户的View树添加到DecorView中。

到这时为止，我想我们已经很清晰的认识到它们3者之间的关系，并知道其创建流程。
4. Activity在onCreate之前调用attach方法，在attach方法中会创建window对象。window对象创建时有没有创建Decor对象。用户在Activity中调用setContentView,然后调用window的setContentView，这时会检查DecorView是否存在，如果不存在则创建DecorView对象，然后把用户自己的View 添加到DecorView中。

# 4.DecorView的概念和结构
1. 接着上面提到的installDecor()方法，我们看看它的源码，PhoneWindow#installDecor:
```
private void installDecor() {
    if (mDecor == null) {
        mDecor = generateDecor(); // 1
        mDecor.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        mDecor.setIsRootNamespace(true);
        if (!mInvalidatePanelMenuPosted && mInvalidatePanelMenuFeatures != 0) {
            mDecor.postOnAnimation(mInvalidatePanelMenuRunnable);
        }
    }
    if (mContentParent == null) {
        mContentParent = generateLayout(mDecor); // 2
        ...
        } 
    }
}
```
首先，会执行1号代码，调用PhoneWindow#generateDecor方法：
```
protected DecorView generateDecor() {
    return new DecorView(getContext(), -1);
}
```
可以看出，这里实例化了DecorView，而DecorView则是PhoneWindow类的一个内部类，继承于FrameLayout，由此可知它也是一个ViewGroup。
2. 其实，DecorView是整个ViewTree的最顶层View，它是一个FrameLayout布局，代表了整个应用的界面。在该布局下面，有标题view和内容view这两个子元素，而内容view则是上面提到的mContentParent。我们接着看②号代码，PhoneWindow#generateLayout方法

```
protected ViewGroup generateLayout(DecorView decor) {
        // Apply data from current theme.
        // 从主题文件中获取样式信息
        TypedArray a = getWindowStyle();

        ...

        if (a.getBoolean(R.styleable.Window_windowNoTitle, false)) {
            requestFeature(FEATURE_NO_TITLE);
        } else if (a.getBoolean(R.styleable.Window_windowActionBar, false)) {
            // Don't allow an action bar if there is no title.
            requestFeature(FEATURE_ACTION_BAR);
        }

        if(...){
            ...
        }

        // Inflate the window decor.
        // 加载窗口布局
        int layoutResource;
        int features = getLocalFeatures();
        // System.out.println("Features: 0x" + Integer.toHexString(features));
        if ((features & (1 << FEATURE_SWIPE_TO_DISMISS)) != 0) {
            layoutResource = R.layout.screen_swipe_dismiss;
        } else if(...){
            ...
        }

        View in = mLayoutInflater.inflate(layoutResource, null);    //加载layoutResource
        decor.addView(in, new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)); //往DecorView中添加子View，即mContentParent
        mContentRoot = (ViewGroup) in;

        ViewGroup contentParent = (ViewGroup)findViewById(ID_ANDROID_CONTENT); // 这里获取的就是mContentParent
        if (contentParent == null) {
            throw new RuntimeException("Window couldn't find content container view");
        }

        if ((features & (1 << FEATURE_INDETERMINATE_PROGRESS)) != 0) {
            ProgressBar progress = getCircularProgressBar(false);
            if (progress != null) {
                progress.setIndeterminate(true);
            }
        }

        if ((features & (1 << FEATURE_SWIPE_TO_DISMISS)) != 0) {
            registerSwipeCallbacks();
        }

        // Remaining setup -- of background and title -- that only applies
        // to top-level windows.
        ...

        return contentParent;
    }

```
该方法还是做了相当多的工作的，首先根据设置的主题样式来设置DecorView的风格，比如说有没有titlebar之类的，接着为DecorView添加子View，而这里的子View则是上面提到的mContentParent，如果上面设置了FEATURE_NO_ACTIONBAR，那么DecorView就只有mContentParent一个子View，这也解释了上面的疑问：mContentParent是DecorView的一个子元素。
用一幅图来表示DecorView的结构如下：

![image](https://upload-images.jianshu.io/upload_images/1734948-cd6dd09d50cb0bb5.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/531)

小结：DecorView是顶级View，内部有titlebar和contentParent两个子元素，contentParent的id是content，而我们设置的main.xml布局则是contentParent里面的一个子元素。

3. 在DecorView创建完毕后，让我们回到PhoneWindow#setContentView方法，直接看②号代码： mLayoutInflater.inflate(layoutResID, mContentParent);这里加载了我们设置的main.xml布局文件，并且设置mContentParent为main.xml的父布局，至于它怎么加载的，这里就不展开来说了。到目前为止，通过setContentView方法，创建了DecorView和加载了我们提供的布局，是这时，我们的View还是不可见的，因为我们仅仅是加载了布局，并没有对View进行任何的测量、布局、绘制工作。在View进行测量流程之前，还要进行一个步骤，那就是把DecorView添加至window中，然后经过一系列过程触发ViewRootImpl#performTraversals方法，在该方法内部会正式开始测量、布局、绘制这三大流程。
4. 另外系统的状态栏statusbar是不算在decorView里面的。 状态栏属于系统级别的UI，不包括在当前Activity的UI上，DecorView的宽高和手机屏幕一样是正常的，你可以查看下DecorView的层级关系，会发现DecorView的ActionBar的位置是处于某一个值之下的，而这个值正好是StatusBar的高度，所以推测DecorView在绘制子View的时候会预留一部分空间给StatusBar，而StatusBar则是有系统自身进行绘制。







# 5.下拉状态栏（通知栏）是不是影响activity的生命周期，如果在onStop的时候做了网络请求，onResume的时候怎么恢复（这里没按照原来题目，自己扩展了一下意思）
1. Android下拉通知栏不会影响Activity的生命周期方法。可以通过在activity生命周期打log的方式测试，结果证明并不会影响。
2. 延伸问题：Android 如何在程序里监听通知栏是否被下拉？在写项目的时候遇到一个bug，就是当程序启动时，下拉通知栏，再将通知栏隐藏后，程序里面的一些显示会被重置或修改。这时就需要监听一下通知栏是否被下拉。原先想着是不是activity的生命周期改变了，可以在生命周期中做处理，失去焦点了，在onPause()中处理一下数据，但测试后证明，onPause()方法不会被调用，这就蒙了，怎么办呢？ 
其实这里有一个很好的解决办法，就是在onWindowFocusChanged()中处理，当Activity重新获得焦点的时候，对界面和数据进行处理，这就解决了上面的问题，其实很简单，换个思维方式就能很好的解决当前的问题。

```
@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            //如果焦点获得，进行操作
        }
    }
```
3. 当我们在使用网络请求的时候，一般都是通过回调来获取请求到的数据。对于网络请求的回调需要注意的几个点：
* 比如我们的回调在Activity中处理数据，当我们把Activity关闭后，如果获取到数据时，就算我们的Activity被finish回调还是会执行，那么这个时候我们在回调里所做的一些操作，很可能会造成程序的错误。所以我们需要在回调的时候判断下Activity是否finish.

```
isFinishing()//判断当前的Activity是否关闭，如果活动结束,返回true,否则返回false。
```
* 在一个Activity或Fragment发起网络请求的时候，当我们关闭界面后，需要把发出的请求给取消掉，也就是说当我们发出一个请求的时候，数据还没有获取到我们又进入了新的界面，发起了一个新的请求，当请求多起来的时候就会出现阻塞的现象，比如：年级筛选，商品分类等这些请求都是会出现这种情况的

```
我目前的几个项目都是使用volley，取消请求的时候可以通过tag来取消，下面是取消请求的代码
  /**
     * 取消某个标示的所有请求，一般取消某个界面的所有请求
     *
     * @param tag 标示请求从哪个界面而来，方便界面退出后取消请求
     */
    public void cancelAll(final String tag) {
        getRequestQueue().cancelAll(new RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                boolean compareBool = false;
                if (!TextUtils.isEmpty(tag)) {
                    Object objTag = request.getTag();
                    if (null != objTag) {
                        compareBool = tag.equalsIgnoreCase(objTag.toString());
                    }
                }
                return compareBool;
            }
        });
    }
```

* 这里说下很多的时候别太相信服务器返回的数据，反正我是被坑过，所以我们尽量对获取到的数据进行判断
4. 像volley、okhttp、retrofit这样的网络请求框架，当Activity销毁时，他们网络会自动取消？
* Activity销毁时，需要取消掉网络请求，如，Volley一般在onStop()调用VolleyRequest.cancelPendingRequests(tagName);
* 其他的不清楚, 就Volley而言是不会取消网络请求的, 即使你使用了cancel方法, 也不会停止网络请求, 使用了cancel方法只是不进行回调, 实际上网络请求是后台线程, 还有超时限制, 所以网络请求即使不停止影响也不是很大. 关键是不能再生命周期结束后回调方法, 所以在Activity等结束的时候要cancel掉请求
* 一般情况下,Retrofit 在Activity销毁之后,任务还是会继续,我们这边只能选择是否接收回调


# 6.系统关机时，弹出关机Dialog之后，此时，桌面Activity会触发哪几个生命周期？
1. 在弹出关机dialog时不会执行任何生命周期的回调方法，在用AlertDialog.Builder弹出dialog时也不会回调任何方法。其中dialog是用当前Activity的上下文创建的，弹出它不会出发任何生命周期的方法。
2. 如果是当前Activity弹出的dialog则不会执行Activity任何生命周期中的方法,只有其它Activity弹出了Dialog或者拦住了当前的Activity才会执行onPause() 

# 7.实际应用场景中，假设A Activity位于栈顶，此时用户操作，从A Activity跳转到B Activity。那么对AB来说，具体会回调哪些生命周期中的方法呢？回调方法的具体回调顺序又是怎么样的呢？
1. 当用户点击A中按钮来到B时，假设B全部遮挡住了A，将依次执行A:onPause -> B:onCreate -> B:onStart -> B:onResume -> A:onStop。（记住一定是先执行A的onPasue方法之后才会去创建B）
* 具体原因可以参看源码，这也是google不建议我们在onPause方法执行耗时操作的原因，会影响活动B的创建。

2. 此时如果点击Back键，将依次执行B:onPause -> A:onRestart -> A:onStart -> A:onResume -> B:onStop -> B:onDestroy。

# 8.android 请举例什么情况下在使用onresume？
* 比如做一个音乐播放程序，在播放过程中，突然有电话打进来了，这时系统自动调出电话，而你的音乐播放程序置于后台，触发了onPause方法。当你电话结束后，关闭电话，又自动回到音乐播放程序，此时，触发onResume方法，这时，如果你之前在onPause的时候记录了播放点，就可以在onResume方法里来继续播放。



# 9.BroadcastReceiver，LocalBroadcastReceiver 区别
1. 下面已经说到了，其实就是全局广播和局部广播（针对App内部使用的广播）

# 10.Android中的广播概述（以及动态注册和静态注册区别等等）
1. 概述：广播，是一个全局的监听器，属于Android四大组件之一，Android 广播分为两个角色：广播发送者、广播接收者。监听 / 接收 应用 App 发出的广播消息，并 做出响应
2. 应用场景：Android不同组件间的通信（含 ：应用内 / 不同应用之间）、多线程通信、与 Android 系统在特定情况下的通信（比如电话呼入、网络可用切换等等）
3. 实现原理：Android中的广播使用了设计模式中的观察者模式：基于消息的发布 / 订阅事件模型（因此，Android将广播的发送者 和 接收者 解耦，使得系统方便集成，更易扩展）
![image](https://upload-images.jianshu.io/upload_images/944365-9fca9fd3978cef10.png)
4. 使用流程
![image](https://upload-images.jianshu.io/upload_images/944365-7c9ff656ebd1b981.png)
* 自定义广播接收者BroadcastReceiver：继承BroadcastReceivre基类
，必须复写抽象方法onReceive()方法
> 广播接收器接收到相应广播后，会自动回调 onReceive() 方法

> 一般情况下，onReceive方法会涉及 与
其他组件之间的交互，如发送Notification、启动Service等

> 默认情况下，广播接收器运行在 UI 线程，因此，onReceive()方法不能执行耗时操作，否则将导致ANR

* 注册的方式分为两种：静态注册、动态注册

静态注册：在AndroidManifest.xml里通过<receive>标签声明

```
<receiver 
    android:enabled=["true" | "false"]
//此broadcastReceiver能否接收其他App的发出的广播
//默认值是由receiver中有无intent-filter决定的：如果有intent-filter，默认值为true，否则为false
    android:exported=["true" | "false"]
    android:icon="drawable resource"
    android:label="string resource"
//继承BroadcastReceiver子类的类名
    android:name=".mBroadcastReceiver"
//具有相应权限的广播发送者发送的广播才能被此BroadcastReceiver所接收；
    android:permission="string"
//BroadcastReceiver运行所处的进程
//默认为app的进程，可以指定独立的进程
//注：Android四大基本组件都可以通过此属性指定自己的独立进程
    android:process="string" >

//用于指定此广播接收器将接收的广播类型
//本示例中给出的是用于接收网络状态改变时发出的广播
 <intent-filter>
<action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
    </intent-filter>
</receiver>


```
当此 App首次启动时，系统会自动实例化mBroadcastReceiver类，并注册到系统中。

动态注册：注册方式：在代码中调用Context.registerReceiver（）方法


```
// 选择在Activity生命周期方法中的onResume()中注册
@Override
  protected void onResume(){
      super.onResume();

    // 1. 实例化BroadcastReceiver子类 &  IntentFilter
     mBroadcastReceiver mBroadcastReceiver = new mBroadcastReceiver();
     IntentFilter intentFilter = new IntentFilter();

    // 2. 设置接收广播的类型
    intentFilter.addAction(android.net.conn.CONNECTIVITY_CHANGE);

    // 3. 动态注册：调用Context的registerReceiver（）方法
     registerReceiver(mBroadcastReceiver, intentFilter);
 }


// 注册广播后，要在相应位置记得销毁广播
// 即在onPause() 中unregisterReceiver(mBroadcastReceiver)
// 当此Activity实例化时，会动态将MyBroadcastReceiver注册到系统中
// 当此Activity销毁时，动态注册的MyBroadcastReceiver将不再接收到相应的广播。
 @Override
 protected void onPause() {
     super.onPause();
      //销毁在onResume()方法中的广播
     unregisterReceiver(mBroadcastReceiver);
     }
}


```

动态广播最好在Activity 的 onResume()注册、onPause()注销。防止内存泄漏。

```
不在onCreate() & onDestory() 或 onStart() & onStop()注册、注销是因为：

当系统因为内存不足（优先级更高的应用需要内存，请看上图红框）要回收Activity占用的资源时，Activity在执行完onPause()方法后就会被销毁，
有些生命周期方法onStop()，onDestory()就不会执行。当再回到此Activity时，是从onCreate方法开始执行。

假设我们将广播的注销放在onStop()，onDestory()方法里的话，有可能在Activity被销毁后还未执行onStop()，onDestory()方法，即广播仍还未注销，从而导致内存泄露。

但是，onPause()一定会被执行，从而保证了广播在App死亡前一定会被注销，从而防止内存泄露。


```
两种方式区别：

![image](https://upload-images.jianshu.io/upload_images/944365-8d163ab3ca20de0b.png)

# 11.Android中广播的分类
1. 广播的类型主要分为5类：
- 普通广播（Normal Broadcast）
- 系统广播（System Broadcast）
- 有序广播（Ordered Broadcast）
- 粘性广播（Sticky Broadcast）-API21已经失效
- App应用内广播（Local Broadcast）
2. 普通广播（Normal Broadcast）
即 开发者自身定义 intent的广播（最常用）。发送广播使用如下：

```
Intent intent = new Intent();
//对应BroadcastReceiver中intentFilter的action
intent.setAction(BROADCAST_ACTION);
//发送广播
sendBroadcast(intent);
```
若被注册了的广播接收者中注册时intentFilter的action与上述匹配，则会接收此广播（即进行回调onReceive()）
3. 系统广播（System Broadcast）：
Android中内置了多个系统广播：只要涉及到手机的基本操作（如开机、网络状态变化、拍照等等），都会发出相应的广播
每个广播都有特定的Intent - Filter（包括具体的action），常见的Android系统广播包括网络变化、电量变化、SD卡、屏幕变化等等
4. 有序广播（Ordered Broadcast）：发送出去的广播被广播接收者按照先后顺序接收，广播接受者接收广播的顺序规则（同时面向静态和动态注册的广播接受者）按照Priority属性值从大-小排序；
Priority属性相同者，动态注册的广播优先；

* 接收广播按顺序接收
* 先接收的广播接收者可以对广播进行截断，即后接收的广播接收者不再接收到此广播；
* 先接收的广播接收者可以对广播进行修改，那么后接收的广播接收者将接收到被修改后的广播
* sendOrderedBroadcast(intent);

5.App应用内广播（Local Broadcast）
* 考虑到Android中的广播可以跨App直接通信（exported对于有intent-filter情况下默认值为true），其他App针对性发出与当前App intent-filter相匹配的广播，由此导致当前App不断接收广播并处理；其他App注册与当前App一致的intent-filter用于接收广播，获取广播具体信息；即会出现安全性 & 效率性的问题。
* 使用App应用内广播（Local Broadcast）App应用内广播可理解为一种局部广播，广播的发送者和接收者都同属于一个App。
相比于全局广播（普通广播），App应用内广播优势体现在：安全性高 & 效率高
* 具体使用2 - 使用封装好的LocalBroadcastManager类
使用方式上与全局广播几乎相同，只是注册/取消注册广播接收器和发送广播时将参数的context变成了LocalBroadcastManager的单一实例

```
注：对于LocalBroadcastManager方式发送的应用内广播，只能通过LocalBroadcastManager动态注册，不能静态注册（需要静态注册，只能设置exported属性和增设权限来让全局广播变成局部广播）
```

```
/注册应用内广播接收器
//步骤1：实例化BroadcastReceiver子类 & IntentFilter mBroadcastReceiver 
mBroadcastReceiver = new mBroadcastReceiver(); 
IntentFilter intentFilter = new IntentFilter(); 

//步骤2：实例化LocalBroadcastManager的实例
localBroadcastManager = LocalBroadcastManager.getInstance(this);

//步骤3：设置接收广播的类型 
intentFilter.addAction(android.net.conn.CONNECTIVITY_CHANGE);

//步骤4：调用LocalBroadcastManager单一实例的registerReceiver（）方法进行动态注册 
localBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);

//取消注册应用内广播接收器
localBroadcastManager.unregisterReceiver(mBroadcastReceiver);

//发送应用内广播
Intent intent = new Intent();
intent.setAction(BROADCAST_ACTION);
localBroadcastManager.sendBroadcast(intent);

作者：Carson_Ho
链接：https://www.jianshu.com/p/ca3d87a4cdf3
來源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```

```
对于不同注册方式的广播接收器回调OnReceive（Context context，Intent intent）中的context返回值是不一样的：

对于静态注册（全局+应用内广播），回调onReceive(context, intent)中的context返回值是：ReceiverRestrictedContext；
对于全局广播的动态注册，回调onReceive(context, intent)中的context返回值是：Activity Context；
对于应用内广播的动态注册（LocalBroadcastManager方式），回调onReceive(context, intent)中的context返回值是：Application Context。
对于应用内广播的动态注册（非LocalBroadcastManager方式），回调onReceive(context, intent)中的context返回值是：Activity Context；

```

# 12.Service基础概念，生命周期
1. 基本概念
* 简介：服务，是Android四大组件之一， 属于 计算型组件，提供 需在后台长期运行的服务（比如复杂计算、音乐播放，下载等等，无用户界面、在后台运行、生命周期长）
* 分类
![image](https://upload-images.jianshu.io/upload_images/944365-ab970a084ab936c2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)
![image](https://upload-images.jianshu.io/upload_images/944365-dec9e6e2428f41f0.png)
2. 生命周期
![image](https://upload-images.jianshu.io/upload_images/944365-ba6f19f002b9b8f6.png?imageMogr2/auto-orient/)
service的生命周期里，常用的有四个手动调用的方法和五个内部调用的方法，下面通过启动流程来说明生命周期变化
* 只使用startService（）启动服务：startService调用-->onCreate-->onStartCommand->Service运行ing-->外界停止服务（stopService）-->onDestroy()-->Service停止

```
注意：
1.若一个Service被startService多次调用，那么onCreate也只会执行一次，因为Service实例只有一个
2.整个生命周期方法里面，只有onStartCommand可以调用多次，其他只能一次
3.onStartCommand调用次数=startService调用次数
```
* 只使用bindService（）启动服务：bindService调用-->onCreate-->onBind->Service绑定-运行ing-->外界解绑服务（unBindService）-->-->onUnbind-->onDestroy()-->Service停止

```
1.客户端通过一个IBinder接口和服务端通信
2.若一个Service被bindService多次调用，那么onCreate也只会执行一次，因为Service实例只有一个
3.多个客户端可以绑定到同一个服务端，当所有客户端都解绑后，服务会停止
```
* 先启动服务在绑定服务：startService调用-->onCreate-->onStartCommand->Service运行ing-->外界绑定服务（bindService）-->onBind()-->Service绑定-运行ing-->外接要求解绑(unbindService)-->onUnBind()-->外接要求停止服务(stopService)-->onDestroy()-->Service停止
* startServcie开启的Service，调用者退出后，Service仍然存在。但是BindService启动的就跟着退出销毁了

# 13.Service如何使用？如何通信？
![image](https://upload-images.jianshu.io/upload_images/944365-db081c31bd7b64c9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/606)
1. 本地Servcie：普通、最常用的后台服务Service。
* 使用步骤

```
步骤1：新建子类继承Service类
需重写父类的onCreate()、onStartCommand()、onDestroy()和onBind()方法
步骤2：构建用于启动Service的Intent对象
步骤3：调用startService()启动Service、调用stopService()停止服务
步骤4：在AndroidManifest.xml里注册Service


```
```
Manifest文件常见属性
属性	说明	备注
android:name	Service的类名	
android:label	Service的名字	若不设置，默认为Service类名
android:icon	Service的图标	
android:permission	申明此Service的权限	有提供了该权限的应用才能控制或连接此服务
android:process	表示该服务是否在另一个进程中运行（远程服务)	不设置默认为本地服务；remote则设置成远程服务
android:enabled	系统默认启动	true：Service 将会默认被系统启动；不设置则默认为false
android:exported	该服务是否能够被其他应用程序所控制或连接	不设置默认此项为 false

```
2. 可通信的Service：上面介绍的Service是最基础的，但只能单机使用，即无法与Activity通信。接下来将在上面的基础用法上，增设“与Activity通信”的功能，即使用绑定Service服务（Binder类、bindService()、onBind(）、unbindService()、onUnbind()）

```
步骤1：在新建子类继承Service类，并新建一个子类继承自Binder类、写入与Activity关联需要的方法、创建实例

public class MyService extends Service {

    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("执行了onCreat()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("执行了onStartCommand()");
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("执行了onDestory()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("执行了onBind()");
        //返回实例
        return mBinder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("执行了onUnbind()");
        return super.onUnbind(intent);
    }

    //新建一个子类继承自Binder类
    class MyBinder extends Binder {

        public void service_connect_Activity() {
            System.out.println("Service关联了Activity,并在Activity执行了Service的方法");

        }
    }
}


```
步骤2：在主布局文件再设置两个Button分别用于绑定和解绑Service

步骤3：在Activity通过调用MyBinder类中的public方法来实现Activity与Service的联系

```
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startService;
    private Button stopService;
    private Button bindService;
    private Button unbindService;

    private MyService.MyBinder myBinder;


    //创建ServiceConnection的匿名类
    private ServiceConnection connection = new ServiceConnection() {

        //重写onServiceConnected()方法和onServiceDisconnected()方法
        //在Activity与Service建立关联和解除关联的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        //在Activity与Service解除关联的时候调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //实例化Service的内部类myBinder
            //通过向下转型得到了MyBinder的实例
            myBinder = (MyService.MyBinder) service;
            //在Activity调用Service类的方法
            myBinder.service_connect_Activity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startService = (Button) findViewById(R.id.startService);
        stopService = (Button) findViewById(R.id.stopService);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

        bindService = (Button) findViewById(R.id.bindService);
        unbindService = (Button) findViewById(R.id.unbindService);

        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //点击启动Service
            case R.id.startService:
                //构建启动服务的Intent对象
                Intent startIntent = new Intent(this, MyService.class);
                //调用startService()方法-传入Intent对象,以此启动服务
                startService(startIntent);
                break;

            //点击停止Service
            case R.id.stopService:
                //构建停止服务的Intent对象
                Intent stopIntent = new Intent(this, MyService.class);
                //调用stopService()方法-传入Intent对象,以此停止服务
                stopService(stopIntent);
                break;

            //点击绑定Service
            case R.id.bindService:
                //构建绑定服务的Intent对象
                Intent bindIntent = new Intent(this, MyService.class);
                //调用bindService()方法,以此停止服务

                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                //参数说明
                //第一个参数:Intent对象
                //第二个参数:上面创建的Serviceconnection实例
                //第三个参数:标志位
                //这里传入BIND_AUTO_CREATE表示在Activity和Service建立关联后自动创建Service
                //这会使得MyService中的onCreate()方法得到执行，但onStartCommand()方法不会执行
                break;

            //点击解绑Service
            case R.id.unbindService:
                //调用unbindService()解绑服务
                //参数是上面创建的Serviceconnection实例
                unbindService(connection);
                break;

                default:
                    break;

        }
    }
}


```
3. 前台Service：前台Service和后台Service（普通）最大的区别就在于：前台Service在下拉通知栏有显示通知（如下图），但后台Service没有，前台Service优先级较高，不会由于系统内存不足而被回收；后台Service优先级较低，当系统出现内存不足情况时，很有可能会被回收。

```
用法很简单，只需要在原有的Service类对onCreate()方法进行稍微修改即可，如下图：
@Override
    public void onCreate() {
        super.onCreate();
        System.out.println("执行了onCreat()");

        //添加下列代码将后台Service变成前台Service
        //构建"点击通知后打开MainActivity"的Intent对象
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        //新建Builer对象
        Notification.Builder builer = new Notification.Builder(this);
        builer.setContentTitle("前台服务通知的标题");//设置通知的标题
        builer.setContentText("前台服务通知的内容");//设置通知的内容
        builer.setSmallIcon(R.mipmap.ic_launcher);//设置通知的图标
        builer.setContentIntent(pendingIntent);//设置点击通知后的操作

        Notification notification = builer.getNotification();//将Builder对象转变成普通的notification
        startForeground(1, notification);//让Service变成前台Service,并在系统的状态栏显示出来

    }


```
4. 远程Service：远程服务与本地服务最大的区别是：远程Service与调用者不在同一个进程里（即远程Service是运行在另外一个进程）；而本地服务则是与调用者运行在同一个进程里

![image](https://upload-images.jianshu.io/upload_images/944365-843b2b4e2988ff66.png?imageMogr2/auto-orient/)

* 使用场景：多个应用程序共享同一个后台服务（远程服务），即一个远程Service与多个应用程序的组件（四大组件）进行跨进程通信
* 为了让远程Service与多个应用程序的组件（四大组件）进行跨进程通信（IPC），需要使用AIDL。
* 在多进程通信中，存在两个进程角色（以最简单的为例）：服务器端和客户端。
* 下面其实就是AIDL的跨进程通讯了，如果想详细了解，[可以看](https://www.jianshu.com/p/34326751b2c6)

# 14.Service 与 Thread的区别
1. 结论：Service 与 Thread 无任何关系
之所以有不少人会把它们联系起来，主要因为Service的后台概念
![image](https://upload-images.jianshu.io/upload_images/944365-ad8ff95781d19451.png?imageMogr2/auto-orient/)
2. 注：一般会将 Service 和 Thread联合着用，即在Service中再创建一个子线程（工作线程）去处理耗时操作逻辑，如下代码：

```
@Override  
public int onStartCommand(Intent intent, int flags, int startId) {  
//新建工作线程
    new Thread(new Runnable() {  
        @Override  
        public void run() {  
            // 开始执行后台任务  
        }  
    }).start();  
    return super.onStartCommand(intent, flags, startId);  
}  
  
class MyBinder extends Binder {  
    public void service_connect_Activity() {  
  //新建工作线程
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                // 执行具体的下载任务  
            }  
        }).start();  
    }  
  
}

```
# 15.Service和IntentService的区别
![image](https://upload-images.jianshu.io/upload_images/944365-dfd91eb78d2338a3.png?imageMogr2/auto-orient/)

1.继承四大组件之一的Service，处理异步请求 & 实现多线程，线程任务 用于需要按顺序、在后台执行的应用。比如离线下载功能。不符合多个数据同时请求的场景：所有的任务都在同一个Thread looper里执行
2.使用步骤
```
步骤1：定义 IntentService的子类，需 传入线程名称、复写onHandleIntent()方法
public class myIntentService extends IntentService {

  /** 
    * 在构造函数中传入线程名字
    **/  
    public myIntentService() {
        // 调用父类的构造函数
        // 参数 = 工作线程的名字
        super("myIntentService");
    }

   /** 
     * 复写onHandleIntent()方法
     * 根据 Intent实现 耗时任务 操作
     **/  
    @Override
    protected void onHandleIntent(Intent intent) {

        // 根据 Intent的不同，进行不同的事务处理
        String taskName = intent.getExtras().getString("taskName");
        switch (taskName) {
            case "task1":
                Log.i("myIntentService", "do task1");
                break;
            case "task2":
                Log.i("myIntentService", "do task2");
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate() {
        Log.i("myIntentService", "onCreate");
        super.onCreate();
    }
   /** 
     * 复写onStartCommand()方法
     * 默认实现 = 将请求的Intent添加到工作队列里
     **/  
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("myIntentService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i("myIntentService", "onDestroy");
        super.onDestroy();
    }
}


步骤2：在Manifest.xml中注册服务
<service android:name=".myIntentService">
            <intent-filter >
                <action android:name="cn.scu.finch"/>
            </intent-filter>
        </service>

步骤3：在Activity中开启Service服务
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            // 同一服务只会开启1个工作线程
            // 在onHandleIntent（）函数里，依次处理传入的Intent请求
            // 将请求通过Bundle对象传入到Intent，再传入到服务里

            // 请求1
            Intent i = new Intent("cn.scu.finch");
            Bundle bundle = new Bundle();
            bundle.putString("taskName", "task1");
            i.putExtras(bundle);
            startService(i);

            // 请求2
            Intent i2 = new Intent("cn.scu.finch");
            Bundle bundle2 = new Bundle();
            bundle2.putString("taskName", "task2");
            i2.putExtras(bundle2);
            startService(i2);

            startService(i);  //多次启动
        }
    }


```
3.源码分析
![image](https://upload-images.jianshu.io/upload_images/944365-fa5bfe6dffa531ce.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/670)

特别注意：若启动IntentService 多次，那么 每个耗时操作 则 以队列的方式 在 IntentService的 onHandleIntent回调方法中依次执行，执行完自动结束

接下来，我们将通过 源码分析 解决以下问题：

IntentService 如何单独开启1个新的工作线程

IntentService 如何通过onStartCommand() 将Intent 传递给服务 & 依次插入到工作队列中

[link](https://www.jianshu.com/p/8a3c44a9173a)



# 16.怎么启动service，service和activity怎么进行数据交互
上面题目已经说过了Service的三种启动方式了，和activity的交互也已经详细说明。

# 17.Android系统为什么会设计ContentProvider，进程共享和线程安全问题？每个ContentProvider的操作是在哪个线程中运行的呢？
1. 在系统部署一个又一个Android应用之后，系统里将会包含多个Android应用，有时候需要在不同的应用之间共享数据，比如现在又一个短息接收应用，用户想吧数据接收到陌生短息的发信人添加到联系人管理应用中，就需要在不同应用之间共享数据。对于这种需要在不同应用之间共享数据的需求，当然可以让一个应用程序直接去操作另一个应用程序所记录的数据，比如操作它所记录的SharedPerferences，文件或者是数据库，这种方式太杂乱了：不同的应用程序记录数据的方式差别很大，这种方式不利于应用程序之间进行数据交换。

 为了在应用程序之间交换数据，Android提供了ContentProvider，ContentProvider是不同应用程序之间进行数据交换的标准API，当一个应用程序需要把自己的数据暴露给其他程序使用时，该应用程序就可以通过提供ContentProvider来实现；其他应用程序就可以通过ContentProvider来操作ContentProvider暴漏的数据。 
 
封装。对数据进行封装，提供统一的接口，使用者完全不必关心这些数据是在DB，XML、Preferences或者网络请求来的。当项目需求要改变数据来源时，使用我们的地方完全不需要修改。

提供一种跨进程数据共享的方式。

2. ContentResolver虽然是通过Binder进程间通信机制打通了应用程序之间共享数据的通道，但Content Provider组件在不同应用程序之间传输数据是基于匿名共享内存机制来实现的。

3. 分两种情况：
* ContentProvider和调用者在同一个进程，ContentProvider的方法（query/insert/update/delete等）和调用者在同一线程中；
* ContentProvider和调用者在不同的进程，ContentProvider的方法会运行在它自身所在进程的一个Binder线程中。

注意这两种方式在ContentProvider的方法没有执行完成前都会blocked调用者

# 18.intent-filter
1.  Intent中文意思指”意图”,按照Android的设计理念,Android使用Intent来封装程序的”调用意图”,不管启动Activity、Service、BroadcastReceiver,Android都使用统一的Intent对象来封装这一”启动意图”。此外,Intent也是应用程序组件之间通信的重要媒介。
2.  Intent的属性及intent-filter的配置相结合使用来完成隐式调用；Intent的属性大致包含:Component,Action,Category,Data,Type,Extra,Flag这7种属性,其中Component用于明确指定需要启动的组件,而Extra则用于”携带”需要交换的数据。 
3.  IntentFilter中的过滤信息有action、category和data，只有一个intent同时匹配这三种信息才是匹配成功。
* 一个过滤规则中可以有多个action，只要intent中的action能够和过滤规则中的任何一个相同则算匹配成功。
* category则要求intent如果含有category，那么所有的categor都必须和过滤规则的其中一个category相同。intent也可以没有category，因为会默认加上DEFAULT这个category，这也是过滤规则必须添加的。
* data匹配规则和action类似，如果过滤规则定义了data，那么intent中必须也要定义可以匹配的data。data由两部分构成：mimeType和URI，前面指的是媒体类型，后面是一种结构

# 19.Fragment的概念？如何和Activity以及其他Fragment通讯？
1. [基本介绍](https://www.jianshu.com/p/2bf21cefb763)
2. [通信方式](https://www.jianshu.com/p/825eb1f98c19)
3. 还有通过broadcast和eventbus进行通信的





