import urllib
from urllib.request import Request
from urllib import parse, request

url = "https://www.ershicimi.com/signin"
data = {"email": "963633167@qq.com", "password": "feng930409"}

header = {
    "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.79 Safari/537.36"
}
# 对参数进行编码
data = parse.urlencode(data).encode('utf-8')
req = Request(url = url ,headers = header,method="POST")
response = request.urlopen(req,data=data)
result = response.read()
print(result.decode('utf-8'))