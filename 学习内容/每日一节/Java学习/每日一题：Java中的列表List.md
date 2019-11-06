# Java中的列表List

在Java中常用集合列表List就是其中一种，在List中常用的列表是ArrayList,LinkedList,Vector,当然还有其他类型的List。

如下图List接口图所示:
![Java列表接口](http://jikelearn.cn/2019-11-04-23-24-21.png)

那今天不讲解源码级别，说下三个实现列表的区别。

## ArrayList

ArrayList 常用的数组List,底层是数组，在前面一篇文章中说过数组的结构，请看这一篇文章[程序中常用的数组](https://github.com/menhuan/notes/blob/master/%E5%AD%A6%E4%B9%A0%E5%86%85%E5%AE%B9/%E6%AF%8F%E6%97%A5%E4%B8%80%E8%8A%82/%E7%AE%97%E6%B3%95%E5%AD%A6%E4%B9%A0/%E6%AF%8F%E6%97%A5%E4%B8%80%E8%8A%82%EF%BC%9A%E7%A8%8B%E5%BA%8F%E4%B8%AD%E5%B8%B8%E7%94%A8%E7%9A%84%E6%95%B0%E7%BB%84.md)。由于数组的一些特性以及操作上的不方便，在Java中就有了ArrayList这个数据结构。

ArrayList能解决数组数据操作不方便，添加数据，删除数据，修改数据。但还拥有数组的特性。

- ArrayList的扩容跟数组的扩容是类似的，需要进行数据的迁移,数据的扩容在Java8中默认是**int newCapacity = oldCapacity + (oldCapacity >> 1); 原先长度的1.5倍**
- ArrayList的随机访问时间复杂度是O(1),可以根据下标来检索数据。
- 在ArrayList中删除数据，就内部就需要数据的迁移。
- ArrayList中增加数据，add的方法是在末尾增加，所以时间复杂度是O(1)，但如果不是在中间增加数据，ArrayList中也需要进行数据的迁移。
- ArrayList在知道容量的情况下，初始化时指定长度，减少扩容，增加性能。
  
## LinkedList

LinkedList 是建立在**链表**数据结构上的，链表的特点是需要保存下一个节点的地址，并且该列表是双向链表，需要保存前节点与后节点的地址。占用的内存空间会比ArrayList多，但链表形式的List也有自己的特点。

- 链表形式的List，节点的插入与删除很高效，删除数据与插入数据不需要数据迁移
- LinkedList的随机访问性能差，只能一个个数据遍历。
- 空间占用比ArrayList要大一些。

## Vector

该List是线程安全的List，所以相对来说性能会比上面两种List差一些，不存在多想成环境下不建议使用Vector。

- 内部也是使用数组构建，也需要进行扩容。
- 线程安全，保证多线程情况下数据的同步。

