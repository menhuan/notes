在前面我们了解了Spring是怎么来处理前台页面的。那么后端会怎么做呢？这一篇就是我们需要梳理的了。

#首先了解下Spring的数据访问异常体系
java中的jdbc体系关于异常的体系解决的十分简单，无法了解其具体异常信息，然而我们在开发的时候需要了解其错误，帮助我们解决问题。Spring就提供了平台无关的持久化异常。不同于JDBC，Spring提供了多个数据访问异常，分别描述了他们抛出时所对应的问题。下面所示就是Spring数据访问异常与JDBC异常的对比。

![数据异常对比](http://upload-images.jianshu.io/upload_images/4237685-aa20ab27507052c3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我们Spring的数据异常都是继承自DataAccessException，他的特殊之处就在于它是一个非检查性异常，可以不必捕捉Spring抛出的异常。
#数据访问模板化（利用了一种设计模式--模板方法模式）
模板具体职责可以如图所示

![Spring数据访问模板](http://upload-images.jianshu.io/upload_images/4237685-c62de058af2288bf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
Spring模板只处理数据访问的固定部分，应用程序相关的数据访问在回调的实现中处理，我们只需要关注自己的数据访问逻辑即可。
Spring提供了很多种模板来访问数据库

![数据模板](http://upload-images.jianshu.io/upload_images/4237685-91d86cbe3324af9e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#使用JNDI数据源
关于JNDI这里就不怎么介绍了，请不清楚的同学自行百度。我们在这里利用Spring将jndi引入到我们的程序中
```
<jee:jndi-lookup id="dataSource" jndi-name="jdbc/SpitterDS"  resource-ref="true" />
 //jndi-name 是指定的jndi中的资源名称， 程序会自动的去查找数据源， 如果该程序运行在java应用服务器中，我们需要将resource-ref属性设置为true,这样给定的jndi-name 将会自动的添加“java:comp/env/”前缀
。
//如果不想利用配置的方式我们还可以写入在java程序中。
      @Bean
	public JndiObjectFactoryBean dataSoruce(){
		
		JndiObjectFactoryBean bean=new JndiObjectFactoryBean();
		bean.setJndiName("jdbc/SpitterDS");
		bean.setResourceRef(true);
		bean.setProxyInterface(javax.sql.DataSource.class);
		
		return new JndiObjectFactoryBean();
	}
```
#使用数据源连接池
1. Apache Commons DBCP (http://jakarta.apache.org/commons/dbcp)；
2. c3p0 (http://sourceforge.net/projects/c3p0/) ；
3. BoneCP (http://jolbox.com/) 。
一般我们都是在Spring配置文件中配置数据源。
```
 <!--我这里采用的是阿里的数据源配置  基本上这几个配置都是一样的。-->
	<!-- 数据库连接池 -->
	<!-- 加载配置文件 -->
	<context:property-placeholder location="classpath:properties/*.properties" />
	<!-- 数据库连接池 -->
	<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
		destroy-method="close">
		<property name="url" value="${jdbc.url}" />
		<property name="username" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
		<property name="driverClassName" value="${jdbc.driver}" />
		<property name="maxActive" value="10" />
		<property name="minIdle" value="5" />
	</bean>
	<!-- 让spring管理sqlsessionfactory 使用mybatis和spring整合包中的 -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<!-- 数据库连接池 -->
		<property name="dataSource" ref="dataSource" />
		<!-- 加载mybatis的全局配置文件 -->
		<property name="configLocation" value="classpath:mybatis/SqlMapConfig.xml" />
	</bean>
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.taotao.mapper" />
	</bean>
```
当然我们也可以写一个java类 在java中直接加载也可以，不必都写到配置文件中。下面如图是介绍一些属性的类型。

![BasicDataSource池配置](http://upload-images.jianshu.io/upload_images/4237685-61d00d3a530065f4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
除了使用数据库连接池之外，我们还能使用嵌入式数据库。这里就不做介绍了。
介绍了这几种方式，我们可能还会遇到一个问题就是生产环境是一中数据源或者数据库，测试是一个，开发是一种这种情况，那么我们可以在pom文件中设置
```
<profiles>
		<!-- 开发默认激活 -->
		<profile>
			<id>dev</id>
			<properties>
				<env>dev</env>
			</properties>
		 	 <activation> 
				<activeByDefault>true</activeByDefault>
			</activation>  
		</profile>
		<!-- 测试环境 -->
		<profile>
			<id>test</id>
			<properties>
				<env>test</env>
			</properties>
		</profile>
		<!-- 生产环境 -->
		<profile>
			<id>pro</id>
			<properties>
				<env>product</env>
			</properties>
		</profile>
	</profiles>

这个可以让我们使用不同的数据源文件。在读取配置文件中
```
在这里我们还可以使用profile选择数据源。

![选择数据源](http://upload-images.jianshu.io/upload_images/4237685-a54ca78a1c98e5a8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
哪个处于激活状态就会创建DBCP去链接数据库。这个java方法也可以转换为xml文件中配置。方式与上面的配置一样。
