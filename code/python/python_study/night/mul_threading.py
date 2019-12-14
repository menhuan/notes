from threading import Thread
import threading
from time import sleep


num = 0


def sum_value():
    lock = threading.Lock()
    with lock:
        global num
        for index in range(10000):
            num += 1


thread_sum = Thread(target=sum_value)
thread_sum1 = Thread(target=sum_value)
thread_sum.start()
thread_sum1.start()
thread_sum.join()
thread_sum1.join()
print(f"num is {num}")


class TaskThread(Thread):
    def __init__(self, name=None, daemon=None):
        super().__init__()
        self.name = name
        self.daemon = daemon

    def run(self):
        print("开始task", threading.current_thread())
        sleep(2)
        print("Task 结束", threading.current_thread())


# task_first = TaskThread("task1")
# task_end = TaskThread("task2")
# task_first.start()
# task_end.start()
# task_first.join()
# task_end.join()
# print("执行完毕")


time = 0
rlock = threading.RLock()


def method_sum_time():
    with rlock:
        global time
        time += 100
        print(f"time is {time}")
        method_sum_second_time()


def method_sum_second_time():
    with rlock:
        global time
        time += 200
        print(f"second time is {time}")

thread = Thread(target=method_sum_time)
thread.start()
thread.join()