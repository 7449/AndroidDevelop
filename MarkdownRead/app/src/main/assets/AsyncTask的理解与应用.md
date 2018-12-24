## 线程

在Android当中，通常将线程分为两种，一种叫做Main Thread，除了Main Thread之外的线程都可称为Worker Thread。

当一个应用程序运行的时候，Android操作系统就会给该应用程序启动一个线程，这个线程就是我们的Main Thread，这个线程非常的重要，它主要用来加载我们的UI界面，完成系统和我们用户之间的交互，并将交互后的结果又展示给我们用户，所以Main Thread又被称为UI Thread。

Android系统默认不会给我们的应用程序组件创建一个额外的线程，所有的这些组件默认都是在同一个线程中运行。然而，某些时候当我们的应用程序需要完成一个耗时的操作的时候，例如访问网络或者是对数据库进行查询时，此时我们的UI Thread就会被阻塞。例如，当我们点击一个Button，然后希望其从网络中获取一些数据，如果此操作在UI Thread当中完成的话，当我们点击Button的时候，UI线程就会处于阻塞的状态，此时，我们的系统不会调度任何其它的事件，更糟糕的是，当我们的整个现场如果阻塞时间超过5秒钟(官方是这样说的)，这个时候就会出现 ANR (Application Not Responding)的现象，此时，应用程序会弹出一个框，让用户选择是否退出该程序。对于Android开发来说，出现ANR的现象是绝对不能被允许的。

另外，由于我们的Android UI控件是线程不安全的，所以我们不能在UI Thread之外的线程当中对我们的UI控件进行操作。因此在Android的多线程编程当中，我们有两条非常重要的原则必须要遵守：

 1. 绝对不能在UI Thread当中进行耗时的操作，不能阻塞我们的UI Thread
 2. 不能在UI Thread之外的线程当中操纵我们的UI元素

