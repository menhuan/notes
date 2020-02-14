# gprc使用问题集合

## Python使用问题

问题集合列表

### 使用grpcio找不到模块

具体报错如下：

```linux
python -m grpc_tools.protoc --version
/usr/local/opt/python/bin/python3.7: Error while finding module specification for 'grpc_tools.protoc' (ModuleNotFoundError: No module named 'grpc_tools')
一般解决方案是在当前环境中安装相关包
pip install grpcio
pip install grpcio-tools

还有该问题出现可能是安装的包路径不同，可以使用
python3 -m grpc_tools.protoc --version 进行验证

```