import re
import time

content = "www.jikelearn"

result = re.match('jikelearn', content)
print(result)
result = re.match("www", content)
print(result)
print(result.span())
result = re.match(r'(.*) 173.* ', "I am from China,my phone is 1735678272. end", re.M | re.I)

if result:
    print(result)
    print(result.groups())
    print(result.group())
    print(result.group(1))
else:
    print('result is None ,Not to match')

match = re.search("jikelearn", content)
print(match)
match = re.search("None", content)
print(match)

result = re.search(r'173.* ', "I am from China,my phone is 1735678272. end", re.M | re.I)

if result:
    print(result)
    print(result.groups())
    print(result.group(0))
else:
    print('result is None ,Not to match')

sub = re.sub("123123123", "", "www.jikelearn123123123.com")
print(sub)
subn = re.subn("123123123", "", "www.jikelearn123123123.com")
print(subn)

sub = re.sub("souguo", "", "www.jikelearn.com")
print(sub)
subn = re.subn("souguo", "", "www.jikelearn.com")
print(subn)
subn = re.subn("[0-9]*", "", "www.jikelear1231231234n.com 13212  822911")
print(subn)
subn = re.subn(" *", "", subn[0])
print(subn)

finds = re.findall("s", "Add a handler sending log messages to a sink adequately configured.")
print(finds)
finds = re.findall("y", "Add a handler sending log messages to a sink adequately configured.")
print(finds)
finds = re.findall("B", "Add a handler sending log messages to a sink adequately configured.")
print(finds)

finds = re.finditer("s", "Add a handler sending log messages to a sink adequately configured.")
print(finds)
finds = re.finditer("\d+", "Add a handler sending log messages to a 1 2 sink adequately 543  configured.")
for find in finds:
    print(find)

finds = re.split("\d+", "Add a handler sending log messages to a 1 2 sink adequately 543  configured.")
print(finds)

finds = re.split("\W+", "Add a handler sending log messages to a 1 2 sink adequately 543  configured.")
print(finds)
finds = re.split("\W+", "Add a handler sending log messages to a 1 2 sink adequately 543  configured.",4)
print(finds)
