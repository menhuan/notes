在我们Java开发的过程中，接口使我们经常使用到。但是我们也会用到抽象类。那么我们在开发的时，具体应该怎么选择呢？这两者有什么区别？我们今天就来学习下。
## 抽象类
  抽象类顾名思义就是不能实例化的类，我们在创建的时候会使用abstract  来创建。在抽象类中**含有无具体实现的方法，所以我们在使用的时候不能使用该类来创建对象。**
 *  抽象类在表面上普通的类没有区别，可以有多个抽象方法，当然也可以没有抽象方法。
 *  抽象类的实现主要是为了提供代码复用的目的。
 *  抽象类前面的修饰除了abstract ，还有有public,protected，缺省情况下是public。不能是private.**不然子类不能继承父类**。
 * 我们子类在继承抽象类时，子类必须实现父类的抽象方法，否则 ，子类也必须定义为抽象类。
```
public abstract class Abstorct {
  //方法声明为抽象的。
  public abstract  void  add();

}

public class AbstorctImpl  extends Abstorct {


    @Override
    public void add() {

    }
}
```
## 接口 
编程中，接口我们经常使用，利用它达到API定义与实现分离的目的。
1. 在Java中，是没有多继承这一实现的，但是我们可以使用接口来达到这个目的。方便我们去实现我们想要的目的。
2. 接口中的变量总是被隐式指定为public static final 变量。 并且方法也是被隐式的定义为public abstract。接口中的方法必须都是抽象方法。
3. 在java8以后，接口中也有了默认的实现方法。
```
//java集合中一个默认实现的方法。
public interface Collection<E> extends Iterable<E> {
 default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }
}
```
## 两者不同点
1. 两者一个是类一个是接口，从子类来实现或者继承时，实现就不同，继承类java中只能单继承。
2. 抽象类是事务的抽象，比如我们对于一个动物的描述，人和猴都有相似的东西，可以抽象出来共同的特征。 抽象类 是一个 has-a的关系。接口是is-a的关系。比如猴是否能跳，能跳跃，那么就能实现该接口，不能实现则不能实现。
3. 接口中不能含有静态代码块和静态方法，但是抽象类是可以存在静态代码块和静态方法的。
4. 接口中的成员变量默认是public static final 类型的。抽象函数中的成员可以是各个类型的。
5. 我们在程序中如果对接口进行增加方法，那么子类实现也得增加其实现方法 不可取，但是java8以后增加了默认方法的实现。而抽象类中添加了非抽象方法，子类只会享受其能力的扩展，不用担心编译出现问题。
简单了解下抽象方法和接口，让我们在编程的时候更加享受其特性带来的方便。在选择上可以有更多的选择。
