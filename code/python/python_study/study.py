

fruits =["apple","banana","berry","bryony","core"]

for fruit in fruits:
    if "banana" == fruit:
        print("这个水果是：",fruit)
    else:
        print("不是想吃的水果")

fruits = {"banana":10,"apple":5,"berry":5,"corre":20}

for fruit,value in fruits.items():
    print("水果是:",fruit," 数量有: ",value)

for fruit in fruits.keys():
    print("水果是",fruit)

for value in fruits.values():
    print("水果数量",value)

for fruit,_ in fruits.items():
    print("水果是：",fruit)

for _,value in fruits.items():
    print("水果数量是",value)