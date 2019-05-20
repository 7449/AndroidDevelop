[推荐博文](http://blog.csdn.net/carson_ho/article/details/79373026)

# 1.简单介绍一下HashMap
1. HashMap是基于哈希表的Map接口实现类，实现了Map接口，Cloneable接口（可进行浅拷贝）以及Serializable接口（可以实现序列化）
2. 主要用来存放键值对，用于进行高效率的查找
3. 数据结构主要是数组结合单链表（利用链地址法解决hash冲突），在JDK1.8中还加入了红黑树
4. HashMap中的数组元素 & 链表节点 采用 Entry类 实现，HashMap的本质 = 1个存储Entry类对象的数组 + 多个单链表。
5. 特点
* 允许键值为空对象
* 线程不安全
* 不保证有序且不保证顺序不随时间变化

# 2.简述一下HashMap的存储流程
![image](http://upload-images.jianshu.io/upload_images/944365-524548a1f5087115.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 3.HashMap的遍历方式
1. 首先你需要获取key-value对（Entry） 或 key 或 value的Set集合
2. 遍历上述Set集合(使用for循环 、 迭代器（Iterator）均可)
3. 对于遍历方式，推荐使用针对 key-value对（Entry）的方式：效率高
4. 原因
* 遍历keySet 、valueSet，实质上 = 遍历了2次：1 = 转为 iterator 迭代器遍历、2 = 从 HashMap 中取出 key 的 value 操作（通过 key 值 hashCode 和 equals 索引）
* 遍历 entrySet ，实质 = 遍历了1次 = 获取存储实体Entry（存储了key 和 value ）

# 4.HashMap中有哪些比较重要的参数
1. 主要有容量、加载因子、扩容阈值
* 容量（capacity）就是HashMap中数组的长度，必须是2的幂 & <最大容量（2的30次方），默认容量为16，最大容量为2的30次方
* 加载因子(Load factor)：HashMap在其容量自动增加前可达到多满的一种尺度，越大、填满的元素越多 = 空间利用率高、但冲突的机会加大、查找效率变低（因为链表变长了），反之空间利用率小、冲突的机会减小、查找效率高（链表不长），默认0.75，空间和时间取舍
* 扩容阈值（threshold）：当哈希表的大小 ≥ 扩容阈值时，就会扩容哈希表（即扩充HashMap的容量），扩容阈值 = 容量 x 加载因子

# 5.为什么HashTable的key不能为null而HashMap可以？
1. 根据HashMap的源代码，在put的时候HashMap考虑到的key为null的情况，调用addEntry（），将空键 & 对应的值封装到Entry中，并放到table[0]中
2. 对比HashTable，由于HashTable对key直接hashCode（），若key为null时，会抛出异常，所以HashTable的key不可为null
3.  HashMap的键key 可为null且只能为1个，但值value可为null且为多个

# 6.HashMap如何计算得到存放在hash数组的位置？为什么不直接利用hashCode得到的hash值进行索引？
1. 通过三个步骤：计算哈希码（java提供的hashcode方法）、二次处理哈希码（扰动处理：4次位运算和5次异或运算）、最终计算存储的数组位置（同数组长度减1后进行与操作）
2. 直接利用java提供的方法容易出现计算得到的hash码和数组大小范围不匹配的情况，hash码可能不在范围内，所以需要上述第三步。而第二步的扰动处理可以加大哈希码低位的随机性，使得分布更均匀，减少Hash冲突。（因为实际上只能根据数组长度取哈希码低几位作为下标位置，冲突概率大）
![image](http://upload-images.jianshu.io/upload_images/944365-84e9503fb49c46ab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 7.简述向HashMap中添加键值对（put）的流程
![image](http://upload-images.jianshu.io/upload_images/944365-a5570a7fe29fb8b9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
1. 首先会判断哈希表是否初始化，若未初始化，则利用构造函数时设置的阈值来初始化数组。
2. 接着会判断put的key是否为null，如果为null，该键值对会存放到数组table的第一个位置table[0]。若不为null，根据key计算hash值，再根据hash值计算得到key对应数组的下标
3. 判断该key对应的值是否存在（遍历单链表），若存在则用新的value替换旧的value。如果不存在则直接将键值对添加到数组中。
* 真正初始化哈希表（初始化存储数组table）是在第1次添加键值对时，即第1次调用put（）时
* 分析下键值对添加方法addEntry：插入前判断是否需要扩容，若不足够，则进行扩容（2倍）、重新计算Hash值、重新计算存储数组下标，若容量足够，则创建1个新的数组元素（Entry）。

# 8.简述HashMap的扩容过程
1. 保存旧数组和旧容量，若旧容量已经是系统默认最大容量了，那么将阈值设置成整型的最大值，退出。
2. 根据新容量新建数组并将旧数组数据转移到新数组中并重新设定阈值
3. 转移方法transfer主要是按照旧链表的正序遍历链表（遍历旧数组->正序遍历该数组元素为首的链表->重新计算新的存储位置）、在新链表的头部依次插入

![image](http://upload-images.jianshu.io/upload_images/944365-000e08831c45b66c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 9.哈希表如何解决哈希冲突
![image](http://upload-images.jianshu.io/upload_images/944365-7621b15f58e87a66.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 10.为什么HashMap是线程不安全的？
1. 没有加同步锁进行保护且多线程下容易出现resize时发生死循环。
* 扩容resize（）过程中，在将旧数组上的数据 转移到 新数组上时，转移数据操作 = 按旧链表的正序遍历链表、在新链表的头部依次插入，即在转移数据、扩容后，容易出现链表逆序的情况，此时若（多线程）并发执行 put（）操作，一旦出现扩容情况，则 容易出现 环形链表，从而在获取数据、遍历链表时 形成死循环（Infinite Loop），即 死锁的状态
* JDK 1.8 转移数据操作 = 按旧链表的正序遍历链表、在新链表的尾部依次插入，所以不会出现链表 逆序、倒置的情况，故不容易出现环形链表的情况。
2. 在使用迭代器的过程中出现并发操作会抛出ConrueentModificationException异常。
2. 在JDK1.8中解决了出现死循环的问题
3. 多线程情况下建议使用线程安全的ConcurrentHashMap。

# 11.为什么 HashMap 中 String、Integer 这样的包装类适合作为 key 键
![image](http://upload-images.jianshu.io/upload_images/944365-318b6e178419065b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 12.HashMap 中的 key若 Object类型， 则需实现哪些方法？
![image](http://upload-images.jianshu.io/upload_images/944365-23536584ac590783.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 13.JDK1.8中对HashMap的改进
1. 数据结构方面引入了红黑树：数组+链表+红黑树。解决hash冲突后链表过程的地效率问题，利用红黑树快速增删改查特点将时间复杂度从O(n)降低到O(logn)
![image](http://upload-images.jianshu.io/upload_images/944365-98178707855677bc.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2. HashMap中加入了和红黑树相关的参数。
* 桶的树化阈值TREEIFY_THRESHOLD：即 链表转成红黑树的阈值，在存储数据时，当链表长度 > 该值时，则将链表转换成红黑树
* 桶的链表还原阈值UNTREEIFY_THRESHOLD：即 红黑树转为链表的阈值，当在扩容（resize（））时（此时HashMap的数据存储位置会重新计算），在重新计算存储位置后，当原有的红黑树内数量 < 6时，则将 红黑树转换成链表
* 最小树形化容量阈值：即 当哈希表中的容量 > 该值时，才允许树形化链表 （即 将链表 转换成红黑树）否则，若桶内元素太多时，则直接扩容，而不是树形化，为了避免进行扩容、树形化选择的冲突，这个值不能小于 4 * TREEIFY_THRESHOLD
3. 与1.7不同主要在计算hash值和解决hash冲突这两个方面（1.8节点采用Node类而非Entry类，仅仅名字不同）
![image](http://upload-images.jianshu.io/upload_images/944365-45ec8c640c5e5363.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* JDK 1.8计算hash值：将 键key 转换成 哈希码（hash值）操作 = 使用hashCode() + 1次位运算 + 1次异或运算（2次扰动）
![image](http://upload-images.jianshu.io/upload_images/944365-1f13afa0cd516356.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* 发生了hash冲突之后，会首先判断当前插入结构为红黑树还是链表：若为红黑树则直接在树中插入或者更新键值对，若为链表，遍历并采用尾插法进行插入，同时检查插入后节点是否大于阈值，是否需要转化为红黑树
* 扩容resize时候重新计算每个数据的位置的方式也不同，前面图里面有说明，链表采用的尾插法
![image](http://upload-images.jianshu.io/upload_images/944365-e62deb3ca3055dd1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 14.JDK1.8的put流程
![image](http://upload-images.jianshu.io/upload_images/944365-08fb248f05aead3f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 15.equals和==区别
![image](http://upload-images.jianshu.io/upload_images/944365-e1ba0f95861f430e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
