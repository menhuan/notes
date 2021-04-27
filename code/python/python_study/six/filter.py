

result = filter(lambda x:  x > 5, [1, 2, 3, 6, 7, 8])
print(result)
print(list(result))

result = filter(lambda x, y: x > y, [1, 2, 3, 5, 6, 7], [2, 3, 1, 4, 7])

print(list(result))

value = filter(None,[1,2,3,4,5])
print(list(value))


from functools import reduce
result = reduce(lambda x,y:x*y,[1,2,3,4,5])
print(result)


iter_value = iter([1,2,3])
next(iter_value)
next(iter_value)
next(iter_value)

list_value = list(iter_value)
print(list_value)

result = [x for x in range(1,10)]
print(result)

result = [item  if item >5 else 0 for item in range(2,8) ]
print(result )

result = (x for x in range(1,3))
print(result)
for value in result:
    print(value)

for value in result:
    print(value)
