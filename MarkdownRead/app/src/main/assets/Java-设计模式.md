# 1.简述一下你了解的设计模式
1. 所谓设计模式，就是一套被反复使用的代码设计经验的总结（情境中一个问题经过证实的一个解决方案）。使用设计模式是为了可重用代码、让代码更容易被他人理解、保证代码可靠性。设计模式使人们可以更加简单方便的复用成功的设计和体系结构。将已证实的技术表述成设计模式也会使新系统开发者更加容易理解其设计思路。

```
提高代码复用率，降低开发成本和周期
提高代码可维护性、可拓展性
使代码更加优雅
让代码更容易被他人理解
```

2. 在GoF的《Design Patterns: Elements of Reusable Object-Oriented Software》中给出了三类（创建型[对类的实例化过程的抽象化]、结构型[描述如何将类或对象结合在一起形成更大的结构]、行为型[对在不同的对象之间划分责任和算法的抽象化]）共23种设计模式
* Abstract Factory（抽象工厂模式），Builder（建造者模式），Factory Method（工厂方法模式），Prototype（原始模型模式），Singleton（单例模式）；Facade（门面模式），Adapter（适配器模式），Bridge（桥梁模式），Composite（合成模式），Decorator（装饰模式），Flyweight（享元模式），Proxy（代理模式）；Command（命令模式），Interpreter（解释器模式），Visitor（访问者模式），Iterator（迭代子模式），Mediator（调停者模式），Memento（备忘录模式），Observer（观察者模式），State（状态模式），Strategy（策略模式），Template Method（模板方法模式）， Chain Of Responsibility（责任链模式）。

