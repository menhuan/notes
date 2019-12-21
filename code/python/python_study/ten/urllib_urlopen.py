from urllib import request
from urllib.request import Request

# 百度的网站首页
url = "https://www.jianshu.com"

content = {
    "user-agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.79 Safari/537.36"
}
req = Request(url = url ,headers = content)
response = request.urlopen(req)
result = response.read()
print(result.decode('utf-8'))