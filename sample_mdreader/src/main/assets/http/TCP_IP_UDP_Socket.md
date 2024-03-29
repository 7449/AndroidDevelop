# TCP/IP，TCP，UDP，IP，Socket

## 概述

- 什么是 TCP/IP？
- TCP，UDP 有什么区别？
- 什么是 Socket？

网上这方面的资料有很多，但都很琐碎，没有系统化，要么看完很快就忘记了，要么就是看完迷迷糊糊似懂非懂，下面我们来一一攻破以上问题。

## 什么是 TCP/IP ？

计算机与网络设备要相互通信，双方就必须基于相同的方法。比如，如何探测到通信目标、由哪一边先发起通信、使用哪种语言进行通信、怎样结束通信等规则都需要事先确定。不同的硬件、操作系统之间的通信，所有的这一切都需要一种规则。而我们就把这种规则称为协议（protocol）。

TCP/IP 是互联网相关的各类协议族的总称，比如：TCP，UDP，IP，FTP，HTTP，ICMP，SMTP 等都属于 TCP/IP
族内的协议。像这样把与互联网相关联的协议集合起来总称为 TCP/IP。也有说法认为，TCP/IP 是指 TCP 和 IP
这两种协议。还有一种说法认为，TCP/IP 是在 IP 协议的通信过程中，使用到的协议族的统称。

> 个人认为，因为 TCP/IP 族内的协议有很多，为了突出 TCP 与 IP 这两个协议的重要性，所以就用 TCP/IP 来表示
> TCP/IP 协议族了。

## 网络参考模型

![参考模型][1]

- OSI 参考模型

OSI 参考模型是 ISO 的建议，它是为了使各层上的协议国际标准化而发展起来的。OSI
参考模型全称是开放系统互连参考模型(Open System Interconnection Reference Model)
。这一参考模型共分为七层：物理层、数据链路层、网络层、传输层、会话层、表示层和应用层。

- TCP/IP 参考模型

TCP/IP 参考模型是首先由 ARPANET 所使用的网络体系结构。这个体系结构在它的两个主要协议出现以后被称为
TCP/IP 参考模型(TCP/IP Reference Model)。这一网络协议共分为四层：数据链路层、网络层、传输层和应用层。

## TCP/IP 的分层管理

TCP/IP 协议族里重要的一点就是分层。把 TCP/IP
层次化是有好处的。比如，如果互联网只由一个协议统筹，某个地方需要改变设计时，就必须把所有部分整体替换掉。而分层之后只需把变动的层替换掉即可。把各层之间的接口部分规划好之后，每个层次内部的设计就能够自由改动了。

值得一提的是，层次化之后，设计也变得相对简单了。处于应用层上的应用可以只考虑分派给自己的任务，而不需要弄清对方在地球上哪个地方、对方的传输路线是怎样的、是否能确保传输送达等问题。

| OSI中的层		     | 功能           					     | TCP/IP协议族									                     |
|:-------------|:-----------------------|:---------------------------------------|
| 7 应用层      	 | 文件传输，电子邮件，文件服务，虚拟终端	   | TFTP，HTTP，SNMP，FTP，SMTP，DNS，Telnet 等等	 |
| 6 表示层      	 | 数据格式化，代码转换，数据加密      	 | 没有协议										                         |
| 5 会话层 		     | 解除或建立与别的接点的联系      		  | 没有协议										                         |
| 4 传输层 		     | 提供端对端的接口      				     | TCP，UDP										                      |
| 3 网络层 		     | 为数据包选择路由      				     | IP，ICMP，OSPF，EIGRP，IGMP					           |
| 2 数据链路层 	    | 传输有地址的帧以及错误检测功能      	 | SLIP，CSLIP，PPP，MTU							              |
| 1 物理层 		     | 以二进制数据形式在物理媒体上传输数据	    | ISO2110，IEEE802，IEEE802.2					         |

- 数据链路层

数据链路层是负责接收 IP 数据包并通过网络发送，或者从网络上接收物理帧，抽出 IP 数据包，交给 IP 层。

ARP 是正向地址解析协议，通过已知的 IP，寻找对应主机的 MAC 地址。

RARP 是反向地址解析协议，通过 MAC 地址确定 IP 地址。比如无盘工作站还有 DHCP 服务。

常见的接口层协议有：
Ethernet 802.3、Token Ring 802.5、X.25、Frame relay、HDLC、PPP ATM等。

- 网络层

负责相邻计算机之间的通信。其功能包括三方面。

