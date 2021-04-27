from time import sleep
import time
import threading
import concurrent.futures 
from concurrent.futures.thread import ThreadPoolExecutor
def output_time(index):
    sleep(1)
    current_time =  time.ctime()
    print(f"当前时间：{current_time}")




with ThreadPoolExecutor(5) as pool:
    index = [1,2,3,4,5,6,7]
    pool.map(output_time,index)

print("执行结束")