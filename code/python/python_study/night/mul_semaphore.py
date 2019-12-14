import threading
from time import sleep
import time

"""
信号量的概念
"""

seamphore = threading.Semaphore()

def output_time():
    seamphore.acquire()
    sleep(1)
    current_time =  time.ctime()
    print(f"当前时间：{current_time}")
    seamphore.release()



for index in range(10):
    connection = threading.Thread(target=output_time)
    connection.start()
