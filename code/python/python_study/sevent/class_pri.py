class People:
    __name = "pr"
    people_type = "base"

    def __colour_people(self):
        return "yellow"


class OtherPeople(People):
    pass


other = OtherPeople()

print(other.people_type)
print(other.__name)
print(other.__colour_people())