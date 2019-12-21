"""
time 模块 
"""
import time 

print(time.time())
# 获取时间戳
timestamp = time.time()
# 将时间戳转为元组时间
local_struct_time = time.localtime(timestamp)
print("local_struct_time:",local_struct_time)
utc_struct_time=time.gmtime(timestamp)
print("utc_local_time:",utc_struct_time)

time_stamp = time.mktime(local_struct_time)
print(time_stamp)

from datetime import datetime

time_day = time.strptime('2019-2-1','%Y-%m-%d')
print(time_day)

stamp_time = time.strftime('%Y-%m-%d %H:%M:%S',time_day)
print(stamp_time)