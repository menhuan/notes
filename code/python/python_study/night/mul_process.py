"""
多进程的实现
"""
from multiprocessing import Process


def print_value(value):
    for index in range(4):
        print(value, " 输出的值是", index + 1)


if __name__ == '__main__':
    process = Process(target=print_value, args=(1,))
    process.start()
    process.join()


class MyProcess(Process):
    def __init__(self, name):
        super().__init__(name=name)
        self.description = "my process"

    def run(self):
        print("进程执行完毕")

if __name__ == '__main__':
   my_process =  MyProcess("one")
   my_process.start()

import concurrent.futures
from concurrent.futures.process import ProcessPoolExecutor

def out_value(value):
    for index in range(2):
        print(value, " 输出的值是", index + 1)

if __name__ == '__main__':
    with ProcessPoolExecutor(5) as pool:
        for index in range(5):
            pool.submit(out_value,index)