def test(first,second,third):
    print(first,second,third)

test(1,2,3)

first = [1,2,3,4,5,6]
second = {
    "num":1,
    "save":False,
    "content":[1,2,3,4,5],
}

third =tuple([3,4,5,6,7])


test(first,second,1)

def test(first,second,third):
    print(first,second,third)

test(1,2,3)

first = [1,2,3,4,5,6]
second = {
    "num":1,
    "save":False,
    "content":[1,2,3,4,5],
}

test(first,second,1)


def test_prarm(first,second,third=None):
    if third == None :
        print(f"third is {third}")
        return 
    if isinstance(first,str):
        print(first)
    else:
        raise Exception()
    if isinstance(second,dict):
        print(second)



first ="content"
second ={ 
    "num":1,
    "save":False,
    "content":[1,2,3,4,5],
}
third = 3 

test_prarm(first=first,second=second,third = 5 )
content
{'num': 1, 'save': False, 'content': [1, 2, 3, 4, 5]}

######################################################

def changeable_param(*param):
    print(param)

changeable_param(1,"Your",True,[1,2,3,4],{"content":1})
##############################
def test_param(first,second,third):
    print(first,second,third)

content = [1,2,3]
test_param(*content)

content = [1,2,3,4]
test_param(*content)


def test_param(first,second,third=None):
    print(first,second,third)


content = [1,2]
test_param(*content)