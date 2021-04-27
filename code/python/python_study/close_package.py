
def sum_num(one):
    def sum_two(second):
        return one + second

    return sum_two


function_two = sum_num(1)
value = function_two(5)
print(type(function_two))
print(value)


def origin_num():
    value = 10 
    def sum_value(sum):
        nonlocal value
        value +=10
        return value + sum
    print("value:",value)
    return sum_value

origin = origin_num()
print(type(origin))

result = origin(1)
print(result)
result = origin(2)
print(result)

def error_var():
    def test_var():
        nonlocal sum_result
    
        sum_result =1

    return test_var

error_var()

