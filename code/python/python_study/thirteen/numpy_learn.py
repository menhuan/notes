import numpy as np
from numpy.core import arange

nums = np.array([1, 2, 3, 4, 5])
print(nums.shape)
print(nums.dtype)

nums = np.array(arange(10))
print(nums.shape[0])
print(nums)

two_demension = np.array([[1, 2, 3], ["i", "m", 3], [4, 5, 6]])
print(two_demension.shape)
print(two_demension)
two_demension = np.array([[1, 2, 3], ["i", "m", 3, 4], [4, 5, 6]])
print(two_demension.shape)
print(two_demension)
two_demension = np.array([[1, 2, 3], [4, 5, 6]])
print(two_demension.shape)
print(two_demension)

three_demension = np.array([[[1, 2, 3], [4, 5, 6]], [[3, 4, 5], [6, 7, 8]]])
print(three_demension.shape)
print(three_demension)

### 结构数组
header = np.dtype(
    {
        "names": ["name", "price", "area", "age"],
        "formats": ["U10", "f", "f", "i"]
    }
)

data = [
    ("丰开苑 3室1厅", 4800000.0, 77.8, 23),
    ("莱圳家园一层带院", 8800000.0, 113.12, 13),
    ("西城区-双南两居", 620, 57.1, 34),
    ("南三环珠江骏景", 3100000.0, 68.3, 13)
]

homes = np.array(data, dtype=header)
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
nums = np.arange(2, 13, 2)
nums_value = np.linspace(2, 12, 6)
print(nums)
print(nums_value)

print(np.add(nums, nums_value))
print(np.subtract(nums, nums_value))
print(np.multiply(nums, nums_value))
print(np.divide(nums, nums_value))
print(np.mod(nums, nums_value))

# print(np.add([1,2,3,4],[1,2,3]))


#### np的组合与拆分。
print("==========================拆分")
two_demension = np.arange(20).reshape(4, 5)
print(two_demension)
three_demension = np.arange(20).reshape(2, 2, 5)
print(three_demension)

# 3维数组转为2维数组
three_demension.shape = (5, 4)
print(three_demension)
three_demension.resize(2, 2, 5)
print(three_demension)
one_demension = three_demension.flatten()
print(one_demension)
one_damension = three_demension.ravel()
print(one_damension)

print("=========数组的组合")
first = np.arange(9).reshape(3, 3)
print(first)
second = np.arange(18).reshape(3, 6)
print(second)
result = np.hstack((first, second))
print(result)

first = np.arange(9).reshape(3, 3)
print(first)
second = np.arange(18).reshape(6, 3)
print(second)
result = np.vstack((first, second))
print(result)

### 拆分
print("===========================拆分")
nums = np.arange(20).reshape(4, 5)
# 水平拆分
h_result = np.hsplit(nums, 5)
print(nums)
print(h_result)
# 垂直拆分
v_result = np.vsplit(nums, 4)
print(v_result)

###单个数组迭代
nums = np.arange(10).reshape(2, 5)
print("=====num:")
print(nums)
print("=====输出结束")
for num in np.nditer(nums):
    print(num, end=" ")

print("\n")
print("=====C顺序")
for num in np.nditer(nums, order='C'):
    print(num, end=" ")
print("\n")
print("=====Fortran顺序")
for num in np.nditer(nums, order='F'):
    print(num, end=" ")

###单个数组遍历修改

nums = np.arange(20).reshape(4, 5)
print("========nums:")
print(nums)
with np.nditer(nums, op_flags=['readwrite']) as num_values:
    for num in num_values:
        num[...] = 3 * num
print("=======nums:")
print(nums)

### 多个数组迭代
# nums = np.arange(10).reshape(2,5)
# nums_fork = np.arange(20).reshape(2, 10)
# for num in np.nditer([nums, nums_fork]):
#     print(num, end=" ")

### 统计函数

value = np.array([[1, 22, 3], [6, 3, 18], [2, 8, 15]])
print(value)
print("========最小值")
print(np.amin(value))
print(np.amin(value, axis=0))
print(np.amin(value, axis=1))

#### 最大值元素
value = np.array([[1, 22, 3], [6, 3, 18], [2, 8, 15]])
print(value)
print("========最大值")
print(np.amax(value))
print(np.amax(value, axis=0))
print(np.amax(value, axis=1))

# 差值元素
value = np.array([[1, 22, 3], [6, 3, 18], [2, 8, 15]])
print(value)
print("========差值")
print(np.ptp(value))
print(np.ptp(value, axis=0))
print(np.ptp(value, axis=1))

####中位数

value = np.array([[1, 22, 3], [6, 3, 18], [2, 8, 15]])
print(value)
print("========中位数")
print(np.median(value))
print(np.median(value, axis=0))
print(np.median(value, axis=1))

####算数平均值
value = np.array([[1, 22, 3], [6, 3, 18], [2, 8, 15]])
print(value)
print("========算数平均值")
print(np.mean(value))
print(np.mean(value, axis=0))
print(np.mean(value, axis=1))

#### 加权平均值
value = np.array([1, 22, 3])
print(value)
print("========加权平均值")
print(np.average(value))
weight = np.array([4, 3, 2])
print(np.average(value, weights=weight))
print(np.average(value, weights=weight, returned=True))

### 标准差
std_nums = np.array([1, 22, 3, 6, 3, 18, 2, 8, 15])
print(std_nums)
print("=========标准差")
print(np.std(std_nums))

### 方差
var_nums = np.array([1, 22, 3, 6, 3, 18, 2, 8, 15])
print(var_nums)
print("=========方差")
print(np.var(var_nums))

