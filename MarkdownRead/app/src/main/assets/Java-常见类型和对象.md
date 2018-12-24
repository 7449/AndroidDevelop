# 1.是否可以继承String类？
答：String 类是final类，不可以被继承。

补充：继承String本身就是一个错误的行为，对String类型最好的重用方式是关联关系（Has-A）和依赖关系（Use-A）而不是继承关系（Is-A）。

# 2.Java中的String是基本数据类型吗？
不是。Java中的基本数据类型只有8个：byte、short、int、long、float、double、char、boolean；除了基本类型（primitive type）和枚举类型（enumeration type），剩下的都是引用类型（reference type）。

# 3.int和Interger区别
Java是一个近乎纯洁的面向对象编程语言，但是为了编程的方便还是引入了基本数据类型，但是为了能够将这些基本数据类型当成对象操作，Java为每一个基本数据类型都引入了对应的包装类型（wrapper class），int的包装类就是Integer，从Java 5开始引入了自动装箱/拆箱机制，使得二者可以相互转换。

```
class AutoUnboxingTest {
 
    public static void main(String[] args) {
        Integer a = new Integer(3);
        Integer b = 3;                  // 将3自动装箱成Integer类型
        int c = 3;
        System.out.println(a == b);     // false 两个引用没有引用同一对象
        System.out.println(a == c);     // true a自动拆箱成int类型再和c比较
    }
}
```
下面是一个面试题
```
public class Test03 {
 
    public static void main(String[] args) {
        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;
 
        System.out.println(f1 == f2);
        System.out.println(f3 == f4);
    }
}
```
如果不明就里很容易认为两个输出要么都是true要么都是false。首先需要注意的是f1、f2、f3、f4四个变量都是Integer对象引用，所以下面的==运算比较的不是值而是引用。装箱的本质是什么呢？当我们给一个Integer对象赋一个int值的时候，会调用Integer类的静态方法valueOf，如果看看valueOf的源代码就知道发生了什么。
```
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high)
        return IntegerCache.cache[i + (-IntegerCache.low)];
    return new Integer(i);
}
```
简单的说，如果整型字面量的值在-128到127之间，那么不会new新的Integer对象，而是直接引用常量池中的Integer对象，所以上面的面试题中f1==f2的结果是true，而f3==f4的结果是false。

# 4.String和StringBuilder、StringBuffer的区别？
Java平台提供了两种类型的字符串：String和StringBuffer/StringBuilder，它们可以储存和操作字符串。其中String是只读字符串，也就意味着String引用的字符串内容是不能被改变的。而StringBuffer/StringBuilder类表示的字符串对象可以直接进行修改。StringBuilder是Java 5中引入的，它和StringBuffer的方法完全相同，区别在于它是在单线程环境下使用的，因为它的所有方面都没有被synchronized修饰，因此它的效率也比StringBuffer要高。

但是非可变类（String）确实有着自身的优势，如状态单一，对象简单，便于维护。其次，该类对象对象本质上是线程安全的，不要求同步。此外用户可以共享非可变对象，甚至可以共享它们的内部信息。（详见 《Effective java》item 13）。String类在java中被大量运用，甚至在class文件中都有其身影，因此将其设计为简单轻便的非可变类是比较合适的。

# 5.什么情况下用+运算符进行字符串连接比调用StringBuffer/StringBuilder对象的append方法连接字符串性能更好？
* 一般情况下，要维护String的非可变性，只好在串接+完成后新建一个String 对象来表示新产生的字符串了。也就是说，每一次执行串接操作都会导致新对象的产生，如果串接操作执行很频繁，就会导致大量对象的创建，性能问题也就随之而来了。
    为了解决这个问题，jdk为String类提供了一个可变的配套类，StringBuffer。使用StringBuffer对象，由于该类是可变的，串接时仅仅时改变了内部数据结构，而不会创建新的对象，因此性能上有很大的提高。针对单线程，jdk 5.0还提供了StringBuilder类，在单线程环境下，由于不用考虑同步问题，使用该类使性能得到进一步的提高。
    
* 如果在编写代码的过程中大量使用+进行字符串评价还是会对性能造成比较大的影响，但是使用的个数在1000以下还是可以接受的，大于10000的话，执行时间将可能超过1s，会对性能产生较大影响。如果有大量需要进行字符串拼接的操作，最好还是使用StringBuffer或StringBuilder进行。

# 6.请说出下面程序的输出。
```
class StringEqualTest {
 
    public static void main(String[] args) {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program" + "ming"; 
        String s4 = "ming";
        String s5 = "Program"+s4;
        System.out.println(s1 == s2); //false
        System.out.println(s1 == s3); //true
        System.out.println(s1 == s1.intern()); //true
          System.out.println(s1 == s5); //false
    }
}
```
直接通过下面几个例子就可以分析得到
```
java 代码
String str1 = new String("abc"); //jvm 在堆上创建一个String对象  
 
 //jvm 在strings pool中找不到值为“abc”的字符串，因此  
 //在堆上创建一个String对象，并将该对象的引用加入至strings pool中  
 //此时堆上有两个String对象  
 
Stirng str2 = "abc";  
 
 if(str1 == str2){  
         System.out.println("str1 == str2");  
 }else{  
         System.out.println("str1 != str2");  

  //打印结果是 str1 != str2,因为它们是堆上两个不同的对象  
 
  String str3 = "abc";  
 //此时，jvm发现strings pool中已有“abc”对象了，因为“abc”equels “abc”  
 //因此直接返回str2指向的对象给str3，也就是说str2和str3是指向同一个对象的引用  
  if(str2 == str3){  
         System.out.println("str2 == str3");  
  }else{  
         System.out.println("str2 != str3");  
  }  
 //打印结果为 str2 == str3 
```

