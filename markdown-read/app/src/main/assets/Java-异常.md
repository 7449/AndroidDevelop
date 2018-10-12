# 1.Java语言如何进行异常处理，关键字：throws、throw、try、catch、finally分别如何使用？
* Java通过面向对象的方法进行异常处理，把各种不同的异常进行分类，并提供了良好的接口。在Java中，每个异常都是一个对象，它是Throwable类或其子类的实例。当一个方法出现异常后便抛出一个异常对象，该对象中包含有异常信息，调用这个对象的方法可以捕获到这个异常并可以对其进行处理。
* Java的异常处理是通过5个关键词来实现的：try、catch、throw、throws和finally。
* 一般情况下是用try来执行一段程序，如果系统会抛出（throw）一个异常对象，可以通过它的类型来捕获（catch）它，或通过总是执行代码块（finally）来处理；
* throw关键字通常用在方法体中，并且抛出一个异常对象。程序在执行到throw语句时立即停止，它后面的语句都不执行。通过throw抛出异常后，如果想在上一级代码中来捕获并处理异常，则需要在抛出异常的方法中使用throws关键字在方法声明中指明要跑出的异常；如果要捕捉throw抛出的异常，则必须使用try—catch语句
```
class MyException extends Exception { //创建自定义异常类
 String message; //定义String类型变量
 public MyException(String ErrorMessagr) {  //父类方法
       message = ErrorMessagr;
 }
 public String getMessage(){   //覆盖getMessage()方法
  return message;
 }
}
public class Captor { //创建类
static int quotient(int x,int y) throws MyException{//定义方法抛出异常
if(y < 0){  //判断参数是否小于0
         throw new MyException("除数不能是负数");//异常信息
 }
 return x/y;//返回值
 }
public static void main(String args[]){ //主方法
 try{ //try语句包含可能发生异常的语句
                int result = quotient(3,-1);//调用方法quotient()
    }catch (MyException e) { //处理自定义异常
  System.out.println(e.getMessage()); //输出异常信息
  }
    catch (ArithmeticException e) {
                   //处理ArithmeticException异常
  System.out.println("除数不能为0");//输出提示信息
  }
   catch (Exception e) { //处理其他异常
  System.out.println("程序发生了其他的异常");
                  //输出提示信息
  }
 }
}
```

* throws关键字通常被应用在声明方法时，用来指定可能抛出的异常。多个异常可以使用逗号隔开。当在主函数中调用该方法时，如果发生异常，就会将异常抛给指定异常对象。
```
public class Shoot {   创建类
static void pop() throws NegativeArraySizeException {
//定义方法并抛出NegativeArraySizeException异常
int [] arr = new int[-3];//创建数组
}
public static void main(String[] args) {//主方法
try { 
pop(); //调用pop()方法
} catch (NegativeArraySizeException e) {
System.out.println("pop()方法抛出的异常");//输出异常信息
}
}
}
```


# 2.Error和Exception有什么区别？
Error表示系统级的错误和程序不必处理的异常，是恢复不是不可能但很困难的情况下的一种严重问题；比如内存溢出，不可能指望程序能处理这样的情况；Exception表示需要捕捉或者需要程序进行处理的异常，是一种设计或实现问题；也就是说，它表示如果程序运行正常，从不会发生的情况。

提示：用递归编写程序时一定要牢记两点：1. 递归公式；2. 收敛条件（什么时候就不再继续递归）。

# 3.try{}里有一个return语句，那么紧跟在这个try后的finally{}里的代码会不会被执行，什么时候被执行，在return前还是后?
会执行，在方法返回调用者前执行。

注意：在finally中改变返回值的做法是不好的，因为如果存在finally代码块，try中的return语句不会立马返回调用者，而是记录下返回值待finally代码块执行完毕之后再向调用者返回其值，然后如果在finally中修改了返回值，就会返回修改后的值。显然，在finally中返回或者修改返回值会对程序造成很大的困扰，C#中直接用编译错误的方式来阻止程序员干这种龌龊的事情，Java中也可以通过提升编译器的语法检查级别来产生警告或错误，Eclipse中可以在如图所示的地方进行设置，强烈建议将此项设置为编译错误。

# 4.运行时异常与受检异常有何异同？
* 运行时异常表示虚拟机的通常操作中可能遇到的异常，是一种常见运行错误，只要程序设计得没有问题通常就不会发生。
* 受检异常跟程序运行的上下文环境有关，即使程序设计无误，仍然可能因使用的问题而引发。
* Java编译器要求方法必须声明抛出可能发生的受检异常，但是并不要求必须声明抛出未被捕获的运行时异常

# 5.列出一些你常见的运行时异常？
- ArithmeticException（算术异常）
- ClassCastException （类转换异常）
- IllegalArgumentException （非法参数异常）
- IndexOutOfBoundsException （下标越界异常）
- NullPointerException （空指针异常）
- SecurityException （安全异常）

# 6.阐述final、finally、finalize的区别。
- final：修饰符（关键字）有三种用法：如果一个类被声明为final，意味着它不能再派生出新的子类，即不能被继承，因此它和abstract是反义词。将变量声明为final，可以保证它们在使用中不被改变，被声明为final的变量必须在声明时给定初值，而在以后的引用中只能读取不可修改。被声明为final的方法也同样只能使用，不能在子类中被重写。
- finally：通常放在try…catch…的后面构造总是执行代码块，这就意味着程序无论正常执行还是发生异常，这里的代码只要JVM不关闭都能执行，可以将释放外部资源的代码写在finally块中。
- finalize：Object类中定义的方法，Java中允许使用finalize()方法在垃圾收集器将对象从内存中清除出去之前做必要的清理工作。这个方法是由垃圾收集器在销毁对象时调用的，通过重写finalize()方法可以整理系统资源或者执行其他清理工作。

# 7.类ExampleA继承Exception，类ExampleB继承ExampleA。

```
try {
    throw new ExampleB("b")
} catch（ExampleA e）{
    System.out.println("ExampleA");
} catch（Exception e）{
    System.out.println("Exception");
}
```
请问执行此段代码的输出是什么？

答：输出：ExampleA。（根据里氏代换原则[能使用父类型的地方一定能使用子类型]，抓取ExampleA类型异常的catch块能够抓住try块中抛出的ExampleB类型的异常）