"""
Python 异常学习
"""

try:
    print(1/1)
except ZeroDivisionError :
    print(1/3)
else:
    print(1/2)

try:
    print("连接数据库")
    raise IOError("数据库连接异常")
except IOError as e:
    print(e)
finally:
    print("关闭数据库连接")


try:
    print(1/0)
except Exception as e :
    print("Exception 异常",e)
except ZeroDivisionError as e :
    pass 

try:
    value =2 
    print("输出的值是",value)
    raise ValueError("Value 错误")
except ValueError as e:
    print("异常是",e)


class LogError(Exception):
    pass 

try:
    raise LogError("this is a log error")
except LogError as log:
    print(log)

