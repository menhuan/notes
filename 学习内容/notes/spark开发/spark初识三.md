上面两篇大部分介绍的都是理论知识，希望看到前两篇的都读读。读一遍 不容易理解现在这一篇是介绍api操作的。相对来说容易些也是方便我自己记忆。
####RDD的两种类型操作。
有哪两种操作呢？分别是transformation ，action 也是我们上面所说的转换 和行动。 
### Transformations 使用的是常用的api操作还有很多可能介绍不到
  1. map():将原来的RDD的每个数据想根据自定义函数进行映射，转换成一个新的RDD。
```
	SparkConf  conf = new SparkConf().setMaster("local").setAppName("My App Test");
		
		JavaRDD<String>  pairRDD =  scContext.parallelize(Arrays.asList("a","b","c"));
		
		pairRDD.map(result ->  result.split(" "));  
```
 2. filter(): 使用该函数 对RDD数据进行过滤。将符合条件的RDD中的数据 组成新的RDD返回。
```
		JavaRDD<String>  pairRDD =  scContext.parallelize(Arrays.asList("a","b","c"));
		
//		pairRDD.map(result ->  result.split(" "));  
		JavaRDD<String> resultRdd=pairRDD.filter( content  -> {
			return  content.equals('s') ; 
		}) ;
	}
```
  3. flatMap()类似与Map(),不过这个map，返回值是一个数据项集合，而不是一个单项的数据项。
  4. mapPartitions:类似于Map,不过该操作是在每个分区上分别执行的，所以当操作一个类型为T的RDD必须是Iterator =>Iterator  。
  5. sample（）：对数据进行采样用户可以设定，是否有放回，采样的百分比，随机种子等。
  6. union()：聚合操作。可以用来合并多个集合。但是使用union函数时必须抱枕RDD的理性是相同。
  7. distinct()；去重操作。将重复的内容排除掉。
  8. intersection() : 返回两个数据集的交集。
  9. groupByKey(): 进行分组。默认情况下并行情况是根据父RDD的分区数来确定的。但是可以自己指定任务数。
  10. reduceByKey():聚合操作：函数格式必须是（V,V）=>V。也可指定任务数。
参考例子：
![PairRdd的转化操作](http://upload-images.jianshu.io/upload_images/4237685-c3b5f92857e99c99.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###Action
reduce(func)
使用函数func聚集数据集中的元素，这个函数func输入为两个元素，返回为一个元素。这个函数应该符合结合律和交换了，这样才能保证数据集中各个元素计算的正确性。

collect()
在驱动程序中，以数组的形式返回数据集的所有元素。通常用于filter或其它产生了大量小数据集的情况。

count()
返回数据集中元素的个数。

first()
返回数据集中的第一个元素（类似于take(1)）。

take(n)
返回数据集中的前n个元素。

takeSample(withReplacement,num, [seed])
对一个数据集随机抽样，返回一个包含num个随机抽样元素的数组，参数withReplacement指定是否有放回抽样，参数seed指定生成随机数的种子。

takeOrdered(n, [ordering])
返回RDD按自然顺序或自定义顺序排序后的前n个元素。

saveAsTextFile(path)
将数据集中的元素以文本文件（或文本文件集合）的形式保存到指定的本地文件系统、HDFS或其它Hadoop支持的文件系统中。Spark将在每个元素上调用toString方法，将数据元素转换为文本文件中的一行记录。

saveAsSequenceFile(path) (Java and Scala)
将数据集中的元素以Hadoop Sequence文件的形式保存到指定的本地文件系统、HDFS或其它Hadoop支持的文件系统中。该操作只支持对实现了Hadoop的Writable接口的键值对RDD进行操作。在Scala中，还支持隐式转换为Writable的类型（Spark包括了基本类型的转换，例如Int、Double、String等等)。

saveAsObjectFile(path) (Java and Scala)
将数据集中的元素以简单的Java序列化的格式写入指定的路径。这些保存该数据的文件，可以使用SparkContext.objectFile()进行加载。

countByKey()
仅支持对（K,V）格式的键值对类型的RDD进行操作。返回（K,Int）格式的Hashmap，(K,Int)为每个key值对应的记录数目。

foreach(func)
对数据集中每个元素使用函数func进行处理。该操作通常用于更新一个累加器（Accumulator）或与外部数据源进行交互。注意：在foreach()之外修改累加器变量可能引起不确定的后果。详细介绍请阅读
