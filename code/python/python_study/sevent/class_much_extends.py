class Animal:
    def __init__(self, age, name=None):
        self.age = age
        self.name = name


class Lactation():
    def __init__(self, description):
        self.description = description


class Cat(Animal, Lactation):
    def __init__(self, age):
        Animal.__init__(self,age=20)
        Lactation.__init__(self,description="哺乳")
        #super(Cat,self).__init__(age=20,name="cat",description="哺乳")
        # super(Lactation).__init__(self,)
        #self.age_num = age


cat = Cat(23)
print(cat.age)