```
java 代码
String str1 = new String("abc"); //jvm 在堆上创建一个String对象  
 
str1 = str1.intern();  
//程序显式将str1放到strings pool中，intern运行过程是这样的：首先查看strings pool  
//有没“abc”对象的引用，没有，则在堆中新建一个对象，然后将新对象的引用加入至  
//strings pool中。执行完该语句后，str1原来指向的String对象已经成为垃圾对象了，随时会  
//被GC收集。  
 
//此时，jvm发现strings pool中已有“abc”对象了，因为“abc”equels “abc”  
//因此直接返回str1指向的对象给str2，也就是说str2和str1引用着同一个对象，  
//此时，堆上的有效对象只有一个。  
Stirng str2 = "abc";  
 
 if(str1 == str2){  
         System.out.println("str1 == str2");  
 }else{  
         System.out.println("str1 != str2");  
 }  
  //打印结果是 str1 == str2  
 
为什么jvm可以这样处理String对象呢？就是因为String的非可变性。既然所引用的对象一旦创建就永不更改，那么多个引用共用一个对象时互不影响。
```
# 7.char 型变量中能不能存贮一个中文汉字，为什么？
char类型可以存储一个中文汉字，因为Java中使用的编码是Unicode（不选择任何特定的编码，直接使用字符在字符集中的编号，这是统一的唯一方法），一个char类型占2个字节（16比特），所以放一个中文是没问题的。

# 8.String s = new String(“xyz”);创建了几个字符串对象？
两个对象，一个是静态区的”xyz”，一个是用new创建在堆上的对象。

# 9.数据类型之间的转换：

- 如何将字符串转换为基本数据类型？

调用基本数据类型对应的包装类中的方法parseXXX(String)或valueOf(String)即可返回相应基本类型；
- 如何将基本数据类型转换为字符串？

一种方法是将基本数据类型与空字符串（”"）连接（+）即可获得其所对应的字符串；另一种方法是调用String 类中的valueOf()方法返回相应字符串

# 10.如何实现字符串的反转及替换？
方法很多，可以自己写实现也可以使用String或StringBuffer/StringBuilder中的方法。有一道很常见的面试题是用递归实现字符串反转，代码如下所示：
```
public static String reverse(String originStr) {
      if(originStr == null || originStr.length() <= 1) 
          return originStr;
      return reverse(originStr.substring(1)) + originStr.charAt(0);
  }
```

# 11.怎样将GB2312编码的字符串转换为ISO-8859-1编码的字符串？
```
String s1 = "你好";
String s2 = new String(s1.getBytes("GB2312"), "ISO-8859-1");
```

# 12.日期和时间：
- 如何取得年月日、小时分钟秒？
问题1：创建java.util.Calendar 实例，调用其get()方法传入不同的参数即可获得参数所对应的值。Java 8中可以使用java.time.LocalDateTimel来获取，代码如下所示。

```
public class DateTimeTest {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH));    // 0 - 11
        System.out.println(cal.get(Calendar.DATE));
        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
        System.out.println(cal.get(Calendar.MINUTE));
        System.out.println(cal.get(Calendar.SECOND));
 
        // Java 8
        LocalDateTime dt = LocalDateTime.now();
        System.out.println(dt.getYear());
        System.out.println(dt.getMonthValue());     // 1 - 12
        System.out.println(dt.getDayOfMonth());
        System.out.println(dt.getHour());
        System.out.println(dt.getMinute());
        System.out.println(dt.getSecond());
    }
}
```

- 如何取得从1970年1月1日0时0分0秒到现在的毫秒数？
```
Calendar.getInstance().getTimeInMillis();
System.currentTimeMillis();
Clock.systemDefaultZone().millis(); // Java 8
```

- 如何取得某月的最后一天？
```
Calendar time = Calendar.getInstance();
time.getActualMaximum(Calendar.DAY_OF_MONTH);
```

- 如何格式化日期？
利用java.text.DataFormat 的子类（如SimpleDateFormat类）中的format(Date)方法可将日期格式化。Java 8中可以用java.time.format.DateTimeFormatter来格式化时间日期，代码如下所示。
```
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
 
class DateFormatTest {
 
    public static void main(String[] args) {
        SimpleDateFormat oldFormatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date1 = new Date();
        System.out.println(oldFormatter.format(date1));
 
        // Java 8
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date2 = LocalDate.now();
        System.out.println(date2.format(newFormatter));
    }
}
```
