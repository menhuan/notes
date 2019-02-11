在上一篇文章中我们说到了Map集合中的一部分内容，还有并发包中的Map并没有说到，现在我们来说下并发包中的Map~ConcurrentHashMap。
# Java8之前的ConcurrentHashMap 实现
在前期中ConcurrentHashMap的基本实现思路：
* ConcurrentHashMap 采用的是分段锁的设计方案，只有在同一个分段内的数据才会存在竞争关系，这就不会造成对一个Map 进行整体的锁，根据不同的分段进行不同的锁，在这里分段锁被称为Segment。在内部实现的是一个**Entry数组** 。而每一个数组中的元素又是一个链表构成。
* 锁在这里使用的ReentrantLock ，Segment 继承ReentrantLock。在该Map中的HashEntry 的value以及next都被volatile修饰，这样能保证数据的可见性。
* ConcurrentHashMap  中并发度默认是16 但a是我们可以在构造函数的时候进行设置。设置时该值需要设置成2的幂数值。不符合规则的数值设置 会自动的调整成符规则的设置的数值。
* ConcurrentHashMap 也存在扩容的问题，这个跟HashMap类似，但是不是针对的整个ConcurrentHashMap,而是单独对Segment进行扩容。也会遇到同样的操作错误。
* size 计算容易时出错，特别是在并发实现的时候将每个Segment的大小进行相加，在相加的过程中可能还会进行数据的插入，那么得到的结果就是不准确的偏小。
# Java8的ConcurrentHashMap实现
在实现上放弃的Segment 的实现，采用了Node +CAS + Synchronized 来保证并发的安全。
* 虽然在java8中Segment还存在，但是结构上不再使用，采用Lazy-load的形式，这样避免了初始化的开销。
* 数据可见性采用了volatile ,所惭怍采用了CAS并且部分还实现了无锁的操作。
* ConcurrentHashMap 中操作的时候key value 不能是null 这样会出现操作问题。
* 初始化方法时使用的initTable，在调用的时候进行参数的设置。主要是设置sizeCtl该属性，如果发现有竞争性的初识化，那么就自旋等待恢复。
```
/**
     * Initializes table, using the size recorded in sizeCtl.
     */
    private final Node<K,V>[] initTable() {
        Node<K,V>[] tab; int sc;
        while ((tab = table) == null || tab.length == 0) {
                //sizeCtl表示有其他线程正在进行初始化操作，把线程挂起。对于table的初始化工作，只能有一个线程在进行。
            if ((sc = sizeCtl) < 0)
                Thread.yield(); // lost initialization race; just spin
            else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {//利用CAS方法把sizectl的值置为-1 表示本线程正在进行初始化
                try {
                    if ((tab = table) == null || tab.length == 0) {
                        int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                        @SuppressWarnings("unchecked")
                        Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                        table = tab = nt;
                        sc = n - (n >>> 2);//相当于0.75*n 设置一个扩容的阈值
                    }
                } finally {
                    sizeCtl = sc;
                }
                break;
            }
        }
        return tab;
    }
```
* 扩容，在jdk1.7的版本中我们知道扩容容易有很大的性能问题，那么在1.8怎么解决呢？
 1. 扩容分为了两部分，构建新的nextTable ,容量是原先的两倍，单线程操作。
 2. 进行数据复制到新的nextTable中，这里是多线程操作。
 3. 整体 遍历每个节点，然后复制的过程
* size的大小计算，这个计算在新版本中，只是一个估值。虽然计算的是很费周折。一般该值还是很稳定的。


