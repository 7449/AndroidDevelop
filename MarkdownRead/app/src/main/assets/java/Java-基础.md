# 1.面向对象的特征有哪些方面？
我认为Java的面向对象特征有四个方面：封装、继承、多态、抽象
1. 封装：封装是把数据和操作数据的方法绑定起来，对数据的访问只能通过已定义的接口。面向对象的本质就是将现实世界描绘成一系列完全自治、封闭的对象。我们在类中编写的方法就是对实现细节的一种封装；我们编写一个类就是对数据和数据操作的封装。可以说，封装就是隐藏一切可隐藏的东西，只向外界提供最简单的编程接口（可以想想普通洗衣机和全自动洗衣机的差别，明显全自动洗衣机封装更好因此操作起来更简单）。
2. 继承：是从已有类得到继承信息创建新类的过程。提供继承信息的类被称为父类（超类、基类）；得到继承信息的类被称为子类（派生类）。继承让变化中的软件系统有了一定的延续性，同时继承也是封装程序中可变因素的重要手段
3. 多态：多态性是指允许不同子类型的对象对同一消息作出不同的响应。简单的说就是用同样的对象引用调用同样的方法但是做了不同的事情。多态性分为编译时的多态性和运行时的多态性。如果将对象的方法视为对象向外界提供的服务，那么运行时的多态性可以解释为：当A系统访问B系统提供的服务时，B系统有多种提供服务的方式，但一切对A系统来说都是透明的（就像电动剃须刀是A系统，它的供电系统是B系统，B系统可以使用电池供电或者用交流电，甚至还有可能是太阳能，A系统只会通过B类对象调用供电的方法，但并不知道供电系统的底层实现是什么，究竟通过何种方式获得了动力）。方法重载（overload）实现的是编译时的多态性（也称为前绑定），而方法重写（override）实现的是运行时的多态性（也称为后绑定）。运行时的多态是面向对象最精髓的东西，要实现多态需要做两件事：1). 方法重写（子类继承父类并重写父类中已有的或抽象的方法）；2). 对象造型（用父类型引用引用子类型对象，这样同样的引用调用同样的方法就会根据子类对象的不同而表现出不同的行为）。
4. 抽象：抽象是将一类对象的共同特征总结出来构造类的过程，包括数据抽象和行为抽象两方面。抽象只关注对象有哪些属性和行为，并不关注这些行为的细节是什么。

# JAVA多态的实现原理
抽象的来讲，多态的意思就是同一消息可以根据发送对象的不同而采用多种不同的行为方式。（发送消息就是函数调用） 实现的原理是动态绑定，程序调用的方法在运行期才动态绑定，追溯源码可以发现，JVM 通过参数的自动转型来找到合适的办法。

# 2.访问修饰符public,private,protected,以及不写（默认）时的区别？
* java的四个关键字：public、protected、default、private

适用范围<访问权限范围越小，安全性越高>

　　　　  访问权限   类   包  子类  其他包

  　　　　  public     ∨   ∨    ∨     ∨          （对任何人都是可用的）

   　　　　 protect    ∨   ∨   ∨     ×　　　 （继承的类可以访问以及和private一样的权限）

   　　　　 default    ∨   ∨   ×     ×　　　 （包访问权限，即在整个包内均可被访问）

   　　　　 private    ∨   ×   ×     ×　　　 （除类型创建者和类型的内部方法之外的任何人都不能访问的元素）
* 类的成员不写访问修饰时默认为default。默认对于同一个包中的其他类相当于公开（public），对于不是同一个包中的其他类相当于私有（private）。受保护（protected）对子类相当于公开，对不是同一包中的没有父子关系的类相当于私有。Java中，外部类的修饰符只能是public或默认，类的成员（包括内部类）的修饰符可以是以上四种。



