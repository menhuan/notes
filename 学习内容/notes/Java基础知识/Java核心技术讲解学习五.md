昨天我们简单的了解安全点和安全区域了解HotSpot虚拟机是怎么快速的完成GC的，这次我们才了解下期中使用的垃圾回收器都有什么。
垃圾回收器是JVM虚拟机进行垃圾回收的主要力量，那么在现在的发展过程中有多少垃圾回收期类型，并且以后垃圾回收器的发展又是什么呢？
# 垃圾回收器
   垃圾回收器从JVM虚拟机出来在Java中现在已经发展了好几代，大体形成了7种类型的虚拟机。有Serial收集器，ParNew，ParallelScavenge ,CMS  , Serial Old , Parallel Old  ，还有最新的G1收集器。
## Serial 收集器
   这个收集器在Java历史中是时间最长久的收集器，该收集器是一个单线程的收集器，并且收集器在收集的过程中会暂停其他线程的工作，直到该收集工作完成后才会进行工作。Serial收集工作流程如下：
![处理流程](https://upload-images.jianshu.io/upload_images/4237685-0856d872d47b34b7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## Serial Old 收集器
   Serial Old 收集器是Serial 收集器的老年代的版本，我们上面在介绍Serial 时简单说过它内部的算法。标记-整理算法
![处理流程](https://upload-images.jianshu.io/upload_images/4237685-0856d872d47b34b7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## ParNew 收集器
  该收集器可以看成Serial线程 的多线程版本。该收集器在与后面要写到的收集器CMS进行配合工作，也只有该收集器能与CMS收集配合。并且收集器提高了Serial收集器时代的效率问题。属于第一款并行收集器。
为了方便下面的介绍这里简单的介绍下并行与并发。
- 并行： 在同一个时刻多条线程并行工作，工作线程继续等待。也就是暂停。所有线程交给垃圾处理器使用。
- 并发：指的的工作线程与垃圾处理器线程同时执行。
![image.png](https://upload-images.jianshu.io/upload_images/4237685-30b09829e31a4c2f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## Parallel Scavenge 收集器
   该收集器属于新生代收集器跟上面两个收集器在新生代使用的算法一样 都是复制。这个收集器与其他手机器不同的方向是，他的目的是用来控制吞吐量，而其他的收集器目的是尽可能的减少程序的停顿时间。我们在使用这个收集器的时候可以配置-XX:MaxGCPauseMillis 控制垃圾回收的停顿时间，-XX:GCTimeRatio控制吞吐量。 
 * -XX:GCTimeRatio 是一个比例时间控制 大于0 小于100的值， 如果该参数设置成5 那么垃圾回收的时间就是总程序时间的5/100 ；时间过少容易造成GC比较频繁，这样也容易降低吞吐量。设置时间长点，GC就不会太频繁。并且吞吐量提高了。这个时间设置需要平衡。
## Parallel Old 收集器
  Parallel Old 收集器是Parallel Scavenge 收集器的老年版本 使用的是多线程与 标记-整理算法。该线程与Parallel Scavenge 进行配合在注重吞吐量和CPU资源敏感的场合，该组合都可以优先考虑。
![收集器](https://upload-images.jianshu.io/upload_images/4237685-7bf285467ed4a11d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## CMS收集器
 CMS收集器 是一种  以获取最短回收停顿时间为目标的收集器。CMS使用的算法是标记-清除算法。整个运行流程为  
* 初始标记  ：执行过程汇总会暂停工作线程。 该过程中只标记GC Root能关联到的对象
* 并发标记  ：执行过程中可与工作线程一块进行 。该过程进行 GC RootsTracing 过程
* 重新标记  ： 执行过程中会暂停工作线程。 并且修改并发标记期间改变的对象的标记记录
* 并发清除  ：执行过程中可与工作线程一块进行 。该过程 清除带有标记的对象。
CMS收集器优点在于并发，低停顿，但是该收集器还有他的缺点:
1. 对CPU资源敏感 。 虽然可以与应用程序一块执行，但是总会占用CPU资源，如果程序是CPU敏感性程序，那么CMS收集器就会造成程勋运行慢的情况。
2. CMS收集器无法处理浮动垃圾，这种情况容易造成 Full GC。在垃圾回收的过程中 还会有新的垃圾不断产生，所以只能留到下次执行的时候才能去处理。
3. 算法是标记-清除算法，该算法我们前面 介绍过，无法处理碎片空间，如果碎片空间过多，大对象无法创建，就会引发Full gc j进行清理。
## G1 收集器
  G1收集器 应该来说是现在最好的收集器，但是在程序执行过程中我们应该具体情况具体分析。G1都有什么特点呢？
 1. 并行与并发：G1能充分利用多核CPU的环境，可以使用并行来缩短Stop-The-Word 停顿的时间，但是并不停顿java线程，能让程序继续执行。
 2. 分代收集：G1收集器不用其他收集器配合就能管理GC堆。采用不同的方式去处理创建的对象和已经存活好好几代的就对象。
3. 空间整合： 整体看属于标记-整理算法，但是在每个区域内还形成了复制算法。这样能不产生碎内存，分配大对象时能得到充足的空间不会导致新的一次GC.
4. 可预测的停顿： 建立可预测的停顿时间模型。让使用者明确指定在一个长度为M毫秒内的时间段内，小号在垃圾收集上的时间不超过N毫秒。该模型避免了在Java整个堆内进行全区域的垃圾收集。G1会维护一个优先列表。用来跟踪Region里面的堆的价值大小。
5. 在避免Gc进行全盘扫描的对象的情况下,G1在每个Region中是用Remembered Set 来维护 。虚拟机在对对象进行写操作时，会产生一个终端操作。检查Reference引用的对象是佛UC呼吁不同的Region中，如果是便通过CardTable 把相关引用记录到被引用对象所属的Region的Remembered Set 中。

G1收集器在收集的过程中，一般会进行以下几个步骤。
1. 初识标记 ：只是标记GC root 能关联到的对象。会停顿，但是耗时十分短。
2. 并发标记： 对对象进行可达性分析。找出存活对象 ，可以与用户线程并发执行。耗时长点
3. 最终标记： 修正在并发标记期间导致变动的标记记录。将记录记录在 Remembered Set Logs中。将Remembered Set Logs 合并到Remembered Set 中 。需要停顿，但是可以并行执行。
4. 筛选回收： 对Region中的数据进行回收。
![G1收集流程](https://upload-images.jianshu.io/upload_images/4237685-3c7e539c00fd106a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 
# 垃圾收集器参数总结
垃圾参数设置有很多，并且设置上也有很多的不同这个就不全部说了。最近知道一个阿里的大佬出来创业就是做的相关的jvm参数调优。可以去这里看看 学习学习[参数调优](http://xxfox.perfma.com/jvm/generate)
![参数参考深入Java虚拟机参数](https://upload-images.jianshu.io/upload_images/4237685-ea492780c5398e9e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![参数参考深入Java虚拟机参数](https://upload-images.jianshu.io/upload_images/4237685-b5f92e1dfede4308.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![星球](https://upload-images.jianshu.io/upload_images/4237685-d981658098f293ff.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

