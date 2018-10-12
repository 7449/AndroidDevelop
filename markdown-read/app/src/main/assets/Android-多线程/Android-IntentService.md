# 1.基本概念
## 1.1 定义
Android里的一个封装类，继承四大组件之一的Service

处理异步请求 & 实现多线程

应用场景：线程任务 需 **按顺序、在后台执行**

```
最常见的场景：离线下载
不符合多个数据同时请求的场景：所有的任务都在同一个Thread looper里执行
```


## 1.2 使用步骤

```
步骤1：定义 IntentService的子类

需 传入线程名称、复写onHandleIntent()方法
步骤2：在Manifest.xml中注册服务
步骤3：在Activity中开启Service服务
```

## 1.3 实例

注意这里是隐式调用，Intent在5.0之后需要显式调用

定义 IntentService的子类：传入线程名称、复写onHandleIntent()方法
```
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


```
步骤2：在Manifest.xml中注册服务
```
<service android:name=".myIntentService">
            <intent-filter >
                <action android:name="cn.scu.finch"/>
            </intent-filter>
        </service>


```
步骤3：在Activity中开启Service服务

```
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

![image](https://upload-images.jianshu.io/upload_images/944365-fadf671e3671b52a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/307)

## 1.4 和Service区别
IntentService是继承并处理异步请求的一个类，在IntentService内有一个工作线程来处理耗时操作，启动IntentService的方式和启动传统的Service一样，同时，当任务执行完后，IntentService会自动停止，而不需要我们手动去控制或stopSelf()。另外，可以启动IntentService多次，而每一个耗时操作会以工作队列的方式在IntentService的onHandleIntent回调方法中执行，并且，每次只会执行一个工作线程，执行完第一个再执行第二个，以此类推。

Intent服务开启后，执行完onHandleIntent里面的任务就自动销毁结束，通过打印的线程名称可以发现是新开了一个线程来处理耗时操作的，即是耗时操作也可以被这个线程管理和执行，同时不会产生ANR的情况。

![image](https://upload-images.jianshu.io/upload_images/944365-c85aac01a4325ed9.png?imageMogr2/auto-orient/)

## 1.5和其他线程区别
![image](https://upload-images.jianshu.io/upload_images/944365-6a86410410a8278b.png?imageMogr2/auto-orient/)

## 1.6源码分析

### 1.6.1基本流程
![image](https://upload-images.jianshu.io/upload_images/944365-fa5bfe6dffa531ce.png?imageMogr2/auto-orient/)

特别注意：若启动IntentService 多次，那么 每个耗时操作 则 以队列的方式 在 IntentService的 onHandleIntent回调方法中依次执行，执行完自动结束：


```
接下来，我们将通过 源码分析 解决以下问题：

IntentService 如何单独开启1个新的工作线程
IntentService 如何通过onStartCommand() 将Intent 传递给服务 & 依次插入到工作队列中
```

### 1.6.2IntentService如何单独开启1个新的工作线程
主要分析内容 = IntentService源码中的 onCreate()方法
```
@Override
public void onCreate() {
    super.onCreate();
    
    // 1. 通过实例化andlerThread新建线程 & 启动；故 使用IntentService时，不需额外新建线程
    // HandlerThread继承自Thread，内部封装了 Looper
    HandlerThread thread = new HandlerThread("IntentService[" + mName + "]");
    thread.start();
  
    // 2. 获得工作线程的 Looper & 维护自己的工作队列
    mServiceLooper = thread.getLooper();

    // 3. 新建mServiceHandler & 绑定上述获得Looper
    // 新建的Handler 属于工作线程 ->>分析1
    mServiceHandler = new ServiceHandler(mServiceLooper); 
}
/** 
     * 分析1：ServiceHandler源码分析
     **/ 
     private final class ServiceHandler extends Handler {

         // 构造函数
         public ServiceHandler(Looper looper) {
         super(looper);
       }

        // IntentService的handleMessage（）把接收的消息交给onHandleIntent()处理
        @Override
         public void handleMessage(Message msg) {
  
          // onHandleIntent 方法在工作线程中执行
          // onHandleIntent() = 抽象方法，使用时需重写 ->>分析2
          onHandleIntent((Intent)msg.obj);
          // 执行完调用 stopSelf() 结束服务
          stopSelf(msg.arg1);

    }
}

   /** 
     * 分析2： onHandleIntent()源码分析
     * onHandleIntent() = 抽象方法，使用时需重写
     **/ 
      @WorkerThread
      protected abstract void onHandleIntent(Intent intent);


```
### 1.6.3 问题2：IntentService 如何通过onStartCommand() 将Intent 传递给服务 & 依次插入到工作队列中

```
/** 
  * onStartCommand（）源码分析
  * onHandleIntent() = 抽象方法，使用时需重写
  **/ 
  public int onStartCommand(Intent intent, int flags, int startId) {

    // 调用onStart（）->>分析1
    onStart(intent, startId);
    return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
}

/** 
  * 分析1：onStart(intent, startId)
  **/ 
  public void onStart(Intent intent, int startId) {

    // 1. 获得ServiceHandler消息的引用
    Message msg = mServiceHandler.obtainMessage();
    msg.arg1 = startId;

    // 2. 把 Intent参数 包装到 message 的 obj 发送消息中，
    //这里的Intent  = 启动服务时startService(Intent) 里传入的 Intent
    msg.obj = intent;

    // 3. 发送消息，即 添加到消息队列里
    mServiceHandler.sendMessage(msg);
}


```
从上面源码可看出：IntentService本质 = Handler + HandlerThread：

- 通过HandlerThread 单独开启1个工作线程：IntentService
- 创建1个内部 Handler ：ServiceHandler
- 绑定 ServiceHandler 与 IntentService
- 通过 onStartCommand() 传递服务intent 到ServiceHandler 、依次插入Intent到工作队列中 & 逐个发送给 onHandleIntent()
- 通过onHandleIntent() 依次处理所有Intent对象所对应的任务

因此我们通过复写onHandleIntent() & 在里面 根据Intent的不同进行不同线程操作 即可

## 1.7注意事项
* 注意事项1. 工作任务队列 = 顺序执行

即 若一个任务正在IntentService中执行，此时你再发送1个新的任务请求，这个新的任务会一直等待直到前面一个任务执行完毕后才开始执行

原因：
1. 由于onCreate()只会调用一次 = 只会创建1个工作线程；
1. 当多次调用 startService(Intent)时（即 onStartCommand（）也会调用多次），其实不会创建新的工作线程，只是把消息加入消息队列中 & 等待执行。
1.  所以，多次启动 IntentService 会按顺序执行事件

若服务停止，则会清除消息队列中的消息，后续的事件不执行

* 注意事项2：不建议通过 bindService() 启动 IntentService


```
// 在IntentService中，onBind()`默认返回null
@Override
public IBinder onBind(Intent intent) {
    return null;
}
```
采用 bindService()启动 IntentService的生命周期如下：
```
onCreate() ->> onBind() ->> onunbind()->> onDestory()
```
即，并不会调用onStart() 或 onStartcommand()，故不会将消息发送到消息队列，那么onHandleIntent()将不会回调，即无法实现多线程的操作

此时，你应该使用Service，而不是IntentService

* SeriviceHanlder是个非静态内部类，是放置在线程HandlerThread中的，这种情况下会不会有内存泄漏的风险？

ServiceHandler绑定的是后台线程的Looper，而在Service停止时，Looper退出了，内存很快会被回收，所以不存在内存泄露。