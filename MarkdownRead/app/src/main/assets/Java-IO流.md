# 1.Java中如何实现序列化，有什么意义？
1. 序列化就是一种用来处理对象流的机制，所谓对象流也就是将对象的内容进行流化。可以对流化后的对象进行读写操作，也可将流化后的对象传输于网络之间。序列化是为了解决对象流读写操作时可能引发的问题（如果不进行序列化可能会存在数据乱序的问题）。
2. 要实现序列化，需要让一个类实现Serializable接口，该接口是一个标识性接口，标注该类对象是可被序列化的，然后使用一个输出流来构造一个对象输出流并通过writeObject(Object)方法就可以将实现对象写出（即保存其状态）
3. 如果需要反序列化则可以用一个输入流建立对象输入流，然后通过readObject方法从流中读取对象。序列化除了能够实现对象的持久化之外，还能够用于对象的深度克隆。

# 2.Java中有几种类型的流？
1. 字节流和字符流。字节流继承于InputStream、OutputStream，字符流继承于Reader、Writer。在java.io 包中还有许多其他的流，主要是为了提高性能和使用方便。关于Java的I/O需要注意的有两点：一是两种对称性（输入和输出的对称性，字节和字符的对称性）；二是两种设计模式（适配器模式和装潢模式）。另外Java中的流不同于C#的是它只有一个维度一个方向。
2. 编程实现文件拷贝

```
public final class MyUtil {
 
    private MyUtil() {
        throw new AssertionError();
    }
 
    public static void fileCopy(String source, String target) throws IOException {
        try (InputStream in = new FileInputStream(source)) {
            try (OutputStream out = new FileOutputStream(target)) {
                byte[] buffer = new byte[4096];
                int bytesToRead;
                while((bytesToRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesToRead);
                }
            }
        }
    }
 
    public static void fileCopyNIO(String source, String target) throws IOException {
        try (FileInputStream in = new FileInputStream(source)) {
            try (FileOutputStream out = new FileOutputStream(target)) {
                FileChannel inChannel = in.getChannel();
                FileChannel outChannel = out.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                while(inChannel.read(buffer) != -1) {
                    buffer.flip();
                    outChannel.write(buffer);
                    buffer.clear();
                }
            }
        }
    }
}
```
上面用到Java 7的TWR，使用TWR后可以不用在finally中释放外部资源 ，从而让代码更加优雅

# 3.写一个方法，输入一个文件名和一个字符串，统计这个字符串在这个文件中出现的次数。
```
/**
     * 统计给定文件中给定字符串的出现次数
     * 
     * @param filename  文件名
     * @param word 字符串
     * @return 字符串在文件中出现的次数
     */
    public static int countWordInFile(String filename, String word) {
        int counter = 0;
        try (FileReader fr = new FileReader(filename)) {
            try (BufferedReader br = new BufferedReader(fr)) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    int index = -1;
                    while (line.length() >= word.length() && (index = line.indexOf(word)) >= 0) {
                        counter++;
                        line = line.substring(index + word.length());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return counter;
    }
 
```
字符输入流读取文件，每一行进行遍历，通过indexOf方法判断当前字符串是否存在以及存在的其实下标。

# 4.如何用Java代码列出一个目录下所有的文件？

```
import java.io.File;
 
class Test12 {
 
    public static void main(String[] args) {
        showDirectory(new File("/Users/Hao/Downloads"));
    }
 
    public static void showDirectory(File f) {
        _walkDirectory(f, 0);
    }
 
    private static void _walkDirectory(File f, int level) {
        if(f.isDirectory()) {
            for(File temp : f.listFiles()) {
                _walkDirectory(temp, level + 1);
            }
        }
        else {
            for(int i = 0; i < level - 1; i++) {
                System.out.print("\t");
            }
            System.out.println(f.getName());
        }
    }
}
```
首先判断当前文件是否为文件夹，如果是就递归调用，如果不是根据level打印制表符，然后打印文件名。

# 5.简述一下Java的字节输入输出流
1. 普通字节流示例
```
public static void demo2() throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream("aaa.txt");
		FileOutputStream fos = new FileOutputStream("bbb.txt");
		int len;
		byte[] arr = new byte[1024 * 8];
		
		while((len = fis.read(arr)) != -1) {
			//fos.write(arr);
			fos.write(arr, 0, len);
		}
		
		fis.close();
		fos.close();
	}
```
* 字节数组定义2个太小了，一般定义为1024*8，就是8K字节传一次。
* 上面是比较推荐的文件复制方式。

2. Buffered输入输出流拷贝
* 为输入输出流添加一些功能，底层添加了缓冲区数组。
* 构造函数只要放InputStream子类对象就可以，这是一种装饰设计模式

```
/**
	 * @param args
	 * @throws IOException 
	 * 如果文件足够大,read()方法执行一次,就会将文件上字节数据一次读取8192个字节存储在,BufferedInputStream的缓冲区中
	 * 从缓冲区中返给一个字节一个字节返给b
	 * 如果write一次,先将缓冲区装满,然后一股脑的写到文件上去
	 * 这么做减少了到硬盘上读和写的操作
	 */
	public static void main(String[] args) throws IOException {
		FileInputStream fis = new FileInputStream("致青春.mp3");			//创建文件输入流对象,关联致青春.mp3
		BufferedInputStream bis = new BufferedInputStream(fis);			//创建缓冲区对fis装饰
		FileOutputStream fos = new FileOutputStream("copy.mp3");		//创建输出流对象,关联copy.mp3
		BufferedOutputStream bos = new BufferedOutputStream(fos);		//创建缓冲区对fos装饰
		
		int b;
		while((b = bis.read()) != -1) {		
			bos.write(b);
		}
		
		bis.close();						//只关装饰后的对象即可
		bos.close();
		
	}
```
* 不用定义字节数组了，通过Buffered装饰之后，添加了缓冲区，默认大小8192字节，源码里面定义了对应的字节数组。
* 调用read()，其实一次会把8192装满，然后一个一个给b，b一个一个给输出缓冲区的字节数组。这样虽然中间次数多，但是这个动作是在内存里面执行的，比起之前一个一个读硬盘要块多了，然后输出缓冲区装满8192再一次性写过去。

