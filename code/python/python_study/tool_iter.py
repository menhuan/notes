import functools
import operator
from itertools import count, cycle, repeat, accumulate, chain, compress, dropwhile, filterfalse, groupby, islice, \
    starmap, takewhile, tee, zip_longest, product, permutations, combinations, combinations_with_replacement

count = count(1, 1)
print(next(count))
print(next(count))
print(next(count))
print(next(count))
print(next(count))
cycle = cycle([1, 2, 3])
print(next(cycle))
print(next(cycle))
print(next(cycle))
print(next(cycle))
print(next(cycle))
print(next(cycle))
print(next(cycle))
repeat1 = repeat([1, 2, 3])
print(next(repeat1))
print(next(repeat1))
print(next(repeat1))
print(next(repeat1))

repeat2 = repeat([4, 5, 6], times=3)
print(next(repeat2))
print(next(repeat2))
print(next(repeat2))
# print(next(repeat2))

accu = [1, 2, 3, 4, 5]
accu_result1 = accumulate(accu)
print(list(accu_result1))
accu_result2 = accumulate(accu, func=operator.mul)
print(list(accu_result2))

accu = [1, 2, 3, 4, 5]
accu_result1 = list(accumulate(accu))

accu_result2 = list(accumulate(accu, func=operator.mul))
chain_reuslt = chain(accu, list(accu_result1), list(accu_result2))
print(list(chain_reuslt))

accu = [1, 2, 3, 4, 5, 6]
selector = [True, False, True, False, True, False]
print(list(compress(accu, selector)))
selector = [0, 1, 1, 0, 0, 1]
print(list(compress(accu, selector)))

drop_result = dropwhile(lambda x: x // 2 < 3, [2, 4, 8, 9, 10])
print(list(drop_result))

drop_result = dropwhile(lambda x: x // 2 < 3, [2, 4, 8, 2, 9])
print(list(drop_result))
filter_result = filterfalse(lambda x: x // 2 < 3, [2, 4, 8, 2, 9])
print(list(filter_result))

sudent = [(172, 20, 'feng', 'man'), (156, 12, 'zhao', 'woman'), (168, 22, 'ming', 'woman')]

group_student = groupby(sudent, key=lambda x: x[3])
for key, group in group_student:
    print(key, ":", list(group))

data = iter([1, 2, 3, 4, 5, 6, 7, 8, 9, 0])
print("============")
slice_result = islice(data, 1)
print(list(slice_result))
data = iter([1, 2, 3, 4, 5, 6, 7, 8, 9, 0])
slice_result = islice(data, 1, 9)
print(list(slice_result))
data = iter([1, 2, 3, 4, 5, 6, 7, 8, 9, 0])
slice_result = islice(data, 1, 9, 2)
print(list(slice_result))

print("===========")
data = [(1, 2), (3, 4), (5, 6)]
map_result = starmap(operator.add, data)
print(list(map_result))
data = [(1, 2), (3, 4), (5, 6)]
map_result = starmap(operator.mod, data)
print(list(map_result))

data = [1, 2, 3, 4, 5, 6, 7, 8, 9, 0]
take_result = takewhile(lambda x: x < 5, data)
print(take_result)
print(list(take_result))
take_result = takewhile(lambda x: x % 2 != 0, data)
print(take_result)
print(list(take_result))
print("==========")

data2 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 0]
tee_result = tee(data2, 3)
for tee in tee_result:
    print(list(tee))

print(list(zip_longest('iterator', 'assigne', fillvalue='=')))
print(list(zip_longest([1, 2, 3, 4, 5], 'assigne', fillvalue='=')))

print(list(product([1, 2], repeat=2)))
print(list(permutations([3, 4], r=2)))
print(list(combinations([1, 2, 3, 4], r=2)))
print(list(combinations_with_replacement([1, 2, 3, 4], r=2)))


def add(a, b, c):
    return a + b + c


add_func = functools.partial(add)
print(add_func(1, 2, 3))
add_func = functools.partial(add, c=3)
print(add_func(1, 2))
