# 1.AsyncTask简介
## 1.1 基本概述
1. 一个Android 已封装好的轻量级异步类
属于抽象类，即使用时需 实现子类
2. 作用：

实现多线程
在工作线程中执行任务，如 耗时任务

异步通信、消息传递
实现工作线程 & 主线程（UI线程）之间的通信，即：将工作线程的执行结果传递给主线程，从而在主线程中执行相关的UI操作

3. 优点：

- 方便实现异步通信
不需使用 “任务线程（如继承Thread类） + Handler”的复杂组合
- 节省资源
采用线程池的缓存线程 + 复用线程，避免了频繁创建 & 销毁线程所带来的系统资源开销

## 1.2类和方法介绍
1. 类定义
AsyncTask类属于抽象类，即使用时需 实现子类
```
public abstract class AsyncTask<Params, Progress, Result> { 
 ... 
}

// 类中参数为3种泛型类型
// 整体作用：控制AsyncTask子类执行线程任务时各个阶段的返回类型
// 具体说明：
    // a. Params：开始异步任务执行时传入的参数类型，对应excute（）中传递的参数
    // b. Progress：异步任务执行过程中，返回下载进度值的类型
    // c. Result：异步任务执行完成后，返回的结果类型，与doInBackground()的返回值类型保持一致
// 注：
    // a. 使用时并不是所有类型都被使用
    // b. 若无被使用，可用java.lang.Void类型代替
    // c. 若有不同业务，需额外再写1个AsyncTask的子类
}

```
2. 核心方法

