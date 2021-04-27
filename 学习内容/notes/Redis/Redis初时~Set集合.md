Redis中得集合以无序得方式来存储多个各不相同得元素，用户可以快速得对集合执行添加，移除操作或者检查一个元素是否存在集合中。
###Set集合命令操作
1. sadd:将一个元素或者多个元素插入到集合key中 已存在的集合元素会被忽略。 key不存在的情况下会创建一个集合。 时间复杂度是O(N)，n是被添加的元素的数量。 sadd key member member
2. scard: 返回集合中key的基数 时间复杂度是O(1). scard key 
3. sdiff: 返回一个集合的全部成员，该集合是所有的给定集合之间的差集。时间复杂度是O(N)。sdiff key key 相当于 第一个集合与接下来的集合 没有相同的元素成员集合返回。
4. sdiffstore: 作用与sdiff类似。 但是是将返回的结果集可以返回到destination集合当中， 如果destination集合已经存在 ，则将其覆盖。 destination可以使key本身。 sdiffstore destination key key 
5. sinter: 返回一个几个的全部成员 该集合是所有给定集合的交集 不存在的key 被视为空集。 时间复杂度为O(N*M) N为给定集合当中基数最小的集合，M为给定集合的个数。
6. sinterstore:作用与sinter类似。 但是是将返回的结果集可以返回到destination集合当中， 如果destination集合已经存在 ，则将其覆盖。 destination可以使key本身。 sinterstore destination key key 
7. sismember: 返回集合key中的所有成员，时间复杂度是O(N).N为集合的基数。sismember key
8. smove: 将member元素从 source集合 移动到destination集合中。 smove是原子操作，当源key不存在的时候不执行操作。 如果目的集合已存在 那么move操作相当于一个移除操作。smove source destination member .时间复杂度是O(1)
9. spop: 移除并返回集合中的一个随机元素。 时间复杂度是O(1)。被移除的随机元素 spop key。
10. srandmember: 与pop元素类似，但是 该命令仅仅是返回随机元素 不移除元素。 srandmember key count 并且还可以制定语出的数量。 count 可正可负。时间复杂度是O（|count|）
11. srem: 移除一个或多个元素 。时间复杂度是O(N)。 srrem key member member .
12. sunion: 返回一个集合的全部成员 该集合是所有给定集合的并集。 O（N）N是指所有成员的数量之和。 sunion key key key
13. sunionsttore: 类似 sunion 命令，但是是将返回的结果集可以返回到destination集合当中.存在就会覆盖。时间复杂度是O（N）。N是给定集合的成员数量之和
### SortedSet有序集合
我们上面介绍的集合是没有顺序的，但是很多时候我们在看榜单的时候都是有排名的那么我们完全可以借助redis的有序Set来实现这个功能。
1. zadd: 将一个或多个member元素及其score值加入到有序集合中key中。如果member存在那么只会更新score值。并通过重新插入这个元素来保证member在正确的位置上。score可以是整数值或者是双精度的浮点数。时间复杂度是O(M*log(N))N是有序集的基数。M为成功添加的新成员的数量。zadd key score member score member .
2. zcard: 返回有序集key的基数  时间复杂度是O(1). 时间复杂度是O（1）。返回有序集合的基数。
3. zcount: 返回有序集合key中 score值在min 和max 之间 的成员的数量。 双闭区间。 时间复杂度是O（log(N)+M）N为有序集合的基数 ，M为值在min和max之间的元素数量。zcount key min max .
4. zincrby: 为有序集合key中成员member 的score值增加 变量 increment.  时间复杂度 是O(log(N))。zincrby key increment member . 
5. zrange: 返回有序集合key中指定区间内的成员，其中score的值 递增从小到大排序。 时间复杂度是O(log(N)+M)）N为有序集合的基数 ，M为结果集的基数. zrange ket stat stop withscores
6. zrangebyscore:返回有序集合key中指定区间内的成员，其中score的值 递增从小到大排序. 区间及无限。可以增加( 代表 不带有的开区间。O(log(N)+M)， N 为有序集的基数， M 为被结果集的基数。zrangebyscore key start stop withscores limt
7. zrank:返回有序集key中member的排名，其中有序集合按照score值递增。 时间复杂度是O(log(N))zrank key member 。返回的是排名位置。不包含其值
8. zrem:移除有序集key中的一个或多个成员，不存在的成员将被忽略。zrem key member member .
9. zrevrangebyrank: 移除有序集key中，指定排名rank区间内的所有成员 时间复杂度是O(log(N)+M)， N 为有序集的基数，而 M 为被移除成员的数量。返回值是移除成员的数量。 zrevrangebyrank key start stop。
10. zrevrangebyscore:返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列。 时间复杂度是O(log(N)+M) ZREVRANGEBYSCORE key max  min 
11. zrevrank:返回有序集key中成员member的排名，排序按照从大到小的顺序。时间复杂度是O(log(N))。zrevrank key member . 
12. zscore: 返回有序集key中，成员member中的score值。 时间复杂度是O（1）.zscore key member. 值按照字符串的形式返回。
13. zunionstore:计算给定的一个或多个有序集的并集，其中给定 key 的数量必须以 numkeys 参数指定，并将该并集(结果集)储存到 destination 。默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之 和 。 O(N)+O(M log(M))， N 为给定有序集基数的总和， M 为结果集的基数。zunionstore destination numkeys key WEIGHTS AGGREGATE  
14. zinterstire: 计算给定的一个或者多个有序集的交易 其中给定的key数量必须以numkeys指定。并将结果存储到destination中。 O(N*K)+O(M*log(M))， N 为给定 key 中基数最小的有序集， K 为给定有序集的数量， M 为结果集的基数。zinterstire destination numkeys key key ket 