1. 处理来自传输层的分组发送请求，收到请求后，将分组装入 IP 数据报，填充报头，选择去往信宿机的路径，然后将数据报发往适当的网络接口。
2. 处理输入数据报：首先检查其合法性，然后进行寻径--假如该数据报已到达信宿机，则去掉报头，将剩下部分交给适当的传输协议；假如该数据报尚未到达信宿，则转发该数据报。
3. 处理路径、流控、拥塞等问题。

网络层包括：IP(Internet Protocol) 协议、ICMP(Internet Control Message Protocol)

控制报文协议、ARP(Address Resolution Protocol) 地址转换协议、RARP(Reverse ARP) 反向地址转换协议。

IP 是网络层的核心，通过路由选择将下一条IP封装后交给接口层。IP数据报是无连接服务。

ICMP 是网络层的补充，可以回送报文。用来检测网络是否通畅。

Ping 命令就是发送 ICMP 的 echo 包，通过回送的 echo relay 进行网络测试。

- 传输层

提供应用程序间的通信。其功能包括：一、格式化信息流；二、提供可靠传输。为实现后者，传输层协议规定接收端必须发回确认，并且假如分组丢失，必须重新发送，即耳熟能详的“三次握手”过程，从而提供可靠的数据传输。

传输层协议主要是：传输控制协议 TCP(Transmission Control Protocol) 和用户数据报协议 UDP(User Datagram
protocol)。

- 应用层

向用户提供一组常用的应用程序，比如电子邮件、文件传输访问、远程登录等。远程登录 TELNET 使用 TELNET
协议提供在网络其它主机上注册的接口。TELNET 会话提供了基于字符的虚拟终端。文件传输访问 FTP 使用 FTP
协议来提供网络内机器间的文件拷贝功能。

应用层协议主要包括如下几个：FTP、TELNET、DNS、SMTP、NFS、HTTP。

