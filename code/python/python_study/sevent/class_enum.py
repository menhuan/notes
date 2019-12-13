from enum import Enum,unique
@unique
class Week(Enum):
    Sun = 0  
    Mon = 1
    Tue = 2
    Wed = 3
    Thu = 4
    Fri = 5
    Sat = 6

print(Week.Fri.value)
print(Week.Sat.value)
for member,member_value in Week.__members__.items():
    print(member,member_value,member_value.value)
