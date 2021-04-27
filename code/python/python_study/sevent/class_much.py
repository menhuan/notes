class Animal:
    def __init__(self):
        self.description = "animal"
        self.age = 10

    def get_age(self):
        return self.age

    def get_description(self):
        return self.description


class Cat(Animal):
    def get_age(self):
        return 5

    def get_description(self):
        return "cat"


class Dog(Animal):
    def get_age(self):
        return 10

    def get_description(self):
        return "dog"


cat = Cat()
print(cat.get_age())
print(cat.get_description())
dog = Dog()
print(dog.get_age())
print(dog.get_description())
