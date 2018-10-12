
> 不定期更新维护中....


----------


### **Android部分**
1.	View的绘制流程；自定义View如何考虑机型适配；自定义View的事件分发机制；View和ViewGroup分别有哪些事件分发相关的回调方法；自定义View如何提供获取View属性的接口；
2.	Art和Dalvik对比；虚拟机原理，如何自己设计一个虚拟机(内存管理，类加载，双亲委派)；JVM内存模型及类加载机制；内存对象的循环引用及避免
3.	内存回收机制与GC算法(各种算法的优缺点以及应用场景)；GC原理时机以及GC对象；内存泄露场景及解决方法；OOM的避免及解决方法
4.	四大组件及生命周期；ContentProvider的权限管理(读写分离，权限控制-精确到表级，URL控制)；Activity的四种启动模式对比；Activity状态保存于恢复
5.	Fragment生命周期；Fragment状态保存
6.	startActivityForResult是哪个类的方法，在什么情况下使用，如果在Adapter中使用应该如何解耦
7.	AsyncTask原理及不足;IntentService原理
8.	AstncTask+HttpClient与AsyncHttpClient有什么区别
9.	如何保证一个后台服务不被杀死；比较省电的方式是什么
10.	如何通过广播拦截和abort一条短信；广播是否可以请求网络；广播引起anr的时间限制
11.	进程间通信，AIDL

[12.	Handler机制及底层实现](https://github.com/crazyandcoder/job_interview/blob/master/articles/Handler%E6%9C%BA%E5%88%B6.md)
13.	Binder机制及底层实现
14.	ApplicationContext和ActivityContext的区别
15.	一张Bitmap所占内存以及内存占用的计算
16.	对于应用更新这块是如何做的？(灰度，强制更新，分区域更新)
17.	混合开发，RN，weex，H5，小程序(做Android的了解一些前端js等还是很有好处的)
18.	说一款你认为当前比较火的应用并设计(直播APP)

[19.	内存泄漏的原因](https://github.com/crazyandcoder/job_interview/blob/master/articles/%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E7%9A%84%E5%8E%9F%E5%9B%A0.md)

[20.	老司机谈APK瘦身套路-图片资源篇](http://blog.csdn.net/mynameishuangshuai/article/details/51752832)

----------


### **Java部分**
1.	集合类以及集合框架；HashMap与HashTable实现原理，线程安全性，hash冲突及处理算法；ConcurrentHashMap
2.	进程和线程的区别；多线程与线程池
3.	数据一致性如何保证；Synchronized关键字，类锁，方法锁，重入锁
4.	同步的方法；多进程开发以及多进程应用场景
5.	服务器只提供数据接收接口，在多线程或多进程条件下，如何保证数据的有序到达
6.	ThreadLocal原理，实现及如何保证Local属性
7.	String StringBuilder StringBuffer对比
8.	接口与回调；回调的原理；写一个回调demo；
9.	泛型原理，举例说明；解析与分派
10.	抽象类与接口的区别；应用场景；抽象类是否可以没有方法和属性
11.	静态属性和静态方法是否可以被继承？是否可以被重写？原因
12.	修改对象A的equals方法的签名，那么使用HashMap存放这个对象实例的时候，会调用哪个equals方法

[13.	ArrayList和LinkedList的区别](https://github.com/crazyandcoder/job_interview/blob/master/articles/ArrayList%E5%92%8CLinkedList%E7%9A%84%E5%8C%BA%E5%88%AB.md)

[14. Iterator和Enumeration的不同](https://github.com/crazyandcoder/job_interview/blob/master/articles/Iterator%E5%92%8CEnumeration%E7%9A%84%E4%B8%8D%E5%90%8C.md)

[15. hashmap和hashtable的不同](https://github.com/crazyandcoder/job_interview/blob/master/articles/hashmap%E5%92%8Chashtable%E7%9A%84%E4%B8%8D%E5%90%8C.md)

[16. 接口的注意点](https://github.com/crazyandcoder/job_interview/blob/master/articles/%E6%8E%A5%E5%8F%A3%E7%9A%84%E6%B3%A8%E6%84%8F%E7%82%B9.md)



----------


### **数据结构与算法**
1.	堆和栈在内存中的区别是什么(数据结构方面以及实际实现方面)
2.	最快的排序算法是哪个？给阿里2万多名员工按年龄排序应该选择哪个算法？堆和树的区别；写出快排代码；链表逆序代码
3.	求1000以内的水仙花数以及40亿以内的水仙花数
4.	子串包含问题(KMP 算法)写代码实现
5.	万亿级别的两个URL文件A和B，如何求出A和B的差集C,(Bit映射->hash分组->多文件读写效率->磁盘寻址以及应用层面对寻址的优化)
6.	蚁群算法与蒙特卡洛算法
7.	写出你所知道的排序算法及时空复杂度，稳定性
8.	百度POI中如何试下查找最近的商家功能(坐标镜像+R树)


----------


### **其他部分**
1.	死锁的四个必要条件
2.	常见编码方式；utf-8编码中的中文占几个字节；int型几个字节
3.	实现一个Json解析器(可以通过正则提高速度)
4.	MVC MVP MVVM; 常见的设计模式；写出观察者模式的代码
5.	TCP的3次握手和四次挥手；TCP与UDP的区别
6.	HTTP协议；HTTP1.0与2.0的区别；HTTP报文结构
7.	HTTP与HTTPS的区别以及如何实现安全性

----------
