### 列表简介
&nbsp;&nbsp;Redis的列表允许用户从序列的两端推入元素或者弹出元素。可以来创建常见的队列信息。
### 常用命令
1. blpop: 列表阻塞式弹出。当在查询如果指定得key 都是空列表那么会一直等待。如果存在得key中有值那么会取出值来。不建议放在事务中MULTI/EXEC中。时间复杂度为O(1)。模式:事件提醒。为了等待一个元素得到达列表中会采用轮询得方式来进行探查。另一种好得方式是使用阻塞原语。在新元素没有到达得时候阻塞住，避免轮询占用资源。blpop key key key timeout 
2. brpop: 同上，阻塞惭怍。
3. brpoplpush: 阻塞版本。从一个列表中取出元素，然后放入到目标列表中。 brpoplpush source destination timeout. 超时参数timeout接收一个秒为单位得数字作为值。 设为0表示无限期延长。 时间复杂度为O(1). 
4. lindex: 返回劣种key中 下表为index得元素。  0代表第一个元素 ，-1代表 最后一个元素。时间复杂度O(N) .n为下标index 过程中经过得元素数量。第一个元素和最后一个元素 时间复杂度是O(1);
5. linsert: linsert key before | after pivot value 将值value 插入到列表key中。位于值pivot之前和之后。 pivot 不存在得时候不执行操作。key 不存在 也不执行操作。linsert key before pivot value
6. llen:返回列表key得长度，key不存在 返回0 .不是列表类型返回一个错误 llen key。时间复杂度O(1);
7. lpop: 移除并返回列表key中得头元素。 时间复杂度为O(1); key不存在得时返回nil。
8. lpush: 将一个value 或者多个value插入到列表中。  如果key不存在 一个空列表会被创建并执行lpush操作。时间复杂度是O(1); lpush key value
9. lrange:取回列表中区间内得元素。区间以偏移量start 和stop 指定。但是这个操作不会移除选择得元素。时间复杂度O(S+N) S是偏移量start N为指定区间内得元素得数量。 lrange key start stop 
10. lrem: 根据参数count得值 移除列表中与参数value相等得元素。count 可以是0 大于0 或者小于0 。0代表移除表中得与value相等得所有元素。 大于0 代表从表头开始索索。移除与value相等得值 数量为count.小于0 同理。从表尾开始搜索。
11. lset: 将列表key 下表为index得元素设置为value. 当index 超出范围 或者key 不存在 时返回一个错误。时间复杂度：O(N)。N为列表的长度。
12. ltrim:对一个列表进行修剪，只保留区间内的元素。当选择的key不是一个列表类型的时候。返回一个错误类型。ltrim key start stop 双闭。 时间复杂是O(N),N为被移除的元素的数量。
13. rpop:移除并返回列表key的尾元素 时间复杂度是O(1);key不存在的时 ，返回nil.
14. rpoplpush:rpoplpush soruce destionation .同brpoplpush . 
15. rpush:将一个或多个值 value 插入到列表 key 的表尾. rpush key value value .![微信公众号二维码.jpg](https://upload-images.jianshu.io/upload_images/4237685-93ff2dd02ed0a481.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![星球二维码.jpg](https://upload-images.jianshu.io/upload_images/4237685-bfd39dc08c2d5ea7.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

16. rpushx:将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表。
