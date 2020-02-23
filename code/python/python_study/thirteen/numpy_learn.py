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

# print(np.add([1,2,3,4],[1,2,3]))


#### np的组合与拆分。
print("==========================拆分")
two_demension  = np.arange(20).reshape(4,5)
print(two_demension)
three_demension = np.arange(20).reshape(2,2,5)
print(three_demension)

# 3维数组转为2维数组
three_demension.shape =(5,4)
print(three_demension)
three_demension.resize(2,2,5)
print(three_demension)
one_demension = three_demension.flatten()
print(one_demension) 
one_damension = three_demension.ravel()
print(one_damension)

print("=========数组的组合")
first = np.arange(9).reshape(3,3)
print(first)
second = np.arange(18).reshape(3,6)
print(second)
result = np.hstack((first,second))
print(result)

first = np.arange(9).reshape(3,3)
print(first)
second = np.arange(18).reshape(6,3)
print(second)
result = np.vstack((first,second))
print(result)

### 拆分
print("===========================拆分")
nums = np.arange(20).reshape(4,5)
# 水平拆分
h_result = np.hsplit(nums,5)
print(nums)
print(h_result)
# 垂直拆分
v_result = np.vsplit(nums,4)
print(v_result)
