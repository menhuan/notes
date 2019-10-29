
content = "hello world"
print(content)

# if 判断
number_a = 10
number_b = 20
if number_a > number_b:
    print("number_a 大于 number_b")
if number_a < number_b:
    print("number_a 小于 number_b")

print("执行结束")

number_a = 10
number_b = 20
if number_a > number_b:
    print("number_a 大于 number_b")
elif number_a < number_b:
    print("number_a 小于  number_b")

animals = ["deer", "dog", "cat", "bull", "pony"]

if "mule" not in animals:
    animals.append("mule")
elif "cat" in animals:
    animals.append("mule")

elif "pig" not in animals:
    animals.append("pig")
else:
    animals.remove("deer")
print(animals)

fruits = {"apple":10,"peach":20,"tomato":13,"pear":5}

if fruits["apple"] == 10:
    print(fruits.get("pear",None))
    print(fruits.get("flat",None))
if "flat" not in fruits.keys() :
    fruits.pop("apple")
    print(fruits)
else:
    print(f"水果集合为:{fruits}")


fruits = {"apple":10,"peach":20,"tomato":13,"pear":5}

if len(fruits)>3:
    if "flat" in fruits.keys():
        print("flat的数量",fruits["flat"])
    else:
        print("apple的数量",fruits["apple"])
elif "tomato" in fruits.keys():
    if fruits["tomato"] > 10:
        print("tomato的数量",fruits["tomato"])
    else:
        print("水果:",fruits)
else:
    print("都有什么水果?",fruits)

fruits = {"apple":10,"peach":20,"tomato":13,"pear":5}

while "peach" in fruits.keys():
    fruits.popitem()
    print(fruits)

print("执行结束")

numbers = {1,2,3,40,52,32,23,11,0}

while len(numbers) > 7:
    print("numbers的长度",len(numbers))
    numbers.pop()
    print("numbers移除一个元素后的长度",len(numbers))