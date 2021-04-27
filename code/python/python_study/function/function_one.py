
def my_sum(a,b):
    return a+b

print(my_sum(1,2))


def find_max_value(contents):
    """
    寻找列表中最大的值
    """    
    if not isinstance(contents,list):
        print("该参数不是列表，不能查找")
        return
    if len(contents)<=0:
        print("列表是空的")
    max_value = 0 
    for content in contents:
        if content > max_value:
            max_value = content
            continue
        else:
            pass

    return max_value

print(find_max_value([1,21,9,5,32]))


def find_key_and_value(contents):
    if not isinstance(contents,dict):
        print("params is not dict")
        return
    if len(contents)>= 0:
        return contents.keys(),contents.values()        

contents = {"number_min":1,"number_max":2}
key,value = find_key_and_value(contents=contents)
print(key,value )
_,value = find_key_and_value(contents)

def print_value(set_content):
    print(len(set_content))

print(print_value(set([1,2,3,4,5])))

