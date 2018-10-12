# 1.Handler，包括Looper，MessageQueue，ThreadLocal这些类要详细的看
## 1.1基本介绍

* 在安卓开发中：

为了保证Android的UI操作是线程安全的，Android规定了只允许UI线程修改Activity里的UI组件；
但在实际开发中，必然会用到多个线程并发操作UI组件，这又将导致UI操作的线程不安全
* 所以问题在于：如何同时满足：
保证线程安全
使多个线程并发操作UI组件
Handler消息传递机制就是这个问题的。


* Handler通过调用sendmessage方法把消息放在消息队列MessageQueue中，Looper负责把消息从消息队列中取出来，重新再交给Handler进行处理，三者形成一个循环
* 通过构建一个消息队列，把所有的Message进行统一的管理，当Message不用了，并不作为垃圾回收，而是放入消息队列中，供下次handler创建消息时候使用，提高了消息对象的复用，减少系统垃圾回收的次数
* 每一个线程，都会单独对应的一个looper，这个looper通过ThreadLocal来创建，保证每个线程只创建一个looper，looper初始化后就会调用looper.loop创建一个MessageQueue，这个方法在UI线程初始化的时候就会完成，我们不需要手动创建

![image](https://upload-images.jianshu.io/upload_images/944365-3a654c47005484ec.png?imageMogr2/auto-orient/)

## 1.2 几个相关概念
* Message

定义：消息，理解为线程间通讯的数据单元（Handler接受和处理的消息对象。）
例如后台线程在处理数据完毕后需要更新UI，则可发送一条包含更新信息的Message给UI线程

* Message Queue

定义：消息队列
作用：用来存放通过Handler发过来的消息，按照先进先出执行

* Handler

定义：Handler是Message的主要处理者
作用：负责将Message添加到消息队列&处理Looper分派过来的Message

* Looper

定义：循环器，扮演Message Queue和Handler之间桥梁的角色

作用：主要负责消息循环：循环取出Message Queue的Message；消息派发：将取出的Message交付给相应的Handler

每个线程中只能拥有一个Looper,但是一个Looper可以和多个线程的Handler绑定起来，也就是说很多个线程可以往一个Looper所持有的MessageQueue中发送消息。这就给我们提供了线程之间通信的可能。

Handler在创建的时候可以显示指定Looper，这样在Handler在调用sendMessage()投递消息的时候会将消息添加到指定的Looper里面的MessageQueue。如果不指定Looper，Handler默认绑定的是创建它的线程的Looper。

## 1.4 工作流程
* Handler

提供sendMessage方法，将消息放置到队列中
提供handleMessage方法，定义个各种消息的处理方式；

* Looper

Looper.prepare()：实例化Looper对象；为当前线程生成一个消息队列；
Looper.loop() ：循环从消息队列中获取消息，交给Handler处理；此时线程处于无限循环中，不停的从MessageQueue中获取Message 消息 ；如果没有消息就阻塞

* MessageQueue

提供enqueueMessage 方法，将消息根据时间放置到队列中；
提供next方法，从队列中获取消息，没有消息的时候阻塞；

异步通信传递机制步骤主要包括异步通信的准备、消息发送、消息循环和消息处理


```
异步通信的准备
包括Looper对象的创建&实例化、MessageQueue队列的创建和Handler的实例化
消息发送
Handler将消息发送到消息队列中
消息循环
Looper执行Looper.loop()进入消息循环，在这个循环过程中，不断从该Message Queue取出消息，并将取出的消息派发给创建该消息的Handler
消息处理
调用该Handler的dispatchMessage(msg)方法，即回调handleMessage(msg)处理消息
```






## 1.5 handler机制两种使用方式
Handler使用方式 因发送消息到消息队列的方式不同而不同
共分为2种：使用Handler.sendMessage（）、使用Handler.post（）

* 使用 Handler.sendMessage（）


* 使用post方法，代码如下
```
package ispring.com.testhandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements Button.OnClickListener {

    private TextView statusTextView = null;

    //uiHandler在主线程中创建，所以自动绑定主线程
    private Handler uiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusTextView = (TextView)findViewById(R.id.statusTextView);
        Button btnDownload = (Button)findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(this);
        System.out.println("Main thread id " + Thread.currentThread().getId());
    }

    @Override
    public void onClick(View v) {
        DownloadThread downloadThread = new DownloadThread();
        downloadThread.start();
    }

    class DownloadThread extends Thread{
        @Override
        public void run() {
            try{
                System.out.println("DownloadThread id " + Thread.currentThread().getId());
                System.out.println("开始下载文件");
                //此处让线程DownloadThread休眠5秒中，模拟文件的耗时过程
                Thread.sleep(5000);
                System.out.println("文件下载完成");
                //文件下载完成后更新UI
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Runnable thread id " + Thread.currentThread().getId());
                        MainActivity.this.statusTextView.setText("文件下载完成");
                    }
                };
                uiHandler.post(runnable);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
```
我们在Activity中创建了一个Handler成员变量uiHandler，Handler有个特点，在执行new Handler()的时候，默认情况下Handler会绑定当前代码执行的线程，我们在主线程中实例化了uiHandler，所以uiHandler就自动绑定了主线程，即UI线程。当我们在DownloadThread中执行完耗时代码后，我们将一个Runnable对象通过post方法传入到了Handler中，Handler会在合适的时候让主线程执行Runnable中的代码，这样Runnable就在主线程中执行了，从而正确更新了主线程中的UI。

通过输出结果可以看出，Runnable中的代码所执行的线程ID与DownloadThread的线程ID不同，而与主线程的线程ID相同，因此我们也由此看出在执行了Handler.post(Runnable)这句代码之后，运行Runnable代码的线程与Handler所绑定的线程是一致的，而与执行Handler.post(Runnable)这句代码的线程（DownloadThread）无关。

* 使用sendMessage方法，代码如下：
```
package ispring.com.testhandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements Button.OnClickListener {

    private TextView statusTextView = null;

    //uiHandler在主线程中创建，所以自动绑定主线程
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    System.out.println("handleMessage thread id " + Thread.currentThread().getId());
                    System.out.println("msg.arg1:" + msg.arg1);
                    System.out.println("msg.arg2:" + msg.arg2);
                    MainActivity.this.statusTextView.setText("文件下载完成");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusTextView = (TextView)findViewById(R.id.statusTextView);
        Button btnDownload = (Button)findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(this);
        System.out.println("Main thread id " + Thread.currentThread().getId());
    }

    @Override
    public void onClick(View v) {
        DownloadThread downloadThread = new DownloadThread();
        downloadThread.start();
    }

    class DownloadThread extends Thread{
        @Override
        public void run() {
            try{
                System.out.println("DownloadThread id " + Thread.currentThread().getId());
                System.out.println("开始下载文件");
                //此处让线程DownloadThread休眠5秒中，模拟文件的耗时过程
                Thread.sleep(5000);
                System.out.println("文件下载完成");
                //文件下载完成后更新UI
                Message msg = new Message();
                //虽然Message的构造函数式public的，我们也可以通过以下两种方式通过循环对象获取Message
                //msg = Message.obtain(uiHandler);
                //msg = uiHandler.obtainMessage();

                //what是我们自定义的一个Message的识别码，以便于在Handler的handleMessage方法中根据what识别
                //出不同的Message，以便我们做出不同的处理操作
                msg.what = 1;

                //我们可以通过arg1和arg2给Message传入简单的数据
                msg.arg1 = 123;
                msg.arg2 = 321;
                //我们也可以通过给obj赋值Object类型传递向Message传入任意数据
                //msg.obj = null;
                //我们还可以通过setData方法和getData方法向Message中写入和读取Bundle类型的数据
                //msg.setData(null);
                //Bundle data = msg.getData();

                //将该Message发送给对应的Handler
                uiHandler.sendMessage(msg);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
```
需要说明的是，如果在handleMessage中 不需要判断Message类型，那么就无须设置Message的what值；而且让Message携带数据也不是必须的，只有在需要的时候才需要让其携带数据；如果确实需要让Message携带数据，应该尽量使用arg1或arg2或两者，能用arg1和arg2解决的话就不要用obj，因为用arg1和arg2更高效。 



## 1.6源码分析内部机制

### 1.6.1 Looper
Looper主要负责：

自身的创建&创建Message Queue
消息循环（消息取出、派发）
对应职责我们来看下相应的源码：

* 自身的创建&创建Message Queue：prepare()方法
```
public static final void prepare() {
//判断sThreadLocal是否为null，否则抛出异常
//即Looper.prepare()方法不能被调用两次
//也就是说，一个线程中只能对应一个Looper实例
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
//sThreadLocal是一个ThreadLocal对象，用于在一个线程中存储变量
//实例化Looper对象并存放在ThreadLocal
//这说明Looper是存放在Thread线程里的
        sThreadLocal.set(new Looper(true));
}
//再来看下Looper的构造方法
private Looper(boolean quitAllowed) {
//创建了一个MessageQueue（消息队列）
//这说明，当创建一个Looper实例时，会自动创建一个与之配对的MessageQueue（消息队列）
        mQueue = new MessageQueue(quitAllowed);
        mRun = true;
        mThread = Thread.currentThread();
}

```
消息循环：loop()方法

```
public static void loop() {
//myLooper()方法作用是返回sThreadLocal存储的Looper实例，如果me为null，loop()则抛出异常
//也就是说loop方法的执行必须在prepare方法之后运行
//也就是说，消息循环必须要先在线程当中创建Looper实例
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
//获取looper实例中的mQueue（消息队列）
        final MessageQueue queue = me.mQueue;


        Binder.clearCallingIdentity();
        final long ident = Binder.clearCallingIdentity();
//进入消息循环
        for (;;) {
//next()方法用于取出消息队列里的消息
//如果取出的消息为空，则线程阻塞
            Message msg = queue.next(); // might block
            if (msg == null) {
          
                return;
            }

  
            Printer logging = me.mLogging;
            if (logging != null) {
                logging.println(">>>>> Dispatching to " + msg.target + " " +
                        msg.callback + ": " + msg.what);
            }

//消息派发：把消息派发给msg的target属性，然后用dispatchMessage方法去处理
//Msg的target其实就是handler对象，下面会继续分析
            msg.target.dispatchMessage(msg);

            if (logging != null) {
                logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
            }

            final long newIdent = Binder.clearCallingIdentity();
            if (ident != newIdent) {
                Log.wtf(TAG, "Thread identity changed from 0x"
                        + Long.toHexString(ident) + " to 0x"
                        + Long.toHexString(newIdent) + " while dispatching to "
                        + msg.target.getClass().getName() + " "
                        + msg.callback + " what=" + msg.what);
            }
//释放消息占据的资源
            msg.recycle();
        }
}

```
总结Looper的作用：

* 实例化本身、与当前线程绑定、创建与之相应的MessageQueue：prepare()方法

一个线程只会有一个Looper实例，同时一个Looper实例也只有一个MessageQueue

* 消息循环（消息取出、消息派发）：loop()方法

不断从MessageQueue中去取消息，派发给消息的target属性的Handler，然后调用相应Handler的dispatchMessage()方法进行消息处理。

### 1.6.2 Handler
在子线程发送消息给MessageQueue
处理Looper派发过来的消息

* 注意

Handler是需要和线程绑定在一起的，**在初始化Handler的时候一般通过指定Looper对象从而绑定相应线程**，即给Handler指定Looper对象=绑定到了Looper对象所在的线程中，Handler的消息处理回调会在那个线程中执行。一般有两种方法创建：

通过Loop.myLooper()得到当前线程的Looper对象/通过Loop.getMainLooper()可以获得当前进程的主线程的Looper对象。
不指定Looper对象，那么这个Handler绑定到了创建这个线程的线程上，消息处理回调也就在创建线程中执行.

* 首先看Handler的构造方法


```
public Handler() {
        this(null, false);
}
public Handler(Callback callback, boolean async) {
        if (FIND_POTENTIAL_LEAKS) {
            final Class<? extends Handler> klass = getClass();
            if ((klass.isAnonymousClass() || klass.isMemberClass() || klass.isLocalClass()) &&
                    (klass.getModifiers() & Modifier.STATIC) == 0) {
                Log.w(TAG, "The following Handler class should be static or leaks might occur: " +
                    klass.getCanonicalName());
            }
        }
//通过Looper.myLooper()获取了当前线程保存的Looper实例，如果线程没有Looper实例那么会抛出异常
//这说明在一个没有创建Looper的线程中是无法创建一个Handler对象的
//所以说我们在子线程中创建一个Handler时首先需要创建Looper，并且开启消息循环才能够使用这个Handler。
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                "Can't create handler inside thread that has not called Looper.prepare()");
        }
//获取了这个Looper实例中保存的MessageQueue（消息队列）
//这样就保证了handler的实例与我们Looper实例中MessageQueue关联上了

        mQueue = mLooper.mQueue;
        mCallback = callback;
        mAsynchronous = async;
    }


```
上述说明：当Handler初始化时，可通过构造方法自动关联Looper和相应的MessageQueue

* Handler向MessageQueue发送消息：对于Handler的发送方式可以分为post和send两种方式。

send的发送方法：sendMessage()
```
public final boolean sendMessage(Message msg)
    {
        return sendMessageDelayed(msg, 0);
    }

//我们往下扒
   public final boolean sendEmptyMessageDelayed(int what, long delayMillis) {
        Message msg = Message.obtain();
        msg.what = what;
        return sendMessageDelayed(msg, delayMillis);
    }


 public final boolean sendMessageDelayed(Message msg, long delayMillis)
    {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
    }


 public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
//直接获取MessageQueue
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessageAtTime() called with no mQueue");
            Log.w("Looper", e.getMessage(), e);
            return false;
        }
//调用了enqueueMessage方法
        return enqueueMessage(queue, msg, uptimeMillis);
    }

//调用sendMessage方法其实最后是调用了enqueueMessage方法
 private boolean enqueueMessage(MessageQueue queue, Message msg, long uptimeMillis) {
//为msg.target赋值为this，也就是把当前的handler作为msg的target属性
//如果大家还记得Looper的loop()方法会取出每个msg然后执行msg.target.dispatchMessage(msg)去处理消息，其实就是派发给相应的Handler
        msg.target = this;
        if (mAsynchronous) {
            msg.setAsynchronous(true);
        }
//最终调用queue的enqueueMessage的方法，也就是说handler发出的消息，最终会保存到消息队列中去。
        return queue.enqueueMessage(msg, uptimeMillis);
    }

```
Post的发送方法：sendMessage()
```
showhandler.post(new Runnable() {
                @Override
                public void run() {
                    String line = "\n";
                    StringBuffer text = new StringBuffer(show.getText());
                            text.append(line).append("angelababy:Yes,I do");
                            show.setText(text);
                }


```

相比send方法，post方法最大的不同在于，更新UI操作可直接在重写的run方法定义。
其实Runnable并没有创建什么线程，而是发送了一条消息，下面看源码：
```
public final boolean post(Runnable r)
    {
       return  sendMessageDelayed(getPostMessage(r), 0);
    }

  private static Message getPostMessage(Runnable r) {
//创建了一个Message对象
//创建Message对象可以new，也可以使用Message.obtain()方法；
//但是更建议使用obtain方法，因为Message内部维护了一个Message池用于Message的复用，避免使用new 重新分配内存。
        Message m = Message.obtain();
//将我们创建的Runable对象作为callback属性，赋值给了此message.
        m.callback = r;
//创建了一个Message对象
        return m;
    }

 public final boolean sendMessageDelayed(Message msg, long delayMillis)
    {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
    }

 public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessageAtTime() called with no mQueue");
            Log.w("Looper", e.getMessage(), e);
            return false;
        }
        return enqueueMessage(queue, msg, uptimeMillis);
    }

```
从上面的源码发现了吧？和send中的handler.sendMessage是一样的。

调用了sendMessageAtTime，然后调用了enqueueMessage方法，给msg.target赋值为handler，最终Handler将消息加入MessagQueue.

* 但是细心的你会发现，在使用Post方法时会将我们创建的Runable对象作为callback属性赋值给了此message
那么msg的callback和target都有值，那么会执行哪个呢？
我们已知回调发送消息的方法是：dispatchMessage()

*  处理Looper派发过来的消息：dispathMessage()方法

```
public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            handleMessage(msg);
        }
    }

    public void handleMessage(Message msg) {
    }


```
- 可以看到dispathMessage()方法里调用了 handleMessage()方法，但handleMessage()是一个空方法
- 因为Handler发送消息过来是希望进行一定的处理，至于怎么处理消息是该Handler最终控制的，所以我们在创建handler时需要通过复写handleMessage()方法从而实现我们需要的消息处理方式，然后根据msg.what标识进行消息处理。
- 这就是为什么我们在主线程中实例化Handler的时候需要重写handleMessage()

特别注意

在一个Android应用启动的时候，会创建一个主线程，即ActivityThread（也叫UI线程），在ActivityThread中有一个静态的main方法：应用程序的入口点

```
//一个进程会默认生成一个主线程
public static void main(String[] args) {
 ......
//主线程生成时自动通过prepareMainLooper方法为主线程创建一个Looper
//prepare()方法是用于在子线程中创建一个Looper对象，在子线程中是可以退出消息循环的：调用消息队列的quit方法
//Looper生成时会自动生成与之配套的消息队列
Looper.prepareMainLooper(); 
ActivityThread thread = new ActivityThread(); 
thread.attach(false);
 ...... 
//loop()方法开启消息循环
//主线程的消息循环是不允许被退出的
Looper.loop(); 
throw new RuntimeException("Main thread loop unexpectedly exited");
}


```
### 1.6.3 MessageQueue
用于存放Handler发送过来的消息，为了提高插入删除的效率，采用单链表的方式实现。
* MessageQueue入队
```
boolean enqueueMessage(Message msg, long when) {

    ......

    synchronized (this) {

        ......

        msg.markInUse();
        msg.when = when;
        Message p = mMessages;
        boolean needWake;
        if (p == null || when == 0 || when < p.when) {
            // New head, wake up the event queue if blocked.
            msg.next = p;
            mMessages = msg;
            needWake = mBlocked;
        } else {
            // Inserted within the middle of the queue.  Usually we don't have to wake
            // up the event queue unless there is a barrier at the head of the queue
            // and the message is the earliest asynchronous message in the queue.
            needWake = mBlocked && p.target == null && msg.isAsynchronous();
            Message prev;
            for (;;) {
                prev = p;
                p = p.next;
                if (p == null || when < p.when) {
                    break;
                }
                if (needWake && p.isAsynchronous()) {
                    needWake = false;
                }
            }
            msg.next = p; // invariant: p == prev.next
            prev.next = msg;
        }

        // We can assume mPtr != 0 because mQuitting is false.
        if (needWake) {
            nativeWake(mPtr);
        }
    }
    return true;
}


```

消息的入队（插入）过程

首先判断消息队列里有没有消息，没有的话则将当前插入的消息作为队头，并且这时消息队列如果处于等待状态的话则将其唤醒。
若是在中间插入，则根据Message创建的时间进行插入。

* MessageQueue出队
```
Message next() {

    ......

    int nextPollTimeoutMillis = 0;
    for (;;) {
        if (nextPollTimeoutMillis != 0) {
            Binder.flushPendingCommands();
        }
// nativePollOnce方法在native层，若是nextPollTimeoutMillis为-1，这时候消息队列处于等待状态。 　　
        nativePollOnce(ptr, nextPollTimeoutMillis);

        synchronized (this) {
            // Try to retrieve the next message.  Return if found.
            final long now = SystemClock.uptimeMillis();
            Message prevMsg = null;
            Message msg = mMessages;

            if (msg != null && msg.target == null) {
                // Stalled by a barrier.  Find the next asynchronous message in the queue.
                do {
                    prevMsg = msg;
                    msg = msg.next;
                } while (msg != null && !msg.isAsynchronous());
            }
//按照我们设置的时间取出消息
            if (msg != null) {
                if (now < msg.when) {
                    // Next message is not ready.  Set a timeout to wake up when it is ready.
                    nextPollTimeoutMillis = (int) Math.min(msg.when - now, Integer.MAX_VALUE);
                } else {
                    // Got a message.
                    mBlocked = false;
                    if (prevMsg != null) {
                        prevMsg.next = msg.next;
                    } else {
                        mMessages = msg.next;
                    }
                    msg.next = null;
                    if (DEBUG) Log.v(TAG, "Returning message: " + msg);
                    msg.markInUse();
                    return msg;
                }
            } else {
// 如果消息队列中没有消息，将nextPollTimeoutMillis设为-1，下次循环消息队列则处于等待状态
                nextPollTimeoutMillis = -1;
            }

           
//退出消息队列，返回null，这时候Looper中的消息循环也会终止。 
            if (mQuitting) {
                dispose();
                return null;
            }

            ......
        }

        .....
    }
}


```

# 2.Looper 死循环为什么不会导致应用卡死？
* 对于线程既然是一段可执行的代码，当可执行代码执行完成后，线程生命周期便该终止了，线程退出。而对于**主线程**，我们是绝不希望会被运行一段时间，自己就退出，那么**如何保证能一直存活呢**？

简单做法就是**可执行代码是能一直执行下去的，死循环便能保证不会被退出**，例如，binder线程也是采用死循环的方法，通过循环方式不同与Binder驱动进行读写操作，当然并非简单地死循环，无消息时会休眠。但这里可能又引发了另一个问题，既然是死循环又如何去处理其他事务呢？通过创建新线程的方式。真正会卡死主线程的操作是在回调方法onCreate/onStart/onResume等操作时间过长，会导致掉帧，甚至发生ANR，looper.loop本身不会导致应用卡死。

* 有相关代码为这个死循环准备了一个新线程去运转

事实上，会在进入死循环之前便创建了新binder线程，在代码ActivityThread.main()中：
```
public static void main(String[] args) {
        ....

        //创建Looper和MessageQueue对象，用于处理主线程的消息
        Looper.prepareMainLooper();

        //创建ActivityThread对象
        ActivityThread thread = new ActivityThread(); 

        //建立Binder通道 (创建新线程)
        thread.attach(false);

        Looper.loop(); //消息循环运行
        throw new RuntimeException("Main thread loop unexpectedly exited");
    }


```
thread.attach(false)；便会创建一个Binder线程（具体是指ApplicationThread，Binder的服务端，用于接收系统服务AMS发送来的事件），该Binder线程通过Handler将Message发送给主线程，具体过程可查看 startService流程分析，这里不展开说，简单说Binder用于进程间通信，采用C/S架构。

ActivityThread实际上并非线程，不像HandlerThread类，ActivityThread并没有真正继承Thread类，只是往往运行在主线程，该人以线程的感觉，其实承载ActivityThread的主线程就是由Zygote fork而创建的进程。

* 主线程的死循环一直运行是不是特别消耗CPU资源呢？

其实不然，这里就涉及到Linux pipe/epoll机制，简单说就是在主线程的MessageQueue没有消息时，便阻塞在loop的queue.next()中的nativePollOnce()方法里，详情见Android消息机制1-Handler(Java层)，此时主线程会释放CPU资源进入休眠状态，直到下个消息到达或者有事务发生，通过往pipe管道写端写入数据来唤醒主线程工作。这里采用的epoll机制，是一种IO多路复用机制，可以同时监控多个描述符，当某个描述符就绪(读或写就绪)，则立刻通知相应程序进行读或写操作，本质同步I/O，即读写是阻塞的。 所以说，主线程大多数时候都是处于休眠状态，并不会消耗大量CPU资源。

* Activity的生命周期是怎么实现在死循环体外能够执行起来的？

ActivityThread的内部类H继承于Handler，通过handler消息机制，简单说Handler机制用于同一个进程的线程间通信。**Activity的生命周期都是依靠主线程的Looper.loop，当收到不同Message时则采用相应措施**：在H.handleMessage(msg)方法中，根据接收到不同的msg，执行相应的生命周期。  

比如收到msg=H.LAUNCH_ACTIVITY，则调用ActivityThread.handleLaunchActivity()方法，最终会通过反射机制，创建Activity实例，然后再执行Activity.onCreate()等方法；    再比如收到msg=H.PAUSE_ACTIVITY，则调用ActivityThread.handlePauseActivity()方法，最终会执行Activity.onPause()等方法。

上述过程，我只挑核心逻辑讲，真正该过程远比这复杂。主线程的消息又是哪来的呢？当然是App进程中的其他线程通过Handler发送给主线程，请看接下来的内容：

![image](https://pic1.zhimg.com/7fb8728164975ac86a2b0b886de2b872_r.jpg)


**system_server进程是系统进程**，java framework框架的核心载体，里面运行了大量的系统服务，比如这里提供ApplicationThreadProxy（简称ATP），ActivityManagerService（简称AMS），这个两个服务都运行在system_server进程的不同线程中，由于ATP和AMS都是基于IBinder接口，都是binder线程，binder线程的创建与销毁都是由binder驱动来决定的。

**App进程则是我们常说的应用程**序，主线程主要负责Activity/Service等组件的生命周期以及UI相关操作都运行在这个线程； 另外，每个App进程中至少会有两个binder线程 ApplicationThread(简称AT)和ActivityManagerProxy（简称AMP），除了图中画的线程，其中还有很多线程，比如signal catcher线程等，这里就不一一列举。

Binder用于不同进程之间通信，由一个进程的Binder客户端向另一个进程的服务端发送事务，比如图中线程2向线程4发送事务；而handler用于同一个进程中不同线程的通信，比如图中线程4向主线程发送消息。结合图说说Activity生命周期，比如暂停Activity，流程如下：

- 线程1的AMS中调用线程2的ATP；（由于同一个进程的线程间资源共享，可以相互直接调用，但需要注意多线程并发问题）
- 
- 线程2通过binder传输到App进程的线程4；
- 
- 线程4通过handler消息机制，将暂停Activity的消息发送给主线程；
- 
- 主线程在looper.loop()中循环遍历消息，当收到暂停Activity的消息时，便将消息分发给ActivityThread.H.handleMessage()方法，再经过方法的调用，最后便会调用到Activity.onPause()，当onPause()处理完后，继续循环loop下去。


简单一句话是：Android应用程序的主线程在进入消息循环过程前，会在内部创建一个Linux管道（Pipe），这个管道的作用是使得Android应用程序主线程在消息队列为空时可以进入空闲等待状态，并且使得当应用程序的消息队列有消息需要处理时唤醒应用程序的主线程。

# 3.主线程的消息循环机制是什么（死循环如何处理其它事务）？
理解上个问题就可以了。
说白了主线程还是在loop里面进行死循环，但是会在进入死循环之前便创建了新binder线程，在代码ActivityThread.main()中：同时借助自身的handler接收来自系统进程的线程的通信，这样loop取出这些通信从而进行例如onCreate等等方法。

从消息队列中取消息可能会阻塞，取到消息会做出相应的处理。如果某个消息处理时间过长，就可能会影响UI线程的刷新速率，造成卡顿的现象。

ActivityThread通过ApplicationThread和AMS进行进程间通讯，AMS以进程间通信的方式完成ActivityThread的请求后会回调ApplicationThread中的Binder方法，然后ApplicationThread会向H发送消息，H收到消息后会将ApplicationThread中的逻辑切换到ActivityThread中去执行，即切换到主线程中去执行，这个过程就是。主线程的消息循环模型

# 4.ActivityThread 的动力是什么？
其实承载ActivityThread的主线程就是由Zygote fork而创建的进程。

# 5.Handler 是如何能够线程切换
线程间是共享资源的。所以Handler处理不同线程问题就只要注意异步情况即可。 

通过上面的分析我们可以知道，当在A线程中创建handler的时候，同时创建了MessageQueue与Looper，Looper在A线程中调用loop进入一个无限的for循环从MessageQueue中取消息，当B线程调用handler发送一个message的时候，会通过msg.target.dispatchMessage(msg);将message插入到handler对应的MessageQueue中，Looper发现有message插入到MessageQueue中，便取出message执行相应的逻辑，因为Looper.loop()是在A线程中启动的，所以则回到了A线程，达到了从B线程切换到A线程的目的。

这里再引申出Handler的一些小知识点。 
Handler创建的时候会采用当前线程的Looper来构造消息循环系统，Looper在哪个线程创建，就跟哪个线程绑定，并且Handler是在他关联的Looper对应的线程中处理消息的

那么Handler内部如何获取到当前线程的Looper呢—–ThreadLocal。ThreadLocal可以在不同的线程中互不干扰的存储并提供数据，通过ThreadLocal可以轻松获取每个线程的Looper。当然需要注意的是①线程是默认没有Looper的，如果需要使用Handler，就必须为线程创建Looper。我们经常提到的主线程，也叫UI线程，它就是ActivityThread，②ActivityThread被创建时就会初始化Looper，这也是在主线程中默认可以使用Handler的原因。


# 6.系统为什么不允许在子线程中访问UI？
这是因为Android的UI控件不是线程安全的，如果在多线程中并发访问可能会导致UI控件处于不可预期的状态，那么为什么系统不对UI控件的访问加上锁机制呢？缺点有两个： 


```
①首先加上锁机制会让UI访问的逻辑变得复杂 
②锁机制会降低UI访问的效率，因为锁机制会阻塞某些线程的执行。 
所以最简单且高效的方法就是采用单线程模型来处理UI操作。
```
# 7.子线程有哪些更新UI的方法。
- 主线程中定义Handler，子线程通过mHandler发送消息，主线程Handler的handleMessage更新UI。
- 用Activity对象的runOnUiThread方法。
- 创建Handler，传入getMainLooper。
- View.post(Runnable r) 。

# 8.子线程中Toast，showDialog的使用方法。
我觉得大家还应该再翻翻源码，**就会发现，Toast.show并不是所谓的更新UI操作**!（｀｀｀逃）Toast,Handler,分析为什么抛异常。ActivityThread和ViewRootImpl分析到底什么叫子线程不能更新UI。Toast本质上是一个window，跟activity是平级的，checkThread只是Activity维护的View树的行为。**Toast使用的无所谓是不是主线程Handler，吐司操作的是window，不属于checkThread抛主线程不能更新UI异常的管理范畴**。它用Handler只是为了用队列和时间控制排队显示吐司。即使是子线程，先Looper.prepare,再show吐司，再Looper.loop一样可以吐出来，只不过loop操作会阻塞这个线程，没人这么玩罢了，都是让Toast用主线程的Handler，这个是在ActivityThread里初始化的，本来就是阻塞处理所有的UI交互逻辑。


```
public void show() {
    if (mNextView == null) {
        throw new RuntimeException("setView must have been called");
    }

    INotificationManager service = getService();
    String pkg = mContext.getOpPackageName();
    TN tn = mTN;
    tn.mNextView = mNextView;

    try {
        service.enqueueToast(pkg, tn, mDuration);
    } catch (RemoteException e) {
        // Empty
    }
}

private static class TN extends ITransientNotification.Stub {
    final Runnable mShow = new Runnable() {
        @Override
        public void run() {
            handleShow();
        }
    };

    final Runnable mHide = new Runnable() {
        @Override
        public void run() {
            handleHide();
            // Don't do this in handleHide() because it is also invoked by handleShow()
            mNextView = null;
        }
    };

    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    final Handler mHandler = new Handler();

    。。。。。代码省略。。。。。。
```



我相信聪明的你们应该看到了这里为什么错了，对，就是Handler不能再子线程里运行的 因为子线程没有创建Looper.prepare(); 所以就报错了。主线程不需要调用，是因为主线程已经默认帮你调用了。

可以看到一个Toast的创建需要依赖Handler。

第一种方法
```
new Thread(){
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            try {
                Toast.makeText(MainActivity.this,"ceshi",Toast.LENGTH_SHORT).show();
            }catch (Exception e) {
                Logger.e("error",e.toString());
            }
            Looper.loop();
        }
    }.start();
```
第二种方法就是
```
runOnUiThread(new Runnable() {
        @Override
        public void run() {
            Toast.makeText(MainActivity.this,"ceshi23333",Toast.LENGTH_SHORT).show();
        }
    });
    new Thread(){

    }.start();
```
第三种方法
```
Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        //这里写你的Toast代码
        }
    };

    new Thread(){
        @Override
        public void run() {
            super.run();
            mHandler.sendEmptyMessage(0);
        }
    }.start();
```

# 9.如何处理Handler 使用不当导致的内存泄露？
内存泄露出现的原因：当一个对象已经不再被使用时，本该被回收但却因为有另外一个正在使用的对象持有它的引用从而导致它不能被回收。
这就导致了内存泄漏。

## 9.1一般用法
Handler的一般用法 = 新建Handler子类（内部类） 、匿名Handler内部类
```
/** 
     * 方式1：新建Handler子类（内部类）
     */  
    public class MainActivity extends AppCompatActivity {

            public static final String TAG = "carson：";
            private Handler showhandler;

            // 主线程创建时便自动创建Looper & 对应的MessageQueue
            // 之后执行Loop()进入消息循环
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                //1. 实例化自定义的Handler类对象->>分析1
                //注：此处并无指定Looper，故自动绑定当前线程(主线程)的Looper、MessageQueue
                showhandler = new FHandler();

                // 2. 启动子线程1
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // a. 定义要发送的消息
                        Message msg = Message.obtain();
                        msg.what = 1;// 消息标识
                        msg.obj = "AA";// 消息存放
                        // b. 传入主线程的Handler & 向其MessageQueue发送消息
                        showhandler.sendMessage(msg);
                    }
                }.start();

                // 3. 启动子线程2
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // a. 定义要发送的消息
                        Message msg = Message.obtain();
                        msg.what = 2;// 消息标识
                        msg.obj = "BB";// 消息存放
                        // b. 传入主线程的Handler & 向其MessageQueue发送消息
                        showhandler.sendMessage(msg);
                    }
                }.start();

            }

            // 分析1：自定义Handler子类
            class FHandler extends Handler {

                // 通过复写handlerMessage() 从而确定更新UI的操作
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            Log.d(TAG, "收到线程1的消息");
                            break;
                        case 2:
                            Log.d(TAG, " 收到线程2的消息");
                            break;


                    }
                }
            }
        }

   /** 
     * 方式2：匿名Handler内部类
     */ 
     public class MainActivity extends AppCompatActivity {

        public static final String TAG = "carson：";
        private Handler showhandler;

        // 主线程创建时便自动创建Looper & 对应的MessageQueue
        // 之后执行Loop()进入消息循环
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //1. 通过匿名内部类实例化的Handler类对象
            //注：此处并无指定Looper，故自动绑定当前线程(主线程)的Looper、MessageQueue
            showhandler = new  Handler(){
                // 通过复写handlerMessage()从而确定更新UI的操作
                @Override
                public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                                Log.d(TAG, "收到线程1的消息");
                                break;
                            case 2:
                                Log.d(TAG, " 收到线程2的消息");
                                break;
                        }
                    }
            };

            // 2. 启动子线程1
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // a. 定义要发送的消息
                    Message msg = Message.obtain();
                    msg.what = 1;// 消息标识
                    msg.obj = "AA";// 消息存放
                    // b. 传入主线程的Handler & 向其MessageQueue发送消息
                    showhandler.sendMessage(msg);
                }
            }.start();

            // 3. 启动子线程2
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // a. 定义要发送的消息
                    Message msg = Message.obtain();
                    msg.what = 2;// 消息标识
                    msg.obj = "BB";// 消息存放
                    // b. 传入主线程的Handler & 向其MessageQueue发送消息
                    showhandler.sendMessage(msg);
                }
            }.start();

        }
}


```
上述例子虽可运行成功，但代码会出现严重警告：

警告的原因 = 该Handler类由于无设置为 静态类，从而导致了内存泄露
最终的内存泄露发生在Handler类的外部类：MainActivity类

![image](https://upload-images.jianshu.io/upload_images/944365-0360da991d92b36b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/583)

## 9.2原因
上述的Handler实例的消息队列有2个分别来自线程1、2的消息（分别 为延迟1s、6s）

* 在Handler消息队列 还有未处理的消息 / 正在处理消息时，消息队列中的Message持有Handler实例的引用

* 由于Handler = 非静态内部类 / 匿名内部类（2种使用方式），故又默认持有外部类的引用（即MainActivity实例），引用关系如下图

上述的引用关系会一直保持，直到Handler消息队列中的所有消息被处理完毕

![image](https://upload-images.jianshu.io/upload_images/944365-05300414b4aeaa8e.png?imageMogr2/auto-orient/)

在Handler消息队列 还有未处理的消息 / 正在处理消息时，此时若需销毁外部类MainActivity，但由于上述引用关系，垃圾回收器（GC）无法回收MainActivity，从而造成内存泄漏。

若出现 Handler的生命周期 > 外部类的生命周期 时（即 Handler消息队列 还有未处理的消息 / 正在处理消息 而 外部类需销毁时），将使得外部类无法被垃圾回收器（GC）回收，从而造成 内存泄露

## 9.3解决方案
从上面可看出，造成内存泄露的原因有2个关键条件：

- 存在“未被处理 / 正处理的消息 -> Handler实例 -> 外部类” 的引用关系
- Handler的生命周期 > 外部类的生命周期

### 解决方案1：静态内部类+弱引用
静态内部类 不默认持有外部类的引用，从而使得 “未被处理 / 正处理的消息 -> Handler实例 -> 外部类” 的引用关系 的引用关系 不复存在。

将Handler的子类设置成 静态内部类

同时，还可加上 使用WeakReference弱引用持有Activity实例
原因：弱引用的对象拥有短暂的生命周期。在垃圾回收器线程扫描时，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存
```
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "carson：";
    private Handler showhandler;

    // 主线程创建时便自动创建Looper & 对应的MessageQueue
    // 之后执行Loop()进入消息循环
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. 实例化自定义的Handler类对象->>分析1
        //注：
            // a. 此处并无指定Looper，故自动绑定当前线程(主线程)的Looper、MessageQueue；
            // b. 定义时需传入持有的Activity实例（弱引用）
        showhandler = new FHandler(this);

        // 2. 启动子线程1
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // a. 定义要发送的消息
                Message msg = Message.obtain();
                msg.what = 1;// 消息标识
                msg.obj = "AA";// 消息存放
                // b. 传入主线程的Handler & 向其MessageQueue发送消息
                showhandler.sendMessage(msg);
            }
        }.start();

        // 3. 启动子线程2
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // a. 定义要发送的消息
                Message msg = Message.obtain();
                msg.what = 2;// 消息标识
                msg.obj = "BB";// 消息存放
                // b. 传入主线程的Handler & 向其MessageQueue发送消息
                showhandler.sendMessage(msg);
            }
        }.start();

    }

    // 分析1：自定义Handler子类
    // 设置为：静态内部类
    private static class FHandler extends Handler{

        // 定义 弱引用实例
        private WeakReference<Activity> reference;

        // 在构造方法中传入需持有的Activity实例
        public FHandler(Activity activity) {
            // 使用WeakReference弱引用持有Activity实例
            reference = new WeakReference<Activity>(activity); }

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d(TAG, "收到线程1的消息");
                    break;
                case 2:
                    Log.d(TAG, " 收到线程2的消息");
                    break;


            }
        }
    }
}


```
### 解决方案2：当外部类结束生命周期时，清空Handler内消息队列
不仅使得 “未被处理 / 正处理的消息 -> Handler实例 -> 外部类” 的引用关系 不复存在，同时 使得  Handler的生命周期（即 消息存在的时期） 与 外部类的生命周期 同步

当 外部类（此处以Activity为例） 结束生命周期时（此时系统会调用onDestroy（）），清除 Handler消息队列里的所有消息（调用removeCallbacksAndMessages(null)）


```
@Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        // 外部类Activity生命周期结束时，同时清空消息队列 & 结束Handler生命周期
    }


```

为了保证Handler中消息队列中的所有消息都能被执行，此处推荐使用解决方案1解决内存泄露问题，即 静态内部类 + 弱引用的方式

# 10.讲解一下HandlerThread
## 10.1概述
回到我们的HandlerThread，大家一定听说过Looper、Handler、Message三者的关系（如果不了解，可以查看Android 异步消息处理机制 让你深入理解 Looper、Handler、Message三者关系），在我们的UI线程默默的为我们服务。其实我们可以借鉴UI线程Looper的思想，**搞个子线程，也通过Handler、Message通信，可以适用于很多场景**。

对了，我之前写过一篇博文Android Handler 异步消息处理机制的妙用 创建强大的图片加载类，这篇博文中就在子线程中创建了Looper，Handler，原理和HandlerThread一模一样，可惜当时我并不知道这个类，不过大家也可以当做HandlerThread应用场景进行学习。

## 10.2实例
假设我们要做一个股市数据实时更新的app，我们可以在网上找个第三方的股市数据接口，然后在我们的app中每隔1分钟（合适的时间）去更新数据，然后更新我们的UI即可。

```
package com.zhy.blogcodes.intentservice;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.zhy.blogcodes.R;


public class HandlerThreadActivity extends AppCompatActivity
{

    private TextView mTvServiceInfo;

    private HandlerThread mCheckMsgThread;
    private Handler mCheckMsgHandler;
    private boolean isUpdateInfo;

    private static final int MSG_UPDATE_INFO = 0x110;

    //与UI线程管理的handler
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_handler);

        //创建后台线程
        initBackThread();

        mTvServiceInfo = (TextView) findViewById(R.id.id_textview);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //开始查询
        isUpdateInfo = true;
        mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //停止查询
        isUpdateInfo = false;
        mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);

    }

    private void initBackThread()
    {
        mCheckMsgThread = new HandlerThread("check-message-coming");
        mCheckMsgThread.start();
        mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                checkForUpdate();
                if (isUpdateInfo)
                {
                    mCheckMsgHandler.sendEmptyMessageDelayed(MSG_UPDATE_INFO, 1000);
                }
            }
        };


    }

    /**
     * 模拟从服务器解析数据
     */
    private void checkForUpdate()
    {
        try
        {
            //模拟耗时
            Thread.sleep(1000);
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    String result = "实时更新中，当前大盘指数：<font color='red'>%d</font>";
                    result = String.format(result, (int) (Math.random() * 3000 + 1000));
                    mTvServiceInfo.setText(Html.fromHtml(result));
                }
            });

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //释放资源
        mCheckMsgThread.quit();
    }


}
```
## 10.3源码分析
我们轻轻的掀开HandlerThread的源码，还记得我们是通过
```
mCheckMsgThread = new HandlerThread("check-message-coming");
 mCheckMsgThread.start(); 
```

```
public class HandlerThread extends Thread {
    int mPriority;
    int mTid = -1;
    Looper mLooper;

    public HandlerThread(String name) {
        super(name);
        mPriority = Process.THREAD_PRIORITY_DEFAULT;
    }

    protected void onLooperPrepared() {
    }

    @Override
    public void run() {
        mTid = Process.myTid();
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        Process.setThreadPriority(mPriority);
        onLooperPrepared();
        Looper.loop();
        mTid = -1;
    }
```

看到了什么，其实我们就是初始化和启动了一个线程；然后我们看run()方法，可以看到该方法中调用了Looper.prepare()，Loop.loop();

prepare()呢，中创建了一个Looper对象，并且把该对象放到了该线程范围内的变量中（sThreadLocal），在Looper对象的构造过程中，初始化了一个MessageQueue，作为该Looper对象成员变量。

loop()就开启了，不断的循环从MessageQueue中取消息处理了，当没有消息的时候会阻塞，有消息的到来的时候会唤醒。如果你不明白我说的，参考上面推荐的文章。

接下来，我们创建了一个mCheckMsgHandler，是这么创建的
```
mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper())
```

```
public Looper getLooper() {
        if (!isAlive()) {
            return null;
        }

        // If the thread has been started, wait until the looper has been created.
        synchronized (this) {
            while (isAlive() && mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        return mLooper;
    }
```
mCheckMsgThread.getLooper()返回的就是我们在run方法中创建的mLooper。

那么Handler的构造呢，其实就是在Handler中持有一个指向该Looper.mQueue对象，当handler调用sendMessage方法时，其实就是往该mQueue中去插入一个message，然后Looper.loop()就会取出执行。

如果你够细心你会发现，run方法里面当mLooper创建完成后有个notifyAll()，getLooper()中有个wait()，这是为什么呢？因为的mLooper在一个线程中执行，而我们的handler是在UI线程初始化的，也就是说，我们必须等到mLooper创建完成，才能正确的返回getLooper();wait(),notify()就是为了解决这两个线程的同步问题。

# handler实现机制（很多细节需要关注：如线程如何建立和退出消息循环等等）