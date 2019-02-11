首先声明下，因为自己原先对于版权的了解的甚少。原先写的文章摘录极客时间的内容比较多，以后写文章虽然还会按照极客时间的目录走。如果我写的太浅显，大家还是去极客时间上看原版。谢谢。
今天我们开始看下反射与代理。希望大家能满意。

# 反射
我们都知道反射在Java语言中是一种基础功能，在运行状态中，能够通过反射的方式来知道一个类的所有属性，方法，构造对象甚至是修改类的信息等。总结 ，**反射就是把Java类映射成一个个的Java对象**
```
             Class  stu  = Class.forName("com.love.manage.bean.StudentBean");

            Constructor[] constructors = stu.getConstructors();  // 获得构造器的方法

            Object object = stu.newInstance();   //获得一个对象
            Method[] methods = stu.getMethods();   //获得其 方法
            Field dec = stu.getDeclaredField("name");  //获得私有字段
            dec.setAccessible(true);  //暴力反射 解除私有限定  修改其内容  
            // 并且这个应用尝尽十分普遍，自爱我们的日常的开发、测试 依赖注入等都有用到了。特别是框架中使用反射来做到持久化数据
            //在最新的Java9 中 肯能会将其限制丢弃 改用别的模式
```  
除了我们在平常中通过反射 获得其方法属性构造方法之外，我们还有通过反射越过泛型的检查
```
ArrayList<String> list= new ArrayList<>();  
        list.add("content1");  
        list.add("content2");  

        //获取ArrayList的Class对象，反向的调用add()方法，添加数据  
        Class listClass= list.getClass(); //得到 list对象的字节码 对象  
        //获取add()方法  
        Method m = listClass.getMethod("add", Object.class);  
        //调用add()方法  
        m.invoke(list, 100);  
          
        //遍历集合    这样我们就把泛型的检查去除了
        for(Object obj : strList){  
            System.out.println(obj);  
        }  
```
# 动态代理
在java中代理是一种十分普遍的现象，我们经常会用到，比如我们经常用到的Spring 中的AOP 就用到了代理。其中java中的代理主要是通过jdk的代理实现的。当然我们还有其他动态代理比如cglib ，ASM等。
## 为什么会使用动态代理？
我们都知道在程序中，有一点比较重要的就是解耦。减少程序与程序间的影响。我们就可以通过代理来实现。使用代理者可以不用了解底层是怎么实现的，只关注自己在使用的时候怎么使用即可，方便了自己去扩展。
** jdk代理**
java中的jdk动态代理是通过其内部的反射机制来实现的，这样在生成类的时候更加高效。但是jdk动态代理的应用前提是必须是目标类基于统一的接口，如果没有统一的接口那么jdk动态代理就无法使用
```
public class Test implements TestImpl {

    @Override
    public String getName(String name) {
        return name;
    }

    @Override
    public int getOld(String id) {
        return Integer.valueOf(id) + 1;
    }
}

public interface TestImpl {
    public String  getName(String name);
    public  int  getOld (String id) ;

}

class InvocationMyHandler  implements InvocationHandler{

    /**
     * 被代理者
     */
    private Object   target ;

    /**
     * 构造方法
     * @param target  需要代理的目标对象
     */
    public InvocationMyHandler(Object target) {
        this.target = target;
    }

    /**
     * 在这里是需要多代理进行操作的，通过代理者给被代理者进行的操作都在该方法中
     * @param proxy   代理 者
     * @param method   被执行的方法
     * @param args    执行该方法需要的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object  object   =  method.invoke(target,args);
        return object;
    }
}


public class Main {

    public static void main(String[] args) {
        Test  test  = new Test() ;
        InvocationMyHandler  handler = new InvocationMyHandler(test);
        Test  proxy  = (Test) Proxy.newProxyInstance(TestImpl.class.getClassLoader(),TestImpl.class.getInterfaces(),handler);
        System.out.println(proxy.getName("name"));
    }
}
```
该例子用接口来实现了调用其目标对象。来实现代理的，我们前面也说的了通过接口调用有局限性，如果没有实现其接口那么就不能使用代理了。结果该问题，我们可以使用cglib这个代理，来帮助我们其实现代理。
![星球和公众号.jpg](https://upload-images.jianshu.io/upload_images/4237685-74bf416996bd0c08.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
