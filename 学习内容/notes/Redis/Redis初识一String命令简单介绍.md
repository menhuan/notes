####Redis简介
&nbsp;&nbsp;Redis 是一个速度非常快的非关系型数据库，可以存储key与5种不同类型的value值之间的映射，可以将存储在内存的键值对数据持久化到硬盘中，并且还可以使用复制的特性来扩展读的性能。在Redis中用户可以直接使用院子的atomic命令及其变种来计算聚合数据。
###Redis 特有的几种命令形式
1. String ：可以是字符串，也可以是整数或者浮点数。
- set： set redis 2   ----命令格式是 set key  value   重复对一个key进行操作的话的相当于新值覆盖旧值 。时间复杂度：O(1)，可以加参数 用来代替setnx ,setex,psetex.
- get ： get redis    ----命令格式是 get  key  如果不存在key值返回nil
- append : append redis  3  ----命令格式是 append key value  在已经有的key值情况下，将value追加到key原来值的末尾。如果key不存在 就相当于set命令了 。时间复杂度：平摊O(1) 。返回值：追加 value 之后， key 中字符串的长度。
- bigcount : bitcount redis 0 1 ----命令格式  bitcount key start stop . 计算给定字符串中被设置为1的比特位的数量。 不存在的key当做空字符串来处理 
- bitop :  bitop and redis1  redis redis1       ----命令格式： bitop operation(and,or,xor,not) destkey key [key...] 对一个或多个保存二进制位的字符串key进行位元操作。 并将结果保存到destkey上。时间复杂度O(N) 
- decr: decr redis ----命令格式  decr key 将key中存储的数字值减1 ，如果目标key不存在 那么 key对应的值初始化为0 。 然后在执行decr操作。时间复杂度：O(1)
- decrby : decrby redis 4 ----命令格式 decrby key decrement . 同decr 如果目标值不存在 默认为0  再减去decrement. 时间复杂度：O(1)
- getbit : getbit redis  2  ----命令格式 对key所存储的字符串值 获取指定偏移量上的位，当给定的偏移量大于字符串值的长度时 返回0 . 时间复杂度：O(1)
- getrange : getrange redis 0  2 ----命令格式 ：getrange key start end  ，返回key中字符串值的子字符串。字符串的街区范围由start 和end 两个偏移量决定。包含 start 和end.时间复杂度： O(N),N 为要返回的字符串的长度。相当于截取子字符串。
- getset : getset redis4 value  ----命令格式 ：getset key value  ，将给定的key设置成value,并且返回key的旧值。key不存在的时候返回nil
- incr : incr redis 4 ----命令格式 : incr key decrement . incr 如果目标值不存在 默认为0  再加1. 时间复杂度：O(1)。这是一个针对字符串的操作，因为 Redis 没有专用的整数类型，所以 key 内储存的字符串被解释为十进制 64 位有符号整数来执行 INCR 操作。
- incr by : incrby redis 4 ----命令格式 : incr rby key decrement . 同incr  如果目标值不存在 默认为0  再加上decrement. 时间复杂度：O(1)
- mget : mget redis redis4 ..... ----命令格式:  mget key..... .如果给定的key中值不存在返回nil.时间复杂度:
    O(N) , N 为给定 key 的数量。
- mset :mset redis6 1 redis2 2  ... ----命令格式 : mset key value  key value ....，存在key的值 新值会覆盖旧值。这个命令 是一个原子的操作，并且给定的key是在同一时间内改变的。某些给定的kkey被更新 一些不备更新这个事情不会发生。时间复杂度：O(N)， N 为要设置的 key 数量。
- msetnx: msetnx redis5 2 redis2 2 ...----命令格式:  msetnx key value  key value ....，这个命令也是原子操作，在该命令的操作下 所以字段要么全部被复制，要么全部失败。时间复杂度： O(N)， N 为要设置的 key 的数量。
- psetex : psetex redis2 200 v2 . 命令格式： psetex key milliseconds value . 设置key的生存时间 以秒为单位，时间复杂度：O(1)
- setbit :setbit redis2 1 2   命令格式：setbit key offset value . 对存在key 设置清除指定便宜量上的bit.对使用大的 offset 的 SETBIT 操作来说，内存分配可能造成 Redis 服务器被阻塞。具体参考 SETRANGE 命令，warning(警告)部分。
- setrange key offset value : 用value 来覆盖给定可所存储的字符串的值。如果key不存在 当做空字符串来处理。 时间复杂度 ： 对小(small)的字符串，平摊复杂度O(1)。(关于什么字符串是”小”的，请参考 APPEND 命令)否则为O(M)， M 为 value 参数的长度。
- strlen key  : 返回key所存储的字符串值的长度。 当key存储不是字符串值 返回一个错误 。key不存在 返回0 。复杂度：O(1)

![实例](https://upload-images.jianshu.io/upload_images/4237685-97df5a8f4a2ad482.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![微信公众号二维码.jpg](https://upload-images.jianshu.io/upload_images/4237685-5d0bb5924ca15a1d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
