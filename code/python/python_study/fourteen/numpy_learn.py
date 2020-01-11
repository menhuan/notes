import numpy as np
from numpy.core import arange

nums = np.array([1,2,3,4,5])
print(nums.shape)
print(nums.dtype)

nums = np.array(arange(10))
print(nums.shape[0])
print(nums)

two_demension = np.array([[1,2,3],["i","m",3],[4,5,6]])
print(two_demension.shape)
print(two_demension)
two_demension = np.array([[1,2,3],["i","m",3,4],[4,5,6]])
print(two_demension.shape)
print(two_demension)
two_demension = np.array([[1,2,3],[4,5,6]])
print(two_demension.shape)
print(two_demension)

three_demension = np.array([[[1,2,3],[4,5,6]],[[3,4,5],[6,7,8]]])
print(three_demension.shape)
print(three_demension)

### 结构数组
header = np.dtype(
    {
        "names":["name","price","area","age"],
        "formats":["U10","f","f","i"]
    }
)

data = [
    ("丰开苑 3室1厅",4800000.0,77.8,23),
    ("莱圳家园一层带院",8800000.0,113.12,13),
    ("西城区-双南两居",620,57.1,34),
    ("南三环珠江骏景",3100000.0,68.3,13)
]

homes = np.array(data,dtype = header)
print(homes)
names = homes[:]["name"]
prices = homes[:]["price"]
areas = homes[:]["area"]
ages = homes[:]["age"]
print(names)
print(prices)
print(areas)
print(ages)

## np的算数运算
nums  = np.arange(2,13,2)
nums_value = np.linspace(2,12,6)
print(nums)
print(nums_value)

print(np.add(nums,nums_value))
print(np.subtract(nums, nums_value))
print(np.multiply(nums, nums_value))
print(np.divide(nums, nums_value))
print(np.mod(nums, nums_value))

print(np.add([1,2,3,4],[1,2,3]))