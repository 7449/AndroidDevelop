# 1.Thread类的sleep()方法和对象的wait()方法都可以让线程暂停执行，它们有什么区别?
1. sleep()方法（休眠）是线程类（Thread）的静态方法，调用此方法会让当前线程暂停执行指定的时间，将执行机会（CPU）让给其他线程，但是对象的锁依然保持，因此休眠时间结束后会自动恢复（线程回到就绪状态，请参考第66题中的线程状态转换图）。
2. wait()是Object类的方法，调用对象的wait()方法导致当前线程放弃对象的锁（线程暂停执行），进入对象的等待池（wait pool），只有调用对象的notify()方法（或notifyAll()方法）时才能唤醒等待池中的线程进入等锁池（lock pool），如果线程重新获得对象的锁就可以进入就绪状态。

# 2.线程的sleep()方法和yield()方法有什么区别？
* sleep()方法给其他线程运行机会时不考虑线程的优先级，因此会给低优先级的线程以运行的机会；yield()方法只会给相同优先级或更高优先级的线程以运行的机会；
* 线程执行sleep()方法后转入阻塞（blocked）状态，而执行yield()方法后转入就绪（ready）状态；
* sleep()方法声明抛出InterruptedException，而yield()方法没有声明任何异常；
* sleep()方法比yield()方法（跟操作系统CPU调度相关）具有更好的可移植性。

# 3.当一个线程进入一个对象的synchronized方法A之后，其它线程是否可进入此对象的synchronized方法B？
不能。其它线程只能访问该对象的非同步方法，同步方法则不能进入。因为非静态方法上的synchronized修饰符要求执行方法时要获得对象的锁，如果已经进入A方法说明对象锁已经被取走，那么试图进入B方法的线程就只能在等锁池（注意不是等待池哦）中等待对象的锁。

# 4.请说出与线程同步以及线程调度相关的方法。


# 5.Java并发编程的一些思考和理解
1. 在Java 5以前，可以用synchronized关键字来实现锁的功能，它可以用在代码块和方法上，表示在执行整个代码块或方法之前线程必须取得合适的锁。对于类的非静态方法（成员方法）而言，这意味这要取得对象实例的锁，对于类的静态方法（类方法）而言，要取得类的Class对象的锁，对于同步代码块，程序员可以指定要取得的是那个对象的锁。 
不管是同步代码块还是同步方法，每次只有一个线程可以进入，如果其他线程试图进入（不管是同一同步块还是不同的同步块），JVM会将它们挂起（放入到等锁池中）。这种结构在并发理论中称为临界区（critical section）。
2. 在JVM内部，为了提高效率，同时运行的每个线程都会有它正在处理的数据的缓存副本，当我们使用synchronzied进行同步的时候，真正被同步的是在不同线程中表示被锁定对象的内存块（副本数据会保持和主内存的同步，现在知道为什么要用同步这个词汇了吧），简单的说就是在同步块或同步方法执行完后，对被锁定的对象做的任何修改要在释放锁之前写回到主内存中；在进入同步块得到锁之后，被锁定对象的数据是从主内存中读出来的，持有锁的线程的数据副本一定和主内存中的数据视图是同步的 。
3. 在Java最初的版本中，就有一个叫volatile的关键字，它是一种简单的同步的处理机制，因为被volatile修饰的变量遵循以下规则：

* 变量的值在使用之前总会从主内存中再读取出来。
* 对变量值的修改总会在完成之后写回到主内存中。

使用volatile关键字可以在多线程环境下预防编译器不正确的优化假设（编译器可能会将在一个线程中值不会发生改变的变量优化成常量），但只有修改时不依赖当前状态（读取时的值）的变量才应该声明为volatile变量。 

不变模式也是并发编程时可以考虑的一种设计。让对象的状态是不变的，如果希望修改对象的状态，就会创建对象的副本并将改变写入副本而不改变原来的对象，这样就不会出现状态不一致的情况，因此不变对象是线程安全的。Java中我们使用频率极高的String类就采用了这样的设计。如果对不变模式不熟悉，可以阅读阎宏博士的《Java与模式》一书的第34章。说到这里你可能也体会到final关键字的重要意义了。

4. Java5的并发编程感谢Doug Lea在Java 5中提供了他里程碑式的杰作java.util.concurrent包，它的出现让Java的并发编程有了更多的选择和更好的工作方式。Doug Lea的杰作主要包括以下内容：

```
更好的线程安全的容器
线程池和相关的工具类
可选的非阻塞解决方案
显示的锁和信号量机制
```
* 原子类

Java 5中的java.util.concurrent包下面有一个atomic子包，其中有几个以Atomic打头的类，例如AtomicInteger和AtomicLong。它们利用了现代处理器的特性，可以用非阻塞的方式完成原子操作

* 显示锁
基于synchronized关键字的锁机制有以下问题：


```
锁只有一种类型，而且对所有同步操作都是一样的作用
锁只能在代码块或方法开始的地方获得，在结束的地方释放
线程要么得到锁，要么阻塞，没有其他的可能性
```
Java 5对锁机制进行了重构，提供了显示的锁，这样可以在以下几个方面提升锁机制



```
可以添加不同类型的锁，例如读取锁和写入锁
可以在一个方法中加锁，在另一个方法中解锁
可以使用tryLock方式尝试获得锁，如果得不到锁可以等待、回退或者干点别的事情，当然也可以在超时之后放弃操作
```

显示的锁都实现了java.util.concurrent.Lock接口，主要有两个实现类


```
ReentrantLock - 比synchronized稍微灵活一些的重入锁
ReentrantReadWriteLock - 在读操作很多写操作很少时性能更好的一种重入锁
```

对于如何使用显示锁，可以参考我的Java面试系列文章《Java面试题集51-70》中第60题的代码。只有一点需要提醒，解锁的方法unlock的调用最好能够在finally块中，因为这里是释放外部资源最好的地方，当然也是释放锁的最佳位置，因为不管正常异常可能都要释放掉锁来给其他线程以运行的机会。

* ConcurrentHashMap

ConcurrentHashMap是HashMap在并发环境下的版本，大家可能要问，既然已经可以通过Collections.synchronizedMap获得线程安全的映射型容器，为什么还需要ConcurrentHashMap呢？因为通过Collections工具类获得的线程安全的HashMap会在读写数据时对整个容器对象上锁，这样其他使用该容器的线程无论如何也无法再获得该对象的锁，也就意味着要一直等待前一个获得锁的线程离开同步代码块之后才有机会执行。实际上，HashMap是通过哈希函数来确定存放键值对的桶（桶是为了解决哈希冲突而引入的），修改HashMap时并不需要将整个容器锁住，只需要锁住即将修改的“桶”就可以了。

