"""
datetime 模块学习
"""
from datetime import datetime 
from pytz import timezone
print(datetime.now())


day = datetime(2019,10,2,2,2,2)
print(day)
day1 = datetime(2019,5,5)
print(day1)

# 获取时间utc时间的方式
now_date = datetime.now()
utc_now = datetime.utcnow()
print(now_date)
print(utc_now)
utc_tz = timezone('UTC')  
now_utc =datetime.now(utc_tz)
print(now_utc)

## 时间戳与datetime的转换
timestamp = 1576925361
date = datetime.fromtimestamp(timestamp)
print("由时间戳生成的时间",date)
utc_date = datetime.utcfromtimestamp(timestamp)
print(utc_date)

day = datetime.strptime('2019-2-1 21:29:32', '%Y-%m-%d %H:%M:%S')
print("字符串时间转为时间：",day)
now_day = datetime.now()
print(now_day.strftime('%Y-%m-%d %H:%M:%S'))