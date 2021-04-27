class Student:
    code = '112'

    def __init__(self, name, age):
        self.name = name
        self.age = age

    def age(self):
        return self.age + 2


# stu_function = getattr(Student,'age')
# print(stu_function)
# print(stu_function(stu_function))
# print(stu_function(13))

code = getattr(Student, 'code')
print(code)

stu_default = getattr(Student, 'name', None)
print(stu_default)

# stu_name = getattr(Student,'name')
# print(stu_name)

has_false = hasattr(Student, 'name')
print(has_false)
has_true = hasattr(Student, 'code')
print(has_true)

stu = Student("test", 14)
grade = setattr(stu, "grade", 6)
print(stu.grade)
setattr(stu, "name", "update")
print(stu.name)
print(stu.__dict__)
print(Student.__dict__)

stu = Student("delete", 14)
print(stu.__dict__)
delattr(stu, 'name')
print(stu.__dict__)


class Animal:
    def __init__(self, height, type):
        self.height = height
        self.type = type

    def name(self, name):
        self.name = name


class Cat(Animal):
    def __init__(self, name, height, **kwargs):
        super().__init__(height, kwargs.get('type', "cat"))
        self.name = name


cat = Cat(name="cat", height="16cm")
animal = Animal(height=20, type=None)
if isinstance(cat, Animal):
    print("cat 属于Animal类型")
else:
    print("cat 不属于Animal类型")
if isinstance(animal, Animal):
    print("属于Animal类型")
else:
    print("不属于Animal类型")


if issubclass(Cat,Animal):
    print("cat 是Animal子类")
else:
    print("cat 不是Animal子类")