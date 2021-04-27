"""
study file
"""
path= "code\python\python_study\eight\learn.txt"
learn = open(path,"r+")
content = learn.read()
print(content)

path= "t.txt"
learn = open(path,"w")
value= learn.write("test")
re = open("t.txt","r")
content = re.read()
print(content)


path = "write.txt"
write_file = open(path,"w+")
write_file.write("i like Python,are you ?\n")
write_file.flush()
write_file.write("write Python")
write_file.close()
read_file = open(path,"r+")
for line in read_file:
    print(line)
read_file.close()


import fileinput

path= "code\python\python_study\eight\learn.txt"
file_obj = fileinput.input(path)

for line in file_obj:
    print(line)


path= "code\python\python_study\eight\learn.txt"
   
try:
    learn = open(path,"r+")
    content = learn.read()
    print(content)
except IOError as e:
    print("异常",e) 
finally:
    learn.close()

with open(path,"r+") as learn :
    try:
        content = learn.read()
        print(content)
    except IOError as e:
        print("异常",e)
    