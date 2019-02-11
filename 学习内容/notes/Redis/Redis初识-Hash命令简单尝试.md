### Hash命令
&nbsp;&nbsp;上次我们说了redis中的String命令，这次来简单的介绍下Hash命令。Hash命令可以存储多个键值对之间的映射，和字符串类似，散列存储的值既可以是字符串也可以是数值。并且我们也可以对散列存储的数字执行自增操作或者自减操作。
###操作命令
1.  hset: 将哈希表中的key 中的域值 设置为 value ; 如果key  filed 都不存在 ，设置的时候就是新建立的过程。存在就是将value 修改为新值。 时间复杂度为O(1)；hset key field value;
2.  hget: 根据给定的key filed 得到值。 如果不存在 返回nil ； 时间复杂度O(1);hget key filed 得到给定的域值。不存在即返回nil.
3.  hgetall: 返回给定的哈希值中的所有值。如果key不存在 返回空列表不是返回nil. 时间复杂度O(N)， N 为哈希表的大小。hgetall key.
4. hdel: 删除哈希表key中的一个活多个指定域。不存在的域将被忽略掉。时间复杂度O(N)， N 为要删除的域的数量。返回值时被移除的域值的数量。不包含被忽略的域值。
5. hexists:查看哈希表key中某个域值是否存在。时间复杂度是O(1),如果存在返回1 不存在返回0.hexists key field .
6. hincrby:执行自增 域中对应的值需为数字。不然会出错。 时间复杂度O(1).返回值 是执行hincrby key field increment (自增的值).
7. hincrbyfloat:同上，但是是自增为浮点数。
8. hkeys: 返回指定key中的所有域。 时间复杂度是O(N),N为哈希表的大小。当key不存在的时候返回空列表。hkeys key 
9. hlen:返回的是哈希表中 key 域的数量  。时间复杂度是O(1).key不存在的时，返回的是0.
10. hmget:返回哈希表中key。一个或者多个域值。 不存在返回nil值。时间复杂度是O(N). hmget key filed filed filed.
11. hmset: 将多个filed-value值 设置到哈希表中 此命令会覆盖已经存在的哈希表中的域值。 时间复杂度是O(N). 执行成功返回ok ：hmset key filed value filed value  filed value .
12. hsetnx: 将hashe表中的filed 值设置为value 仅filed 值不存在 该操作才有效。设置成功返回1。时间复杂度是O(1).给定的域存在 返回0 。
13. hvals:返回哈希表中key中所有的域值。 时间复杂度是O(N). 不存在的key 返回一个空表。hvals key 
### 命令尝试
![命令尝试](https://upload-images.jianshu.io/upload_images/4237685-a703836b60c52da9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![命令尝试](https://upload-images.jianshu.io/upload_images/4237685-a55e190dc778a559.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![微信公众号二维码.jpg](https://upload-images.jianshu.io/upload_images/4237685-86b0941e7a540bfc.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![星球二维码.jpg](https://upload-images.jianshu.io/upload_images/4237685-6125ab539963c8dd.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

