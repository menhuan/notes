### spark 介绍
&nbsp;&nbsp;spark是一个实现快速通用的集群计算平台。它是由加州大学伯克利分校AMP实验室 开发的通用内存并行计算框架，用来构建大型的、低延迟的数据分析应用程序。它扩展了广泛使用的MapReduce计算模型。高效的支撑更多计算模式，包括交互式查询和流处理。spark的一个主要特点是能够在内存中进行计算，及时依赖磁盘进行复杂的运算，Spark依然比MapReduce更加高效。
### spark一个大一统的软件栈
&nbsp;&nbsp; Spark的各个组件如图所示：
![Spark软件栈](http://upload-images.jianshu.io/upload_images/4237685-b88aaef69c655651.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. Spark Core 实现了Spark的基本功能：任务调度、内存管理、错误恢复、存储系统交互模块以及弹性分布式数据集api定义
2. Spark Sql 是Spark来操作结构化数据的程序包，可以让我使用SQL语句的方式来查询数据，Spark支持 多种数据源，包含Hive表，parquest以及JSON等内容。
3. Spark Streaming 是Spark提供的实时数据进行流式计算的组件。
4. Mlib 是Spark中提供的常见的机器学习功能的程序库。
5. GraphX 是一种操作图的程序库。可以进行并行的图计算。
6. 集群管理器 高效的运行在一个计算节点到数千个计算节点伸缩计算。

###应用场景
Yahoo将Spark用在Audience Expansion中的应用，进行点击预测和即席查询等
淘宝技术团队使用了Spark来解决多次迭代的机器学习算法、高计算复杂度的算法等。应用于内容推荐、社区发现等
腾讯大数据精准推荐借助Spark快速迭代的优势，实现了在“数据实时采集、算法实时训练、系统实时预测”的全流程实时并行高维算法，最终成功应用于广点通pCTR投放系统上。
优酷土豆将Spark应用于视频推荐(图计算)、广告业务，主要实现机器学习、图计算等迭代计算。
### 核心概念简介
1. 在每个Spark程序运行过程中，我们都是由一个驱动器程序(SparkContext)发起来并发集群上的各种并行操作。 并且驱动程序主要包含在main函数中。定义了集群上的分布式数据集。
2. 我们创建好SparkConext之后就可以创建RDD,在此基础上进行各种操作，如collect,count等等
3. 在我们执行这些操作的时候，驱动程序一般会管理多个执行器节点。这个节点是跟在配置的cpu核心数有关。1:1的对比关系。核心数越多代表我们并行的任务越多
![执行器](http://upload-images.jianshu.io/upload_images/4237685-6fbd292bd19d3d82.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
4. 代码简单实现
```
@Component
public class SparkTest implements Serializable {

	
	@Autowired
	private transient JavaSparkContext  scContext;
	
	public void  sparkContextTest() {
		SparkConf  conf = new SparkConf().setMaster("local").setAppName("My App Test");		
	}
}
```
maven 配置文件
```
        
  <properties>		
          <scala.version>2.10.4</scala.version>
          <spark.version>1.6.2</spark.version>
		
	</properties>

	<!-- spark 相关内容 -->
		 <dependency>
            <groupId>org.scala-lang</groupId>
            <artifactId>scala-library</artifactId>
            <version>2.10.4</version>
        </dependency>
        
            <dependency>
            <groupId>org.apache.spark</groupId>
            <artifactId>spark-core_2.10</artifactId>
            <version>${spark.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>log4j</groupId>
                    <artifactId>log4j</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.apache.spark</groupId>
            <artifactId>spark-launcher_2.10</artifactId>
            <version>${spark.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.spark</groupId>
            <artifactId>spark-mllib_2.10</artifactId>
            <version>${spark.version}</version>
        </dependency>
	</dependencies>

```
构建完成我们第一个程序 。
