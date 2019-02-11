首先我们在前面介绍的springMVC已经是很强大的内容了，但是我们还是想说下其他的方案。虽然我的内容还是SpringMVC中获取的，但还是想写下来记录下

1SpringMVC配置的替代方案（该方案是在servlet3.0以上版本）

     1.我们自定义DispatcherServlet配置
          在这里我们会用到的是customizeRegistration()这个方法，在我们调用AbstractAnnotationConfigDispatcherServletInitializer将dispachServlet注册到容器中之后就会调用customizeRegistration()方法。我们重载这个方法就可以进行多余的配置。

     2.添加其他的Servlet和filter
          在java中有一个initializer初始器，我们可以在初始器里面定义所需要的初始化器类。想增加其他的组件，我们只需要增加初始化器类就行，最简单的方式就是我们实现Spring的WebApplicationInitializer接口。
```
    
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;

public class TestServlet implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {

		/**注册Servlet*/
		Dynamic testServlet=servletContext.addServlet("myServlet",(Class<? extends Servlet>) MyTest.class);
		testServlet.addMapping("/custom/***");  //映射Servlet
	}

}

```
上面的代码就是简单的我们将注册一个servlet并将其映射到一个路径上，当然我们也可以将其映射到DispatchServlet上。
类似的我们也可以通过这样锝方式实现Listener和filter的方式注册。
```
import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
public void onStartup(ServletContext servletContext) throws ServletException {
		Dynamic  filter=servletContext.addFilter("myFilter", (Class<? extends Filter>) MyTest.class);
		
		/**第一个参数  null 为默认的dispatcher 也可以自己制定      第二个参数false 之前匹配模式  第三个参数路径 */
		filter.addMappingForUrlPatterns(null,false, "/custom/**");
		
	}
```
这种方式适合在Servlet容器当中，如果项目所属的项目还是低于该容器那么需要在web.xm中配置。
```
<!-- 设置跟上下文配置文件位置 -->
  		<context-param>
  			<param-name>contextConfigLocation </param-name>
  			<param-value>/WEB-INF/spring/root-context.xml</param-value>	
  		</context-param>
  		
  		<!-- 注册ContextLoaderListener -->
  		<listener>
  			<listener-class>
  				org.springframework.web.context.ContextLoaderLister
  			</listener-class>
  		</listener>
  		
  		<!-- 注册DispatcherServlet -->
		<servlet>
			<servlet-name>appServlet</servlet-name>
			<servlet-class>
				org.springframwork.web.servlet.DispatcherServlet
			</servlet-class>
			<!-- 加载优先级 -->
			<load-on-startup>1</load-on-startup>
		</servlet>	  
		
		<!-- 将DispatcherServlet映射到/  映射url可以自定义-->
		<servlet-mapping>
				<servlet-name>appServlet</servlet-name>
				<url-pattern>/</url-pattern>
		</servlet-mapping>
```
我们在xml中配置好之后会根据配置文件去读取上下问信息。
```
<!-- 扫描注解 -->
  <annotation-driven />

  <!-- 扫描具体包下面的组件 -->
  <context:component-scan base-package="spitter" />

  <!-- 映射前后缀 -->	
  <beans:bean
    class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <beans:property name="prefix" value="/WEB-INF/views/" />
    <beans:property name="suffix" value=".jsp" />
  </beans:bean>

  <!-- 资源文件 映射-->	
  <resources mapping="/resources/**" location="/resources/" />

  <!-- 
  <view-controller path="/" view-name="home" />
  -->
```
这是用配置文件加载我们还可以使用配置文件的方式加载，使用注解@Configuration注解的类上加载配置
那么可能我们需要重新改下配置文件web.xml了。
```
<!-- 设置上下文   使用java配置-->
		<context-param>
			 <param-name> contextClass</param-name>
			 <param-value>org.springframework.web.context.support.AnnotationConfigWebapplicationContext</param-value>
		</context-param>
		<!-- 指定根配置的类 -->
		<context-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>com.textSpring.Spilter</param-value>    
		</context-param>
		
		<listener>
			<listener-class>
  				org.springframework.web.context.ContextLoaderLister
  			</listener-class>
		</listener>
		
		<servlet>
			<servlet-name>appServlet</servlet-name>
			<servlet-class>
				org.springframwork.web.servlet.DispatcherServlet
			</servlet-class>
			<!-- 使用java配置 -->
			<init-param>
				 <param-name> contextClass</param-name>
				 <param-value>org.springframework.web.context.support.AnnotationConfigWebapplicationContext</param-value>
			
			</init-param>
			<!-- 指定dispatcherServlet配置类 -->
			<init-param>
				 <param-name> contextConfigLocation</param-name>
				 <param-value>com.textSpring.Spilter</param-value>
			
			</init-param>
			
			<load-on-startup>1</load-on-startup>
		</servlet>	  
		

		<servlet-mapping>
				<servlet-name>appServlet</servlet-name>
				<url-pattern>/</url-pattern>
					
		</servlet-mapping>
```
在这里我们配置的web.xml跟刚开始配置的web.xml就是在这里直接配置好启动的java类去启动dispatcherServlet，我们在这里需要用到的是init_param标签 将上面配置的context-param中的文件配置成java类。其他的基本是一样。
