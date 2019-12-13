class Student:
    def __init__(self, age):
        self.age = age

    def get_age(self):
        return self.age - 4

    @staticmethod
    def update_age():
        print("age:", 12)


student = Student(23)
print(student.get_age())
Student.update_age()
student.update_age()
