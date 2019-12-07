"""
Python的公有属性与私有属性
"""


class Student:
    def __init__(self, name, description):
        self.name = name
        self.description = description
        self.__age = 20

    def message(self):
        return self.name + ','+self.description

    def __acquire_age(self):
        return self.__age

    def acquire_age(self):
        return self.__age

student = Student("mengrui","youth")
#print(f"age is {student.__age}")
age = student.acquire_age()
print(f"age is {age}")
student.__acquire_age()

print(1/0,)