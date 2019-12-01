# class Student:
#     def __init__(self):
#         self.name = "mengrui"

#     @property
#     def age(self):

#         if self.value > 10:
#             return self.value
#         else:
#             return 10

#     @age.setter
#     def age(self, value):
#         self.value = value

#     @age.deleter
#     def age(self):
#         del self.value


# student = Student()
# student.age = 15
# print(student.age)

def get_age(self):
    return self.age + 5

class Teacher:
    def __init__(self, age):
        self.age = age

    def birth(self):
        return 2019 - self.age
    get_age=get_age
class Student:
    def __init__(self,age):
        self.age=age
    get_age=get_age
teacher = Teacher(25)
print(teacher.get_age())
student = Student(20)
print(student.get_age())