# 3.数组是一个类吗？
在语法和实现方式上，数组不像一个类。在虚拟机规范和底层实现，数组是一个像类的对象。是一种特殊的类。
[link](http://note.youdao.com/)

你的思考方向很对，是的java除了8种基础类型外，其余都是类，数组也是一种特殊的类，你System.out.println(bText.getClass().getName());
你会发现java对数组命了一个奇怪的类名，[byte。也就是[xxxx形式。
java语言由于虚拟机全权托管内存，所以new这个关键字就是从heap区标记一块内存，返回一个指针给bText，当然java没有指针了，已经封装了。

# 4.short s1 = 1; s1 = s1 + 1;有错吗?short s1 = 1; s1 += 1;有错吗？
对于short s1 = 1; s1 = s1 + 1;由于1是int类型，因此s1+1运算结果也是int 型，需要强制转换类型才能赋值给short型。而short s1 = 1; s1 += 1;可以正确编译，因为s1+= 1;相当于s1 = (short)(s1 + 1);其中有隐含的强制类型转换。



# 5.解释内存中的栈(stack)、堆(heap)和静态区(static area)的用法。
通常我们定义一个基本数据类型的变量，一个对象的引用，还有就是函数调用的现场保存都使用内存中的栈空间；而通过new关键字和构造器创建的对象放在堆空间；程序中的字面量（literal）如直接书写的100、”hello”和常量都是放在静态区中。栈空间操作起来最快但是栈很小，通常大量的对象都是放在堆空间，理论上整个内存没有被其他进程使用的空间甚至硬盘上的虚拟内存都可以被当成堆空间来使用。


```
String str = new String("hello");
```
上面的语句中变量str放在栈上，用new创建出来的字符串对象放在堆上，而”hello”这个字面量放在静态区。

补充：较新版本的Java（从Java 6的某个更新开始）中使用了一项叫”逃逸分析”的技术，可以将一些局部对象放在栈上以提升对象的操作性能。

# 6.switch 是否能作用在byte 上，是否能作用在long 上，是否能作用在String上？
在Java 5以前，switch(expr)中，expr只能是byte、short、char、int。从Java 5开始，Java中引入了枚举类型，expr也可以是enum类型，从Java 7开始，expr还可以是字符串（String），但是长整型（long）在目前所有的版本中都是不可以的。

# 7.用最有效率的方法计算2乘以8？
2 << 3（左移3位相当于乘以2的3次方，右移3位相当于除以2的3次方）。

# 8.两个对象值相同(x.equals(y) == true)，但却可有不同的hash code，这句话对不对？
不对，如果两个对象x和y满足x.equals(y) == true，它们的哈希码（hash code）应当相同。Java对于eqauls方法和hashCode方法是这样规定的：(1)如果两个对象相同（equals方法返回true），那么它们的hashCode值一定要相同；(2)如果两个对象的hashCode相同，它们并不一定相同。当然，你未必要按照要求去做，但是如果你违背了上述原则就会发现在使用容器时，相同的对象可以出现在Set集合中，同时增加新元素的效率会大大下降（对于使用哈希存储的系统，如果哈希码频繁的冲突将会造成存取性能急剧下降）。

# 9.内部类，静态内部类，匿名内部类，局部内部类简述。以及他们的加载
1. 在Java中，可以将一个类定义在另一个类里面或者一个方法里面，这样的类称为内部类。广泛意义上的内部类一般来说包括这四种：成员内部类、局部内部类、匿名内部类和静态内部类。下面就先来了解一下这四种内部类的用法。
* 成员内部类是最普通的内部类，它的定义为位于另一个类的内部，形如下面的形式：
```
class Circle {
    double radius = 0;
     
    public Circle(double radius) {
        this.radius = radius;
    }
     
    class Draw {     //内部类
        public void drawSahpe() {
            System.out.println("drawshape");
        }
    }
}
```
这样看起来，类Draw像是类Circle的一个成员，Circle称为外部类。成员内部类可以无条件访问外部类的所有成员属性和成员方法（包括private成员和静态成员）。

不过要注意的是，当成员内部类拥有和外部类同名的成员变量或者方法时，会发生隐藏现象，即默认情况下访问的是成员内部类的成员。如果要访问外部类的同名成员，需要以下面的形式进行访问：
```
外部类.this.成员变量
外部类.this.成员方法
```
虽然成员内部类可以无条件地访问外部类的成员，而外部类想访问成员内部类的成员却不是这么随心所欲了。在外部类中如果要访问成员内部类的成员，必须先创建一个成员内部类的对象，再通过指向这个对象的引用来访问

成员内部类是依附外部类而存在的，也就是说，如果要创建成员内部类的对象，前提是必须存在一个外部类的对象。创建成员内部类对象的一般方式如下


```
        Outter outter = new Outter();
        Outter.Inner inner = outter.new Inner();  //必须通过Outter对象来创建
```
内部类可以拥有private访问权限、protected访问权限、public访问权限及包访问权限。比如上面的例子，如果成员内部类Inner用private修饰，则只能在外部类的内部访问，如果用public修饰，则任何地方都能访问；如果用protected修饰，则只能在同一个包下或者继承外部类的情况下访问；如果是默认访问权限，则只能在同一个包下访问。这一点和外部类有一点不一样，外部类只能被public和包访问两种权限修饰。我个人是这么理解的，由于成员内部类看起来像是外部类的一个成员，所以可以像类的成员一样拥有多种权限修饰

* 局部内部类是定义在一个方法或者一个作用域里面的类，它和成员内部类的区别在于局部内部类的访问仅限于方法内或者该作用域内。
```
public People getWoman(){
        class Woman extends People{   //局部内部类
            int age =0;
        }
        return new Woman();
    }
```
注意，局部内部类就像是方法里面的一个局部变量一样，是不能有public、protected、private以及static修饰符的。
* 匿名内部类应该是平时我们编写代码时用得最多的，在编写事件监听的代码时使用匿名内部类不但方便，而且使代码更加容易维护。下面这段代码是一段Android事件监听代码：
```
scan_bt.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                 
            }
        });
         
        history_bt.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                 
            }
        });
```
匿名内部类是唯一一种没有构造器的类。正因为其没有构造器，所以匿名内部类的使用范围非常有限，大部分匿名内部类用于接口回调。匿名内部类在编译的时候由系统自动起名为Outter$1.class。一般来说，匿名内部类用于继承其他类或是实现接口，并不需要增加额外的方法，只是对继承方法的实现或是重写。

* 静态内部类也是定义在另一个类里面的类，只不过在类的前面多了一个关键字static。静态内部类是不需要依赖于外部类的，这点和类的静态成员属性有点类似，并且它不能使用外部类的非static成员变量或者方法，这点很好理解，因为在没有外部类的对象的情况下，可以创建静态内部类的对象，如果允许访问外部类的非static成员就会产生矛盾，因为外部类的非static成员必须依附于具体的对象。

2.加载一个类时，其内部类是否同时被加载？下面我们做一个实验来看一下。
```
public class Outer {  
    static {  
        System.out.println("load outer class...");  
    }  
      
    //静态内部类  
    static class StaticInner {  
        static {  
            System.out.println("load static inner class...");  
        }  
          
        static void staticInnerMethod() {  
            System.out.println("static inner method...");  
        }  
    }  
          
    public static void main(String[] args) {  
        Outer outer = new Outer();      //此刻其内部类是否也会被加载？  
         System.out.println("===========分割线===========");  
        Outer.StaticInner.staticInnerMethod();      //调用内部类的静态方法  
    }  
}  
```
运行结果： 

```
load outer class... 
==========分割线========== 
load static inner class... 
static inner method...
```

调用构造方法时，外部类Outer被加载，但这时其静态内部类StaticInner却未被加载。直到调用该内部类的静态方法（在分割线以下），StaticInner才被加载。可以做类似的实验验证非静态内部类的情况。 
    结论：加载一个类时，其内部类不会同时被加载。一个类被加载，当且仅当其某个静态成员（静态域、构造器、静态方法等）被调用时发生。 

内部类是延时加载的，也就是说只会在第一次使用时加载。不使用就不加载，所以可以很好的实现单例模式。

这个用例可以很清楚的看到内部类（不论是静态内部类还是非静态内部类）都是在第一次使用时才会被加载。

对于非静态内部类是不能出现静态模块（包含静态块，静态属性，静态方法等）

# 10.Java模块化实现（好处，原因）
1. Java的模块化功能是一个Java 9的功能，模块化(Modular)；这可能使Java有史以来最大的Feature，它将自己长期依赖JRE的结构，转变成以Module为基础的组件，这感觉就像一个壮士，需要把自己的胳膊，腿等，一个个拆下来，并且还能够正常运行工作，难度可想而知。
2. 模块化的功能：
模块化的功能有几个目的：


```
让Java的SE程序更加容易轻量级部署
改进组件间的依赖管理，引入比Jar粒度更大的Module
改进性能和安全性
```
相比于Java,其实js领域很早就进行了模块化了。闲话少扯。今天我们来看看Java9是怎么做到模块化的。

熟悉Java的同学都知道,在Java(1--->8)之前,使用的都是一个很大的jar文件rt.jar 这个jar包在Java8 中有60多M.并且,在我们的Java代码运行时,还需要tools.jar这个jar,这个jar也不小。

也有十几M。这两个文件是运行一个Java程序最小的所需环境。也就是说运行一个Helloworld java程序也需要引入几十M的jar。

模块就是代码和数据的封装体,代码是指一个packages,就是以前我们写的一个个的包。而模块是一个或者多个包的集合体。

3. 什么是Java Module(模块）
模块就是代码和数据的封装体，代码是指一些包括类型的Packages。Package是一些类路径名字的约定，而模块是一个或多个Packages组成的一个封装体。

![image](https://images2015.cnblogs.com/blog/742978/201704/742978-20170419170721696-168545877.jpg)

代码示例

```
模块的是通过module-info.java进行定义，编译后打包后，就成为一个模块的实体；
在模块的定义文件中，我们需要指定模块之间的依赖靠关系，可以exports给那些模块用，需要使用那些模块(requires) 。

module com.foo.bar {
requires org.baz.qux;
exportscom.foo.bar.alpha;
exportscom.foo.bar.beta;
}
META-INF/
META-INF/MANIFEST.MF
module-info.class
com/foo/bar/alpha/AlphaFactory.class
com/foo/bar/alpha/Alpha.class
...
```
4. 模块化的代码实现
* 在Java 9中，为了定义模块，需要为java文件设定一个特别的名字，建议为： module-info.java

* module-info.java 应该放在哪里呢？按照惯例，应该把它放在和模块名相同的目录下。
```
如果你的模块名称是

com.me.mymodule

那么你的模块  module-info.java 应该放在：

src/com.me.mymodule

这使您的  module-info.java 文件放在：

src/com.me.mymodule/module-info.java 路径。

得到它了？ <module-path> == <module name> 
```
* 编写一个模块：现在我们知道我们的模块 filename 和我们的模块 filepath，我们来编写一个具有这个命名和文件夹约定的模块：

```
tomerb@tomerb-mac.local:~/tmp/java9-modules$ mkdir -p src/com.me.mymodule
tomerb@tomerb-mac.local:~/tmp/java9-modules$ vi src/com.me.mymodule/module-info.java

module com.me.mymodule { }
```
* 向我们的模块添加代码

```
mkdir -p src/com.me.mymodule/com/me/mymodule
$ vi src/com.me.mymodule/com/me/mymodule/Main.java

package com.me.mymodule;
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World from Module! :)"); // nothing special here, standard java < 9 class.
    }
}
```
注意我们代码中的目录名称。为什么这样做呢？ 我们首先输入我们模块所在的路径，然后我们为源代码创建完整的包名称。在这种情况下， /com/me/mymodule 在 /com.me.mymodule 的上层。只是我们的源文件属于我们的模块，并且模块已经在Java 9惯例的标准模块目录中。

所以，我们就在Main.java编写hello world：
* 首先制作 mods 目录（我们将进一步传递给： java --module-path）：
```
$ mkdir -p mods/com.me.mymodule
$ javac -d mods/com.me.mymodule \
          src/com.me.mymodule/module-info.java \
          src/com.me.mymodule/com/me/mymodule/Main.java
          
$ java --module-path mods -m com.me.mymodule/com.me.mymodule.Main
Hello World from Module! :)
```



5. 模块的原理
* 将系统内部类进行模块化：
这样不用在区分太多J2ME, J2SE，J2EE了，大家都是用模块作为沟通语言。这需要整理所有的类和它们调用关系，调用频次等，把系统类模块化，这可能最复杂的一部分，不过结果是完美的。
* 将ClassLoader分级：
将ClassLoader分为三个级别，Bootstrap Loader具有最高优先级和权限，主要是核心的系统类；Platform Loader用于扩展的一些系统类，例如SQL,XML等；Application Loader主要用于应用程序的Loader。在这三个级别的Loader下面有一个统一Module 管理，用于控制和管理模块间的依赖关系，可读性，可访问性等。 注意，ClassLoader在Java 9中的类装载逻辑和之前一样，但是，通过模块管理系统，ClassLoader.FindClass的能力，将被限制在readable&accessible的条件下，而不是之前的简单的Public条件。
![image](http://img.blog.csdn.net/20170923014152225?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcmlja2l5ZWF0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

6. 访问权限的变化

还有,在Java9中,public 访问修饰符不再意味着可以访问了。

模块之间的关系被称作readability（可读性），代表一个模块是否可以找到这个模块文件，并且读入系统中（注意：并非代表可以访问其中的类型）。在实际的代码，一个类型对于另外一个类型的调用，我们称之为可访问性(Accessible)，这意味着可以使用这个类型； 可访问性的前提是可读性，换句话说，现有模块可读，然后再进一步检测可访问性（安全）。

![image](http://img.blog.csdn.net/20170910141822468?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzgwMzI2Mg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

![image](http://img.blog.csdn.net/20170910141908667?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzgwMzI2Mg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

就是模块先要是是可读的，然后类型是可访问的，这样你才能引用另外一个模块里面的可访问的数据。

在介绍一下什么是模块的Transitive引用(间接引用)

当我们的模块2引用了模块1,如果模块1引用了java.sql模块。实际上模块2是引用不到java.sql模块的。

但是如果我们将模块1的引用申明为间接引用了java.sql模块,则模块2可以引用到java.sql模块。

这个跟maven的依赖传递有点类似。

# 11.java注解
1. 初学者可以这样理解注解：想像代码具有生命，注解就是对于代码中某些鲜活个体的贴上去的一张标签。简化来讲，注解如同一张标签。
2. 注解语法：因为平常开发少见，相信有不少的人员会认为注解的地位不高。其实同 classs 和 interface 一样，注解也属于一种类型。它是在 Java SE 5.0 版本中开始引入的概念。
3. 注解的定义：注解通过 @interface 关键字进行定义。

```
public @interface TestAnnotation {
}
```
它的形式跟接口很类似，不过前面多了一个 @ 符号。上面的代码就创建了一个名字为 TestAnnotaion 的注解。

你可以简单理解为创建了一张名字为 TestAnnotation 的标签。
4. 注解的应用

上面创建了一个注解，那么注解的的使用方法是什么呢。


```
@TestAnnotation
public class Test {
}
```
创建一个类 Test,然后在类定义的地方加上 @TestAnnotation 就可以用 TestAnnotation 注解这个类了。

你可以简单理解为将 TestAnnotation 这张标签贴到 Test 这个类上面。

不过，要想注解能够正常工作，还需要介绍一下一个新的概念那就是元注解。

5. 元注解
元注解是什么意思呢？

元注解是可以注解到注解上的注解，或者说元注解是一种基本注解，但是它能够应用到其它的注解上面。

如果难于理解的话，你可以这样理解。元注解也是一张标签，但是它是一张特殊的标签，它的作用和目的就是给其他普通的标签进行解释说明的。

元标签有 @Retention、@Documented、@Target、@Inherited、@Repeatable 5 种。
* @Retention：Retention 的英文意为保留期的意思。当 @Retention 应用到一个注解上的时候，它解释说明了这个注解的的存活时间。

它的取值如下： 

```
- RetentionPolicy.SOURCE 注解只在源码阶段保留，在编译器进行编译时它将被丢弃忽视。 
- RetentionPolicy.CLASS 注解只被保留到编译进行的时候，它并不会被加载到 JVM 中。 
- RetentionPolicy.RUNTIME 注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们。

```


我们可以这样的方式来加深理解，@Retention 去给一张标签解释的时候，它指定了这张标签张贴的时间。@Retention 相当于给一张标签上面盖了一张时间戳，时间戳指明了标签张贴的时间周期。


```
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
}
```
* @Documented

顾名思义，这个元注解肯定是和文档有关。它的作用是能够将注解中的元素包含到 Javadoc 中去。

* @Target

Target 是目标的意思，@Target 指定了注解运用的地方。

你可以这样理解，当一个注解被 @Target 注解时，这个注解就被限定了运用的场景。

类比到标签，原本标签是你想张贴到哪个地方就到哪个地方，但是因为 @Target 的存在，它张贴的地方就非常具体了，比如只能张贴到方法上、类上、方法参数上等等。

* @Inherited

Inherited 是继承的意思，但是它并不是说注解本身可以继承，而是说如果一个超类被 @Inherited 注解过的注解进行注解的话，那么如果它的子类没有被任何注解应用的话，那么这个子类就继承了超类的注解。 

注解 Test 被 @Inherited 修饰，之后类 A 被 Test 注解，类 B 继承 A,类 B 也拥有 Test 这个注解。

* @Repeatable

Repeatable 自然是可重复的意思。@Repeatable 是 Java 1.8 才加进来的，所以算是一个新的特性。

什么样的注解会多次应用呢？通常是注解的值可以同时取多个。

6. 注解的属性

注解的属性也叫做成员变量。注解只有成员变量，没有方法。注解的成员变量在注解的定义中以“无形参的方法”形式来声明，其方法名定义了该成员变量的名字，其返回值定义了该成员变量的类型。
```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {

    int id();

    String msg();

}
```
上面代码定义了 TestAnnotation 这个注解中拥有 id 和 msg 两个属性。在使用的时候，我们应该给它们进行赋值。

赋值的方式是在注解的括号内以 value=”” 形式，多个属性之前用 ，隔开。
```
@TestAnnotation(id=3,msg="hello annotation")
public class Test {

}
```
需要注意的是，在注解中定义属性时它的类型必须是 8 种基本数据类型外加 类、接口、注解及它们的数组。

注解中属性可以有默认值，默认值需要用 default 关键值指定。比如：
```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {

    public int id() default -1;

    public String msg() default "Hi";

}
```
TestAnnotation 中 id 属性默认值为 -1，msg 属性默认值为 Hi。 
它可以这样应用。

7. Java 预置的注解
学习了上面相关的知识，我们已经可以自己定义一个注解了。其实 Java 语言本身已经提供了几个现成的注解。

* @Deprecated

这个元素是用来标记过时的元素，想必大家在日常开发中经常碰到。编译器在编译阶段遇到这个注解时会发出提醒警告，告诉开发者正在调用一个过时的元素比如过时的方法、过时的类、过时的成员变量。


```
public class Hero {

    @Deprecated
    public void say(){
        System.out.println("Noting has to say!");
    }


    public void speak(){
        System.out.println("I have a dream!");
    }


}
```
定义了一个 Hero 类，它有两个方法 say() 和 speak() ，其中 say() 被 @Deprecated 注解。然后我们在 IDE 中分别调用它们。 

![image](http://img.blog.csdn.net/20170627214008588?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYnJpYmx1ZQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

* @Override

这个大家应该很熟悉了，提示子类要复写父类中被 @Override 修饰的方法

* @SuppressWarnings

阻止警告的意思。之前说过调用被 @Deprecated 注解的方法后，编译器会警告提醒，而有时候开发者会忽略这种警告，他们可以在调用的地方通过 @SuppressWarnings 达到目的。

* @FunctionalInterface

函数式接口注解，这个是 Java 1.8 版本引入的新特性。函数式编程很火，所以 Java 8 也及时添加了这个特性。

函数式接口 (Functional Interface) 就是一个具有一个方法的普通接口。

比如
```
@FunctionalInterface
public interface Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    public abstract void run();
}
```
我们进行线程开发中常用的 Runnable 就是一个典型的函数式接口，上面源码可以看到它就被 @FunctionalInterface 注解。

可能有人会疑惑，函数式接口标记有什么用，这个原因是函数式接口可以很容易转换为 Lambda 表达式。这是另外的主题了，有兴趣的同学请自己搜索相关知识点学习。

7. 注解和反射
* 我通过用标签来比作注解，前面的内容是讲怎么写注解，然后贴到哪个地方去，而现在我们要做的工作就是检阅这些标签内容。 形象的比喻就是你把这些注解标签在合适的时候撕下来，然后检阅上面的内容信息。要想正确检阅注解，离不开一个手段，那就是反射。
```
注解通过反射获取。首先可以通过 Class 对象的 isAnnotationPresent() 方法判断它是否应用了某个注解

public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {}

然后通过 getAnnotation() 方法来获取 Annotation 对象。

 public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {}

或者是 getAnnotations() 方法。


public Annotation[] getAnnotations() {}
前一种方法返回指定类型的注解，后一种方法返回注解到这个元素上的所有注解。


```
如果获取到的 Annotation 如果不为 null，则就可以调用它们的属性方法了。比如


```
@TestAnnotation()
public class Test {

    public static void main(String[] args) {

        boolean hasAnnotation = Test.class.isAnnotationPresent(TestAnnotation.class);

        if ( hasAnnotation ) {
            TestAnnotation testAnnotation = Test.class.getAnnotation(TestAnnotation.class);

            System.out.println("id:"+testAnnotation.id());
            System.out.println("msg:"+testAnnotation.msg());
        }

    }

}
```

8. 注解的使用场景

我相信博文讲到这里大家都很熟悉了注解，但是有不少同学肯定会问，注解到底有什么用呢？

对啊注解到底有什么用？

我们不妨将目光放到 Java 官方文档上来。

文章开始的时候，我用标签来类比注解。但标签比喻只是我的手段，而不是目的。为的是让大家在初次学习注解时能够不被那些抽象的新概念搞懵。既然现在，我们已经对注解有所了解，我们不妨再仔细阅读官方最严谨的文档。


```
注解是一系列元数据，它提供数据用来解释程序代码，但是注解并非是所解释的代码本身的一部分。注解对于代码的运行效果没有直接影响。
注解有许多用处，主要如下： 
- 提供信息给编译器： 编译器可以利用注解来探测错误和警告信息 
- 编译阶段时的处理： 软件工具可以用来利用注解信息来生成代码、Html文档或者做其它相应处理。 
- 运行时的处理： 某些注解可以在程序运行的时候接受代码的提取
```
还是回到官方文档的解释上，注解主要针对的是编译器和其它工具软件(SoftWare tool)。

当开发者使用了Annotation 修饰了类、方法、Field 等成员之后，这些 Annotation 不会自己生效，必须由开发者提供相应的代码来提取并处理 Annotation 信息。这些处理提取和处理 Annotation 的代码统称为 APT（Annotation Processing Tool)。

现在，我们可以给自己答案了，注解有什么用？给谁用？给 编译器或者 APT 用的。


# 9.应用实例
注解运用的地方太多了，因为我是 Android 开发者，所以我接触到的具体例子有下：

JUnit

JUnit 这个是一个测试框架，典型使用方法如下：
```
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}
```
@Test 标记了要进行测试的方法 addition_isCorrect().

* ButterKnife

ButterKnife 是 Android 开发中大名鼎鼎的 IOC 框架，它减少了大量重复的代码。

```
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_test)
    TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }
}
```

* Dagger2

也是一个很有名的依赖注入框架。

* Retrofit

很牛逼的 Http 网络访问框架
```
public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
}

Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build();

GitHubService service = retrofit.create(GitHubService.class);
```


# 12.当一个对象被当作参数传递到一个方法后，此方法可改变这个对象的属性，并可返回变化后的结果，那么这里到底是值传递还是引用传递？

值传递：

方法调用时，实际参数把它的值传递给对应的形式参数，函数接收的是原始值的一个copy，此时内存中存在两个相等的基本类型，即实际参数和形式参数，后面方法中的操作都是对形参这个值的修改，不影响实际参数的值。

引用传递：

也称为传地址。方法调用时，实际参数的引用(地址，而不是参数的值)被传递给方法中相对应的形式参数，函数接收的是原始值的内存地址；
在方法执行中，形参和实参内容相同，指向同一块内存地址，方法执行中对引用的操作将会影响到实际对象。

是值传递。Java语言的方法调用只支持参数的值传递。当一个对象实例作为一个参数被传递到方法中时，参数的值就是对该对象的引用。对象的属性可以在被调用过程中被改变，但对对象引用的改变是不会影响到调用者的。C++和C#中可以通过传引用或传输出参数来改变传入的参数的值。在C#中可以编写如下所示的代码，但是在Java中却做不到。

# 13.重载（Overload）和重写（Override）的区别。重载的方法能否根据返回类型进行区分？
* 方法的重载和重写都是实现多态的方式，区别在于前者实现的是编译时的多态性，而后者实现的是运行时的多态性。重载发生在一个类中，同名的方法如果有不同的参数列表（参数类型不同、参数个数不同或者二者都不同）则视为重载；重写发生在子类与父类之间，重写要求子类被重写方法与父类被重写方法有相同的返回类型，比父类被重写方法更好访问，不能比父类被重写方法声明更多的异常（里氏代换原则）。重载对返回类型没有特殊的要求。

* 因为调用时不能指定类型信息，编译器不知道你要调用哪个函数。
```
例如
float max(int a, int b);
int max(int a, int b);
```
当调用max(1, 2);时无法确定调用的是哪个，单从这一点上来说，仅返回值类型不同的重载是不应该允许的。

函数的返回值只是作为函数运行之后的一个“状态”
他是保持方法的调用者与被调用者进行通信的关键。
并不能作为某个方法的“标识”

# 14.描述一下JVM加载class文件的原理机制？
JVM中类的装载是由类加载器（ClassLoader）和它的子类来实现的，Java中的类加载器是一个重要的Java运行时系统组件，它负责在运行时查找和装入类文件中的类。
由于Java的跨平台性，经过编译的Java源程序并不是一个可执行程序，而是一个或多个类文件。当Java程序需要使用某个类时，JVM会确保这个类已经被加载、连接（验证、准备和解析）和初始化。类的加载是指把类的.class文件中的数据读入到内存中，通常是创建一个字节数组读入.class文件，然后产生与所加载类对应的Class对象。加载完成后，Class对象还不完整，所以此时的类还不可用。当类被加载后就进入连接阶段，这一阶段包括验证、准备（为静态变量分配内存并设置默认的初始值）和解析（将符号引用替换为直接引用）三个步骤。最后JVM对类进行初始化，包括：1)如果类存在直接的父类并且这个类还没有被初始化，那么就先初始化父类；2)如果类中存在初始化语句，就依次执行这些初始化语句。

类的加载是由类加载器完成的，类加载器包括：根加载器（BootStrap）、扩展加载器（Extension）、系统加载器（System）和用户自定义类加载器（java.lang.ClassLoader的子类）。从Java 2（JDK 1.2）开始，类加载过程采取了父亲委托机制（PDM）。PDM更好的保证了Java平台的安全性，在该机制中，JVM自带的Bootstrap是根加载器，其他的加载器都有且仅有一个父类加载器。类的加载首先请求父类加载器加载，父类加载器无能为力时才由其子类加载器自行加载。JVM不会向Java程序提供对Bootstrap的引用。下面是关于几个类加载器的说明：



# 15.抽象类（abstract class）和接口（interface）有什么异同？
1.相同点：
*抽象类和接口都不能够实例化，但可以定义抽象类和接口类型的引用。一个类如果继承了某个抽象类或者实现了某个接口都需要对其中的抽象方法全部进行实现，否则该类仍然需要被声明为抽象类。

2.不同点
* 接口比抽象类更加抽象，因为抽象类中可以定义构造器，可以有抽象方法和具体方法，而接口中不能定义构造器而且其中的方法全部都是抽象方法
* 抽象类中的成员可以是private、默认、protected、public的，而接口中的成员全都是public的。
* 抽象类中可以定义成员变量，而接口中定义的成员变量实际上都是常量。有抽象方法的类必须被声明为抽象类，而抽象类未必要有抽象方法。



![image](https://upload-images.jianshu.io/upload_images/944365-0ff5d61b5b2eeaf0.png?imageMogr2/auto-orient/)

# 16.静态嵌套类(Static Nested Class)和内部类（Inner Class）的不同？
Static Nested Class是被声明为静态（static）的内部类，它可以不依赖于外部类实例被实例化。而通常的内部类需要在外部类实例化后才能实例化，其语法看起来挺诡异的，

比如一道面试题--面试题 – 下面的代码哪些地方会产生编译错误？
```
class Outer {
 
    class Inner {}
 
    public static void foo() { new Inner(); }
 
    public void bar() { new Inner(); }
 
    public static void main(String[] args) {
        new Inner();
    }
}
```
Java中非静态内部类对象的创建要依赖其外部类对象，上面的面试题中foo和main方法都是静态方法，静态方法中没有this，也就是说没有所谓的外部类对象，因此无法创建内部类对象，如果要在静态方法中创建内部类对象，可以这样做：（内部类的实例化必须要有外部类的实例）
```
new Outer().new Inner();
```

# 17.接口是否可继承（extends）接口？抽象类是否可实现（implements）接口？抽象类是否可继承具体类（concrete class）？
接口可以继承接口，而且支持多重继承。抽象类可以实现(implements)接口，抽象类可继承具体类也可以继承抽象类。

# 18.Anonymous Inner Class(匿名内部类)是否可以继承其它类？是否可以实现接口？
可以继承其他类或实现其他接口，在Swing编程和Android开发中常用此方式来实现事件监听和回调。

# 19.成员内部类可以引用它的包含类（外部类）的成员吗？有没有什么限制？为什么？
1. 一个成员内部类对象（静态内部类不能访问非static外部类对象）可以访问创建它的外部类对象的成员，包括私有成员。

2. 具体究竟是如何实现的呢？下面通过反编译字节码文件看看究竟。事实上，编译器在进行编译的时候，会将成员内部类单独编译成一个字节码文件，下面是Outter.java的代码：
```
public class Outter {
    private Inner inner = null;
    public Outter() {
         
    }
     
    public Inner getInnerInstance() {
        if(inner == null)
            inner = new Inner();
        return inner;
    }
      
    protected class Inner {
        public Inner() {
             
        }
    }
}
```
编译之后，出现了两个字节码文件：
![image](https://images0.cnblogs.com/i/288799/201407/021630063402064.jpg)

反编译Outter$Inner.class文件，发现一个指向外部类对象的指针，看到这里想必大家豁然开朗了。也就是说编译器会默认为成员内部类添加了一个指向外部类对象的引用，那么这个引用是如何赋初值的呢？

虽然我们在定义的内部类的构造器是无参构造器，编译器还是会默认添加一个参数，该参数的类型为指向外部类对象的一个引用，所以成员内部类中的Outter this&0 指针便指向了外部类对象，因此可以在成员内部类中随意访问外部类的成员。从这里也间接说明了成员内部类是依赖于外部类的，如果没有创建外部类的对象，则无法对Outter this&0引用进行初始化赋值，也就无法创建成员内部类的对象了。

# 20.Java 中的final关键字有哪些用法？
(1)修饰类：表示该类不能被继承；(2)修饰方法：表示方法不能被重写；(3)修饰变量：表示变量只能一次赋值以后值不能被修改（常量）。

# 21.指出下面程序运行结果

```
class A {
 
    static {
        System.out.print("1");
    }
 
    public A() {
        System.out.print("2");
    }
}
 
class B extends A{
 
    static {
        System.out.print("a");
    }
 
    public B() {
        System.out.print("b");
    }
}
 
public class Hello {
 
    public static void main(String[] args) {
        A ab = new B();
        ab = new B();
    }
 
}
```
执行结果：1a2b2b。创建对象时构造器的调用顺序是：先初始化静态成员，然后调用父类构造器，再初始化非静态成员，最后调用自身构造器。

# 22.什么时候用断言（assert）？
断言在软件开发中是一种常用的调试方式，很多开发语言中都支持这种机制。一般来说，断言用于保证程序最基本、关键的正确性。断言检查通常在开发和测试时开启。为了保证程序的执行效率，在软件发布后断言检查通常是关闭的。断言是一个包含布尔表达式的语句，在执行这个语句时假定该表达式为true；如果表达式的值为false，那么系统会报告一个AssertionError。断言的使用如下面的代码所示：
```
assert(a > 0); // throws an AssertionError if a <= 0    
```
简单的说，如果希望在不满足某些条件时阻止代码的执行，就可以考虑用断言来阻止它。

# 23.使用内部类的好处？
为什么在Java中需要内部类？总结一下主要有以下四点：

　　1.每个内部类都能独立的继承一个接口的实现，所以无论外部类是否已经继承了某个(接口的)实现，对于内部类都没有影响。内部类使得多继承的解决方案变得完整，

　　2.方便将存在一定逻辑关系的类组织在一起，又可以对外界隐藏。

　　3.方便编写事件驱动程序

　　4.方便编写线程代码

　　个人觉得第一点是最重要的原因之一，内部类的存在使得Java的多继承机制变得更加完善。

# 24.什么事反射，在哪里需要用到？
反射机制是在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意一个方法和属性；这种动态获取的信息以及动态调用对象的方法的功能称为java语言的反射机制。

反射机制主要提供了以下功能： 

```
在运行时判断任意一个对象所属的类；

在运行时构造任意一个类的对象；

在运行时判断任意一个类所具有的成员变量和方法；

在运行时调用任意一个对象的方法；

生成动态代理。
```

