Service Mesh 是一种新型的用于处理服务与服务之间通信的技术，保证服务与服务之间调用的可靠性。
在使用的时候使用轻量级的网络代理的方式跟应用的代码部署在一起，从而应用感知的方式实现服务治理。
## 与传统的微服务架构的区别
1. 跨语言的需要，使用grpc 或者Thrift 都要有IDL文件进行适配，每个SDK都需要设计实现一遍，成本高。
2. 云原生应用服务治理的需要。传统的微服务治理是在业务代码里面集成服务框架的SDK，与云原生概念
相悖。
## 第一代的Service Mesh
第一代的Service Mesh 产品是Linkerd .
主要有几个中心点集成。
1. 轻量级网络代理SideCar: 实现的是转发服务之间的调用。
![摘自极客时间](https://upload-images.jianshu.io/upload_images/4237685-da268f5c4254ee49.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
服务框架功能都集中在SideCar上面，服务功能框架是服务发现，负载均衡，熔断降级，监控日志信息。
- 网络代理实现有两种方式：
  1. 基于iptables的拦截方式，端口拦截转发，有点事从网络层调用实现拦截，业务完全无感知，但是性能有一定的损耗
![摘自极客时间](https://upload-images.jianshu.io/upload_images/4237685-4cfb582080c00583.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
  2. 采用协议转换的方式， 直接把请求发送给代理服务器，这个需要加入业务逻辑，对代码有一定的业务侵入性。
![摘自极客时间](https://upload-images.jianshu.io/upload_images/4237685-482e497896aff6a9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2.  Control Plane:统一的治理中心
- 服务发现：SideCar注册到这里的注册中心。来获取服务节点信息。
- 负载均衡 ： 可以通过Control Plane 冬天修改SideCar上的负载均衡配置。
- 请求路由：获取服务节点列表，通过其动态修改。
- 故障处理：动态配置、
- 安全认证：控制服务是否可以被访问，哪些信息可以被访问。
- 监控上报：本地的转发信息需要上报到监控系统通过治理中心进行操作。
- 日志记录：上传到日志系统。
- 配额控制： 调用的最大次，服务限制等

![摘自极客时间](https://upload-images.jianshu.io/upload_images/4237685-bd3ecde70def7ffa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
