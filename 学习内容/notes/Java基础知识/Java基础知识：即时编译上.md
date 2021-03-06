# 即时编译上

## 分层编译模式

Java7之后引入了分层编译。分为5个层次

- 0：解释执行。
- 1：执行不带profiling的C1代码；
- 2: 执行仅带方法调用次数以及循环回边执行次数的profiling的C1代码。
- 3：执行带所有profiling的C1代码。
- 4：执行C2代码。
  
profiling越多，需要额外的性能消耗越大，1>2>3 执行效果。

pfofiling是什么呢？ 指在程序执行过程中，收集能够反映程序执行状态的数据。**收集的数据**称之为程序的profile.

1层与4层属于终止状态。

方法被编译后，编译后的代码没有失效，虚拟机不会再次发出该方法的编译请求的。

![2019-03-20-22-15-50](http://jikelearn.cn/2019-03-20-22-15-50.png)

- 一般都是在3层进行编译热点数据，但是如果在3层没有得到多余的profiling数据的代码，会返回1层进行执行， 1层是终止状态，执行完毕后，就不再进行编译

- C1忙碌，虚拟机执行对程序进行收集程序，然后在C2执行。
- C2也忙碌，方法会被2层的C1执行，然后在被3层的C1执行，减少方法在3层的执行时间。

java8默认开起分层编译。

## 即时编译器的触发

虚拟机是根据**方法的调用次数**以及**循环回边的执行次数**触发即时编译器。

循环回边的执行次数是代表循环的执行返回开头那块代码，进行计数。

解释执行与C1代码中增加循环回边计数器的位置并不相同。

超过一定的值就代表是热点数据了，不同的C1 C2 都有不同的触发值。

分层编译的时候采用**动态调整的方式**。阈值乘以某个系数。

## OSR编译

判断一个代码是否是热点代码的因素有两个：方法的调用次数，循环回边的执行次数。

按照**方法**为单位的即时编译。

OSR是按照**循环为单位**的即时编译器。一种技术，指代的是程序执行过程中，动态的替换掉Java方法栈帧，从而使得程序能够在非方法入口处进行解释执行和编译后的代码进行切换。

该编译类型一般采用在基准测试中。
