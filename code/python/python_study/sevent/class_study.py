class Student:

    name = ""

    def __init__(self):
        self.age = 10

    def set_name(self, name):
        self.name = name


student = Student()
print("该学生的年龄:", student.age)
student.set_name("mengrui")
print("该学生的姓名:", student.name)
