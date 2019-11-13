
content = "hello world"
print(content)























# if 判断
# number_a = 10
# number_b = 20
# if number_a > number_b:
#     print("number_a 大于 number_b")
# if number_a < number_b:
#     print("number_a 小于 number_b")

# print("执行结束")

# number_a = 10
# number_b = 20
# if number_a > number_b:
#     print("number_a 大于 number_b")
# elif number_a < number_b:
#     print("number_a 小于  number_b")

# animals = ["deer", "dog", "cat", "bull", "pony"]

# if "mule" not in animals:
#     animals.append("mule")
# elif "cat" in animals:
#     animals.append("mule")

# elif "pig" not in animals:
#     animals.append("pig")
# else:
#     animals.remove("deer")
# print(animals)

# fruits = {"apple": 10, "peach": 20, "tomato": 13, "pear": 5}

# if fruits["apple"] == 10:
#     print(fruits.get("pear", None))
#     print(fruits.get("flat", None))
# if "flat" not in fruits.keys():
#     fruits.pop("apple")
#     print(fruits)
# else:
#     print(f"水果集合为:{fruits}")


# fruits = {"apple": 10, "peach": 20, "tomato": 13, "pear": 5}

# if len(fruits) > 3:
#     if "flat" in fruits.keys():
#         print("flat的数量", fruits["flat"])
#     else:
#         print("apple的数量", fruits["apple"])
# elif "tomato" in fruits.keys():
#     if fruits["tomato"] > 10:
#         print("tomato的数量", fruits["tomato"])
#     else:
#         print("水果:", fruits)
# else:
#     print("都有什么水果?", fruits)

# fruits = {"apple": 10, "peach": 20, "tomato": 13, "pear": 5}

# while "peach" in fruits.keys():
#     fruits.popitem()
#     print(fruits)

# print("执行结束")

# numbers = {1, 2, 3, 40, 52, 32, 23, 11, 0}

# while len(numbers) > 7:
#     print("numbers的长度", len(numbers))
#     numbers.pop()
#     print("numbers移除一个元素后的长度", len(numbers))

# content = "i will buy some fruits and gifts "
# for value in content:
#     if value == "s":
#         print(value)

# values = range(10)

# for value in values:
#     print(value)

# for value in range(1, 20, 2):
#     print(value)

# for value in range(1, 5):
#     print(value)

# numbers = [0, 1, 2, 3, 4, 5]
# fruits = ["apple", "peach", "tomato", "pear"]

# for fruit in zip(numbers, fruits):
#     print("输出水果：", fruit)

# for index, fruit in enumerate(fruits):
#     print("索引值", index, "  水果为", fruit)

# numbers = [0, 1, 2, 3, 4, 5]

# for number in numbers:
#     if number == 3:
#         print("终止执行", number)
#         break
#     else:
#         print(number)

# for content in "i will go home":
#     if content == "g":
#         print("结束", content)
#         break
# fruits = ["apple", "peach", "tomato", "pear"]

# while len(fruits) > 1:
#     fruit = fruits.pop()
#     if fruit == "tomato":
#         break

# print(fruits)

# numbers = [0, 1, 2, 3, 4, 5]

# for number in numbers:
#     if number == 3:
#         print("终止执行", number)
#         break
#     else:
#         print(number)

# for content in "i will go home":
#     if content == "g":
#         print("结束", content)
#         break
# fruits = ["apple", "peach", "tomato", "pear"]

# while len(fruits) > 1:
#     fruit = fruits.pop()
#     if fruit == "tomato":
#         break

# print(fruits)


# numbers = [0, 1, 2, 3, 4, 5]

# for number in numbers:
#     if number == 3:
#         continue
#     print(number)


# for content in "i will go home":
#     if content == "g":
#         continue
#     print(content)
# fruits = ["apple", "peach",  "pear", "tomato"]

# while len(fruits) > 2:
#     fruit = fruits.pop()
#     if fruit == "tomato":
#         continue
#     print(fruit)

# print(fruits)

# fruits = ["apple", "peach",  "pear", "tomato"]

# if len(fruits) ==3:
#     print("水果的长度",len(fruits))
# elif "apple" in fruits:
#     pass
# else:
#     print("什么也没有")


# while 1>2:
#     print("1大于2")
# else:
#     print("1小于2")

# fruits =["apple","banana","berry","bryony","core"]

# for fruit in fruits:
#     if "banana" == fruit:
#         print("这个水果是：",fruit)
#     else:
#         print("不是想吃的水果")

# fruits = {"banana":10,"apple":5,"berry":5,"corre":20}

# for fruit,value in fruits.items():
#     print("水果是:",fruit," 数量有: ",value)

# for fruit in fruits.keys():
#     print("水果是",fruit)

# for value in fruits.values():
#     print("水果数量",value)

# for fruit,_ in fruits.items():
#     print("水果是：",fruit)

# for _,value in fruits.items():
#     print("水果数量是",value)
