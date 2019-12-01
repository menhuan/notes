class Student:

    name = ""

    def __init__(self, name,age=None,num=None):
        self.age = age
        self.name = name
        if num is not None:
            self.num = num


print(Student.__name__)
print(Student.__base__)
print(Student.__module__)
print(Student.__doc__)
print(Student.__dict__)
print(Student.__dir__)


class Teacher:
    
    def __init__(self):
        self.name = "test"


teacher = Teacher()
teacher.name = "mengrui"
teacher.age = 10
teacher.num = 13452

print(teacher.name)
print(teacher.age)
print(teacher.num)
teacher_other =Teacher()
print(teacher_other.age)


class Product:
    def __init__(self):
        
        self.name = "maquillage"


product = Product()
product.age = 20
print(product.age)

del product.age

print(product.age)

class OnlineData:
    __slots__ = ('progress','num')


online_data = OnlineData()
online_data.progress = 1
print(online_data.progress)
online_data.description = "在线数据"

