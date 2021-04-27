#### 并发集合简介
1. ConcurrentHashMap : 线程安全的HashMap;
2. CopyOnWriteArrayList : 读多少写的场合用的list;
3. ConcurrentLinkedQueue :  高效的并发队列，用链表实现。 线程安全的LinkedList;
4. BlockingQueue :  接口 用链表、 数组等方式实现接口， 阻塞队列 适合用数据共享的 通道。
5. ConcurrentSkipListMap ： 跳表的实现 ，是一个Map . 使用跳表的数据结构进行快速查找。
#### ConcurrentLinkedQueue : 高并发性能最好的队列
![内部方法.png](http://upload-images.jianshu.io/upload_images/4237685-9224bafbba3f286e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- ConcurrentLinkedQueue： 是使用CAS操作保证线程安全的。  

![CAS操作](http://upload-images.jianshu.io/upload_images/4237685-24949873117a1cd3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- casItem ():  设置当前Node的 item 值。当当前值等于cmp期望值时， 就会把目标值 修改为我们需要的值。同样casNext()方法类似。
- 哨兵节点： next 指向自己的节点。 主要表示 删除的节点，或者空节点。
#### CopyOnWriteArrayList:  高效读取
- 当有写入操作的时候 该List不会修改原先的内容，而是对原有数据进行一次复制。将修改的内容写入到副本中，然后将修改的副本替换原来的数据即可。
- 读取的的时候 该List 没有加任何的同步控制和锁操作。
- 写入操作会在写如的时候加锁，先进行数据复制，然后将新元素加入到副本中，并且将副本替换原先的数据即可，因为我们的数组是volatile类型 所有 当有改变的时候 会让线程读取到。
#### BlockingQueue:
- 阻塞 队列。主要是用来线程间通信使用。take() 方法 ： 如果取不到数据 ，就会等待 。等到数据到达后就可以取出来。 put()方法会将数据放到队列中。如果队列满了 ，需要让压入线程等待。
- **放入数据：**
  1. offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false.（本方法不阻塞当前执行方法的线程）
  2. offer(E o, long timeout, TimeUnit unit),可以设定等待的时间，如果在指定的时间内，还不能往队列中加入BlockingQueue，则返回失败。
  3. put(anObject):把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续.
- **取出数据 ：** 
  1. poll(time):取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,取不到时返回null;
  2. poll(long timeout, TimeUnit unit)：从BlockingQueue取出一个队首的对象，如果在指定时间内，队列一旦有数据可取，则立即返回队列中的数据。否则知道时间超时还没有数据可取，返回失败。
  3. take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到BlockingQueue有新的数据被加入; 
  4. drainTo():一次性从BlockingQueue获取所有可用的数据对象(还可以指定获取数据的个数)，通过该方法，可以提升获取数据效率；不需要多次分批加锁或释放锁。
#### 跳表：
- 是一种可以用来快速查找的数据结构，有点类似与平衡树。跳表数据额插入和删除只需要对整个数据结构的局部进行操作就行，平衡树是需要对整个数据结构全局调整。
- 另一个特点是随机算法。 本质是维护了多个链表并且链表还是分层的。先从上读取，当没有读取到 再到第二层读取，慢慢往下走读取数据。
![跳表图示](http://upload-images.jianshu.io/upload_images/4237685-63b6764a0b9b9b1e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 跳表实现的Map : 所有的元素是有序的， 如果需要有序的Map，跳表是一个可靠的实现。
