import threading
import time
from threading import Thread


def deal_time():
    thread_num = threading.current_thread()
    print(f"time is {time.time},thread num is {thread_num}")


for index in range(10):
    thread = Thread(target=deal_time, name="deal_time" + str(index))
    thread.start()

print("输出主线程信息", threading.current_thread())


num = 0


def sum_value():
    global num
    for index in range(10000):
        num += index


sum_value()
# thread_sum = Thread(target=sum_value)
# thread_sum1 = Thread(target=sum_value)
# thread_sum.start()
# thread_sum1.start()
# thread_sum.join()
# thread_sum1.join()

print(f"num is {num}")
