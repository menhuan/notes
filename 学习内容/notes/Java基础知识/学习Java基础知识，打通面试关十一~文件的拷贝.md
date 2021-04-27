上一篇文章我们说了IO流操作，里面区分了BIO,NIO,AIO，这些方式提高了我们在文件的操作，那么我们使用文件拷贝的时候，IO里面又提供了什么内容呢？ 在JDK1.7以前是没有文件的拷贝的方式的。
# 四种方式IO流的拷贝操作
## 使用FileStreams 
   我们使用的是JDK中最直接的方式读取文件，然后写入到文件中。从步骤上来说分为两步，**读取**，**然后写入**，追一个十分低效率的方式。增加了系统上下文切换的的次数。
```
 /**
     * 拷贝文件
     * @param source  源文件
     * @param dest  目的文件
     * @throws Exception
     */
    public void copyFile(File source , File dest) throws  Exception{

        try (InputStream is  = new FileInputStream(source);
             OutputStream out = new FileOutputStream(dest);){
            byte[]  buff = new byte[2048];
            int length  ;
            while ((length = is.read(buff))>0){
                out.write(buff,0,length);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
```
## 使用FileChannel复制
我们在上篇文章[学习Java基础知识，打通面试关十~IO流](https://www.jianshu.com/p/5deeadc1f4a7)中说到
文件读取使用NIO解决堵塞的问题，在这里使用NIO的方式也能提高文件的复制功能。我们使用的NIO中的transferFrom方法.该方式更能利用现代的操作系统的底层机制，避免了一些拷贝和上下文的切换。
```
    /**
     * Nio的方式 来拷贝文件
     * @param source 源文件
     * @param dest 目的文件
     * @throws Exception
     */
    public void copyFileNio(File source , File dest) throws  Exception{

        try (FileChannel  in = new FileInputStream(source).getChannel() ;
             FileChannel out = new FileOutputStream(dest).getChannel();){
             out.transferTo(in,0,in.size());

             //或者采用下面这种方式  使用循环的方式把数据写过去 
            long count =  in.size();
            
            while(count > 0 ){
                long length  = in.transferTo(in.position(),count,out);
                in.position(count-=length );

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
```
## 使用Apache 工具包Commons IO复制
 ```
 /**
     * 使用Common IO操作
     * @param source 源文件
     * @param dest 目的文件
     * @throws Exception
     */
    public void copyFileNCom(File source , File dest) throws  Exception{

        try{
            FileUtils.copyFile(source,dest);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
```
# Java7上增加的copy方式
```
private static void copyFiles(File source, File dest)
        throws IOException {    
        Files.copy(source.toPath(), dest.toPath());
}
```
在这里我们深入了解其拷贝基础 进入源码查看实现有多种的copy方式，方法提供了三个 ，在这里已经不限制文件，可以是其他的流等。虽然最终的拷贝里面内部用的不是我们想说的transferTo,但是内部是通过本地技术来实现用户生态拷贝。减少了一部分的上下文切换，提高了效率问题
![copy的几种方式](https://upload-images.jianshu.io/upload_images/4237685-7a2ce744e63fe963.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# 这几种方式的区别
在Copy的方式上不管是Java7还是 io中的读取文件写入文件，Apache中的工具包 这样的方式文件上下行切换比较多。 我们来学习下底层切换的知识。
## 用户态与内核态
  1. 用户态，当进程执行用户自己的代码时，这种状态称为用户态。
   2. 内核态，当程序执行系统调用的时候进入内核代码执行，我们把其称为内核运行状态。
   3. 用户栈，指向的是用户地址空间。
   4. 内核栈,   指向的是内核空间地址
他们之间的关系可以参考这张图
![关系图](https://upload-images.jianshu.io/upload_images/4237685-b7378815867ff333.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![进程通信](https://upload-images.jianshu.io/upload_images/4237685-276a3ea21272efbd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
通过这两种方式的话增加了额外的开销，会降低系统IO的能力。
而在NIO的方式中，改变了实现的方式，采用了零拷贝的技术，数据传输，不需要到用户态的参与，直接在内核上实现。这种减少了内核态与用户态之间的交流沟通，进而提高了应用的拷贝心梗。并且TransferTo不仅可以用在文件拷贝上，还以用在读取文件进行Socket发送等方式。
![调用TransferTo](https://upload-images.jianshu.io/upload_images/4237685-f409d912ed8432e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
        SocketChannel  socket = SocketChannel.open();
        try (FileChannel  in = new FileInputStream(source).getChannel() ;
             FileChannel out = new FileOutputStream(dest).getChannel();){
             out.transferFrom(in,0,in.size());
             //或者采用下面这种方式  使用循环的方式把数据写过去
            long count =  in.size();
            while(count > 0 ){
                //在这里采用读取数据文件直接发送给Socket 打开的socket链接
                long length  = in.transferTo(in.position(),count,socket);
                in.position(count-=length );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
```
总而言之在系统文件拷贝情况下
- 我们应该注意减少系统的上下文切换，多余的IO操作等，减少IO次数等，
- 减少不必要的操作例如编码解码，序列化，等信息在网络之间传输最好使用二进制信息传输。

