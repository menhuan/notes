from asyncio.subprocess import Process
from concurrent.futures.thread import ThreadPoolExecutor

from flask import Flask

from sixteen import create_app
from sixteen.batch import update_price, watch_price

app = create_app("dev")
executor = ThreadPoolExecutor(3)


def aync_time_task():
    # executor.submit(update_price)
    executor.submit(watch_price)


if __name__ == '__main__':
    aync_time_task()
    app.run("0.0.0.0", debug=True)
