class Animal:
    def __init__(self, age, name="dog", length=None):
        self.age = age
        self.name = name
        self.length = length

    def get_name(self):
        return self.name


class Dog(Animal):
    def __init__(self):
        #super().__init__(age, name=name, length=length)
        pass


class Cat(Animal):
    def __init__(self, age, name='cat', length=None):
        super().__init__(age, name=name, length=length)


cat = Cat(4)
print(cat.name)
dog = Dog()
print(dog.name)
