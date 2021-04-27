### Spring Security简介
      Spring Security 是专门针对基于Spring的项目安全框架，充分利用了依赖注入和AOP来实现安全的功能，安全框架主要用的是认证（Authentication）和授权（Authorization）。
### SpringBoot 对其的支持
    关于Spring Security 的自动配置在SpringBoot 中在org.springframework.boot.autoconfigure.security包中 
关于在SpringBoot中配置Spring Security 包含下图这样

   
![Spring Security在SpringBoot中配置](http://upload-images.jianshu.io/upload_images/4237685-c36067327e80eb3a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
当我们需要使用自己的扩展配置时需要自己的类实现WebSecurityConfigurerAdapter类即可
```
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .permitAll();

    }

}
```
### 实战
1. 首先添加pom文件 。这里使用的mysql 数据库 

```
    <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		<dependency>
			<groupId>org.thymeleaf.extras</groupId>
			<artifactId>thymeleaf-extras-springsecurity4</artifactId>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.21</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>
```

2. 在application.properties中配置好数据库链接

``` 
spring.jpa.database=mysql
spring.datasource.url=jdbc:MySQL://localhost:3306/com_study
spring.datasource.username = root
spring.datasource.password=111111
spring.datasource.driverClassName = com.mysql.jdbc.Driver

spring.jackson.srialization.indent_output=true


spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
debug=true

logging.level.org.springframework.security= INFO
spring.thymeleaf.cache=false
```
3. 定义实现我们的实体bean
```
@Entity
public class SysUser implements UserDetails{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String userName;

    private String password;

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<SysRole> roles;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
        List<SysRole> roles=this.getRoles();

        for(SysRole role:roles){
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }

        return auths;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
}

@Entity
public class SysRole {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

```
4. 添加配置文件web和Security配置
```
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .permitAll();

    }

}

```
5. 实现service 并且dao层实现jpa
```
public interface SysUserRepository extends JpaRepository<SysUser,Long> {

        SysUser findByUserName(String userName);

}

public class CustomUserService implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username)  {

        SysUser user=sysUserRepository.findByUserName(username);
        if(user==null){
            throw new  UsernameNotFoundException("用户名不存在");
        }

        return user;
    }


}

```
6. 添加我们的页面的登录页面login.html 
###总结
我们在实现安全验证内容的时候，首先需要实现我们需要的WebSecurityConfigurerAdapter  然后我们重写我们config 方法，在代码Security配置中我们设置权限访问anyRequest  任何访问页面的路径都需要先登录验证后才可以。如果不想每个页面都得登录才可以访问，我们可以自己定制匹配路径可以在http.authorizeRequests()中使用antMatcher("/user/index")或者regexMatchers("/user/index"),更多配置方式可以看Spring的页面。