![image](https://upload-images.jianshu.io/upload_images/944365-01a94a08a13b32a0.jpg?imageMogr2/auto-orient/)
面试被问到关于设计模式的知识时，可以拣最常用的作答，例如：
* 工厂模式：工厂类可以根据条件生成不同的子类实例，这些子类有一个公共的抽象父类并且实现了相同的方法，但是这些方法针对不同的数据进行了不同的操作（多态方法）。当得到子类的实例后，开发人员可以调用基类中的方法而不必考虑到底返回的是哪一个子类的实例。
* 代理模式：给一个对象提供一个代理对象，并由代理对象控制原对象的引用。实际开发中，按照使用目的的不同，代理可以分为：远程代理、虚拟代理、保护代理、Cache代理、防火墙代理、同步化代理、智能引用代理。
* 适配器模式：把一个类的接口变换成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起使用的类能够一起工作。
* 模板方法模式：提供一个抽象类，将部分逻辑以具体方法或构造器的形式实现，然后声明一些抽象方法来迫使子类实现剩余的逻辑。不同的子类可以以不同的方式实现这些抽象方法（多态实现），从而实现不同的业务逻辑。
* 除此之外，还可以讲讲上面提到的门面模式、桥梁模式、单例模式、装潢模式（Collections工具类和I/O系统中都使用装潢模式）等，反正基本原则就是拣自己最熟悉的、用得最多的作答，以免言多必失。

# 2.几种Java中常用过的设计模式以及应用范例。
1. 创建型设计模式：工厂方法模式、单例模式和建造者模式

2. 结构型设计模式：适配器模式、装潢模式和代理模式

3. 行为型设计模式：观察者模式、策略模式和模板方法模式


## 2.1.1工厂方法模式：
又称工厂模式、多态工厂模式和虚拟构造器模式，通过定义工厂父类负责定义创建对象的公共接口，而子类则负责生成具体的对象。

作用：将类的实例化（具体产品的创建）延迟到工厂类的子类（具体工厂）中完成，即由子类来决定应该实例化（创建）哪一个类。

工厂一旦需要生产新产品就需要修改工厂类的方法逻辑，违背了“开放 - 关闭原则（简单工厂模式的缺点）

之所以可以解决简单工厂的问题，是因为工厂方法模式把具体产品的创建推迟到工厂类的子类（具体工厂）中，此时工厂类不再负责所有产品的创建，而只是给出具体工厂必须实现的接口，这样工厂方法模式在添加新产品的时候就不修改工厂类逻辑而是添加新的工厂子类，符合开放封闭原则，克服了简单工厂模式中缺点

![image](https://upload-images.jianshu.io/upload_images/944365-27764702a32834a3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

```
组成（角色）	关系	作用
抽象产品（Product）	具体产品的父类	描述具体产品的公共接口
具体产品（Concrete Product）	抽象产品的子类；工厂类创建的目标类	描述生产的具体产品
抽象工厂（Creator）	具体工厂的父类	描述具体工厂的公共接口
具体工厂（Concrete Creator）	抽象工厂的子类；被外界调用	描述具体工厂；实现FactoryMethod工厂方法创建产品的实例


```

实例：


```
背景：小成有一间塑料加工厂（仅生产A类产品）；随着客户需求的变化，客户需要生产B类产品；
冲突：改变原有塑料加工厂的配置和变化非常困难，假设下一次客户需要再发生变化，再次改变将增大非常大的成本；
解决方案：小成决定置办塑料分厂B来生产B类产品；

步骤1： 创建抽象工厂类，定义具体工厂的公共接口

abstract class Factory{
    public abstract Product Manufacture();
}
步骤2： 创建抽象产品类 ，定义具体产品的公共接口；

abstract class Product{
    public abstract void Show();
}
步骤3： 创建具体产品类（继承抽象产品类）， 定义生产的具体产品；

//具体产品A类
class  ProductA extends  Product{
    @Override
    public void Show() {
        System.out.println("生产出了产品A");
    }
}

//具体产品B类
class  ProductB extends  Product{

    @Override
    public void Show() {
        System.out.println("生产出了产品B");
    }
}
步骤4：创建具体工厂类（继承抽象工厂类），定义创建对应具体产品实例的方法；

//工厂A类 - 生产A类产品
class  FactoryA extends Factory{
    @Override
    public Product Manufacture() {
        return new ProductA();
    }
}

//工厂B类 - 生产B类产品
class  FactoryB extends Factory{
    @Override
    public Product Manufacture() {
        return new ProductB();
    }
}
步骤5：外界通过调用具体工厂类的方法，从而创建不同具体产品类的实例

//生产工作流程
public class FactoryPattern {
    public static void main(String[] args){
        //客户要产品A
        FactoryA mFactoryA = new FactoryA();
        mFactoryA.Manufacture().Show();

        //客户要产品B
        FactoryB mFactoryB = new FactoryB();
        mFactoryB.Manufacture().Show();
    }
}

结果：

生产出了产品A
生产出了产品B

```
优点：

```
更符合开-闭原则
新增一种产品时，只需要增加相应的具体产品类和相应的工厂子类即可
简单工厂模式需要修改工厂类的判断逻辑

符合单一职责原则
每个具体工厂类只负责创建对应的产品
简单工厂中的工厂类存在复杂的switch逻辑判断

不使用静态工厂方法，可以形成基于继承的等级结构。
简单工厂模式的工厂类使用静态工厂方法

总结：工厂模式可以说是简单工厂模式的进一步抽象和拓展，在保留了简单工厂的封装优点的同时，让扩展变得简单，让继承变得可行，增加了多态性的体现。

```

缺点：

```
添加新产品时，除了增加新产品类外，还要提供与之对应的具体工厂类，系统类的个数将成对增加，在一定程度上增加了系统的复杂度；同时，有更多的类需要编译和运行，会给系统带来一些额外的开销；
由于考虑到系统的可扩展性，需要引入抽象层，在客户端代码中均使用抽象层进行定义，增加了系统的抽象性和理解难度，且在实现时可能需要用到DOM、反射等技术，增加了系统的实现难度。
虽然保证了工厂方法内的对修改关闭，但对于使用工厂方法的类，如果要更换另外一种产品，仍然需要修改实例化的具体工厂类；
一个具体工厂只能创建一种具体产品


```
Java中应用：[实现一个简单计算器](http://blog.csdn.net/pnjlc/article/details/52711486)


```
一般的MVC框架中，都有一个基本的DB数据库基本操作类
我叫它DB class，有一个baseModel class 去继承 db class
baseModel 是所有框架model的基类，需要继承baseModel
baseModel已经有db类的 增删查改的方法了，baseModel其实就是数据库工厂，不同的模型继承baseModel，就有操作不同数据表的对象实例了，这样就用一个基础的class 完成了实例化各个不同数据表的对象，就好像是工厂一样，传不同的表名字就返回给你不同的对象。
我的理解就是这样的，如有误，还请包涵和斧正。
```


## 2.1.2单例模式

实现1个类只有1个实例化对象 & 提供一个全局访问点

* 作用保证1个类只有1个对象，降低对象之间的耦合度

* 工作原理：在Java中，我们通过使用对象（类实例化后）来操作这些类，类实例化是通过它的构造方法进行的，要是想实现一个类只有一个实例化对象，就要对类的构造方法下功夫：

* 单例模式的一般实现：（含使用步骤）
```
public class Singleton {
//1. 创建私有变量 ourInstance（用以记录 Singleton 的唯一实例）
//2. 内部进行实例化
    private static Singleton ourInstance  = new  Singleton();

//3. 把类的构造方法私有化，不让外部调用构造方法实例化
    private Singleton() {
    }
//4. 定义公有方法提供该类的全局唯一访问点
//5. 外部通过调用getInstance()方法来返回唯一的实例
    public static  Singleton newInstance() {
        return ourInstance;
    }
}

```

* 优点

提供了对唯一实例的受控访问；
由于在系统内存中只存在一个对象，因此可以节约系统资源，对于一些需要频繁创建和销毁的对象单例模式无疑可以提高系统的性能；
可以根据实际情况需要，在单例模式的基础上扩展做出双例模式，多例模式；

* 缺点

单例类的职责过重，里面的代码可能会过于复杂，在一定程度上违背了“单一职责原则”。
如果实例化的对象长时间不被利用，会被系统认为是垃圾而被回收，这将导致对象状态的丢失。

* 实现方式

![image](https://upload-images.jianshu.io/upload_images/944365-9981462dbf695c86.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)
1. 饿汉式

这是 最简单的单例实现方式
```
1.JVM在类的初始化阶段(即 在Class被加载后、被线程使用前)，会执行类的初始化
2.在执行类的初始化期间，JVM会去获取一个锁。这个锁可以同步多个线程对同一个类的初始化
```
具体实现
```
class Singleton {

    // 1. 加载该类时，单例就会自动被创建
    private static  Singleton ourInstance  = new  Singleton();
    
    // 2. 构造函数 设置为 私有权限
    // 原因：禁止他人创建实例 
    private Singleton() {
    }
    
    // 3. 通过调用静态方法获得创建的单例
    public static  Singleton newInstance() {
        return ourInstance;
    }
}

```
除了初始化单例类时 即 创建单例外，继续延伸出来的是：单例对象 要求初始化速度快 & 占用内存小

2. 枚举类型

根据枚举类型的下述特点，满足单例模式所需的 创建单例、线程安全、实现简洁的需求

![image](https://upload-images.jianshu.io/upload_images/944365-bdccdb7827be2eb8.jpg?imageMogr2/auto-orient/)
实现方式
```
public enum Singleton{

    //定义1个枚举的元素，即为单例类的1个实例
    INSTANCE;

    // 隐藏了1个空的、私有的 构造方法
    // private Singleton () {}

}

// 获取单例的方式：
Singleton singleton = Singleton.INSTANCE;

作者：Carson_Ho
链接：https://www.jianshu.com/p/b8c578b07fbc
來源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```
注：这是 最简洁、易用 的单例实现方式，借用《Effective Java》的话：单元素的枚举类型已经成为实现 Singleton的最佳方法

3. 懒汉式（基础实现）

与 饿汉式 最大的区别是：单例创建的时机
```
饿汉式：单例创建时机不可控，即类加载时 自动创建 单例
懒汉式：单例创建时机可控，即有需要时，才 手动创建 单例
```

```
class Singleton {
    // 1. 类加载时，先不自动创建单例
   //  即，将单例的引用先赋值为 Null
    private static  Singleton ourInstance  = null；

    // 2. 构造函数 设置为 私有权限
    // 原因：禁止他人创建实例 
    private Singleton() {
    }
    
    // 3. 需要时才手动调用 newInstance（） 创建 单例   
    public static  Singleton newInstance() {
    // 先判断单例是否为空，以避免重复创建
    if( ourInstance == null){
        ourInstance = new Singleton();
        }
        return ourInstance;
    }
}

    
```
缺点
基础实现的懒汉式是线程不安全的，具体原因如下
![image](https://upload-images.jianshu.io/upload_images/944365-ba2f81731ede7035.png?imageMogr2/auto-orient/)

**同步锁（懒汉式的改进）**

原理
使用同步锁 synchronized锁住 创建单例的方法 ，防止多个线程同时调用，从而避免造成单例被多次创建
```
即，getInstance（）方法块只能运行在1个线程中
若该段代码已在1个线程中运行，另外1个线程试图运行该块代码，则 会被阻塞而一直等待
而在这个线程安全的方法里我们实现了单例的创建，保证了多线程模式下 单例对象的唯一性

// 写法1
class Singleton {
    // 1. 类加载时，先不自动创建单例
    //  即，将单例的引用先赋值为 Null
    private static  Singleton ourInstance  = null；
    
    // 2. 构造函数 设置为 私有权限
    // 原因：禁止他人创建实例 
    private Singleton() {
    }
    
// 3. 加入同步锁
public static synchronized Singleton getInstance(){
        // 先判断单例是否为空，以避免重复创建
        if ( ourInstance == null )
            ourInstance = new Singleton();
        return ourInstance;
    }
}


// 写法2
// 该写法的作用与上述写法作用相同，只是写法有所区别
class Singleton{ 

    private static Singleton instance = null;

    private Singleton(){
}

    public static Singleton getInstance(){
        // 加入同步锁
        synchronized(Singleton.class) {
            if (instance == null)
                instance = new Singleton();
        }
        return instance;
    }
}

```
每次访问都要进行线程同步（即 调用synchronized锁)，造成过多的同步开销（加锁 = 耗时、耗能）

实际上只需在第1次调用该方法时才需要同步，一旦单例创建成功后，就没必要进行同步

**双重校验锁（懒汉式的改进**）

原理
在同步锁的基础上，添加1层 if判断：若单例已创建，则不需再执行加锁操作就可获取实例，从而提高性能


```
class Singleton {
    private static  Singleton ourInstance  = null；

    private Singleton() {
    }
    
    public static  Singleton newInstance() {
     // 加入双重校验锁
    // 校验锁1：第1个if
    if( ourInstance == null){  // ①
     synchronized (Singleton.class){ // ②
      // 校验锁2：第2个 if
      if( ourInstance == null){
          ourInstance = new Singleton();
          }
      }
  }
        return ourInstance;
   }
}

// 说明
// 校验锁1：第1个if
// 作用：若单例已创建，则直接返回已创建的单例，无需再执行加锁操作
// 即直接跳到执行 return ourInstance

// 校验锁2：第2个 if 
// 作用：防止多次创建单例问题
// 原理
  // 1. 线程A调用newInstance()，当运行到②位置时，此时线程B也调用了newInstance()
  // 2. 因线程A并没有执行instance = new Singleton();，此时instance仍为空，因此线程B能突破第1层 if 判断，运行到①位置等待synchronized中的A线程执行完毕
  // 3. 当线程A释放同步锁时，单例已创建，即instance已非空
  // 4. 此时线程B 从①开始执行到位置②。此时第2层 if 判断 = 为空（单例已创建），因此也不会创建多余的实例


```
缺点
实现复杂 = 多种判断，易出错
4. 静态内部类

在静态内部类里创建单例，在装载该内部类时才会去创建单例
线程安全：类是由 JVM加载，而JVM只会加载1遍，保证只有1个单例


```
class Singleton {
    
    // 1. 创建静态内部类
    private static class Singleton2 {
       // 在静态内部类里创建单例
      private static  Singleton ourInstance  = new Singleton()；
    }

    // 私有构造函数
    private Singleton() {
    }
    
    // 延迟加载、按需创建
    public static  Singleton newInstance() {
        return Singleton2.ourInstance;
    }

}

// 调用过程说明：
      // 1. 外部调用类的newInstance() 
      // 2. 自动调用Singleton2.ourInstance
       // 2.1 此时单例类Singleton2得到初始化
       // 2.2 而该类在装载 & 被初始化时，会初始化它的静态域，从而创建单例；
       // 2.3 由于是静态域，因此只会JVM只会加载1遍，Java虚拟机保证了线程安全性
      // 3. 最终只创建1个单例


```
5. 总结
![image](https://upload-images.jianshu.io/upload_images/944365-e08e8d7df9cf302d.png?imageMogr2/auto-orient/)

## 2.1.3 建造者模式
1. 将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示.可以在用户不知道对象的建造过程和细节的情况下就可以直接创建复杂的对象。

```
用户只需要给出指定复杂对象的类型和内容；
建造者模式负责按顺序创建复杂对象（把内部的建造过程和细节隐藏起来)
```
2. 解决问题：方便用户创建复杂的对象（不需要知道实现过程）。
代码复用性 & 封装性（将对象构建过程和细节进行封装 & 复用）


```
例子：造汽车 & 买汽车。

工厂（建造者模式）：负责制造汽车（组装过程和细节在工厂内）
汽车购买者（用户）：你只需要说出你需要的型号（对象的类型和内容），然后直接购买就可以使用了
（不需要知道汽车是怎么组装的（车轮、车门、发动机、方向盘等等））
```
![image](https://upload-images.jianshu.io/upload_images/944365-e4842ec60f89315e.png?imageMogr2/auto-orient/)


```
模式讲解：

指挥者（Director）直接和客户（Client）进行需求沟通；
沟通后指挥者将客户创建产品的需求划分为各个部件的建造请求（Builder）；
将各个部件的建造请求委派到具体的建造者（ConcreteBuilder）；
各个具体建造者负责进行产品部件的构建；
最终构建成具体产品（Product）。

```
3. 实际例子

```
背景：小成希望去电脑城买一台组装的台式主机
过程：
电脑城老板（Diretor）和小成（Client）进行需求沟通（买来打游戏？学习？看片？）
了解需求后，电脑城老板将小成需要的主机划分为各个部件（Builder）的建造请求（CPU、主板blabla）
指挥装机人员（ConcreteBuilder）去构建组件；
将组件组装起来成小成需要的电脑（Product）

```

```
步骤1： 定义组装的过程（Builder）：组装电脑的过程


public  abstract class Builder {  

//第一步：装CPU
//声明为抽象方法，具体由子类实现 
    public abstract void  BuildCPU()；

//第二步：装主板
//声明为抽象方法，具体由子类实现 
    public abstract void BuildMainboard（）；

//第三步：装硬盘
//声明为抽象方法，具体由子类实现 
    public abstract void BuildHD（）；

//返回产品的方法：获得组装好的电脑
    public abstract Computer GetComputer（）；
}
步骤2： 电脑城老板委派任务给装机人员（Director）

public class Director{
                        //指挥装机人员组装电脑
                        public void Construct(Builder builder){
                                
                                 builder. BuildCPU();
                                 builder.BuildMainboard（）;
                                 builder. BuildHD（）;
                              }
 }


**步骤3： **创建具体的建造者（ConcreteBuilder）:装机人员

//装机人员1
  public class ConcreteBuilder extend  Builder{
    //创建产品实例
    Computer computer = new Computer();

    //组装产品
    @Override
    public void  BuildCPU(){  
       computer.Add("组装CPU")
    }  

    @Override
    public void  BuildMainboard（）{  
       computer.Add("组装主板")
    }  

    @Override
    public void  BuildHD（）{  
       computer.Add("组装主板")
    }  

    //返回组装成功的电脑
     @Override
      public  Computer GetComputer（）{  
      return computer
    }  
}

**步骤4： **定义具体产品类（Product）：电脑

public class Computer{
    
    //电脑组件的集合
    private List<String> parts = new ArrayList<String>()；
     
    //用于将组件组装到电脑里
    public void Add(String part){
    parts.add(part);
}
    
    public void Show(){
          for (int i = 0;i<parts.size();i++){    
          System.out.println(“组件”+parts.get(i)+“装好了”);
          }
          System.out.println(“电脑组装完成，请验收”);
          
 
}

}
**步骤5： **客户端调用-小成到电脑城找老板买电脑


public class Builder Pattern{
  public static void main(String[] args){

//逛了很久终于发现一家合适的电脑店
//找到该店的老板和装机人员
  Director director = new Director();
  Builder builder = new ConcreteBuilder();

//沟通需求后，老板叫装机人员去装电脑
director.Construct(builder);

//装完后，组装人员搬来组装好的电脑
Computer computer = builder.GetComputer();
//组装人员展示电脑给小成看
computer.Show()；

    }
        
}
   
结果输出

组件CUP装好了
组件主板装好了
组件硬盘装好了
电脑组装完成，请验收

```

4. 优缺点
* 优点

易于解耦
将产品本身与产品创建过程进行解耦，可以使用相同的创建过程来得到不同的产品。也就说细节依赖抽象。

易于精确控制对象的创建
将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰

易于拓展
增加新的具体建造者无需修改原有类库的代码，易于拓展，符合“开闭原则“。

每一个具体建造者都相对独立，而与其他的具体建造者无关，因此可以很方便地替换具体建造者或增加新的具体建造者，用户使用不同的具体建造者即可得到不同的产品对象。

* 缺点

建造者模式所创建的产品一般具有较多的共同点，其组成部分相似；如果产品之间的差异性很大，则不适合使用建造者模式，因此其使用范围受到一定的限制。

如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大。

5. 应用场景
* 需要生成的产品对象有复杂的内部结构，这些产品对象具备共性；
* 隔离复杂对象的创建和使用，并使得相同的创建过程可以创建不同的产品。

## 2.2.1 适配器模式
1. 定义一个包装类，用于包装不兼容接口的对象，把一个类的接口变换成客户端所期待的另一种接口，从而使原本接口不匹配而无法一起工作的两个类能够在一起工作。适配器模式的形式分为：类的适配器模式 & 对象的适配器模式。原本由于接口不兼容而不能一起工作的那些类可以在一起工作
2. 类的适配器：类的适配器模式是把适配的类的API转换成为目标类的API。
![image](https://upload-images.jianshu.io/upload_images/944365-24c6bf44da1b79ad.png?imageMogr2/auto-orient/)

- 冲突：Target期待调用Request方法，而Adaptee并没有（这就是所谓的不兼容了）。
- 解决方案：为使Target能够使用Adaptee类里的SpecificRequest方法，故提供一个中间环节Adapter类（继承Adaptee & 实现Target接口），把Adaptee的API与Target的API衔接起来（适配）

* Adapter与Adaptee是继承关系，这决定了这个适配器模式是类的
```
使用步骤（代码解析）

步骤1： 创建Target接口；

public interface Target {
 
    //这是源类Adapteee没有的方法
    public void Request(); 
}
步骤2： 创建源类（Adaptee） ；

public class Adaptee {
    
    public void SpecificRequest(){
    }
}
步骤3： 创建适配器类（Adapter）

//适配器Adapter继承自Adaptee，同时又实现了目标(Target)接口。
public class Adapter extends Adaptee implements Target {

    //目标接口要求调用Request()这个方法名，但源类Adaptee没有方法Request()
    //因此适配器补充上这个方法名
    //但实际上Request()只是调用源类Adaptee的SpecificRequest()方法的内容
    //所以适配器只是将SpecificRequest()方法作了一层封装，封装成Target可以调用的Request()而已
    @Override
    public void Request() {
        this.SpecificRequest();
    }

}
步骤4：定义具体使用目标类，并通过Adapter类调用所需要的方法从而实现目标

public class AdapterPattern {

    public static void main(String[] args){

        Target mAdapter = new Adapter()；
        mAdapter.Request（）;
     
    }
}


```
接下来我用一个实例来对类的适配器模式进行更深一步的介绍。
```
实例概况

背景：小成买了一个进口的电视机
冲突：进口电视机要求电压（110V）与国内插头标准输出电压（220V）不兼容
解决方案：设置一个适配器将插头输出的220V转变成110V
即适配器模式中的类的适配器模式
b. 使用步骤
步骤1： 创建Target接口（期待得到的插头）：能输出110V（将220V转换成110V）

 public interface Target {

    //将220V转换输出110V（原有插头（Adaptee）没有的）
    public void Convert_110v();
}
步骤2： 创建源类（原有的插头） ；

class PowerPort220V{
//原有插头只能输出220V
    public void Output_220v(){
    }
}
步骤3：创建适配器类（Adapter）

class Adapter220V extends PowerPort220V implements Target{
   //期待的插头要求调用Convert_110v()，但原有插头没有
    //因此适配器补充上这个方法名
    //但实际上Convert_110v()只是调用原有插头的Output_220v()方法的内容
    //所以适配器只是将Output_220v()作了一层封装，封装成Target可以调用的Convert_110v()而已

    @Override
    public void Convert_110v(){
      this.Output_220v;
    }
}
步骤4：定义具体使用目标类，并通过Adapter类调用所需要的方法从而实现目标（不需要通过原有插头）

//进口机器类
class ImportedMachine {

    @Override
    public void Work() {
        System.out.println("进口机器正常运行");
    }
}


//通过Adapter类从而调用所需要的方法
public class AdapterPattern {
    public static void main(String[] args){

        Target mAdapter220V = new Adapter220V();
        ImportedMachine mImportedMachine = new ImportedMachine();
       
        //用户拿着进口机器插上适配器（调用Convert_110v()方法）
        //再将适配器插上原有插头（Convert_110v()方法内部调用Output_220v()方法输出220V）
        //适配器只是个外壳，对外提供110V，但本质还是220V进行供电
        mAdapter220V.Convert_110v();
        mImportedMachine.Work();
    }
}


```
3. 对象的适配器模式

与类的适配器模式相同，对象的适配器模式也是把适配的类的API转换成为目标类的API。

与类的适配器模式不同的是，对象的适配器模式不是使用继承关系连接到Adaptee类，而是使用委派关系连接到Adaptee类。
![image](https://upload-images.jianshu.io/upload_images/944365-c736416f78a5b2d5.png?imageMogr2/auto-orient/)

在上图中可以看出：

- 冲突：Target期待调用Request方法，而Adaptee并没有（这就是所谓的不兼容了）。
- 解决方案：为使Target能够使用Adaptee类里的SpecificRequest方法，故提供一个中间环节Adapter类（包装了一个Adaptee的实例），把Adaptee的API与Target的API衔接起来（适配）。

Adapter与Adaptee是委派关系，这决定了适配器模式是对象的。


```
使用步骤（代码解析）

步骤1： 创建Target接口；

public interface Target {
 
    //这是源类Adapteee没有的方法
    public void Request(); 
}
步骤2： 创建源类（Adaptee） ；

public class Adaptee {
    
    public void SpecificRequest(){
    }
}
步骤3： 创建适配器类（Adapter）（不适用继承而是委派）

class Adapter implements Target{  
    // 直接关联被适配类  
    private Adaptee adaptee;  
    
    // 可以通过构造函数传入具体需要适配的被适配类对象  
    public Adapter (Adaptee adaptee) {  
        this.adaptee = adaptee;  
    }  
    
    @Override
    public void Request() {  
        // 这里是使用委托的方式完成特殊功能  
        this.adaptee.SpecificRequest();  
    }  
}  
步骤4：定义具体使用目标类，并通过Adapter类调用所需要的方法从而实现目标

public class AdapterPattern {
    public static void main(String[] args){
        //需要先创建一个被适配类的对象作为参数  
        Target mAdapter = new Adapter(new Adaptee())；
        mAdapter.Request();
     
    }
}

```
4. 优缺点
* 优点
- 更好的复用性
系统需要使用现有的类，而此类的接口不符合系统的需要。那么通过适配器模式就可以让这些功能得到更好的复用。
- 透明、简单
客户端可以调用同一接口，因而对客户端来说是透明的。这样做更简单 & 更直接
- 更好的扩展性
在实现适配器功能的时候，可以调用自己开发的功能，从而自然地扩展系统的功能。
- 解耦性
将目标类和适配者类解耦，通过引入一个适配器类重用现有的适配者类，而无需修改原有代码
- 符合开放-关闭原则
同一个适配器可以把适配者类和它的子类都适配到目标接口；可以为不同的目标接口实现不同的适配器，而不需要修改待适配类
* 缺点
过多的使用适配器，会让系统非常零乱，不易整体进行把握


```
类的适配器模式

优点

使用方便，代码简化
仅仅引入一个对象，并不需要额外的字段来引用Adaptee实例
缺点

高耦合，灵活性低
使用对象继承的方式，是静态的定义方式
```

```
对象的适配器模式

优点

灵活性高、低耦合
采用 “对象组合”的方式，是动态组合方式
缺点

使用复杂
需要引入对象实例
```
5. 应用场景
* 系统需要复用现有类，而该类的接口不符合系统的需求，可以使用适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作
* 多个组件功能类似，但接口不统一且可能会经常切换时，可使用适配器模式，使得客户端可以以统一的接口使用它们

6. 类和对象适配器模式的使用场景

灵活使用时：选择对象的适配器模式

* 类适配器使用对象继承的方式，是静态的定义方式；而对象适配器使用对象组合的方式，是动态组合的方式。

需要同时配源类和其子类：选择对象的适配器

- 对于类适配器，由于适配器直接继承了Adaptee，使得适配器不能和Adaptee的子类一起工作，因为继承是静态的关系，当适配器继承了Adaptee后，就不可能再去处理 Adaptee的子类了；
- 对于对象适配器，一个适配器可以把多种不同的源适配到同一个目标。换言之，同一个适配器可以把源类和它的子类都适配到目标接口。因为对象适配器采用的是对象组合的关系，只要对象类型正确，是不是子类都无所谓。

需要重新定义Adaptee的部分行为：选择类适配器
- 对于类适配器，适配器可以重定义Adaptee的部分行为，相当于子类覆盖父类的部分实现方法。
- 对于对象适配器，要重定义Adaptee的行为比较困难，这种情况下，需要定义Adaptee的子类来实现重定义，然后让适配器组合子类。虽然重定义Adaptee的行为比较困难，但是想要增加一些新的行为则方便的很，而且新增加的行为可同时适用于所有的源。
 
仅仅希望使用方便时：选择类适配器
- 对于类适配器，仅仅引入了一个对象，并不需要额外的引用来间接得到Adaptee。
- 对于对象适配器，需要额外的引用来间接得到Adaptee。


建议尽量使用对象的适配器模式，多用合成/聚合、少用继承。

当然，具体问题具体分析，根据需要来选用合适的实现方式



# 2.2.2 装饰模式
1. 装饰器模式（Decorator Pattern）允许向一个现有的对象添加新的功能，同时又不改变其结构。这种类型的设计模式属于结构型模式，它是作为现有的类的一个包装。
这种模式创建了一个装饰类，用来包装原有的类，并在保持类方法签名完整性的前提下，提供了额外的功能。


```
介绍
意图：动态地给一个对象添加一些额外的职责。就增加功能来说，装饰器模式相比生成子类更为灵活。
主要解决：一般的，我们为了扩展一个类经常使用继承方式实现，由于继承为类引入静态特征，并且随着扩展功能的增多，子类会很膨胀。
何时使用：在不想增加很多子类的情况下扩展类。
如何解决：将具体功能职责划分，同时继承装饰者模式。
关键代码： 1、Component 类充当抽象角色，不应该具体实现。 2、修饰类引用和继承 Component 类，具体扩展类重写父类方法。
应用实例： 1、孙悟空有 72 变，当他变成"庙宇"后，他的根本还是一只猴子，但是他又有了庙宇的功能。 2、不论一幅画有没有画框都可以挂在墙上，但是通常都是有画框的，并且实际上是画框被挂在墙上。在挂在墙上之前，画可以被蒙上玻璃，装到框子里；这时画、玻璃和画框形成了一个物体。
优点：装饰类和被装饰类可以独立发展，不会相互耦合，装饰模式是继承的一个替代模式，装饰模式可以动态扩展一个实现类的功能。
缺点：多层装饰比较复杂。
使用场景： 1、扩展一个类的功能。 2、动态增加功能，动态撤销。
注意事项：可代替继承。
```
2. 示例
我们将创建一个 Shape 接口和实现了 Shape 接口的实体类。然后我们创建一个实现了 Shape 接口的抽象装饰类 ShapeDecorator，并把 Shape 对象作为它的实例变量。
RedShapeDecorator 是实现了 ShapeDecorator 的实体类。
DecoratorPatternDemo，我们的演示类使用 RedShapeDecorator 来装饰 Shape 对象。
![image](http://www.runoob.com/wp-content/uploads/2014/08/decorator_pattern_uml_diagram.jpg)


```
步骤 1
创建一个接口。
Shape.java
public interface Shape {
   void draw();
}
步骤 2
创建实现接口的实体类。
Rectangle.java
public class Rectangle implements Shape {

   @Override
   public void draw() {
      System.out.println("Shape: Rectangle");
   }
}
Circle.java
public class Circle implements Shape {

   @Override
   public void draw() {
      System.out.println("Shape: Circle");
   }
}
步骤 3
创建实现了 Shape 接口的抽象装饰类。
ShapeDecorator.java
public abstract class ShapeDecorator implements Shape {
   protected Shape decoratedShape;

   public ShapeDecorator(Shape decoratedShape){
      this.decoratedShape = decoratedShape;
   }

   public void draw(){
      decoratedShape.draw();
   }    
}
步骤 4
创建扩展了 ShapeDecorator 类的实体装饰类。
RedShapeDecorator.java
public class RedShapeDecorator extends ShapeDecorator {

   public RedShapeDecorator(Shape decoratedShape) {
      super(decoratedShape);        
   }

   @Override
   public void draw() {
      decoratedShape.draw();           
      setRedBorder(decoratedShape);
   }

   private void setRedBorder(Shape decoratedShape){
      System.out.println("Border Color: Red");
   }
}
步骤 5
使用 RedShapeDecorator 来装饰 Shape 对象。
DecoratorPatternDemo.java
public class DecoratorPatternDemo {
   public static void main(String[] args) {

      Shape circle = new Circle();

      Shape redCircle = new RedShapeDecorator(new Circle());

      Shape redRectangle = new RedShapeDecorator(new Rectangle());
      System.out.println("Circle with normal border");
      circle.draw();

      System.out.println("\nCircle of red border");
      redCircle.draw();

      System.out.println("\nRectangle of red border");
      redRectangle.draw();
   }
}
步骤 6
验证输出。
Circle with normal border
Shape: Circle

Circle of red border
Shape: Circle
Border Color: Red

Rectangle of red border
Shape: Rectangle
Border Color: Red
```


## 2.2.3 代理模式
在平时写代码时，经常会用到各种设计模式，其中一种就是代理模式，代理实现可以分为静态代理和动态代理。

1. 静态代理
静态代理的模式在平时生活中也很常见，比如买火车票这件小事，黄牛相当于是火车站的代理，我们可以通过黄牛或者代售点进行买票行为，但只能去火车站进行改签和退票，因为只有火车站才有改签和退票的方法。

在代码实现中相当于为一个委托对象realSubject提供一个代理对象proxy，通过proxy可以调用realSubject的部分功能(买票)，并添加一些额外的业务处理(收取手续费)，同时可以屏蔽realSubject中未开放的接口(改签和退票)。

代理对象：起到中介作用，连接客户端和目标对象
例子：电脑桌面的快捷方式。电脑对某个程序提供一个快捷方式（代理对象），快捷方式连接客户端和程序，客户端通过操作快捷方式就可以操作那个程序。

防止直接访问目标对象给系统带来的不必要复杂性。

![image](https://upload-images.jianshu.io/upload_images/2184951-7e362af51534befc.png?imageMogr2/auto-orient/)


```
具体代码实现如下：

interface Subject {
    void request();
}

class RealSubject implements Subject {
    public void request(){
        System.out.println("RealSubject");
    }
}

class Proxy implements Subject {
    private Subject subject;
    
    public Proxy(Subject subject){
        this.subject = subject;
    }
    public void request(){
        System.out.println("begin");
        subject.request();
        System.out.println("end");
    }
}

public class ProxyTest {
    public static void main(String args[]) {
        RealSubject subject = new RealSubject();
        Proxy p = new Proxy(subject);
        p.request();
    }
}


```
静态代理实现中，一个委托类对应一个代理类，代理类在编译期间就已经确定。


```
3.1 优点

协调调用者和被调用者，降低了系统的耦合度
代理对象作为客户端和目标对象之间的中介，起到了保护目标对象的作用
3.2 缺点

由于在客户端和真实主题之间增加了代理对象，因此会造成请求的处理速度变慢；
实现代理模式需要额外的工作（有些代理模式的实现非常复杂），从而增加了系统实现的复杂度。


```
![image](https://upload-images.jianshu.io/upload_images/944365-6ab7f58ee0497fdd.png?imageMogr2/auto-orient/)

2. 动态代理---[这里见Java的动态代理](https://www.jianshu.com/p/6f6bb2f0ece9)


## 2.3.1策略模式


定义一系列算法，将每个算法封装到具有公共接口的一系列策略类中，从而使它们可以相互替换 & 让算法可在不影响客户端的情况下发生变化

简单来说：准备一组算法 & 将每一个算法封装起来，让外部按需调用 & 使得互换

将算法的责任和本身进行解耦，使得：

算法可独立于使用外部而变化
客户端方便根据外部条件选择不同策略来解决不同问题

策略模式仅仅封装算法（包括添加 & 删除），但策略模式并不决定在何时使用何种算法，算法的选择由客户端来决定

![image](https://upload-images.jianshu.io/upload_images/944365-b983c25bafaf8ca8.png?imageMogr2/auto-orient/)

2. 实例

[这个例子也能看看](http://www.runoob.com/design-pattern/strategy-pattern.html)

接下来我用一个实例来对策略模式进行更深一步的介绍。
a. 实例概况

- 背景：小成有一家百货公司，最近在定年度的促销活动
- 冲突：每个节日用同一个促销活动太枯燥，没吸引力
- 解决方案：针对不同节目使用不同促销活动进行促销


```
b. 使用步骤
步骤1： 定义抽象策略角色（Strategy）：百货公司所有促销活动的共同接口


public abstract class Strategy {  

    public abstract void Show()；
}
步骤2：定义具体策略角色（Concrete Strategy）：每个节日具体的促销活动

//为春节准备的促销活动A
class StrategyA extends Strategy{

    @Override
    public void show() {
        System.out.println("为春节准备的促销活动A");
    }
}

//为中秋节准备的促销活动B
class StrategyB extends Strategy{

    @Override
    public void show() {
        System.out.println("为中秋节准备的促销活动B");
    }
}

//为圣诞节准备的促销活动C
class StrategyC extends Strategy{

    @Override
    public void show() {
        System.out.println("为圣诞节准备的促销活动C");
    }
}
步骤3：定义环境角色（Context）：用于连接上下文，即把促销活动推销给客户，这里可以理解为销售员

class Context_SalesMan{
//持有抽象策略角色的引用
    private Strategy strategy;

    //生成销售员实例时告诉销售员什么节日（构造方法）
    //使得让销售员根据传入的参数（节日）选择促销活动（这里使用一个简单的工厂模式）
    public Context_SalesMan(String festival) {
        switch ( festival) {
            //春节就使用春节促销活动
            case "A":
                strategy = new StrategyA();
                break;
            //中秋节就使用中秋节促销活动
            case "B":
                strategy = new StrategyB();
                break;
            //圣诞节就使用圣诞节促销活动
            case "C":
                strategy = new StrategyC();
                break;
        }

    }

    //向客户展示促销活动
    public void SalesManShow(){
        strategy.show();
    }

}
步骤4：客户端调用-让销售员进行促销活动的落地


public class StrategyPattern{
  public static void main(String[] args){

        Context_SalesMan mSalesMan ;

        //春节来了，使用春节促销活动
        System.out.println("对于春节：");
        mSalesMan =  new Context_SalesMan("A");
        mSalesMan.SalesManShow();
        
        
        //中秋节来了，使用中秋节促销活动
        System.out.println("对于中秋节：");
        mSalesMan =  new Context_SalesMan("B");
        mSalesMan.SalesManShow();

        //圣诞节来了，使用圣诞节促销活动
        System.out.println("对于圣诞节：");
        mSalesMan =  new Context_SalesMan("C");
        mSalesMan.SalesManShow();  
  }   
}
   
结果输出

对于春节：
为春节准备的促销活动A
对于中秋节：
为中秋节准备的促销活动B
对于圣诞节：
为圣诞节准备的促销活动B


```
3. 优缺点

```
在全面解析完后，我来分析下其优缺点：

3.1 优点

策略类之间可以自由切换
由于策略类都实现同一个接口，所以使它们之间可以自由切换。
易于扩展
增加一个新的策略只需要添加一个具体的策略类即可，基本不需要改变原有的代码，符合“开闭原则“
避免使用多重条件选择语句（if else），充分体现面向对象设计思想。
3.2 缺点

客户端必须知道所有的策略类，并自行决定使用哪一个策略类。
策略模式将造成产生很多策略类，可以通过使用享元模式在一定程度上减少对象的数量。


```

```
动态选择多种复杂行为

该行为可理解为：

复杂的算法 / 数据结构
类的行为 / 方法
注：提高了行为的保密 
```
## 2.3.2观察者模式
定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。

观察者模式可以结合Android中的ListView来理解，ListView关联的适配器Adapter在数据发生变化时会通过notifyDataSetChanged()方法来通知界面刷新。

其实就是发布订阅模式，发布者发布信息，订阅者获取信息，订阅了就能收到信息，没订阅就收不到信息。

![image](https://images2017.cnblogs.com/blog/1272523/201711/1272523-20171113100406343-149515793.png)


```
抽象被观察者角色：也就是一个抽象主题，它把所有对观察者对象的引用保存在一个集合中，每个主题都可以有任意数量的观察者。抽象主题提供一个接口，可以增加和删除观察者角色。一般用一个抽象类和接口来实现。
抽象观察者角色：为所有的具体观察者定义一个接口，在得到主题通知时更新自己。
具体被观察者角色：也就是一个具体的主题，在集体主题的内部状态改变时，所有登记过的观察者发出通知。
具体观察者角色：实现抽象观察者角色所需要的更新接口，一边使本身的状态与制图的状态相协调。
```
2. 应用实例

有一个微信公众号服务，不定时发布一些消息，关注公众号就可以收到推送消息，取消关注就收不到推送消息。


```
1、定义一个抽象被观察者接口

复制代码
package com.jstao.observer;

/***
 * 抽象被观察者接口
 * 声明了添加、删除、通知观察者方法
 * @author jstao
 *
 */
public interface Observerable {
    
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObserver();
    
}
复制代码
 

 2、定义一个抽象观察者接口

复制代码
package com.jstao.observer;

/***
 * 抽象观察者
 * 定义了一个update()方法，当被观察者调用notifyObservers()方法时，观察者的update()方法会被回调。
 * @author jstao
 *
 */
public interface Observer {
    public void update(String message);
}
复制代码
3、定义被观察者，实现了Observerable接口，对Observerable接口的三个方法进行了具体实现，同时有一个List集合，用以保存注册的观察者，等需要通知观察者时，遍历该集合即可。

复制代码
package com.jstao.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者，也就是微信公众号服务
 * 实现了Observerable接口，对Observerable接口的三个方法进行了具体实现
 * @author jstao
 *
 */
public class WechatServer implements Observerable {
    
    //注意到这个List集合的泛型参数为Observer接口，设计原则：面向接口编程而不是面向实现编程
    private List<Observer> list;
    private String message;
    
    public WechatServer() {
        list = new ArrayList<Observer>();
    }
    
    @Override
    public void registerObserver(Observer o) {
        
        list.add(o);
    }
    
    @Override
    public void removeObserver(Observer o) {
        if(!list.isEmpty())
            list.remove(o);
    }

    //遍历
    @Override
    public void notifyObserver() {
        for(int i = 0; i < list.size(); i++) {
            Observer oserver = list.get(i);
            oserver.update(message);
        }
    }
    
    public void setInfomation(String s) {
        this.message = s;
        System.out.println("微信服务更新消息： " + s);
        //消息更新，通知所有观察者
        notifyObserver();
    }

}
复制代码
4、定义具体观察者，微信公众号的具体观察者为用户User

复制代码
package com.jstao.observer;

/**
 * 观察者
 * 实现了update方法
 * @author jstao
 *
 */
public class User implements Observer {

    private String name;
    private String message;
    
    public User(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String message) {
        this.message = message;
        read();
    }
    
    public void read() {
        System.out.println(name + " 收到推送消息： " + message);
    }
    
}
复制代码
5、编写一个测试类

首先注册了三个用户，ZhangSan、LiSi、WangWu。公众号发布了一条消息"PHP是世界上最好用的语言！"，三个用户都收到了消息。

用户ZhangSan看到消息后颇为震惊，果断取消订阅，这时公众号又推送了一条消息，此时用户ZhangSan已经收不到消息，其他用户

还是正常能收到推送消息。

复制代码
package com.jstao.observer;

public class Test {
    
    public static void main(String[] args) {
        WechatServer server = new WechatServer();
        
        Observer userZhang = new User("ZhangSan");
        Observer userLi = new User("LiSi");
        Observer userWang = new User("WangWu");
        
        server.registerObserver(userZhang);
        server.registerObserver(userLi);
        server.registerObserver(userWang);
        server.setInfomation("PHP是世界上最好用的语言！");
        
        System.out.println("----------------------------------------------");
        server.removeObserver(userZhang);
        server.setInfomation("JAVA是世界上最好用的语言！");
        
    }
}
复制代码
测试结果：
```
![image](https://images2017.cnblogs.com/blog/1272523/201711/1272523-20171113110639796-1181425764.png)

优缺点：

```
优点：
1、观察者和被观察者是抽象耦合的。
2、建立一套触发机制。
缺点： 
1、如果一个被观察者对象有很多的直接和间接的观察者的话，将所有的观察者都通知到会花费很多时间。
2、如果在观察者和观察目标之间有循环依赖的话，观察目标会触发它们之间进行循环调用，可能导致系统崩溃。
3、观察者模式没有相应的机制让观察者知道所观察的目标对象是怎么发生变化的，而仅仅只是知道观察目标发生了变化。
```
# 2.3.3 模板方法模式
在模板模式（Template Pattern）中，一个抽象类公开定义了执行它的方法的方式/模板。它的子类可以按需要重写方法实现，但调用将以抽象类中定义的方式进行。这种类型的设计模式属于行为型模式。
```
意图：定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
主要解决：一些方法通用，却在每一个子类都重新写了这一方法。
何时使用：有一些通用的方法。
如何解决：将这些通用算法抽象出来。
关键代码：在抽象类实现，其他步骤在子类实现。
```

```
优点： 1、封装不变部分，扩展可变部分。 2、提取公共代码，便于维护。 3、行为由父类控制，子类实现。
缺点：每一个不同的实现都需要一个子类来实现，导致类的个数增加，使得系统更加庞大。
使用场景： 1、有多个子类共有的方法，且逻辑相同。 2、重要的、复杂的方法，可以考虑作为模板方法。
注意事项：为防止恶意操作，一般模板方法都加上 final 关键词。
```
2. 实例

我们将创建一个定义操作的 Game 抽象类，其中，模板方法设置为 final，这样它就不会被重写。Cricket 和 Football 是扩展了 Game 的实体类，它们重写了抽象类的方法。
TemplatePatternDemo，我们的演示类使用 Game 来演示模板模式的用法。

![image](http://www.runoob.com/wp-content/uploads/2014/08/template_pattern_uml_diagram.jpg)


```
创建一个抽象类，它的模板方法被设置为 final。
Game.java创建一个抽象类，它的模板方法被设置为 final。
Game.java
public abstract class Game {
   abstract void initialize();
   abstract void startPlay();
   abstract void endPlay();

   //模板
   public final void play(){

      //初始化游戏
      initialize();

      //开始游戏
      startPlay();

      //结束游戏
      endPlay();
   }
}
步骤 2
创建扩展了上述类的实体类。
Cricket.java
public class Cricket extends Game {

   @Override
   void endPlay() {
      System.out.println("Cricket Game Finished!");
   }

   @Override
   void initialize() {
      System.out.println("Cricket Game Initialized! Start playing.");
   }

   @Override
   void startPlay() {
      System.out.println("Cricket Game Started. Enjoy the game!");
   }
}
Football.java
public class Football extends Game {

   @Override
   void endPlay() {
      System.out.println("Football Game Finished!");
   }

   @Override
   void initialize() {
      System.out.println("Football Game Initialized! Start playing.");
   }

   @Override
   void startPlay() {
      System.out.println("Football Game Started. Enjoy the game!");
   }
}
步骤 3
使用 Game 的模板方法 play() 来演示游戏的定义方式。
TemplatePatternDemo.java
public class TemplatePatternDemo {
   public static void main(String[] args) {

      Game game = new Cricket();
      game.play();
      System.out.println();
      game = new Football();
      game.play();        
   }
}
步骤 4
验证输出。
Cricket Game Initialized! Start playing.
Cricket Game Started. Enjoy the game!
Cricket Game Finished!

Football Game Initialized! Start playing.
Football Game Started. Enjoy the game!
Football Game Finished!
```

# 3.设计模式的设计原则
![image](https://upload-images.jianshu.io/upload_images/944365-401fe2613651bf73.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/400)
1. 单一职责原则

定义 : 应该有且只有一个原因引起类的变化

注意 : 这里的类不光指类,也适用于方法和接口,比如我们常说的一个方法实现一个功能

2. 里氏代换原则

定义 : 只要父类出现的地方子类就一定可以出现,而且替换为子类也不会出现任何异常或错误,使用者不需要知道是父类还是子类.但是返回来就不行了,有子类出现的地方,
- 子类必须完全实现父类的方法,如果子类无法完全实现父类的方法,则建议断开父子继承关系,采用依赖 | 聚集 | 组合 等关系来代替
- 子类可以有自己的个性
- 覆盖或实现父类的方法时,输入参数可以被放大,比如父类中有一个方法的输入参数是 HashMap,子类的参数可以是 Map 类型,这样父类就可以被子类替换,如果反过来,则违背了里氏替换原则,所以子类中方法的前置条件必须与父类的被覆写的方法的前置条件相同或者更宽松
- 覆写或实现父类的方法时,输出结果可以被缩小,也就是说如果父类方法返回的类型 T,子类的相同方法(重载或覆写)的返回值类型 S,S 和 T 要么同类型,要么 S 是 T 的子类;跟上面的道理一样
3. 依赖倒置原则

定义 : 依赖倒置原则包含三个含义


```
高层模块不应该依赖低层模块,两者都应该依赖其抽象
抽象不应该依赖细节
细节应该依赖抽象
```
依赖倒置原则在 Java 中的实现是表现是:
- 模块间的依赖通过抽象发生,实现类之间不发生直接的依赖关系,其依赖关系是通过接口或抽象类产生的;
- 接口或抽象类不依赖于实现类
- 实现类依赖接口或抽象类
- 这也是面向接口编程的精髓之一

4.接口隔离原则

我们先来看接口的定义 : 


```
实例接口 : 在 Java 中声明一个类,然后用 new 关键字产生一个实例,它是对一类事物的描述,可以看成是一个接口
类接口 : 使用 interface 定义的接口
```
隔离的的理解 : 
```
客户端不应该依赖它不需要的接口
类之间的依赖关系应该建立在最小的接口上
概括 : 建立单一接口,不要建立臃肿庞大的接口,也就是接口尽量细化,接口中的方法尽量少
```
这个是开闭原则的基础，具体内容：针对接口编程，依赖于抽象而不依赖于具体。

5. 迪米特法则
定义 : 迪米特法则也叫最少知识原则,含义是 一个对象应该对其他对象有最少的了解,这个应该很好理解,就是降低各模块之间的耦合

6. 开闭原则
定义 : 一个软件实体如类,模块和函数应该对扩展开放,对修改关闭,开闭原则也是其他五个原则的基石

# 4.登记式单例实现单例模式的继承（限定一个抽象类的所有子类都必须是单例）
[link](https://www.cnblogs.com/wang9192/p/3975748.html)