# 6.写一个简单的图片加密

```
```
/**
	 * @param args
	 * 给图片加密
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//demo1();
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("a.jpg"));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("b.jpg"));
		
		int b;
		while((b = bis.read()) != -1) {
			bos.write(b ^ 123);
		}
		
		bis.close();
		bos.close();
	}
```
* 写的时候异或一个你自己设置的密码
```

# 7.字符流输入输出简述
* 可以直接读写字符的IO流，字符流读取字符，就要先读取到字节数据，然后利用编码表转换为字符。同样写出字符，需要把字符转化为字节再写出去，因为文本上面也是一个个字节
* 类Reader：用于读取字符流的抽象类。子类必须实现的方法只有 read(char[], int, int) 和 close()。但是，多数子类将重写此处定义的一些方法，以提供更高的效率和/或其他功能。 
* 有一个子类FileReader，这是一个正常类，可以操作文本
* 注意Reader类的read方法也是返回int型，因为char类型0-65535之间，装不了-1（结束标记）.
* Writer这个类底部由1024长度的char[]作为缓冲区，你如果没有close，FileWriter不会输出的，除非你写满

```
*public static void demo1() throws FileNotFoundException, IOException {
		FileReader fr = new FileReader("a.txt");
		FileWriter fw = new FileWriter("b.txt");
		
		int ch;
		while((ch = fr.read()) != -1) {
			fw.write(ch);
		}
		
		fr.close();
		fw.close();
	}
```


# 8.什么情况下用字符流？
* 拷贝情况下：字节流是直接对字节的操作，字符流Read时候先字节转化为字符，Write将字符转化为字节，字符流还需要转化一下，比字节流要慢。
* 字符流：操作纯文本时候好用，只读时候，一次读取的是一个字符，不会出现乱码。只写，可以直接写出字符串（FileWriter底层自动转为了字节数组写到文件上），不用转换。字节流要写字符串要转化为字节数组才能写出去
```
	/**
	 * @param args
	 * @throws IOException 
	 * 拷贝纯文本的文件用字符流好还是用字节流好?
	 * 拷贝纯文本字节流更好
	 * 
	 * 字符流在什么时候用呢?
	 * 操作纯文本
	 * 只读,一次读取的是一个字符,不会出现乱码
	 * 只写,可以直接写出的是字符串,不用转换
	 * 
	 * 当字符流拷贝非纯文本文件和拷贝纯文本的操作是一样的,需要先将字节转换为字符,转换字符如果没有转换成功就会变成?,写出去的时候
	 * 就会将?写出
	 * 
	 * 字符流只能操作纯文本的文件
	 * 字节流可以操作的是任意文件
	 */
```

# 9.转换流

```
```
public static void demo1() throws FileNotFoundException, IOException {
		FileReader fr = new FileReader("UTF-8.txt");	//字节流+GBK
		FileWriter fw = new FileWriter("GBK.txt");
		
		int ch;
		while((ch = fr.read()) != -1) {
			fw.write(ch);
		}
		
		fr.close();
		fw.close();
	}
```
* 发现输出是乱码，而且字符变多了
* UTF-8里面三个字节代表一个中文，而GBK是两个字节，编码规范不一样，需要转换。
* 如果FileReader没有修改构造，用的会是默认编码表（Gbk）
* Reader的子类InputStreamReader构造可以传入编码表名字，注意这个是字符流不是字节流。OutputStreamWriter也是同理
```


```
public static void demo2() throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		InputStreamReader isr = 
				new InputStreamReader(new FileInputStream("UTF-8.txt"), "UtF-8");		//通过指定编码表读
		OutputStreamWriter osw = 
				new OutputStreamWriter(new FileOutputStream("GBK.txt"), "GBK");			//通过指定编码表写
		
		int ch;
		while((ch = isr.read()) != -1) {
			osw.write(ch);
		}
		
		isr.close();
		osw.close();
	}
```

```
* 注意，InputSreamReader是一个包装类，包装的是输入字节流FileInputSream而不是字符流Reader！
* 首先文件A是UTF-8格式，包括4个中文总共12字节，然后通过InputStreamReader将3个字节变成1个字符读入程序，然后写的时候根据GBK编码表生成8个字节，还是那4个字符，输出到文件B。
* 当然根据装饰模式，还可以用Buffered来升级他，增加缓冲区

```

	 * @param args
	 * 转换流
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//demo1();
		//demo2();
		BufferedReader br = 												//InputStreamReader是字节通向字符的桥梁
				new BufferedReader(new InputStreamReader(new FileInputStream("UTF-8.txt"), "UTF-8"));
		BufferedWriter bw = 												//OutputStreamWriter是字符通向字节的桥梁
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream("GBK.txt"), "GBK"));
		
		int ch;
		while((ch = br.read()) != -1) {
			bw.write(ch);
		}
		
		br.close();
		bw.close();
	}
```
* 这个管子相当于有三截，第一段是FileInputStream读取字节流，然后经过InputSreamReader转换为字符，再经过BufferedReader让其变得高效（管子变粗）。输出也是同理。大的缓冲区，字符转字节，输出到文件。
```