* CopyOnWriteArrayList

CopyOnWriteArrayList是ArrayList在并发环境下的替代品。CopyOnWriteArrayList通过增加写时复制语义来避免并发访问引起的问题，也就是说任何修改操作都会在底层创建一个列表的副本，也就意味着之前已有的迭代器不会碰到意料之外的修改。这种方式对于不要严格读写同步的场景非常有用，因为它提供了更好的性能。记住，要尽量减少锁的使用，因为那势必带来性能的下降（对数据库中数据的并发访问不也是如此吗？如果可以的话就应该放弃悲观锁而使用乐观锁），CopyOnWriteArrayList很明显也是通过牺牲空间获得了时间（在计算机的世界里，时间和空间通常是不可调和的矛盾，可以牺牲空间来提升效率获得时间，当然也可以通过牺牲时间来减少对空间的使用）。 

* Queue

队列是一个无处不在的美妙概念，它提供了一种简单又可靠的方式将资源分发给处理单元（也可以说是将工作单元分配给待处理的资源，这取决于你看待问题的方式）。实现中的并发编程模型很多都依赖队列来实现，因为它可以在线程之间传递工作单元。 

Java 5中的BlockingQueue就是一个在并发环境下非常好用的工具，在调用put方法向队列中插入元素时，如果队列已满，它会让插入元素的线程等待队列腾出空间；在调用take方法从队列中取元素时，如果队列为空，取出元素的线程就会阻塞。 

可以用BlockingQueue来实现生产者-消费者并发模型（下一节中有介绍），当然在Java 5以前也可以通过wait和notify来实现线程调度，比较一下两种代码就知道基于已有的并发工具类来重构并发代码到底好在哪里了。

