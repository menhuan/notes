我们这篇只是简单的介绍下介绍下关于Spring中对于Nosql的使用，这里主要是使用到的数据库有MongoDB、Redis。
#MongDB持久化文档数据
在文中我们主要使用Spring Data MonggoDB 来使用MongoDB：
1. 通过注解实现对象-文档映射；
2. 使用MongoTemplate实现基于模板的数据库访问；
3. 自动化的运行时Repository生成功能

#MongDB配置


```
/**
 * 1@EnableMongoRepositories 使用这个注解 能启动Spring data 中的自动化 JPA Repository生成功能
 * 2具体可以参考方法的文档
 */
@Configuration
@EnableMongoRepositories(basePackages="order.db")  //启动MongDB的Repositiory功能 定义的是在那个包下的数据
public class MongoConfig {
	
	/**
	 * 该bean设置mongoDB数据库的地址 链接数据库
	 * @return
	 */
	@Bean
	public MongoFactoryBean mongo(){
		MongoFactoryBean mongo=new MongoFactoryBean();
		mongo.setHost("localhost");
		return mongo;
	}
	/**
	 * 创建模板bean查询数据
	 * @param mong
	 * @return
	 */
	@Bean
	public MongoOperations mongoTemplate(Mongo mong){
		return new MongoTemplate(mong,"OrdersDB");
	}
}
/** 方式2
 * 1@EnableMongoRepositories 使用这个注解 能启动Spring data 中的自动化 JPA Repository生成功能
 * 具体可以参考方法的文档
 */
@Configuration
@EnableMongoRepositories(basePackages="order.db")  //启动MongDB的Repositiory功能 定义的是在那个包下的数据
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "OrderDB";   //指定数据库的名称
	}
	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient();   //创建 Mongo客户端
 	}
```
在本章中主要介绍我们上面所说的第二种方式来使用MongoDB
因为我们 如果使用MongoTemplate的话 都要使用到MongoOperations 这个接口。那么我们可以使用自动注入的方式来实现。
在Spring Data MongoDB中 MongoTemplate 实现了我们上面所说的接口 还有ApplicationContextAware 这个接口，那么我们完全可以在程序中 自动注入这个bean 
```
   @Autowired
    private MongoTemplate bean;
   //然后我们可以在程序汇总实现我们需要的方法 比如查询 getCollection  然后在使用find 方法来查询 具体可以看api,保存save.等等  个人感觉还是这个方法习惯。。当然 还是看个人的使用了
```

#Redis 操作key-value数据
在程序中我们可以用到以下方式来链接redis
1. JedisConnectionFactory
2. JredisConnectionFactory
3. LettuceConnectionFactory
4. SrpConnectionFactory
个人习惯用jedisConnectionFactory这个工程来链接redis
```
 //java代码
   @Bean
 public RedisConnecttionFactory getDisCf(){
  return new   JedisConnectionFactory();
}
//xml文件的话
<bean id="jedisConnFactory"
		class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
		<property name="hostName" value="ip" />
		<property name="port" value="port" />
		<property name="password" value="pas" />
		<property name="database" value="data"></property>
		//还可以配置连接池等等信息
	</bean>
```
 更具体的配置 可以参考官方的配置
在这里我还是习惯使用模板去链接redis  就是 RedisTemplate
```
       /**
	 * 注入template
         *  使用Qualifier  注入指定的bean  在配置文件中配置好的。然后我们就可以使用 这个区操作redis中的set string list 等数据了 具体可以看下截图
	 */
	@Autowired
	@Qualifier("redisTemplate")     
    private RedisTemplate<String, String> template;
```

![RedisApi.png](http://upload-images.jianshu.io/upload_images/4237685-db8a422db2abdd6d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

关于使用Spring Data 使用 redis 和MongoDB本文只是简单的说了下 还有很多需要在学习的。共勉。