### 线程间通信
既然在Android当中有两条重要的原则要遵守，那么我们可能就有疑问了？我们既不能在主线程当中处理耗时的操作，又不能在工作线程中来访问我们的UI控件，那么我们比如从网络中要下载一张图片，又怎么能将其更新到UI控件上呢？这就关系到了我们的主线程和工作线程之间的通信问题了。在Android当中，提供了两种方式来解决线程直接的通信问题，一种是通过Handler的机制( [可以阅读-关于Handler的理解](http://crazyandcoder.github.io/2016/11/01/android%20%E5%85%B3%E4%BA%8E%E5%85%B3%E4%BA%8E%E5%AD%90%E7%BA%BF%E7%A8%8B%E6%9B%B4%E6%96%B0UI%E7%9A%84%E4%B8%80%E4%BA%9B%E4%BA%8B/)),还有一种就是今天要详细讲解的 AsyncTask 机制。

## AsyncTask

 
关于AsyncTask的解释，Google上面是这样说的：

> AsyncTask enables proper and easy use of the UI thread. This class allows you to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers.
> 
> An asynchronous task is defined by a computation that runs on a background thread and whose result is published on the UI thread. An asynchronous task is defined by 3 generic types, called Params, Progress and Result, and 4 steps, called onPreExecute, doInBackground, onProgressUpdate and onPostExecute.

大概意思就是说“它使创建异步任务变得更加简单，不再需要编写任务线程和Handler实例即可完成相同的任务。一个异步任务通常是在后台运行的计算等然后将结果发送到UI主线程中去。通常情况下，异步任务被定义为3中通用类型，分别为：参数、过程以及结果和4个步骤，分别为“onPreExecute、doInBackground、onProgressUpdate、onPostExecute””这就是关于异步任务的大概说明。

 怎么来理解AsyncTask呢？通俗一点来说，AsyncTask就相当于Android给我们提供了一个多线程编程的一个框架，其介于Thread和Handler之间，我们如果要定义一个AsyncTask，就需要定义一个类来继承AsyncTask这个抽象类，并实现其唯一的一个 doInBackgroud 抽象方法。要掌握AsyncTask，我们就必须要一个概念，总结起来就是: 3个泛型，4个步骤。

### 3个泛型
3个泛型指的是什么呢？我们来看看AsyncTask这个抽象类的定义，当我们定义一个类来继承AsyncTask这个类的时候，我们需要为其指定3个泛型参数：

```
public abstract class AsyncTask<Params, Progress, Result> 
```
**Params:** 这个泛型指定的是我们传递给异步任务执行时的参数的类型
**Progress:** 这个泛型指定的是我们的异步任务在执行的时候将执行的进度返回给UI线程的参数的类型
**Result:** 这个泛型指定的异步任务执行完后返回给UI线程的结果的类型

### 4个步骤
4个步骤：当我们执行一个异步任务的时候，其需要按照下面的4个步骤分别执行：

**1、onPreExecute():** 
这个方法是在执行异步任务之前的时候执行，并且是在UI Thread当中执行的，通常我们在这个方法里做一些UI控件的初始化的操作，例如弹出要给ProgressDialog。

**2、doInBackground(Params... params):** 
在onPreExecute()方法执行完之后，会马上执行这个方法，这个方法就是来处理异步任务的方法，Android操作系统会在后台的线程池当中开启一个worker thread来执行我们的这个方法，所以这个方法是在worker thread当中执行的，这个方法执行完之后就可以将我们的执行结果发送给我们的最后一个 onPostExecute 方法，在这个方法里，我们可以从网络当中获取数据等一些耗时的操作。

**3、onProgressUpdate(Progess... values):** 
这个方法也是在UI Thread当中执行的，我们在异步任务执行的时候，有时候需要将执行的进度返回给我们的UI界面，例如下载一张网络图片，我们需要时刻显示其下载的进度，就可以使用这个方法来更新我们的进度。这个方法在调用之前，我们需要在 doInBackground 方法中调用一个 publishProgress(Progress) 的方法来将我们的进度时时刻刻传递给 onProgressUpdate 方法来更新。

**4、onPostExecute(Result... result):** 
当我们的异步任务执行完之后，就会将结果返回给这个方法，这个方法也是在UI Thread当中调用的，我们可以将返回的结果显示在UI控件上。

> 为什么我们的AsyncTask抽象类只有一个 doInBackground 的抽象方法呢？？原因是，我们如果要做一个异步任务，我们必须要为其开辟一个新的Thread，让其完成一些操作，而在完成这个异步任务时，我可能并不需要弹出要给ProgressDialog，我并不需要随时更新我的ProgressDialog的进度条，我也并不需要将结果更新给我们的UI界面，所以除了 doInBackground 方法之外的三个方法，都不是必须有的，因此我们必须要实现的方法是 doInBackground 方法。

### 实例演示
接下来我们通过下载一张网络图片进行演示对于AsyncTask的使用。首先来看下效果：

![](http://img.blog.csdn.net/20170105103902499?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGlqaV94Yw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

其次，我们来了解一些相关代码。其实下载的代码原理很简单，就是通过流的方式转为字节数组，然后再转化为Bitmap而已。


```

//进度框显示

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("提示信息");
        progressDialog.setMessage("正在下载中，请稍后......");
        //    设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
        progressDialog.setCancelable(false);
        //    设置ProgressDialog样式为水平的样式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);



//下载类
public class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //    在onPreExecute()中我们让ProgressDialog显示出来
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;

            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                InputStream inputStream = conn.getInputStream();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    int fileLength = conn.getContentLength();
                    ByteArrayOutputStream outStread = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length = 0;
                    long total = 0;
                    while ((length = inputStream.read(buffer)) != -1) {
                        outStread.write(buffer, 0, length);
                        total += length;
                        if (fileLength > 0) {
                            publishProgress((int) (total * 100 / fileLength));
                        }
                    }

                    outStread.close();
                    inputStream.close();
                    byte[] data = outStread.toByteArray();
                    if (data != null) {
                        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    } else {
                        Toast.makeText(MainActivity.this, "Image error!", Toast.LENGTH_LONG).show();
                    }
                    return bitmap;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //    更新ProgressDialog的进度条
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            try {
                saveFile(bitmap, "netpic.jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }
    }


//在UI主线程中执行下载程序
String picUrl = "http://img3.imgtn.bdimg.com/it/u=2437337628,1430863508&fm=214&gp=0.jpg";

new MyAsyncTask().execute(picUrl);
```
**[详细代码请查看github-easy-net封装学习基本的网络请求库](https://github.com/crazyandcoder/easy-net/blob/master/app/src/main/java/com/liji/easynet/demo/DownloadActivity.java)**


----------


到这里基本上就结束了。这就是简单的运用AsyncTask进行UI线程和Work线程进行通信的基本方式。接下来我们就源码进行深入的研究关于AsyncTask的相关内容。

### 源码解读（基于API25）

首先我们从异步任务的起点execute开始分析：

```

//<p>This method must be invoked on the UI thread.
//必须在UI主线程中调用该方法。
@MainThread
    public final AsyncTask<Params, Progress, Result> execute(Params... params) {
        return executeOnExecutor(sDefaultExecutor, params);
    }
    
//跳转到executeOnExecutor方法
@MainThread
    public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec,
            Params... params) {
        if (mStatus != Status.PENDING) {
            switch (mStatus) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task has already been executed "
                            + "(a task can be executed only once)");
            }
        }

       //设置当前AsyncTask的状态为RUNNING
        mStatus = Status.RUNNING;

        //还是在UI主线程，这个时候可以进行一些初始化操作
        onPreExecute();

        mWorker.mParams = params;
        exec.execute(mFuture);

        return this;
    }
```

代码比较简单，其中出现了mWork和mFuture变量，接下来我们跟踪这两个变量进行研究。

**1、对于mWork变量**

```
private final WorkerRunnable<Params, Result> mWorker;
 
private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;
    }
//可以看到是Callable的子类，且包含一个mParams用于保存我们传入的参数，接下来看看 mWork的初始化操作
//这是在AsyncTask的构造函数中进行初始化的
mWorker = new WorkerRunnable<Params, Result>() {
            public Result call() throws Exception {
				//设置为true，下面要用到
                mTaskInvoked.set(true);
                Result result = null;
                try {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    //noinspection unchecked 
                    //这就是我们使用到的4个方法中的一个，获取处理结果
                    result = doInBackground(mParams);
                    Binder.flushPendingCommands();
                } catch (Throwable tr) {
                    mCancelled.set(true);
                    throw tr;
                } finally {

					//发送执行结果
                    postResult(result);
                }
                return result;
            }
        };
```
从上面源码我们可以分析出mWork在AsyncTask的构造函数中进行初始化，然后实现CallBack的call方法，进行一些设置，然后调用doInBackground方法，最后执行postResult(result)进行结果处理，接下来我们继续分析postResult(result)方法。

```
private Result postResult(Result result) {
        @SuppressWarnings("unchecked")
        Message message = getHandler().obtainMessage(MESSAGE_POST_RESULT,
                new AsyncTaskResult<Result>(this, result));
        message.sendToTarget();
        return result;
    }
```

我们看到了熟悉的异步消息处理，Handler和Message，发送一个消息，

```
msg.what=MESSAGE_POST_RESULT;
msg.obj=new AsyncTaskResult<Result>(this, result);
```

从上面的代码我们可以知道，既然handler已经发送出了消息的话，，那么肯定会存在一个Handler，并在某处进行消息的处理，我们来继续寻找一下这些内容：

```
//找到相关的Handler
private static Handler getHandler() {
        synchronized (AsyncTask.class) {
            if (sHandler == null) {
                sHandler = new InternalHandler();
            }
            return sHandler;
        }
    }


//消息处理
private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
        @Override
        public void handleMessage(Message msg) {
            AsyncTaskResult<?> result = (AsyncTaskResult<?>) msg.obj;
            switch (msg.what) {
                case MESSAGE_POST_RESULT:
                    // There is only one result
                    result.mTask.finish(result.mData[0]);
                    break;
                case MESSAGE_POST_PROGRESS:
                    result.mTask.onProgressUpdate(result.mData);
                    break;
            }
        }
    }

//消息处理完之后，设定状态为finished
  private void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {

			//执行4个方法中的最后一步，处理结果
            onPostExecute(result);
        }

		//设置最后的状态为结束finished
        mStatus = Status.FINISHED;
    }
```

**2、对于mFuture变量**

```
//申明变量
private final FutureTask<Result> mFuture;

//在AsyncTask的构造函数中进行变量的初始化
mFuture = new FutureTask<Result>(mWorker) {
            @Override
            protected void done() {
                try {
                    postResultIfNotInvoked(get());
                } catch (InterruptedException e) {
                    android.util.Log.w(LOG_TAG, e);
                } catch (ExecutionException e) {
                    throw new RuntimeException("An error occurred while executing doInBackground()",
                            e.getCause());
                } catch (CancellationException e) {
                    postResultIfNotInvoked(null);
                }
            }
        };


//查看postResultIfNotInvoked方法，参数是get()，get()表示获取mWorker的call的返回值，即Result。

    private void postResultIfNotInvoked(Result result) {
        final boolean wasTaskInvoked = mTaskInvoked.get();
        if (!wasTaskInvoked) {
            postResult(result);
        }
    }

//注意上面的mWork初始化时设置的变量值mTaskInvoked.set(true)，所以判断中一般都是wasTaskInvoked=true，所以基本不会执行
```

分析完了mWork和mFuture这两个变量，我们接着分析下面的代码：

> exec.execute(mFuture);

这个exec其实就是sDefaultExecutor，那么这个sDefaultExecutor是什么东西呢？

```

//sDefaultExecutor的定义
private static volatile Executor sDefaultExecutor = SERIAL_EXECUTOR;

//继续跟踪SERIAL_EXECUTOR
public static final Executor SERIAL_EXECUTOR = new SerialExecutor();

//SerialExecutor的定义
private static class SerialExecutor implements Executor {

		//维护一个数组队列
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
        Runnable mActive;

		//执行内容
        public synchronized void execute(final Runnable r) {
            //在队列的尾部插入一个任务task
            mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (mActive == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
	
			//取出队首的任务开始执行
            if ((mActive = mTasks.poll()) != null) {
			
				//开始执行任务
                THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }
```

那么这个THREAD_POOL_EXECUTOR又是什么东西呢？接着分析这个变量：

```
/**
     * An {@link Executor} that can be used to execute tasks in parallel.
     */
    public static final Executor THREAD_POOL_EXECUTOR;

    //线程池配置
    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }


    //变量设置
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;
```
以上就是过程分析，接下来我们来进一步总结说明具体的流程。

首先设置当前AsyncTask的状态为RUNNING，然后执行了**onPreExecute()**，当前依然在UI线程，所以我们可以在其中做一些准备工作。其次将我们传入的参数赋值给了mWorker.mParams ,mWorker为一个Callable的子类，且在内部的call()方法中，调用了**doInBackground**(mParams)，然后得到的返回值作为postResult的参数进行执行；postResult中通过sHandler发送消息，最终sHandler的handleMessage中完成**onPostExecute**的调用。最后执行exec.execute(mFuture)，mFuture为真正的执行任务的单元，将mWorker进行封装，然后由sDefaultExecutor交给线程池进行执行。

这里面我们涉及到了4个方法中的三个，那么还有一个方法：

```
//更新进度
@Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
```

那么这个方法是什么时候调用的的呢？我们在使用AsyncTask中的第三个方法doInBackground时在里面我们调用了一个传递进度的方法 publishProgress(int progress)，我们进入到该方法中查看一下：

```
//工作线程中执行该方法
@WorkerThread
    protected final void publishProgress(Progress... values) {
        if (!isCancelled()) {
         //通过Handler和Message异步消息机制进行UI线程和Work线程通信
            getHandler().obtainMessage(MESSAGE_POST_PROGRESS,
                    new AsyncTaskResult<Progress>(this, values)).sendToTarget();
        }
    }
```
publishProgress方法其实就是发送一个消息，

```
msg.what=MESSAGE_POST_PROGRESS//消息类型
msg.obj=new AsyncTaskResult<Progress>(this, values)//进度


//处理消息
private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
        @Override
        public void handleMessage(Message msg) {
            AsyncTaskResult<?> result = (AsyncTaskResult<?>) msg.obj;
            switch (msg.what) {
                case MESSAGE_POST_RESULT:
                    // There is only one result
                    result.mTask.finish(result.mData[0]);
                    break;
                case MESSAGE_POST_PROGRESS://处理进度消息
                
                    //调用onProgressUpdate方法显示进度
                    result.mTask.onProgressUpdate(result.mData);
                    break;
            }
        }
    }
```

这就很明朗了，四个方法都调用到了。以上便是AsyncTask所有的执行流程，通过源码分析可得AsyncTask的内部也是使用Handler+Message的方式进行消息传递和处理的。

### 关于AsyncTask的内幕



[1、深入理解AsyncTask的内幕，线程池引发的重大问题](http://blog.csdn.net/hitlion2008/article/details/7983449)




### 注意
Android6.0 谷歌把HttpClient相关的类移除了，所以如果继续使用的话，需要添加相关的jar包。

**1、对于AndroidStudio的添加方法是：**

```
在相应的module下的build.gradle中加入：
android {
    useLibrary 'org.apache.http.legacy'
}
```

**2、对于Eclipse的添加方法是：**

```
libs中加入
org.apache.http.legacy.jar
上面的jar包在：**\android-sdk-windows\platforms\android-23\optional下（需要下载android 6.0的SDK）
```

### 参考链接
[1、http://www.cnblogs.com/xiaoluo501395377/p/3430542.html](http://www.cnblogs.com/xiaoluo501395377/p/3430542.html)

[2、http://blog.csdn.net/liuhe688/article/details/6532519](http://blog.csdn.net/liuhe688/article/details/6532519)

[3、http://blog.csdn.net/lmj623565791/article/details/38614699](http://blog.csdn.net/lmj623565791/article/details/38614699)
