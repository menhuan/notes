### 什么是Spring Batch
>Spring Batch 是一个轻量级的、完善的批处理框架,旨在帮助企业建立健壮、高效的批处理应用。Spring Batch是Spring的一个子项目,使用Java语言并基于Spring框架为基础开发,使的已经使用 Spring 框架的开发者或者企业更容易访问和利用企业服务。
Spring Batch 提供了大量可重用的组件,包括了日志、追踪、事务、任务作业统计、任务重启、跳过、重复、资源管理。对于大数据量和高性能的批处理任务,Spring Batch 同样提供了高级功能和特性来支持,比如分区功能、远程功能。总之,通过 Spring Batch 能够支持简单的、复杂的和大数据量的批处理作业。
### Spring Batch 使用
1. 我们首先配置Spring Batch 在Spring Boot 中的使用,数据库用的是mysql，pom文件如下，因为Spring Boot 中的Spring Batch 包含 hsqsldb  所以我们将其去除
```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-batch</artifactId>
			<exclusions> <!-- 注意这里-->
				<exclusion>
					<groupId>org.hsqldb</groupId>
					<artifactId>hsqldb</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.21</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
```
2. 配置好我们需要的实体类。页面就不展示了。
3. 如果有数据校验添加的话那么我们需要配置自定义的检验器。若果没有课略过该步骤
```
public class CsvBeanValidator<T> implements Validator<T>,InitializingBean {

    private javax.validation.Validator    validator;

    @Override
    public void validate(T value) throws ValidationException {
        Set<ConstraintViolation<T >> constraintViolations=validator.validate(value);
        if(constraintViolations.size()>0){

            StringBuilder message=new StringBuilder();
            for(ConstraintViolation<T> constraintViolation:constraintViolations){
                message.append(constraintViolation.getMessage() +"\n");
            }
            throw  new ValidationException(message.toString());
        }


    }

    //在这里我们使用的是JSR-303校验数据，在此进行初始化
    @Override
    public void afterPropertiesSet() throws Exception {  
        ValidatorFactory  validatorFactory= Validation.buildDefaultValidatorFactory();
        validator=validatorFactory.usingContext().getValidator();
    }
}

public class CsvItemProcessor extends ValidatingItemProcessor<Person> {


    @Override
    public Person process(Person item) throws ValidationException {
         super.process(item);  // 在这里启动 然后才会调用我们自定义的校验器，否则不能通过 。
         if (item.getNation().equals("汉族")){
             item.setName("01");
         }else{
             item.setNation("02");
         }
         return item;

    }
}

```
4. 进行job任务监听 自定义类实现JobExecutionListener 即可
```
    long startTime;
    long endTime;
    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        System.out.println("任务处理开始");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = System.currentTimeMillis();
        System.out.println("耗时多长时间:" + (endTime - startTime) + "ms");
        System.out.println("任务处理结束");
    }

```
5. 进行Spring Batch 的注入 方法有xml文件注入bean ，在这里选择java注入
```
@Configuration
@EnableBatchProcessing  //开启批处理
public class CsvBatchConfig {
      /**1 首先我们通过 FlatFileItemReader 读取我们需要的文件 通过setResource来实现
        * 2 设置map  在这里通过先设置解析器 setLineTokenizer  来解析我们csv文件中的数     据
        * 3  setFieldSetMapper  将我们需要的数据转化为我们的实体对象 存储
        * 4 如果想 跳过前面的几行 需要使用setLinesToSkip就可以实现 
         */ 
	@Bean
	public ItemReader<Person> reader() throws Exception {
		FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>(); //1
		reader.setResource(new ClassPathResource("people.csv")); //2
	        reader.setLineMapper(new DefaultLineMapper<Person>() {{ //3
	            setLineTokenizer(new DelimitedLineTokenizer() {{
	                setNames(new String[] { "name","age", "nation" ,"address"});
	            }});
	            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
	                setTargetType(Person.class);
	            }});
	        }});
                reader.setLinesToSkip(3);  
	        return reader;
	}
	
	@Bean
	public ItemProcessor<Person, Person> processor() {
		CsvItemProcessor processor = new CsvItemProcessor(); //1
		processor.setValidator(csvBeanValidator()); //2
		return processor;
	}
	
	
        /** 
           *写入数据到数据库中
           * 1执行的sql 语句  2 设置数据源 
            */
	@Bean
	public ItemWriter<Person> writer(DataSource dataSource) {//1
		JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>(); //2
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
		String sql = "insert into person " + "(id,name,age,nation,address) "
				+ "values(hibernate_sequence.nextval, :name, :age, :nation,:address)";
		writer.setSql(sql); //3
		writer.setDataSource(dataSource);
		return writer;
	}
     
      //  作业的仓库 就是设置数据源
	@Bean
	public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager)
			throws Exception {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		jobRepositoryFactoryBean.setDatabaseType("mysql");
		return jobRepositoryFactoryBean.getObject();
	}

        //调度器  使用它来执行 我们的批处理
	@Bean
	public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager)
			throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository(dataSource, transactionManager));
		return jobLauncher;
	}

        //将监听器加入到job中
	@Bean
	public Job importJob(JobBuilderFactory jobs, Step s1) {
		return jobs.get("importJob")
				.incrementer(new RunIdIncrementer())
				.flow(s1) //1
				.end()
				.listener(csvJobListener()) //2
				.build();
	}
        //步骤绑定 reader 与writer  一次性处理65000条记录
	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader, ItemWriter<Person> writer,
			ItemProcessor<Person,Person> processor) {
		return stepBuilderFactory
				.get("step1")
				.<Person, Person>chunk(65000) //1
				.reader(reader) //2
				.processor(processor) //3
				.writer(writer) //4
				.build();
	}



	@Bean
	public CsvJobListener csvJobListener() {
		return new CsvJobListener();
	}

	@Bean
	public Validator<Person> csvBeanValidator() {
		return new CsvBeanValidator<Person>();
	}
	

}

   
```
6. 在配置文件中 启动自动执行批处理
```
spring.batch.job.names = job1,job2 #启动时要执行的Job，默认执行全部Job
spring.batch.job.enabled=true #是否自动执行定义的Job，默认是
spring.batch.initializer.enabled=true #是否初始化Spring Batch的数据库，默认为是
spring.batch.schema=
spring.batch.table-prefix= #设置SpringBatch的数据库表的前缀
```
### 项目汇总
> 从 项目中我们可以看到 总的步骤就是 首先读取我们需要实现的文件进行解析，然后转换成需要的实体类并且绑定到reader中，二 实现我们需要的writer 并且帮到到数据库上，三实现job监听器将其绑定到步骤中 。最后开启批处理 自动执行入库即可 。这个简单步骤主要是配置中用到的 理解流程 自己也可以方便实现 批处理的流程