FTP(File Transfer Protocol）是文件传输协议，一般上传下载用FTP服务，数据端口是 20H，控制端口是 21H。

Telnet 服务是用户远程登录服务，使用 23H 端口，使用明码传送，保密性差、简单方便。

DNS(Domain Name Service）是域名解析服务，提供域名到 IP 地址之间的转换，使用端口 53。

SMTP(Simple Mail Transfer Protocol）是简单邮件传输协议，用来控制信件的发送、中转，使用端口 25。

NFS（Network File System）是网络文件系统，用于网络中不同主机间的文件共享。

HTTP(Hypertext Transfer Protocol）是超文本传输协议，用于实现互联网中的 WWW 服务，使用端口 80。

## 什么是 TCP ？

TCP（Transmission Control Protocol 传输控制协议）是一种面向连接的、可靠的、基于字节流的传输层通信协议，由
IETF 的RFC 793定义。TCP 是面向连接的、可靠的流协议。流就是指不间断的数据结构，你可以把它想象成排水管中的水流。TCP
为了保证报文传输的可靠，就给每个包一个序号，同时序号也保证了传送到接收端实体的包的按序接收。然后接收端实体对已成功收到的字节发回一个相应的确认(
ACK)；如果发送端实体在合理的往返时延(RTT)内未收到确认，那么对应的数据（假设丢失了）将会被重传。

- 连接建立

TCP是因特网中的传输层协议，使用三次握手协议建立连接。当主动方发出SYN连接请求后，等待对方回答 SYN + ACK
，并最终对对方的 SYN 执行 ACK 确认。这种建立连接的方法可以防止产生错误的连接，TCP
使用的流量控制协议是可变大小的滑动窗口协议。

TCP三次握手的过程如下：

![连接建立][2]

1. 客户端发送 SYN（SEQ=x）报文给服务器端，进入 SYN_SEND 状态。
2. 服务器端收到 SYN 报文，回应一个 SYN （SEQ=y）ACK(ACK=x+1）报文，进入 SYN_RECV 状态。
3. 客户端收到服务器端的 SYN 报文，回应一个 ACK(ACK=y+1）报文，进入 Established 状态。

三次握手完成，TCP客户端和服务器端成功地建立连接，可以开始传输数据了。

- 连接终止

建立一个连接需要三次握手，而终止一个连接要经过四次挥手，这是由TCP的半关闭（half-close）造成的。具体过程如下图所示。

![连接终止][3]

1. 某个应用进程首先调用 close，称该端执行“主动关闭”（active close）。该端的 TCP 于是发送一个 FIN
   分节，表示数据发送完毕。
2. 接收到这个 FIN 的对端执行 “被动关闭”（passive close），这个 FIN 由 TCP 确认。

> 注意：FIN 的接收也作为一个文件结束符（end-of-file）传递给接收端应用进程，放在已排队等候该应用进程接收的任何其他数据之后，因为，FIN
> 的接收意味着接收端应用进程在相应连接上再无额外数据可接收。

3. 一段时间后，接收到这个文件结束符的应用进程将调用 close 关闭它的套接字。这导致它的 TCP 也发送一个
   FIN。
4. 接收这个最终FIN的原发送端 TCP（即执行主动关闭的那一端）确认这个 FIN。
   既然每个方向都需要一个 FIN 和一个 ACK，因此通常需要4个分节。

无论是客户还是服务器，任何一端都可以执行主动关闭。通常情况是，客户执行主动关闭，但是某些协议，例如，HTTP/1.0却由服务器执行主动关闭。

## 什么是 UDP ？

UDP 是 User Datagram Protocol 的简称， 中文名是用户数据报协议，是 OSI（Open System
Interconnection，开放式系统互联） 参考模型中一种无连接的传输层协议，提供面向事务的简单不可靠信息传送服务，IETF
RFC 768 是 UDP 的正式规范。UDP 在 IP 报文的协议号是17。

UDP 协议全称是用户数据报协议，在网络中它与 TCP 协议一样用于处理数据包，是一种无连接的协议。UDP
有不提供数据包分组、组装和不能对数据包进行排序的缺点，也就是说，当报文发送之后，是无法得知其是否安全完整到达的。UDP
用来支持那些需要在计算机之间传输数据的网络应用。包括网络视频会议系统在内的众多的客户/服务器模式的网络应用都需要使用
UDP 协议。

UDP 是不具有可靠性的数据报协议。细微的处理他会交给上层的应用去完成。在 UDP
的情况下，虽然可以确保发送消息的大小，确不能保证消息一定会到达。因此应用有时会根据自己的需要进行重发处理。

## TCP 与 UDP 的区别

TCP 用于在传输层有必要实现可靠传输的情况。由于它是面向有链接并具备顺序控制、重发控制等机制的，所以他可以为应用提供可靠的传输。

而在一方面，UDP 主要用于那些对高速传输和实时性有较高要求的通信或广播通信。

我们举一个通过 IP 电话进行通话的例子。如果使用 TCP，数据在传送途中如果丢失会被重发，但这样无法流畅的传输通话人的声音，会导致无法进行正常交流。而采用
UDP，他不会进行重发处理。从而也就不会有声音大幅度延迟到达的问题。即使有部分数据丢失，也支持会影响某一小部分的通话。此外，在多播与广播通信中也是用
UDP 而不是 TCP。

## 什么是 IP ？

网络之间互连的协议（IP）是 Internet Protocol 的外语缩写，IP 是在 TCP/IP
协议中网络层的主要协议，任务是仅仅根据源主机和目的主机的地址传送数据。为此目的，IP
定义了寻址方法和数据报的封装结构。第一个架构的主要版本，现在称为 IPv4，仍然是最主要的互联网协议，尽管世界各地正在积极部署
IPv6。

IP 协议的作用是把各种数据包传送给对方。而要保证确实传送到对方那里，则需要满足各类条件。其中两个重要的条件是
IP 地址和 MAC地址（Media Access Control Address）。

IP 地址指明了节点被分配到的地址，MAC 地址是指网卡所属的固定地址。IP 地址可以和 MAC 地址进行配对。IP
地址可变换，但 MAC地址基本上不会更改。

## 什么是 Socket ？

应用在使用 TCP 或 UDP 时，会用到操作系统提供的类库。这种类库一般被称为 API（Application Programming
Interface，应用编程接口）。

使用 TCP 或 UDP 通讯时，优惠广泛使用到套接字（Socket）的 API。套接字原本是由 BSD UNIX 开发的，但是后被移植到了
Windows 的 Winsock 以及嵌入式操作系统中。

应用程序利用套接字，可以设置对端的 IP 地址，端口号，并实现数据的发送与接收。

网络上的两个程序通过一个双向的通信连接实现数据的交换，这个连接的一端称为一个 Socket。

建立网络通信连接至少要一对端口号(Socket)。Socket 本质是编程接口(API)，对 TCP/IP 的封装，TCP/IP
也要提供可供程序员做网络开发所用的接口，这就是 Socket 编程接口；HTTP 是轿车，提供了封装或者显示数据的具体形式；Socket
是发动机，提供了网络通信的能力。

## TCP/IP 通讯示例

![TCP/IP 通讯示例][4]

在 TCP/IP
通讯过程中，每个分层，都会对所发送的数据附加一个首部，在这个首部中包含了该层必要的信息，如发送端的目标地址一节协议相关信息。通常，为协议提供的信息为包首部，所要发送的内容为数据。在下一层角度看，从上一层收到的包全部被认为是本层的数据。

假设甲给乙发送邮件，内容为：“早上好”。而从 TCP/IP 通信上看，是从一台计算机 A 向另一台计算机 B
发送邮件。我们通过这个例子来讲解一下 TCP/IP 通信的过程。

- 数据包的发送处理

1. 应用程序处理

启动应用程序新建邮件，将收件人邮箱填好，再由键盘输入“早上好”，鼠标点击“发送”按钮就可以开始 TCP/IP 的通信了。

首先，应用程序会对邮件内容进行编码处理，例如：UTF-8，GB2312 等。这些编码相当于 OSI 的表示层功能。应用在发送邮件的那一刻建立
TCP 连接，从而利用这个 TCP 连接发送数据。它的过程首先是将应用的数据发送给下一层的 TCP，在做实际的转发处理。

2. TCP 模块处理

TCP根据应用的提示，负责建立连接，发送数据以及断开连接。TCP 提供将应用层发来的数据顺利发送至对端的可靠传输。

为了实现 TCP 的这一功能，需要在应用层数据的前端附加一个 TCP 的首部。TCP 的首部中包括源端口号和目标端口号、序号。随后将附加了
TCP 首部的包再发送给 IP。

3. IP 模块处理

IP 将 TCP 传过来的 TCP 首部和 TCP 数据合起来当做自己的数据，并在 TCP 首部的前端加上自己的 IP 首部。IP
首部中包含接收端 IP 地址，发送端 IP 地址。随后 IP 包将被发送给连接这些路由器或主机网络接口的驱动程序，以实现真正的发送数据。

4. 网络接口（以太网驱动）的处理

从 IP 传过来的 IP 包，对于以太网卡来说就是数据。给这些数据附加上以太网首部并进行发送处理。以太网首部中包含接收端
MAC 地址，发送端 MAC 地址，以太网类型，以太网数据协议。根据上述信息产生的以太网数据将被通过物理层传输给接收端。

- 数据包的接收处理

1. 网络接口（以太网驱动）的处理

主机收到以太网包以后，首先从以太网的包首部找到 MAC 地址判断是否为发给自己的包。如果不是发给自己的则丢弃数据，如果是发给自己的则将数据传给处理
IP 的子程序。

2. IP 模块处理

IP 模块收到 IP 包首部以及后面的数据部分以后，也做类似的处理。如果判断得出包首部的 IP 地址与自己的 IP
地址匹配，则可接受数据并从中查找上一层的协议。并将后面的数据传给 TCP 或者 UDP
处理。对于有路由的情况下，接收端的地址往往不是自己的地址，此时需要借助路由控制表，从中找出应该送到的主机或者路由器以后再进行转发数据。

3. TCP 模块处理

在 TCP
模块中，首先会校验数据是否被破坏，然后检查是否在按照序号接收数据。最后检查端口号，确定具体的应用程序。数据接收完毕后，接收端则发送一个“确认绘制”给发送端。数据被完整地接收以后，会传给由端口号识别的应用程序。

4. 应用程序处理

接收端应用程序会直接接收发送端发送的数据。通过解析数据可以获知邮件的内容信息。

![TCP/IP 数据包][5]

## 参考资料

《图解HTTP》、《图解TCP/IP》、百度百科

[1]:https://github.com/jeanboydev/Android-ReadTheFuckingSourceCode/blob/master/resources/images/http/tcp_osi.jpg?raw=true

[2]:https://github.com/jeanboydev/Android-ReadTheFuckingSourceCode/blob/master/resources/images/http/tcp_start.jpg?raw=true

[3]:https://github.com/jeanboydev/Android-ReadTheFuckingSourceCode/blob/master/resources/images/http/tcp_end.jpg?raw=true

[4]:https://github.com/jeanboydev/Android-ReadTheFuckingSourceCode/blob/master/resources/images/http/tcp_transaction.jpg?raw=true

[5]:https://github.com/jeanboydev/Android-ReadTheFuckingSourceCode/blob/master/resources/images/http/tcp_data.jpg?raw=true

## 扫一扫关注我的公众账号

<img src="https://github.com/jeanboydev/Android-ReadTheFuckingSourceCode/blob/master/resources/images/wechat/qrcode_for_gh_26eef6f9e7c1_258.jpg?raw=true" width=256 height=256 />

