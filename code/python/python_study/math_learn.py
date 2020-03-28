import decimal
import math
from decimal import Decimal,ROUND_CEILING

print(math.exp(2))
print(math.expm1(2))

print(math.log(10))
print(math.log2(2))
print(math.log10(10))
print(math.log1p(3))
print(math.pow(2,3))
print(math.sqrt(4))

# 三角函数
print("=========")
print(math.acos(1))
print(math.asin(1))
print(math.atan(1))
print(math.atan2(1,1))
print(math.cos(3.14))
print(math.sin(3.14))
print(math.tan(1))
print(math.hypot(1,1))

print(math.pi)
print(math.e)
print(math.tau)
print(math.inf)
print(math.nan)

# Decimal

# decimal = Decimal(10.0)
# print(decimal)
#
decimal2 = Decimal(10.2565188)
# print(decimal+decimal2)
# print(decimal/decimal2)

print(decimal2.quantize(1,ROUND_CEILING))
print(decimal2.quantize(Decimal('1.'),decimal.ROUND_DOWN))

print(decimal2.quantize(Decimal('1.'),decimal.ROUND_FLOOR))
print(decimal2.quantize(Decimal('1.'),decimal.ROUND_HALF_DOWN))
print(decimal2.quantize(Decimal('1.'),decimal.ROUND_HALF_EVEN))
print(decimal2.quantize(Decimal('1.'),decimal.ROUND_HALF_UP))
print(decimal2.quantize(Decimal('1.'),decimal.ROUND_UP))
print(decimal2.quantize(Decimal('1.'),decimal.ROUND_05UP))

# 随机数
import random

value = random.random()
print(value)
value = random.randint(10,20)
print(value)
value = random.uniform(1,4)
print(value)
value = random.uniform(-4,-1)
print(value)
value = random.randrange(1,10,2)
print(value)
value = random.randrange(20,10,step=-1)
print(value)
