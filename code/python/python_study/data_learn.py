from collections import namedtuple

User = namedtuple("User", ['id', 'name', 'code', 'age'])
user = User(1, 'wang', '01', 18)
user2 = User(2, 'feng', '02', 19)
print(user)
print(user2)
user3 = User(3, name=None, code=None, age=None)
print(user3)

from collections import deque

q = deque([1, 2, 3, 4, 'content'])
q.append('co')
q.appendleft('wo')
print(q)
print(list(q))
q.append('wo')
print(q)
print(q.count('wo'))
print(q[1])
print(q[3])
print(q[-1])
print(1 in q)
q.reverse()
print(q)
q.extend(['1', '2'])
print(q)
q.extendleft(['4', '5'])
print(q)
q.remove('4')
print(q)
print(q.pop())
print(q)
print(q.popleft())
q = deque([1, 2, 3, 4], maxlen=5)
print(q)
q.append('1')
print(len(q))
q.append('2')
print(q)
q.append('3')
print(q)

from collections import ChainMap

content = {"a": 3, "b": 10}
chain = ChainMap(content)
print(chain)
content2 = {"c": 10, "d": "20", "b": 20}
chain2 = ChainMap(content, content2)
print(chain2)
maps = chain2.maps
print(maps)
print(chain2['c'])
print(chain2['b'])
chain2['b'] = 30
print(chain2)
chain2['d'] = 20
print(chain2)

from collections import Counter

counter = Counter()
counter['a'] = 1
print(counter)
counter['a'] = 2

print(counter)
counter['a'] = 5
print(counter)

# counter.popitem()
# print(counter)
# counter.pop('a')
# print(counter)
counter = Counter(a=10, b=20, c=-10, d=5, e=10)
print(sorted(counter))
print(counter.elements())
print(sorted(counter.elements()))
print(counter.most_common())
print(counter.most_common(2))
print(counter.most_common(3))
print(counter)
counter.subtract(dict(a=10, h=2))
print(counter)

# 堆操作
import heapq

data = [1, 13, 45, 21, 89, 31, 28, 44, 19, 99]
heapq.heapify(data)
print(data)
data = [1, 13, 45, 21, 89, 31, 28, 44, 19, 99]
heap =[]
for item in data:
    heapq.heappush(heap,item)
print(heap)

print(heapq.heappop(heap))
print(heapq.heappushpop(heap,56))
print(heap)
print(heapq.heapreplace(heap,78))
print(heap)

data = [1, 13, 45, 21, 89, 31, 28, 44, 19, 99]
heapq.heapify(data)
print(data)
print(heapq.nlargest(1,heap))
print(heapq.nlargest(3,heap))
print(heapq.nsmallest(1,heap))
print(heapq.nsmallest(3,heap))

print(heapq.merge([1,2,3,10],[78,23,99,10],[5,3,0]))
print(list(heapq.merge([1,2,3,10],[78,23,99,10],[5,3,0])))