[下面有生产者和消费者代码](http://blog.csdn.net/jackfrued/article/details/44499227)

5. Java7并发编程

Java 7中引入了TransferQueue，它比BlockingQueue多了一个叫transfer的方法，如果接收线程处于等待状态，该操作可以马上将任务交给它，否则就会阻塞直至取走该任务的线程出现。可以用TransferQueue代替BlockingQueue，因为它可以获得更好的性能。 

# 6.并发模型分析
我们还是重温一下几个概念：


```
概念	解释
临界资源	并发环境中有着固定数量的资源
互斥	对资源的访问是排他式的
饥饿	一个或一组线程长时间或永远无法取得进展
死锁	两个或多个线程相互等待对方结束
活锁	想要执行的线程总是发现其他的线程正在执行以至于长时间或永远无法执行
```
讨一下下面的几种并发模型。
1. 生产者-消费者：
一个或多个生产者创建某些工作并将其置于缓冲区或队列中，一个或多个消费者会从队列中获得这些工作并完成之。这里的缓冲区或队列是临界资源。当缓冲区或队列放满的时候，生产这会被阻塞；而缓冲区或队列为空的时候，消费者会被阻塞。生产者和消费者的调度是通过二者相互交换信号完成的。
2. 读者-写者：当存在一个主要为读者提供信息的共享资源，它偶尔会被写者更新，但是需要考虑系统的吞吐量，又要防止饥饿和陈旧资源得不到更新的问题。在这种并发模型中，如何平衡读者和写者是最困难的，当然这个问题至今还是一个被热议的问题，恐怕必须根据具体的场景来提供合适的解决方案而没有那种放之四海而皆准的方法（不像我在国内的科研文献中看到的那样）。
3. 哲学家进餐：1965年，荷兰计算机科学家图灵奖得主Edsger Wybe Dijkstra提出并解决了一个他称之为哲学家进餐的同步问题。这个问题可以简单地描述如下：五个哲学家围坐在一张圆桌周围，每个哲学家面前都有一盘通心粉。由于通心粉很滑，所以需要两把叉子才能夹住。相邻两个盘子之间放有一把叉子如下图所示。哲学家的生活中有两种交替活动时段：即吃饭和思考。当一个哲学家觉得饿了时，他就试图分两次去取其左边和右边的叉子，每次拿一把，但不分次序。如果成功地得到了两把叉子，就开始吃饭，吃完后放下叉子继续思考。 
把上面问题中的哲学家换成线程，把叉子换成竞争的临界资源，上面的问题就是线程竞争资源的问题。如果没有经过精心的设计，系统就会出现死锁、活锁、吞吐量下降等问题。

![image](http://img.blog.csdn.net/20150326074356819)

下面是用信号量原语来解决哲学家进餐问题的代码，使用了Java 5并发工具包中的Semaphore类（代码不够漂亮但是已经足以说明问题了）。

```
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 存放线程共享信号量的上下问
 * @author 骆昊
 *
 */
class AppContext {
    public static final int NUM_OF_FORKS = 5;   // 叉子数量(资源)
    public static final int NUM_OF_PHILO = 5;   // 哲学家数量(线程)

    public static Semaphore[] forks;    // 叉子的信号量
    public static Semaphore counter;    // 哲学家的信号量

    static {
        forks = new Semaphore[NUM_OF_FORKS];

        for (int i = 0, len = forks.length; i < len; ++i) {
            forks[i] = new Semaphore(1);    // 每个叉子的信号量为1
        }

        counter = new Semaphore(NUM_OF_PHILO - 1);  // 如果有N个哲学家，最多只允许N-1人同时取叉子
    }

    /**
     * 取得叉子
     * @param index 第几个哲学家
     * @param leftFirst 是否先取得左边的叉子
     * @throws InterruptedException
     */
    public static void putOnFork(int index, boolean leftFirst) throws InterruptedException {
        if(leftFirst) {
            forks[index].acquire();
            forks[(index + 1) % NUM_OF_PHILO].acquire();
        }
        else {
            forks[(index + 1) % NUM_OF_PHILO].acquire();
            forks[index].acquire();
        }
    }

    /**
     * 放回叉子
     * @param index 第几个哲学家
     * @param leftFirst 是否先放回左边的叉子
     * @throws InterruptedException
     */
    public static void putDownFork(int index, boolean leftFirst) throws InterruptedException {
        if(leftFirst) {
            forks[index].release();
            forks[(index + 1) % NUM_OF_PHILO].release();
        }
        else {
            forks[(index + 1) % NUM_OF_PHILO].release();
            forks[index].release();
        }
    }
}

/**
 * 哲学家
 * @author 骆昊
 *
 */
class Philosopher implements Runnable {
    private int index;      // 编号
    private String name;    // 名字

    public Philosopher(int index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public void run() {
        while(true) {
            try {
                AppContext.counter.acquire();
                boolean leftFirst = index % 2 == 0;
                AppContext.putOnFork(index, leftFirst);
                System.out.println(name + "正在吃意大利面（通心粉）...");   // 取到两个叉子就可以进食
                AppContext.putDownFork(index, leftFirst);
                AppContext.counter.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Test04 {

    public static void main(String[] args) {
        String[] names = { "骆昊", "王大锤", "张三丰", "杨过", "李莫愁" };   // 5位哲学家的名字
//      ExecutorService es = Executors.newFixedThreadPool(AppContext.NUM_OF_PHILO); // 创建固定大小的线程池
//      for(int i = 0, len = names.length; i < len; ++i) {
//          es.execute(new Philosopher(i, names[i]));   // 启动线程
//      }
//      es.shutdown();
        for(int i = 0, len = names.length; i < len; ++i) {
            new Thread(new Philosopher(i, names[i])).start();
        }
    }

}
```

# 7.Java7的排序算法
伴随着Java 7的到来，Java中默认的数组排序算法已经不再是经典的快速排序（双枢轴快速排序）了，新的排序算法叫TimSort，它是归并排序和插入排序的混合体，TimSort可以通过分支合并框架充分利用现代处理器的多核特性，从而获得更好的性能（更短的排序时间）。

# 8.描述一个需要同步的场景
下面的例子演示了100个线程同时向一个银行账户中存入1元钱，在没有使用同步机制和使用同步机制情况下的执行情况。


```
/**
 * 银行账户
 * @author 骆昊
 *
 */
public class Account {
    private double balance;     // 账户余额
 
    /**
     * 存款
     * @param money 存入金额
     */
    public void deposit(double money) {
        double newBalance = balance + money;
        try {
            Thread.sleep(10);   // 模拟此业务需要一段处理时间
        }
        catch(InterruptedException ex) {
            ex.printStackTrace();
        }
        balance = newBalance;
    }
 
    /**
     * 获得账户余额
     */
    public double getBalance() {
        return balance;
    }
}
```

```
/**
 * 存钱线程
 * @author 骆昊
 *
 */
public class AddMoneyThread implements Runnable {
    private Account account;    // 存入账户
    private double money;       // 存入金额
 
    public AddMoneyThread(Account account, double money) {
        this.account = account;
        this.money = money;
    }
 
    @Override
    public void run() {
        account.deposit(money);
    }
 
}
```

```
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
public class Test01 {
 
    public static void main(String[] args) {
        Account account = new Account();
        ExecutorService service = Executors.newFixedThreadPool(100);
 
        for(int i = 1; i <= 100; i++) {
            service.execute(new AddMoneyThread(account, 1));
        }
 
        service.shutdown();
 
        while(!service.isTerminated()) {}
 
        System.out.println("账户余额: " + account.getBalance());
    }
}
```
在没有同步的情况下，执行结果通常是显示账户余额在10元以下，出现这种状况的原因是，当一个线程A试图存入1元的时候，另外一个线程B也能够进入存款的方法中，线程B读取到的账户余额仍然是线程A存入1元钱之前的账户余额，因此也是在原来的余额0上面做了加1元的操作，同理线程C也会做类似的事情，所以最后100个线程执行结束时，本来期望账户余额为100元，但实际得到的通常在10元以下（很可能是1元哦）。解决这个问题的办法就是同步，当一个线程对银行账户存钱时，需要将此账户锁定，待其操作完成后才允许其他的线程进行操作，代码有如下几种调整方案：

* 在银行账户的存款（deposit）方法上同步（synchronized）关键字
```
/**
 * 银行账户
 * @author 骆昊
 *
 */
public class Account {
    private double balance;     // 账户余额
 
    /**
     * 存款
     * @param money 存入金额
     */
    public synchronized void deposit(double money) {
        double newBalance = balance + money;
        try {
            Thread.sleep(10);   // 模拟此业务需要一段处理时间
        }
        catch(InterruptedException ex) {
            ex.printStackTrace();
        }
        balance = newBalance;
    }
 
    /**
     * 获得账户余额
     */
    public double getBalance() {
        return balance;
    }
}
```
在线程调用存款方法时对银行账户进行同步
```
/**
 * 存钱线程
 * @author 骆昊
 *
 */
public class AddMoneyThread implements Runnable {
    private Account account;    // 存入账户
    private double money;       // 存入金额
 
    public AddMoneyThread(Account account, double money) {
        this.account = account;
        this.money = money;
    }
 
    @Override
    public void run() {
        synchronized (account) {
            account.deposit(money); 
        }
    }
 
}
```
通过Java 5显示的锁机制，为每个银行账户创建一个锁对象，在存款操作进行加锁和解锁的操作
```
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 
/**
 * 银行账户
 * 
 * @author 骆昊
 *
 */
public class Account {
    private Lock accountLock = new ReentrantLock();
    private double balance; // 账户余额
 
    /**
     * 存款
     * 
     * @param money
     *            存入金额
     */
    public void deposit(double money) {
        accountLock.lock();
        try {
            double newBalance = balance + money;
            try {
                Thread.sleep(10); // 模拟此业务需要一段处理时间
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            balance = newBalance;
        }
        finally {
            accountLock.unlock();
        }
    }
 
    /**
     * 获得账户余额
     */
    public double getBalance() {
        return balance;
    }
}
```
按照上述三种方式对代码进行修改后，重写执行测试代码Test01，将看到最终的账户余额为100元。当然也可以使用Semaphore或CountdownLatch来实现同步。

# 9.编写多线程程序有几种实现方式？
Java 5以前实现多线程有两种实现方法：一种是继承Thread类；另一种是实现Runnable接口。两种方式都要通过重写run()方法来定义线程的行为，推荐使用后者，因为Java中的继承是单继承，一个类有一个父类，如果继承了Thread类就无法再继承其他类了，显然使用Runnable接口更为灵活。

补充：Java 5以后创建线程还有第三种方式：实现Callable接口，该接口中的call方法可以在线程执行结束时产生一个返回值，代码如下所示：
# 10.synchronize用法
synchronized关键字可以将对象或者方法标记为同步，以实现对对象和方法的互斥访问，可以用synchronized(对象) { … }定义同步代码块，或者在声明方法时将synchronized作为方法的修饰符。在第60题的例子中已经展示了synchronized关键字的用法。

# 11.举例说明同步和异步。
* 如果系统中存在临界资源（资源数量少于竞争资源的线程数量的资源），例如正在写的数据以后可能被另一个线程读到，或者正在读的数据可能已经被另一个线程写过了，那么这些数据就必须进行同步存取（数据库操作中的排他锁就是最好的例子）。
* 当应用程序在对象上调用了一个需要花费很长时间来执行的方法，并且不希望让程序等待方法的返回时，就应该使用异步编程，在很多情况下采用异步途径往往更有效率。事实上，所谓的同步就是指阻塞式操作，而异步就是非阻塞式操作。

# 12.启动一个线程是调用run()还是start()方法？
* 启动一个线程是调用start()方法，使线程所代表的虚拟处理机处于可运行状态，这意味着它可以由JVM 调度并执行，这并不意味着线程就会立即运行。
* run()方法是线程启动后要进行回调（callback）的方法。

# 13.什么是线程池（thread pool）？
[详细的建议看这篇博客](https://www.jianshu.com/p/87bff5cc8d8c)

1. 在Java中，虚拟机将试图跟踪每一个对象，以便能够在对象销毁后进行垃圾回收。所以提高服务程序效率的一个手段就是尽可能减少创建和销毁对象的次数，特别是一些很耗资源的对象创建和销毁，这就是”池化资源”技术产生的原因。
2. 线程池顾名思义就是事先创建若干个可执行的线程放入一个池（容器）中，需要的时候从池中获取线程不用自行创建，使用完毕不需要销毁线程而是放回池中，从而减少创建和销毁线程对象的开销。
3. Java 5+中的Executor接口定义一个执行线程的工具。它的子类型即线程池接口是ExecutorService。要配置一个线程池是比较复杂的，尤其是对于线程池的原理不是很清楚的情况下，因此在工具类Executors面提供了一些静态工厂方法，生成一些常用的线程池。
4. 如下所示：
- newSingleThreadExecutor：创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。此线程池保证所有任务的执行顺序按照任务的提交顺序执行。
- newFixedThreadPool：创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
- newCachedThreadPool：创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。
- newScheduledThreadPool：创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。
- newSingleThreadExecutor：创建一个单线程的线程池。此线程池支持定时以及周期性执行任务的需求。

第60题的例子中演示了通过Executors工具类创建线程池并使用线程池执行线程的代码。如果希望在服务器上使用线程池，强烈建议使用newFixedThreadPool方法来创建线程池，这样能获得更好的性能。

# 13.1线程池的实现原理
1. 优点

1、线程是稀缺资源，使用线程池可以减少创建和销毁线程的次数，每个工作线程都可以重复使用。

2、可以根据系统的承受能力，调整线程池中工作线程的数量，防止因为消耗过多内存导致服务器崩溃。

2. 实现原理

```
提交一个任务到线程池中，线程池的处理流程如下：

1、判断线程池里的核心线程是否都在执行任务，如果不是（核心线程空闲或者还有核心线程没有被创建）则创建一个新的工作线程来执行任务。如果核心线程都在执行任务，则进入下个流程。

2、线程池判断工作队列是否已满，如果工作队列没有满，则将新提交的任务存储在这个工作队列里。如果工作队列满了，则进入下个流程。

3、判断线程池里的线程是否都处于工作状态，如果没有，则创建一个新的工作线程来执行任务。如果已经满了，则交给饱和策略来处理这个任务。
```
从结果可以观察出：

1、创建的线程池具体配置为：核心线程数量为5个；全部线程数量为10个；工作队列的长度为5。

2、我们通过queue.size()的方法来获取工作队列中的任务数。

3、运行原理：

  刚开始都是在创建新的线程，达到核心线程数量5个后，新的任务进来后不再创建新的线程，而是将任务加入工作队列，任务队列到达上线5个后，新的任务又会创建新的普通线程，直到达到线程池最大的线程数量10个，后面的任务则根据配置的饱和策略来处理。我们这里没有具体配置，使用的是默认的配置AbortPolicy:直接抛出异常。
  
　　当然，为了达到我需要的效果，上述线程处理的任务都是利用休眠导致线程没有释放！！！
  
3. 当队列和线程池都满了，说明线程池处于饱和状态，那么必须对新提交的任务采用一种特殊的策略来进行处理。这个策略默认配置是AbortPolicy，表示无法处理新的任务而抛出异常。JAVA提供了4中策略：


```
1、AbortPolicy：直接抛出异常

2、CallerRunsPolicy：只用调用所在的线程运行任务

3、DiscardOldestPolicy：丢弃队列里最近的一个任务，并执行当前任务。

4、DiscardPolicy：不处理，丢弃掉。
```

# 14.线程的基本状态以及状态之间的关系？
![image](http://img.blog.csdn.net/20150408002007838)

说明：其中Running表示运行状态，Runnable表示就绪状态（万事俱备，只欠CPU），Blocked表示阻塞状态，阻塞状态又有多种情况，可能是因为调用wait()方法进入等待池，也可能是执行同步方法或同步代码块进入等锁池，或者是调用了sleep()方法或join()方法等待休眠或其他线程结束，或是因为发生了I/O中断。

# 15.简述synchronized 和java.util.concurrent.locks.Lock的异同？
Lock是Java 5以后引入的新的API，和关键字synchronized相比主要相同点：Lock 能完成synchronized所实现的所有功能；主要不同点：Lock有比synchronized更精确的线程语义和更好的性能，而且不强制性的要求一定要获得锁。synchronized会自动释放锁，而Lock一定要求程序员手工释放，并且最好在finally 块中释放（这是释放外部资源的最好的地方）。

```
 a.最好两个都不用，使用一种java.util.concurrent包提供的机制， 
            能够帮助用户处理所有与锁相关的代码。 
        b.如果synchronized关键字能满足用户的需求，就用synchronized，因为它能简化代码 
        c.如果需要更高级的功能，就用ReentrantLock类，此时要注意及时释放锁，否则会出现死锁，通常在finally代码释放锁 。
```


# 16. Java的同步机制有哪些？
1. synchronized关键字修饰

由于java的每个对象都有一个内置锁，当用此关键字修饰方法时， 
    内置锁会保护整个方法。在调用该方法前，需要获得内置锁，否则就处于阻塞状态。
2. 用特殊域变量(volatile)实现线程同步

```
a.volatile关键字为域变量的访问提供了一种免锁机制， 
    b.使用volatile修饰域相当于告诉虚拟机该域可能会被其他线程更新， 
    c.因此每次使用该域就要重新计算，而不是使用寄存器中的值 
    d.volatile不会提供任何原子操作，它也不能用来修饰final类型的变量 
```
3. 使用重入锁实现线程同步

在JavaSE5.0中新增了一个java.util.concurrent包来支持同步。ReentrantLock类是可重入、互斥、实现了Lock接口的锁， 它与使用synchronized方法和快具有相同的基本行为和语义，并且扩展了其能力。


```
ReenreantLock类的常用方法有：
         ReentrantLock() : 创建一个ReentrantLock实例 
         lock() : 获得锁 
         unlock() : 释放锁 
```

4. 使用局部变量实现线程同步

ThreadLocal一般称为线程本地变量，它是一种特殊的线程绑定机制，将变量与线程绑定在一起，为每一个线程维护一个独立的变量副本。通过ThreadLocal可以将对象的可见范围限制在同一个线程内。

需要重点强调的的是，不要拿ThreadLocal和synchronized做类比，因为这种比较压根就是无意义的！sysnchronized是一种互斥同步机制，是为了保证在多线程环境下对于共享资源的正确访问。而ThreadLocal从本质上讲，无非是提供了一个“线程级”的变量作用域，它是一种线程封闭（每个线程独享变量）技术，更直白点讲，ThreadLocal可以理解为将对象的作用范围限制在一个线程上下文中，使得变量的作用域为“线程级”。

没有ThreadLocal的时候，一个线程在其声明周期内，可能穿过多个层级，多个方法，如果有个对象需要在此线程周期内多次调用，且是跨层级的（线程内共享），通常的做法是通过参数进行传递；而ThreadLocal将变量绑定在线程上，在一个线程周期内，无论“你身处何地”，只需通过其提供的get方法就可轻松获取到对象。极大地提高了对于“线程级变量”的访问便利性。



# 17. volatile用法
1. volatile关键字就是Java中提供的另一种解决可见性和有序性问题的方案。对于原子性，需要强调一点，也是大家容易误解的一点：对volatile变量的单次读/写操作可以保证原子性的，如long和double类型变量，但是并不能保证i++这种操作的原子性，因为本质上i++++是读、写两次操作。
2. 使用场景
* 防止重排序：
现在我们分析一下为什么要在变量singleton之间加上volatile关键字。要理解这个问题，先要了解对象的构造过程，实例化一个对象其实可以分为三个步骤：

```
（1）分配内存空间。

　　（2）初始化对象。

　　（3）将内存空间的地址赋值给对应的引用。
```


但是由于操作系统可以对指令进行重排序，所以上面的过程也可能会变成如下过程：

```
（1）分配内存空间。

　　（2）将内存空间的地址赋值给对应的引用。

　　（3）初始化对象
```


　　如果是这个流程，多线程环境下就可能将一个未初始化的对象引用暴露出来，从而导致不可预料的结果。因此，为了防止这个过程的重排序，我们需要将变量设置为volatile类型的变量。

* 实现可见性
可见性问题主要指一个线程修改了共享变量值，而另一个线程却看不到。引起可见性问题的主要原因是每个线程拥有自己的一个高速缓存区——线程工作内存。volatile关键字能有效的解决这个问题，我们看下下面的例子，就可以知道其作用。

为什么会出现b=3;a=1这种结果呢？正常情况下，如果先执行change方法，再执行print方法，输出结果应该为b=3;a=3。相反，如果先执行的print方法，再执行change方法，结果应该是 b=2;a=1。那b=3;a=1的结果是怎么出来的？原因就是第一个线程将值a=3修改后，但是对第二个线程是不可见的，所以才出现这一结果。如果将a和b都改成volatile类型的变量再执行，则再也不会出现b=3;a=1的结果了。
* 保证原子性

关于原子性的问题，上面已经解释过。volatile只能保证对单次读/写的原子性

这段话的内容跟我前面的描述内容大致类似。因为long和double两种数据类型的操作可分为高32位和低32位两部分，因此普通的long或double类型读/写可能不是原子的。因此，鼓励大家将共享的long和double变量设置为volatile类型，这样能保证任何情况下对long和double的单次读/写操作都具有原子性。

可能每个人运行的结果不相同。不过应该能看出，volatile是无法保证原子性的（否则结果应该是1000）。原因也很简单，i++其实是一个复合操作，包括三步骤：

```
（1）读取i的值。

　　（2）对i加1。

　　（3）将i的值写回内存。0
　　
```


volatile是无法保证这三个操作是具有原子性的，我们可以通过AtomicInteger或者Synchronized来保证+1操作的原子性。


# 18. 什么是原子操作
1. 原子操作是不可分割的，在执行完毕之前不会被任何其它任务或事件中断。在单处理器系统（UniProcessor）中，能够在单条指令中完成的操作都可以认为是" 原子操作"，因为中断只能发生于指令之间。这也是某些CPU指令系统中引入了test_and_set、test_and_clear等指令用于临界资源互斥的原因。但是，在对称多处理器（Symmetric Multi-Processor）结构中就不同了，由于系统中有多个处理器在独立地运行，即使能在单条指令中完成的操作也有可能受到干扰。我们以decl （递减指令）为例，这是一个典型的"读－改－写"过程，涉及两次内存访问。设想在不同CPU运行的两个进程都在递减某个计数值，可能发生的情况
2. Java有原子操作类

原子操作类相当于泛化的volatile变量，能够支持原子读取-修改-写操作。比如AtomicInteger表示一个int类型的数值，提供了get和set方法，这些volatile类型的变量在读取与写入上有着相同的内存语义。原子操作类共有13个类，在java.util.concurrent.atomic包下，可以分为四种类型的原子更新类：原子更新基本类型、原子更新数组类型、原子更新引用和原子更新属性。

使用原子方式更新基本类型，共包括3个类：

```
AtomicBoolean：原子更新布尔变量
AtomicInteger：原子更新整型变量
AtomicLong：原子更新长整型变量
具体到每个类的源代码中，提供的方法基本相同
```

# 19. 多线程断点续传原理

# 9.多线程下载原理
* 为什么多线程能够提高速度？-CPU更多的资源给了你
- 直接原因是 window size 不够大。然而根本原因其实并非 delay ，“时分复用”系统的类比也不大对，应该是 TCP congestion control 导致的。
- 理想情况下， window size 足够大，在等待客户端回 ACK 的 delay 期间，服务器端的 window 还没有用完，就可以继续发送数据，从而不会出现任何形式的中断暂停， pipeline 的优势得到了最大程度的发挥。在这种情况下， delay 多大其实没关系——只要数据大小足够大，一开头的一个 delay 对整个传输时间没什么影响。也就是说最终还是瓶颈带宽（bps）起决定性作用。
- 然而实际情况并非如此。一个网络中并不仅仅有你这么一个连接，单说两个端点处都肯定是如此，更不用说中间大家共用的网络线路了。这种情况下， TCP 的 congestion control 机制就会起作用，通过调节 window size 来避免出现拥塞，因为一旦出现阻塞丢包对整个网络和自身都是很不好的。一般来说最终 window size 无法达到上面说的“理想值”，从而使得传输需要停下来等 ACK ，带宽也就不能被“充分利用”。
- 注意到 congestion control 的算法是 per connection 的，而不是 per server-client pair 。不管实际细节如何（什么 AIMD 、 slow start 等等），最终算法的目的是 connection-wise fairness ——在不出现拥塞的前提下，让每个 connection 都能大致得到同样的、最大的带宽。所以，如果你开多个连接，你就能”抢占“到更多的带宽……
- 补充一些细节（感谢 @寒璿 ），多个 TCP 连接还能减少、摊平单连接时 congestion control 导致的 window size 大幅波动对速度的影响。早期 TCP 在遇到拥塞（丢包或者 duplicate ACK ）时，会大幅减小 window size （甚至直接减到 1 MSS ），而增加又是缓慢的线性递增； TCP Reno 中改进为若是 triple duplicate ACK ，就会把 window size 减半而不是一减到底，增加时也会有一个在小于 ssthreshold 时使用翻倍增加而非线性递增的 slow start 过程，但即使如此波动还是很大（使用这类 AIMD 控制是为了保证“正确性”，即最终的公平分配，Additive increase/multiplicative decrease）。还有一些其他细节可以参看他答案中的那篇文章。
* 不是线程开的越多下载越快，线程再多其实都是CPU在切换时间片，一般都有推荐数量，另外还受服务器带宽的影响。

# 10.多线程下载步骤分析
* 需要将下载内容进行线程等分分割，计算好每个线程的开始和结束位置。每一个线程对应下载一部分内容，
* 首先需要获取下载文件大小
* 在客户端创建一个大小和服务器一摸一样的文件（非必需，但是这个可以提前申请空间），包括计算每个线程的开始位置和结束位置
* 开多个线程去下载文件
* 知道每个线程什么时候下载完毕

# 11.多线程下载之获取文件的大小
* 先创建一个Java工程，方便调试，测试完毕再移植过去
* 创建需要模拟的下载文件并打开tomcat，把你的下载文件放到tomcat的root目录下面。图片文件如果丢了几个字节还是可以看的，所以不太好演示。可以改小的exe文件，丢了任何都不能用了。
* 然后打开tomcat测试能够浏览器下载就可以了。
* 下面开始写java程序了-获取服务器文件大小。
```
//[1]定义下载路径
	static String path = "http://192.168.43.144:8080/Test.exe";
	
	public static void main(String[] args) {
		// [2]获取服务器文件大小-计算每个线程开始和结束位置
		try{
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			int code = conn.getResponseCode();
			if(code == 200){
				//现在只需要拿到服务器文件大小
				//InputStream inputStream = conn.getInputStream();
				int length = conn.getContentLength();
				System.out.println(length);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
```

# 12.多线程下载-计算开始和结束位置
* 这里必须支持对于下载文件的随机读取和写入，这里需要用到RandomAccessFile类，这个示例支持对文件的随机访问读取和写入。
* 此类的实例支持对随机访问文件的读取和写入。随机访问文件的行为类似存储在文件系统中的一个大型 byte 数组。存在指向该隐含数组的光标或索引，称为文件指针；输入操作从文件指针开始读取字节，并随着对字节的读取而前移此文件指针。
* 第二个参数指mode：
```
"r" 以只读方式打开。调用结果对象的任何 write 方法都将导致抛出 IOException。  
"rw" 打开以便读取和写入。如果该文件尚不存在，则尝试创建该文件。  
"rws" 打开以便读取和写入，对于 "rw"，还要求对文件的内容或元数据的每个更新都同步写入到底层存储设备。  
"rwd"   打开以便读取和写入，对于 "rw"，还要求对文件内容的每个更新都同步写入到底层存储设备。 

```
这里的底层设备指硬盘。元数据指文件右击-详细信息。

```
//[3]创建一个大小和服务器一摸一样文件-申请空间
				RandomAccessFile rafAcessFile = new RandomAccessFile("temp.exe", "rw");
				rafAcessFile.setLength(length);
```

* 利用此类的seak方法设置文件指针偏移量
* 计算每个线程下载大小：基本还是采用等分的思想，然后通过每一段去分配各自的起末位置，最后余下来的给最后线程就好，不会多太多。
```
//[4]计算每个线程下载的开始和结束位置
				int blocksize = length/THREADCOUNT;
				for (int i = 0; i < THREADCOUNT; i++) {
					int startIndex = i * blocksize;
					int endIndex = (i+1) * blocksize - 1;
					//最后一个线程比较特殊
					if(i == THREADCOUNT){
						endIndex = length - 1;
					}
					
					//[5]开启线程去服务器下载文件
				}
```
* 开启线程去完成指定部分的下载
```
//[5]开启线程去服务器下载文件
					DownLoadThread downLoadThread = new DownLoadThread(startIndex, endIndex, i);
					downLoadThread.start();
```

```
//定义下载文件线程
	 static class DownLoadThread extends Thread{
		//通过构造方法确定下载和结束位置
		private int startIndex;
		private int endIndex;
		private int threadId;
		
		public DownLoadThread(int startIndex, int endIndex, int threadId) {
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.threadId = threadId;
			
			
		}

		@Override
		public void run() {
			//实现去服务器下载文件的逻辑
			try{
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);
				
				//设置一个请求头Range-作用：告诉服务器每个线程下载的开始和结束位置
				conn.setRequestProperty("Range","bytes="+startIndex+"-"+endIndex);
				
				int code = conn.getResponseCode();
				//200代表获取所有资源成功，206请求部分资源成功
				if(code == 206){
					//创建随机读写文件对象
					RandomAccessFile raf = new RandomAccessFile("temp.exe", "rw");
					//每个线程从自己位置开始写
					raf.seek(startIndex);
					
					InputStream in= conn.getInputStream(); //存的是Test.exe
					//把数据写到文件里面
					int len = 1;
					byte[] buffer = new byte[1024];
					while((len = in.read(buffer))!= -1){
						raf.write(buffer,0,len);
					}
					raf.close();//关闭释放资源
					
					System.out.println("线程id"+threadId + "下载完成");
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}
		
	}
```
# 13.断点续传的逻辑实现
* 重点就是记录当前中断的下载位置

```
//200代表获取所有资源成功，206请求部分资源成功
				if(code == 206){
					//创建随机读写文件对象
					RandomAccessFile raf = new RandomAccessFile("temp.exe", "rw");
					//每个线程从自己位置开始写
					raf.seek(startIndex);
					
					InputStream in= conn.getInputStream(); //存的是Test.exe
					//把数据写到文件里面
					int len = 1;
					byte[] buffer = new byte[1024];
					
					int total = 0;
					
					while((len = in.read(buffer))!= -1){
						raf.write(buffer,0,len);
						
						total += len;
						//[8]实现断点续传-就是把当前线程下载位置给保存起来，下次再下载时候，就是按照这个位置继续下载
						//比如就存到一个普通的TXT文本中
						int currentThreadPosition = startIndex + total;
						
						//注意硬盘都有一个缓存，直接用fos可能会有问题，因为先把数据同步到硬盘缓存，缓存满了再同步到文件中
						//有可能存不进去，我们要用RandomAccessFile，他有一个rwd访问模式，会直接同步硬盘上，不经过缓存（虽然会对硬件有一点点伤害）
						/*File file = new File(threadId + ".txt");
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(b);*/
						
						//[9]用来存下载位置
						RandomAccessFile raff = new RandomAccessFile(threadId+".txt", "rwd");
						raff.write(String.valueOf(currentThreadPosition).getBytes());
			
					}
```
这里把我们的下载位置写到一个txt文件里面，主要硬盘缓存的问题
* 然后需要在前面判断这个startIndex的位置，是不是从头下载
```
@Override
		public void run() {
			//实现去服务器下载文件的逻辑
			try{
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);
				
				//[10]如果中间断过 继续上次位置下载
				File file = new File(threadId + ".txt");
				if(file.exists() && file.length() > 0){
					FileInputStream fis = new FileInputStream(file);
					BufferedReader bufr = new BufferedReader(new InputStreamReader(fis));
					String lastPositionn = bufr.readLine();
					int lastPosition = Integer.parseInt(lastPositionn);
					
					System.out.println("线程id：" + threadId + "真实下载位置" + lastPosition + "-----" + endIndex);
					//[10.1]改变一下startIndex位置
					startIndex = lastPosition;
					fis.close();
					
				}
```
我们可以用一个实际的文件去测试，另外我们完成下载之后哪些txt就可以删了。

另外注意这里我们可以把buffer缓冲数组定义稍微大一点，这样会快一点。
```
//把数据写到文件里面
					int len = 1;
					byte[] buffer = new byte[1024*1024];
					
```
不要太大，不然一断就是丢失整个byte数组的大小。

# 14.断点续传之删除文件
* 首先了解每个线程具体什么时候下载完毕了。---使用变量记录
```
//正在运行线程数
	private static int runningThread = 0;
	//初始化正在运行线程数
				runningThread = THREADCOUNT;
				
```

```
//[11]把txt文件删除 -当线程下载完毕时候-注意这里涉及到线程同步
						synchronized (DownLoadThread.class) {
							runningThread--;
							if(runningThread == 0){
								//说明所有线程执行完毕-把txt删除
								for (int i = 0; i < THREADCOUNT; i++) {
									File delteFile = new File(i + ".txt");
									delteFile.delete();
								}
							}
						}
```

# 14.多线程下载移植Android
* UI界面需要的元素：下载文件的路径，需要的线程数量以及下载进度-分别用EditText和progressbar
* 添加进度条数量需要根据线程数动态调整，然后加到一个垂直布局里面（progressbar单独写一个xml）
```
//[3.0]先移除进度条 在添加 
		ll_pb_layout.removeAllViews();
		
		threadCount = Integer.parseInt(threadCountt);
		pbLists.clear();
		for (int i = 0; i < threadCount; i++) {
		
			//[3.1]把我定义的item布局转换成一个view对象 
			ProgressBar pbView = (ProgressBar) View.inflate(getApplicationContext(), R.layout.item, null);
			
			//[3.2]把pbView 添加到集合中 
			pbLists.add(pbView);
			
			//[4]动态的添加进度条 
			ll_pb_layout.addView(pbView);
			
		}
```
不出意外，应该你的输入线程是几个就会有几个进度条出现
* 下面就开始移植了，直接把那边的联网下载内容里面复制过来，记得开子线程，另外有一些变量作用域需要改一下
```
//[5]开始移植   联网 获取文件长度 
		new Thread(){public void run() {
			
			//[一 ☆☆☆☆]获取服务器文件的大小   要计算每个线程下载的开始位置和结束位置
			
			try {

				//(1) 创建一个url对象 参数就是网址 
				URL url = new URL(path);
				//(2)获取HttpURLConnection 链接对象
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				//(3)设置参数  发送get请求
				conn.setRequestMethod("GET"); //默认请求 就是get  要大写
				//(4)设置链接网络的超时时间 
				conn.setConnectTimeout(5000);
				//(5)获取服务器返回的状态码 
				int code = conn.getResponseCode(); //200  代表获取服务器资源全部成功  206请求部分资源    
				if (code == 200) {

					//(6)获取服务器文件的大小
					int length = conn.getContentLength();
					
					//[6.1]把 线程的数量赋值给正在运行的线程
					runningThread = threadCount;
					
					
					System.out.println("length:"+length);
					
					//[二☆☆☆☆ ] 创建一个大小和服务器一模一样的文件 目的提前把空间申请出来 
					RandomAccessFile rafAccessFile = new RandomAccessFile(getFilename(path), "rw");
					rafAccessFile.setLength(length);
					
					//(7)算出每个线程下载的大小 
					int blockSize = length /threadCount;
					
					//[三☆☆☆☆  计算每个线程下载的开始位置和结束位置 ]
					for (int i = 0; i < threadCount; i++) {
						int startIndex = i * blockSize;   //每个线程下载的开始位置 
						int endIndex = (i+1)*blockSize - 1;
						//特殊情况 就是最后一个线程 
						if (i == threadCount - 1) {
							//说明是最后一个线程 
							endIndex = length - 1;
							
						}
						
						System.out.println("线程id:::"+i + "理论下载的位置"+":"+startIndex+"-----"+endIndex);
						
						//四 开启线程去服务器下载文件 
						DownLoadThread downLoadThread = new DownLoadThread(startIndex, endIndex, i);
						downLoadThread.start();
						
					}
					
					
					
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		};}.start();
```
* 注意这里你开的下载进程还要考虑到进度条的更新，最大值，断点时候的位置等等。结合下载位置的index设置
```
/定义线程去服务器下载文件  
		private  class DownLoadThread extends Thread{
			//通过构造方法把每个线程下载的开始位置和结束位置传递进来 
			
			private int startIndex;
			private int endIndex;
			private int threadId;
			
			private int PbMaxSize; //代表当前线程下载的最大值 
			//如果中断过  获取上次下载的位置 
			private int pblastPostion;
			
			public DownLoadThread(int startIndex,int endIndex,int threadId){
				this.startIndex = startIndex;
				this.endIndex  = endIndex;
				this.threadId = threadId;
			}
			
			@Override
			public void run() {
				//四  实现去服务器下载文件的逻辑  
				
				try {

					//(0)计算当前进度条的最大值 
					PbMaxSize = endIndex - startIndex;
					//(1) 创建一个url对象 参数就是网址 
					URL url = new URL(path);
					//(2)获取HttpURLConnection 链接对象
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					//(3)设置参数  发送get请求
					conn.setRequestMethod("GET"); //默认请求 就是get  要大写
					//(4)设置链接网络的超时时间 
					conn.setConnectTimeout(5000);
					
					
					//[4.0]如果中间断过  继续上次的位置 继续下载   从文件中读取上次下载的位置 
					
					File file =new File(getFilename(path)+threadId+".txt");
					if (file.exists() && file.length()>0) {
						FileInputStream fis = new  FileInputStream(file);
						BufferedReader bufr = new  BufferedReader(new InputStreamReader(fis));
						String lastPositionn = bufr.readLine();  //读取出来的内容就是上一次下载的位置
						int lastPosition = Integer.parseInt(lastPositionn);

						//[4.0]给我们定义的进度条条位置 赋值 
						pblastPostion = lastPosition - startIndex;
						
						//[4.0.1]要改变一下 startIndex 位置
						startIndex = lastPosition + 1;
						
						
						
						System.out.println("线程id::"+threadId + "真实下载的位置"+":"+startIndex+"-----"+endIndex);
						
						fis.close(); //关闭流 
					}
					
					//[4.1]设置一个请求头Range (作用告诉服务器每个线程下载的开始位置和结束位置)
					conn.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
					
					//(5)获取服务器返回的状态码 
					int code = conn.getResponseCode(); //200  代表获取服务器资源全部成功  206请求部分资源 成功  
					if (code == 206) {
						//[6]创建随机读写文件对象 
						RandomAccessFile raf = new RandomAccessFile(getFilename(path), "rw");
						//[6]每个线程要从自己的位置开始写 
						raf.seek(startIndex);
						
						InputStream in = conn.getInputStream(); //存的是feiq.exe 
						
						//[7]把数据写到文件中
						int len = -1;
						byte[] buffer = new byte[1024*1024];//1Mb
						
						int total = 0; //代表当前线程下载的大小 
						
						while((len = in.read(buffer))!=-1){
							raf.write(buffer, 0, len);
							
							total +=len;
							//[8]实现断点续传 就是把当前线程下载的位置 给存起来 下次再下载的时候 就是按照上次下载的位置继续下载 就可以了
							int currentThreadPosition =  startIndex + total;  //比如就存到一个普通的.txt文本中
							
							//[9]用来存当前线程下载的位置 
							RandomAccessFile raff = new RandomAccessFile(getFilename(path)+threadId+".txt", "rwd");
							raff.write(String.valueOf(currentThreadPosition).getBytes());
							raff.close();
							
							//[10]设置一下当前进度条的最大值 和 当前进度
							pbLists.get(threadId).setMax(PbMaxSize);//设置进度条的最大值 
							pbLists.get(threadId).setProgress(pblastPostion+total);//设置当前进度条的当前进度   
							
							
							
						}
						raf.close();//关闭流  释放资源
						
						System.out.println("线程id:"+threadId + "---下载完毕了");
						
						//[10]把.txt文件删除  每个线程具体什么时候下载完毕了 我们不知道 
						
						synchronized (DownLoadThread.class) {
							runningThread--;
							if (runningThread == 0) {
								//说明所有的线程都执行完毕了 就把.txt文件删除
								for (int i = 0; i < threadCount; i++) {
									File  delteFile = new File(getFilename(path)+i+".txt");
									delteFile.delete();
								}
								
								 
								
								
							}
						}
						
						
						
						
						
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
			}
		}
```
* 注意这里几个对进度条的处理，借用了下面的集合去取进度条threadId进行对应的操作。
* 为啥这里的进度条可以在子线程里面更新UI？
* 另外这里注意，存断点位置的文件目录需要根据Android重新编写，不能是原来的path
```
//[2]添加一个集合 用来存进度条的引用 
		pbLists = new ArrayList<ProgressBar>();
```
这个集合用来存进度条的引用。


# 15.开源项目实现多线程下载
* 之前写的断点续传的只是简单demo，不能商用，就是测试API的
* 一个好的应用是要慢慢测试，然后修改的，一个好的断点续传逻辑可能要4-5千行
* 比如：判断是否有SD卡？判断手机网络类型-wifi，流量？判断下载文件的安全，是否病毒？越来越多需求，原来的也会慢慢完善
* 企业里面一般不需要从底层写，尽量用开源项目
* 这里用到xUtils，这个项目有四大模块，我们用到了httpUtils模块，这个支持断点续传，随时停止任务，开始任务，几行代码就解决了问题。一般是把com包里面的考出来放到我们lib里面就好，有的提供开源项目jar包，放到libs下面就行。
* 
```
//点击按钮实现断点续传下载逻辑
	public void click(View v){
		//[1]获取下载路径 
		String path = et_path.getText().toString().trim();
		//[2]创建httputils对象
		HttpUtils http = new HttpUtils();
		//[3]实现断点下载  target下载文件的路径      autoResume 是否支持断点续传的逻辑
		http.download(path, "/mnt/sdcard/xpg.mp3", true, new RequestCallBack<File>() {
			
			//下载成功
			@Override
			public void onSuccess(ResponseInfo<File> responseInfo) {
			
				Toast.makeText(getApplicationContext(), "下载成功", 1).show();
				
			}
			
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				//total  代表总进度      current 代表当前进度
				
				pb.setMax((int) total);
				pb.setProgress((int) current);
			}
			
			//下载失败的回调
			@Override
			public void onFailure(HttpException error, String msg) {
				
			}
		});

		
	}
```
可以看到非常简单，这里面不能选择线程数量，是固定的，其他逻辑很简单，在回调里面完成你需要的功能和更新进度条就行了