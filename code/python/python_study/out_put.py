print(1, 2, 3, 4, 5)
print('1' + '2' + '3' + '4')

print(1, 2, 3, 4, 5)
print(6, 7, 8, 9, 10)

print(1, 2, 3, 4, 5, end=' ')
print(6, 7, 8, 9, 10)

with open("print.txt", 'w') as f:
    print(1, 2, 3, 4, 5, file=f)

import pprint
content = [1, 2, 3, 4, 5]
content.insert(0,[6,8,9,10])
pprint.pprint(content)
pp = pprint.PrettyPrinter(indent=4,width=25)
pp.pprint(content)

pp = pprint.PrettyPrinter(indent=4,width=25,compact=True)
pp.pprint(content)

pp=pprint.PrettyPrinter(depth=1)
pp.pprint(content)

import json
import pprint
import requests
resp = requests.get('https://pypi.org/pypi/sampleproject/json')
content =resp.json()
pprint.pprint(content['releases'])