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




