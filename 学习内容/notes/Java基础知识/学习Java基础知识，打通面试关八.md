
在上一篇 我们简单的说了下List集合里面的信息，今天我们来说说下Map相关的内容，了解集合，让我们在程序应用中灵活的使用。
在平常程序中我们经常使用的map集合为HashMap,TreeMap，HashTable,ConcurrentHashMap。这几种都有什么区别呢？平常中我们应该注意什么？下面我们一个一个的来看。
# Map
  我们经常的使用Map，但是都是使用的Map的子类，然而Map这个接口具体实现的是什么呢？
 * 我们经常使用的Map子类都是实现该Map接口。是顶级的接口
# Hashtable   
   Hashtable 是在Java早期实现的散列表，其实现也是依靠哈希表，我们都知道程序中有线程安全的问题。
* 我们在Hashtable上也实现了线程安全，他是同步的。因为同步，在性能上比较消耗的大，一般程序中不推荐，如果需要线程安全的hash表那么该类可以支持
* 该类在的key和value都不能是null。
* 内部实现也是一开Entry[]数组类型，而Entry时间上是一个单项链表，并且哈希表中的key和value都是保存在Entry数组中的。
* 继承自Dictionary 实现 Map接口。
```
     */
    public synchronized int size() {
        return count;
    }

    /**
     * Tests if this hashtable maps no keys to values.
     *
     * @return  <code>true</code> if this hashtable maps no keys to values;
     *          <code>false</code> otherwise.
     */
    public synchronized boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns an enumeration of the keys in this hashtable.
     *
     * @return  an enumeration of the keys in this hashtable.
     * @see     Enumeration
     * @see     #elements()
     * @see     #keySet()
     * @see     Map
     */
    public synchronized Enumeration<K> keys() {
        return this.<K>getEnumeration(KEYS);
    }
//都是线程安全的代码 
```
# HashMap
在程序中经常用到HashMap，该类是不同步的操作。
* 键与值是可以是null，这点与HashTable不同。
* Hash的性能依赖于哈希码的有效性。我们需要掌握hashCode与equals的一些约定：
  1. equal 相等，那么hashCode 一定相等。但是反过来不一定。
   2. 重写hashCode 也需要重写equal 。
* HashMap的内部实现 ：
  在Java中我们都知道，基本的数据机构有两种，分别为数组和链表，而Map实际上也是数据和链表的组合体。
在java8中，HashMap采用了Node数组的方式来实现，，我们看到的源码也改变了很多。
* HashMap中键值的寻找采用的根据KeyValue的hasdCode来寻址，如果计算的哈希值相同的键值对，采用链表的形式存储。但是在HashMap中还设置了一个阈值，超过这个阈值就会改成树状结构
```
 public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.loadFactor = loadFactor;  //初始化 设置大小 ，尽量自己设置好
        this.threshold = tableSizeFor(initialCapacity);
    }
```
 ```
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;   //java8中使用了node数组结构  
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;    //在这里我们可以看到如果tab 为null ，首先会初始化,其中resize 在容量不足的情况下 ，对Map集合进行扩容
        if ((p = tab[i = (n - 1) & hash]) == null)    /
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)   //在这里 进行比较 是否大于初始化 设定的容量大小 ，大于就进行扩容操作
            resize();
        afterNodeInsertion(evict);
        return null;
    }
```
我们在代码中看到 Map初始化容量后，如果容量不够，那么会进行扩容，这里扩容会怎么扩容呢？代码太多只粘贴重要的代码解释。
```
  final Node<K,V>[] resize() {

 if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {    //在这里 进行判断 是否大于 该最大值  ，MAXIMUM_CAPACITY 大小为 2的30次方
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold 
// 在这里我们看到门限值 是在成倍的增加，只要超过这个门限的大小就进行扩容，
//扩容后还需要把原先的数据放到新的数组上，这个是扩容的开销.
//如果内存紧张的情况下，尽量避免使用集合，采用数组的方式来实现。 并且门限值时等于 负载因子乘以 容量的

        }
}
```
我们也在上面说了 如果hashCode一直会放到链表中，但是如果太大，会将链表改成树状结构，树状结构的改造.  树状结构可以在treefyBin中看到
*  当链表的长度 小于我们设置的MIN_TREEIFY_CAPACITY 时 只是简单的 进行链表扩容，如果大于其值就会进行树状扩容。
*  链表 的特点就是插入删除的快速，在查找上性能较差，map中数据量太大的话，容易导致查找性能下降，改造成树状结构，在大数据情况下性能比较好
```
 final void treeifyBin(Node<K,V>[] tab, int hash) {
        int n, index; Node<K,V> e;
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();
        else if ((e = tab[index = (n - 1) & hash]) != null) {
            TreeNode<K,V> hd = null, tl = null;
            do {
                TreeNode<K,V> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;
                else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            if ((tab[index] = hd) != null)
                hd.treeify(tab);
        }
    }
```

# TreeMap
TreeMap 其实就是树状结构的Map.
* 实现是依靠红黑树算法实现的。红黑树也是一个二叉树，具备二叉树的所有特性，并且是一颗自平衡的排序二叉树。二叉树都满足一个基本形式那就是树中的任何节点的值都大于左节点小于右节点。
* 在TreeMap中他的顺序由键控制的。通过Comparator或者Comparable来决定。
* 平常在使用该TreeMap时，有需要排序规则的可以使用。可以自定义排序规则，或者使用上面所说的自然顺序。
# LinkedHashMap 
  LinkedHashMap属于一个双向链表，通过键值对来维护，这里提供的是遍历顺序符合插入顺序。也就是说遍历的顺序是插入的顺序。
* LinkedHashMap 落脚点还是HashMap。相当于一个有顺序的HashMap。
*LinkedHashMap 增加了两个属性用于保证迭代顺序，分别是 双向链表头结点header 和 标志位accessOrder (值为true时，表示按照访问顺序迭代；值为false时，表示按照插入顺序迭代)。
* 维护双向列表，增加了空间和性能的上的消耗，如果不是特别需求建议不使用。
上面就是简单说的几个Map结构。这些都是我们平常使用的。应该注意其特点使用
**欢迎大家关注的我的公众号，大家一块学习**
![星球和公众号.jpg](https://upload-images.jianshu.io/upload_images/4237685-1b27118bb567cf9c.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
