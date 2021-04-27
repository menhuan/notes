import os

result = os.getenv("OS", '1')

from functools import partial


def start():
    pass


partial(start)

import importlib

pickle = importlib.import_module(name='pickle')
value = pickle.dumps(dict(name="test", age=10))
print(value)

print(os.fspath("/Volumes"))
print(os.getppid())
