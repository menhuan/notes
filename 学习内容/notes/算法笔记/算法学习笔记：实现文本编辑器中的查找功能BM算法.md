# BM算法

BM算法包含两部分，分别是**坏字符规则**和**好后缀规则**。

## 坏字符规则

BM算法的匹配顺序比较特别，是按照模式串下标从大到小的顺序，倒着匹配的。

![2019-05-18-00-18-31](http://jikelearn.cn/2019-05-18-00-18-31.png)

1. 倒着匹配的时候，发现字符匹配不上，那么这个字符就叫做**坏字符**。
2. 再拿着这个坏字符在模式串汇总查找，如果都查找不到，直接主串向后移动**模式串的长度**。
3. 如果坏字符在模式串中存在，移动的位置就不一定了，模式串字符与主串匹配字符，不一致记录模式串的下标为si，主串中的坏字符存在于模式串中的位置记为xi,则位置就是si-xi，不存在的话xi=-1.
4. 如果存在多个xi，那么xi选择靠后的位置，放置滑动太多将其移动，导致匹配失败。

存在的问题是坏字符规则可能存在移动的si-xi 为负数。比如 aaaaaaaa, baaaa.si-xi就会存在负数情况。

## 好后缀原则

![2019-05-18-00-26-58](http://jikelearn.cn/2019-05-18-00-26-58.png)

好后缀原则，不仅要看好后缀在模式串中，是否存在另外一个匹配的子串，还需要考虑好后缀的子串，是否存在跟模式串前缀子串相匹配的。**前缀子串不一定是一个字符**

1. 已经可以匹配上的后缀称为{u}，如果前面在匹配上，那么就叫做{u*}.
2. 如果上面匹配没有u{*}，则将其模式串移动模式串到主串跟{u}中相等的前缀字符对齐即可。

## 两种方式怎么选择

分别计算好后缀与坏字符移动的位数，取两个数中最大的，作为模式串欢动的位数，防止坏字符移动出现负数的情况。

## 算法实现

### 坏字符xi的计算

xi代表的是坏字符在模式串中的位置。

每次在模式串中查找坏字符，模式串与主串的长度都不长的情况下都很好，但如果长度很长，顺序遍历就很费时间了。

则可以采用hash的方式来进行计算，。使用数组，数组下标来存储对应字符的ASCLL码值，数组中存储字符在模式串中出现的位置.

根据下标找到对应的字符在hash中。

```Java
//todo 代码实现
```

![2019-05-18-00-42-13](http://jikelearn.cn/2019-05-18-00-42-13.png)

### 好后缀规则

- 模式串中查找跟好后缀相匹配的字符串
- 在好后缀的后缀子串中，查找最长的，能跟模式串前缀子串相匹配的后缀子串。
  
不采用暴力的方式去执行。

明确一点的是好后缀字符串也是 模式串的一部分，则可以通过**预处理的方式**来计算好模式串中可能存在的每个后缀子串，对弈的另一个可匹配子串的位置。

#### 如何表示不同的后缀子串

1. 后缀子串的最后一个字符的位置是固定的，长度为m，则下标是m-1,通过记录长度，确定唯一的后缀子串。

2. suffix数组：下标表示后缀子串的长度，对应该位置存储的值是 好后缀相匹配的子串{u*}的起始下标值。不存在，值为-1.
![2019-05-28-00-37-34](http://jikelearn.cn/2019-05-28-00-37-34.png)

3. 如果存在过多的相匹配上的u{*},存储最靠后的子串的起始位置，防止数据滑动过头导致处理出现问题。
4. 前缀数组prefix，记录模式串的后缀子串是否能匹配 模式串的前缀子串。
![2019-05-28-00-39-29](http://jikelearn.cn/2019-05-28-00-39-29.png)

#### 填充数据

![2019-05-28-00-42-00](http://jikelearn.cn/2019-05-28-00-42-00.png)

1. 从主串中选择一个长度为i的子串，i小于等于m-2，m代表主串的长度。
2. 拿该串与整个模式串求公众后缀子串。
3. 长度为k,那么记录suffix[k]=j,j是公共后缀子串的开始的下标位置。
4. 如果j=0,代表公共后缀子串也是模式串的前缀子串。记录prefix[k]=true

```Java
//todo 代码实现
```

#### 计算模式串的滑动位置

1. 假设现在找到了主串上的好后缀子串，长度为k。
2. 在我们前面的数组中suffix中查找匹配的子串，其值不等于-1.
3. 将模式串向后移动j-suffix[k]+1。**j表示的是坏字符串对应的模式串中的字符下标**。
4. 如果不存在匹配的字符串，长度为k，如果prefix[k]==true,表示存在可匹配的前缀子串，直接移动下标的起始位置。
5. 如果都找不到那么移动m位。m代表？

