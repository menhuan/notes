class Student:

    @classmethod
    def age(cls):
        return 20

    def name(self):
        return "name"


print(Student.age())
student = Student()
print(student.age())