![image](https://upload-images.jianshu.io/upload_images/944365-153fb37764704129.png?imageMogr2/auto-orient/)

方法执行顺序如下

![image](https://upload-images.jianshu.io/upload_images/944365-31df794006c69621.png?imageMogr2/auto-orient/)

## 1.3使用步骤
AsyncTask的使用步骤有4个：

```
创建 AsyncTask 子类 & 根据需求实现核心方法
创建 AsyncTask子类的实例对象（即 任务实例）
手动调用execute(（）从而执行异步线程任务
```
具体介绍如下：

```
/**
  * 步骤1：创建AsyncTask子类
  * 注： 
  *   a. 继承AsyncTask类
  *   b. 为3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
  *   c. 根据需求，在AsyncTask子类内实现核心方法
  */

  private class MyTask extends AsyncTask<Params, Progress, Result> {

        ....

      // 方法1：onPreExecute（）
      // 作用：执行 线程任务前的操作
      // 注：根据需求复写
      @Override
      protected void onPreExecute() {
           ...
        }

      // 方法2：doInBackground（）
      // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
      // 注：必须复写，从而自定义线程任务
      @Override
      protected String doInBackground(String... params) {

            ...// 自定义的线程任务

            // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
             publishProgress(count);
              
         }

      // 方法3：onProgressUpdate（）
      // 作用：在主线程 显示线程任务执行的进度
      // 注：根据需求复写
      @Override
      protected void onProgressUpdate(Integer... progresses) {
            ...

        }

      // 方法4：onPostExecute（）
      // 作用：接收线程任务执行结果、将执行结果显示到UI组件
      // 注：必须复写，从而自定义UI操作
      @Override
      protected void onPostExecute(String result) {

         ...// UI操作

        }

      // 方法5：onCancelled()
      // 作用：将异步任务设置为：取消状态
      @Override
        protected void onCancelled() {
        ...
        }
  }

/**
  * 步骤2：创建AsyncTask子类的实例对象（即 任务实例）
  * 注：AsyncTask子类的实例必须在UI线程中创建
  */
  MyTask mTask = new MyTask();

/**
  * 步骤3：手动调用execute(Params... params) 从而执行异步线程任务
  * 注：
  *    a. 必须在UI线程中调用
  *    b. 同一个AsyncTask实例对象只能执行1次，若执行第2次将会抛出异常
  *    c. 执行任务中，系统会自动调用AsyncTask的一系列方法：onPreExecute() 、doInBackground()、onProgressUpdate() 、onPostExecute() 
  *    d. 不能手动调用上述方法
  */
  mTask.execute()；

```
## 1.4实例讲解

```
点击按钮 则 开启线程执行线程任务
显示后台加载进度
加载完毕后更新UI组件
期间若点击取消按钮，则取消加载
```
![image](https://upload-images.jianshu.io/upload_images/944365-23bdf9a3bc62e825.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/380)

主逻辑代码文件：MainActivity.java


```
public class MainActivity extends AppCompatActivity {

    // 线程变量
    MyTask mTask;

    // 主布局中的UI组件
    Button button,cancel; // 加载、取消按钮
    TextView text; // 更新的UI组件
    ProgressBar progressBar; // 进度条
    
    /**
     * 步骤1：创建AsyncTask子类
     * 注：
     *   a. 继承AsyncTask类
     *   b. 为3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
     *      此处指定为：输入参数 = String类型、执行进度 = Integer类型、执行结果 = String类型
     *   c. 根据需求，在AsyncTask子类内实现核心方法
     */
    private class MyTask extends AsyncTask<String, Integer, String> {

        // 方法1：onPreExecute（）
        // 作用：执行 线程任务前的操作
        @Override
        protected void onPreExecute() {
            text.setText("加载中");
            // 执行前显示提示
        }


        // 方法2：doInBackground（）
        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        // 此处通过计算从而模拟“加载进度”的情况
        @Override
        protected String doInBackground(String... params) {

            try {
                int count = 0;
                int length = 1;
                while (count<99) {

                    count += length;
                    // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                    publishProgress(count);
                    // 模拟耗时任务
                    Thread.sleep(50);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        // 方法3：onProgressUpdate（）
        // 作用：在主线程 显示线程任务执行的进度
        @Override
        protected void onProgressUpdate(Integer... progresses) {

            progressBar.setProgress(progresses[0]);
            text.setText("loading..." + progresses[0] + "%");

        }

        // 方法4：onPostExecute（）
        // 作用：接收线程任务执行结果、将执行结果显示到UI组件
        @Override
        protected void onPostExecute(String result) {
            // 执行完毕后，则更新UI
            text.setText("加载完毕");
        }

        // 方法5：onCancelled()
        // 作用：将异步任务设置为：取消状态
        @Override
        protected void onCancelled() {

            text.setText("已取消");
            progressBar.setProgress(0);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 绑定UI组件
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.cancel);
        text = (TextView) findViewById(R.id.text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        /**
         * 步骤2：创建AsyncTask子类的实例对象（即 任务实例）
         * 注：AsyncTask子类的实例必须在UI线程中创建
         */
        mTask = new MyTask();

        // 加载按钮按按下时，则启动AsyncTask
        // 任务完成后更新TextView的文本
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 步骤3：手动调用execute(Params... params) 从而执行异步线程任务
                 * 注：
                 *    a. 必须在UI线程中调用
                 *    b. 同一个AsyncTask实例对象只能执行1次，若执行第2次将会抛出异常
                 *    c. 执行任务中，系统会自动调用AsyncTask的一系列方法：onPreExecute() 、doInBackground()、onProgressUpdate() 、onPostExecute()
                 *    d. 不能手动调用上述方法
                 */
                mTask.execute();
            }
        });

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消一个正在执行的任务,onCancelled方法将会被调用
                mTask.cancel(true);
            }
        });

    }

}
```
## 1.5 使用时的注意点
在使用AsyncTask时有一些问题需要注意的：

1. 生命周期
* 结论：AsyncTask不与任何组件绑定生命周期

使用建议
在Activity 或 Fragment中使用 AsyncTask时，最好在Activity 或 Fragment的onDestory（）调用 cancel(boolean)；

2. 内存泄漏
* 结论：若AsyncTask被声明为Activity的非静态内部类，当Activity需销毁时，会因AsyncTask保留对Activity的引用而导致Activity无法被回收，最终引起内存泄露

使用建议
AsyncTask应被声明为Activity的静态内部类

3. 线程任务执行结果 丢失
* 结论当Activity重新创建时（屏幕旋转 / Activity被意外销毁时后恢复），之前运行的AsyncTask（非静态的内部类）持有的之前Activity引用已无效，故复写的onPostExecute()将不生效，即无法更新UI操作

使用建议
在Activity恢复时的对应方法 重启 任务线程

# 2.AsyncTask的原理 及其源码分析
![image](https://upload-images.jianshu.io/upload_images/944365-d4a549ab6b57553b.png?imageMogr2/auto-orient/)

## 2.1工作原理
### 2.1.1 了解线程
![image](https://upload-images.jianshu.io/upload_images/944365-65237c4d3f7ab1fb.png?imageMogr2/auto-orient/)

![image](https://upload-images.jianshu.io/upload_images/944365-1ba1ba7c63d7acab.png?imageMogr2/auto-orient/)

### 2.1.2 具体原理介绍

AsyncTask的实现原理 = 线程池 + Handler

其中：线程池用于线程调度、复用 & 执行任务；Handler 用于异步通信

其内部封装了2个线程池 + 1个Handler，具体介绍如下：

![image](https://upload-images.jianshu.io/upload_images/944365-7ab79c7e114c842b.png?imageMogr2/auto-orient/)

## 2.2源码分析
本次源码分析将根据 AsyncTask的使用步骤讲解

- 创建 AsyncTask 子类 & 根据需求实现核心方法
- 创建 AsyncTask子类的实例对象（即 任务实例）
- 手动调用execute(（）从而执行异步线程任务

```
/**
  * 步骤1：创建AsyncTask子类
  * 注： 
  *   a. 继承AsyncTask类
  *   b. 为3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
  *   c. 根据需求，在AsyncTask子类内实现核心方法
  */

  private class MyTask extends AsyncTask<Params, Progress, Result> {

        ....

      // 方法1：onPreExecute（）
      // 作用：执行 线程任务前的操作
      // 注：根据需求复写
      @Override
      protected void onPreExecute() {
           ...
        }

      // 方法2：doInBackground（）
      // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
      // 注：必须复写，从而自定义线程任务
      @Override
      protected String doInBackground(String... params) {

            ...// 自定义的线程任务

            // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
             publishProgress(count);
              
         }

      // 方法3：onProgressUpdate（）
      // 作用：在主线程 显示线程任务执行的进度
      // 注：根据需求复写
      @Override
      protected void onProgressUpdate(Integer... progresses) {
            ...

        }

      // 方法4：onPostExecute（）
      // 作用：接收线程任务执行结果、将执行结果显示到UI组件
      // 注：必须复写，从而自定义UI操作
      @Override
      protected void onPostExecute(String result) {

         ...// UI操作

        }

      // 方法5：onCancelled()
      // 作用：将异步任务设置为：取消状态
      @Override
        protected void onCancelled() {
        ...
        }
  }

/**
  * 步骤2：创建AsyncTask子类的实例对象（即 任务实例）
  * 注：AsyncTask子类的实例必须在UI线程中创建
  */
  MyTask mTask = new MyTask();

/**
  * 步骤3：手动调用execute(Params... params) 从而执行异步线程任务
  * 注：
  *    a. 必须在UI线程中调用
  *    b. 同一个AsyncTask实例对象只能执行1次，若执行第2次将会抛出异常
  *    c. 执行任务中，系统会自动调用AsyncTask的一系列方法：onPreExecute() 、doInBackground()、onProgressUpdate() 、onPostExecute() 
  *    d. 不能手动调用上述方法
  */
  mTask.execute()；

```


### 2.2.1步骤1：创建AsyncTask子类
在该步骤中，只需知道 “该类中复写的方法将在后续源码中调用” 即可


### 2.2.2步骤2：创建AsyncTask子类的实例对象（即 任务实例）

```
/**
  * 具体使用
  */
  MyTask mTask = new MyTask();

/**
  * 源码分析：AsyncTask的构造函数
  */
  public AsyncTask() {
        // 1. 初始化WorkerRunnable变量 = 一个可存储参数的Callable对象 ->>分析1
        mWorker = new WorkerRunnable<Params, Result>() {
            // 在任务执行线程池中回调：THREAD_POOL_EXECUTOR.execute（）
            // 下面会详细讲解
            public Result call() throws Exception {

                // 添加线程的调用标识
                mTaskInvoked.set(true); 

                Result result = null;
                try {
                    // 设置线程的优先级
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    
                    // 执行异步操作 = 耗时操作
                    // 即 我们使用过程中复写的耗时任务
                    result = doInBackground(mParams);

                    Binder.flushPendingCommands();
                } catch (Throwable tr) {
                    
                    mCancelled.set(true);// 若运行异常,设置取消的标志
                    throw tr;
                } finally {
                    
                    // 把异步操作执行的结果发送到主线程
                    // 从而更新UI，下面会详细讲解
                    postResult(result); 
                }
                return result;
            }
        };

        // 2. 初始化FutureTask变量 = 1个FutureTask ->>分析2
        mFuture = new FutureTask<Result>(mWorker) {

            // done（）简介：FutureTask内的Callable执行完后的调用方法
            // 作用：复查任务的调用、将未被调用的任务的结果通过InternalHandler传递到UI线程
            @Override
            protected void done() {
                try {

                    // 在执行完任务后检查,将没被调用的Result也一并发出 ->>分析3
                    postResultIfNotInvoked(get());

                } catch (InterruptedException e) {
                    android.util.Log.w(LOG_TAG, e);
                } catch (ExecutionException e) {
                    throw new RuntimeException("An error occurred while executing doInBackground()",
                            e.getCause());
                } catch (CancellationException e) {

                    //若 发生异常,则将发出null
                    postResultIfNotInvoked(null);
                }
            }
        };
    }

/**
  * 分析1：WorkerRunnable类的构造函数
  */
  private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        // 此处的Callable也是任务；
        // 与Runnable的区别：Callable<T>存在返回值 = 其泛型
        Params[] mParams;
    }

/**
  * 分析2：FutureTask类的构造函数
  * 定义：1个包装任务的包装类
  * 注：内部包含Callable<T> 、增加了一些状态标识 & 操作Callable<T>的接口
  */
  public FutureTask(Callable<V> callable) {
        if (callable == null)
            throw new NullPointerException();
        this.callable = callable;
        this.state = NEW;      
    }
    // 回到调用原处

/**
  * 分析3：postResultIfNotInvoked()
  */
  private void postResultIfNotInvoked()(Result result) {
        // 取得任务标记
        final boolean wasTaskInvoked = mTaskInvoked.get();

        // 若任务无被执行,将未被调用的任务的结果通过InternalHandler传递到UI线程
        if (!wasTaskInvoked) {
            postResult(result);
        }
    }

```
创建了1个WorkerRunnable类 的实例对象 & 复写了call()方法

创建了1个FutureTask类 的实例对象 & 复写了 done()

### 2.2.3 步骤3：手动调用execute(Params... params)

```
/**
  * 具体使用
  */
  mTask.execute()；

/**
  * 源码分析：AsyncTask的execute()
  */
  public final AsyncTask<Params, Progress, Result> execute(Params... params) {

        return executeOnExecutor(sDefaultExecutor, params);
        // ->>分析1

    }

 /**
  * 分析1：executeOnExecutor(sDefaultExecutor, params)
  * 参数说明：sDefaultExecutor = 任务队列 线程池类（SerialExecutor）的对象
  */
  public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec,Params... params) {

        // 1. 判断 AsyncTask 当前的执行状态
        // PENDING = 初始化状态
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

        // 2. 将AsyncTask状态设置为RUNNING状态
        mStatus = Status.RUNNING;

        // 3. 主线程初始化工作
        onPreExecute();

        // 4. 添加参数到任务中
        mWorker.mParams = params;

        // 5. 执行任务
        // 此处的exec = sDefaultExecutor = 任务队列 线程池类（SerialExecutor）的对象
        // ->>分析2
        exec.execute(mFuture);
        return this;
    }

/**
  * 分析2：exec.execute(mFuture)
  * 说明：属于任务队列 线程池类（SerialExecutor）的方法
  */
  private static class SerialExecutor implements Executor {
        // SerialExecutor = 静态内部类
        // 即 是所有实例化的AsyncTask对象公有的

        // SerialExecutor 内部维持了1个双向队列；
        // 容量根据元素数量调节
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
        Runnable mActive;

        // execute（）被同步锁synchronized修饰
        // 即说明：通过锁使得该队列保证AsyncTask中的任务是串行执行的
        // 即 多个任务需1个个加到该队列中；然后 执行完队列头部的再执行下一个，以此类推
        public synchronized void execute(final Runnable r) {
            // 将实例化后的FutureTask类 的实例对象传入
            // 即相当于：向队列中加入一个新的任务
            mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();->>分析3
                    }
                }
            });
            // 若当前无任务执行，则去队列中取出1个执行
            if (mActive == null) {
                scheduleNext();
            }
        }
        // 分析3
        protected synchronized void scheduleNext() {
            // 1. 取出队列头部任务
            if ((mActive = mTasks.poll()) != null) {

                // 2. 执行取出的队列头部任务
                // 即 调用执行任务线程池类（THREAD_POOL_EXECUTOR）->>继续往下看
                THREAD_POOL_EXECUTOR.execute(mActive);
                
            }
        }
    }

```
* 执行任务前，通过 任务队列 线程池类（SerialExecutor）将任务按顺序放入到队列中；

通过同步锁 修饰execute（）从而保证AsyncTask中的任务是串行执行的

* 之后的线程任务执行是 通过任务线程池类（THREAD_POOL_EXECUTOR） 进行的。

继续往下分析：THREAD_POOL_EXECUTOR.execute（）

```
/**
  * 源码分析：THREAD_POOL_EXECUTOR.execute（）
  * 说明：
  *     a. THREAD_POOL_EXECUTOR实际上是1个已配置好的可执行并行任务的线程池
  *     b. 调用THREAD_POOL_EXECUTOR.execute（）实际上是调用线程池的execute()去执行具体耗时任务
  *     c. 而该耗时任务则是步骤2中初始化WorkerRunnable实例对象时复写的call（）
  * 注：下面先看任务执行线程池的线程配置过程，看完后请回到步骤2中的源码分析call（）
  */

    // 步骤1：参数设置
        //获得当前CPU的核心数
        private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
        //设置线程池的核心线程数2-4之间,但是取决于CPU核数
        private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
        //设置线程池的最大线程数为 CPU核数*2+1
        private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
        //设置线程池空闲线程存活时间30s
        private static final int KEEP_ALIVE_SECONDS = 30;

        //初始化线程工厂
        private static final ThreadFactory sThreadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
            }
        };

        //初始化存储任务的队列为LinkedBlockingQueue 最大容量为128
        private static final BlockingQueue<Runnable> sPoolWorkQueue =
                new LinkedBlockingQueue<Runnable>(128);

    // 步骤2： 根据参数配置执行任务线程池，即 THREAD_POOL_EXECUTOR
    public static final Executor THREAD_POOL_EXECUTOR;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        // 设置核心线程池的 超时时间也为30s
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    // 请回到步骤2中的源码分析call（）


```
至此，我们回到步骤2中的源码分析call（）
```
/**
  * 步骤2的源码分析：AsyncTask的构造函数
  */
    public AsyncTask() {
        // 1. 初始化WorkerRunnable变量 = 一个可存储参数的Callable对象
        mWorker = new WorkerRunnable<Params, Result>() {

            public Result call() throws Exception {

                // 添加线程的调用标识
                mTaskInvoked.set(true); 

                Result result = null;
                try {
                    // 设置线程的优先级
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    
                    // 执行异步操作 = 耗时操作
                    // 即 我们使用过程中复写的耗时任务
                    result = doInBackground(mParams);

                    Binder.flushPendingCommands();
                } catch (Throwable tr) {
                    
                    mCancelled.set(true);// 若运行异常,设置取消的标志
                    throw tr;
                } finally {
                    
                    // 把异步操作执行的结果发送到主线程
                    // 从而更新UI ->>分析1
                    postResult(result); 
                }
                return result;
            }
        };

        .....// 省略
    }
/**
  * 分析1：postResult(result)
  */
   private Result postResult(Result result) {

        @SuppressWarnings("unchecked")

        // 创建Handler对象 ->> 源自InternalHandler类—>>分析2
        Message message = getHandler().obtainMessage(MESSAGE_POST_RESULT,
                new AsyncTaskResult<Result>(this, result));
        // 发送消息到Handler中
        message.sendToTarget();
        return result;

    }


/**
  * 分析2：InternalHandler类
  */
    private static class InternalHandler extends Handler {

        // 构造函数
        public InternalHandler() {
            super(Looper.getMainLooper());
            // 获取的是主线程的Looper()
            // 故 AsyncTask的实例创建 & execute（）必须在主线程使用
        }

        @Override
        public void handleMessage(Message msg) {

            AsyncTaskResult<?> result = (AsyncTaskResult<?>) msg.obj;

            switch (msg.what) {
                // 若收到的消息 = MESSAGE_POST_RESULT
                // 则通过finish() 将结果通过Handler传递到主线程
                case MESSAGE_POST_RESULT:
                    result.mTask.finish(result.mData[0]); ->>分析3
                    break;

                // 若收到的消息 = MESSAGE_POST_PROGRESS
                // 则回调onProgressUpdate()通知主线程更新进度的操作
                case MESSAGE_POST_PROGRESS:
                    result.mTask.onProgressUpdate(result.mData);
                    break;
            }
        }
    }
/**
  * 分析3：result.mTask.finish(result.mData[0])
  */
  private void finish(Result result) {
        // 先判断是否调用了Cancelled()
            // 1. 若调用了则执行我们复写的onCancelled（）
            // 即 取消任务时的操作
            if (isCancelled()) {
                onCancelled(result);
            } else {

            // 2. 若无调用Cancelled()，则执行我们复写的onPostExecute(result)
            // 即更新UI操作
                onPostExecute(result);
            }
            // 注：不管AsyncTask是否被取消，都会将AsyncTask的状态变更为：FINISHED
            mStatus = Status.FINISHED;
        }


```
## 2.3 总结
1. 任务线程池类（THREAD_POOL_EXECUTOR）实际上是1个已配置好的可执行并行任务的线程池
1. 调用THREAD_POOL_EXECUTOR.execute（）实际上是调用线程池的execute()去执行具体耗时任务
1. 而该耗时任务则是步骤2中初始化 WorkerRunnable实例对象时复写的call（）内容
1. 在call（）方法里，先调用 我们复写的doInBackground(mParams)执行耗时操作
1. 再调用postResult(result)， 通过 InternalHandler 类 将任务消息传递到主线程；根据消息标识（MESSAGE_POST_RESULT）判断，最终通过finish(）调用我们复写的onPostExecute(result)，从而实现UI更新操作

![image](https://upload-images.jianshu.io/upload_images/944365-0e28380a923a3a9e.png?imageMogr2/auto-orient/)

![image](https://upload-images.jianshu.io/upload_images/944365-3944b7ca7400b082.png?imageMogr2/auto-orient/)


# AsyncTask内部实现，关注默认线程池是一个串行的线程池
1. 上述提及的SerialExecutor就是任务线程池，关注他如何实现串行工作，为什么是串行的，以及在AsyncTask中的用处。

# 多线程（关于AsyncTask缺陷引发的思考）









