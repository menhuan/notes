# 装饰器 文件
import time

def log(func):
    def wrapper(*args,**kwargs):
        print("日志输出开始",time.time())
        time.sleep(2)
        func()
        print("日志输出结束",time.time())
    return wrapper

@log
def print_log():
    print("开始结束")

print_log()

def add_log_config(level,message):
    def log(func):
        def wrapper(*args,**kwargs):
            time.sleep(2)
            
            print(f"日志输出开始:level: {level}, message :{message}")
            func()
         
        return wrapper

    return log

@add_log_config(level="info",message="成功执行")
def print_result():
    print("执行结束")


print_result()

num = 5 
def sum_value():
    print("num的值是",num)
sum_value()


num1 = 5 
def sum_value():
    num1=num1 +1
    print("num的值是",num1)
sum_value()



simple = lambda x:x*2

print(simple(4))

def sum_result(n):
    # 返回的是一个匿名函数
    return lambda x:x*n
func_result = sum_result(2)
print(func_result(2))
