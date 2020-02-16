
import requests
import time
import json

# 设置header,模拟浏览器操作
headers = {
    "user-agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36",
    "referer":"http://www.weather.com.cn/weather1d/101010100.shtml"
}

def acquire_weather(city_code):
    if city_code is None:
        print("city_code is None")
        return 
    timestamp = int(time.time())*1000
    url = f"http://d1.weather.com.cn/sk_2d/{city_code}.html?_="+str(timestamp)
    result =  requests.get(url,headers = headers )
    # 设置编码
    result.encoding = "utf-8"
    length = len ("var dataSK = ")
    json_str_result = result.text[length:]
    return json.loads(json_str_result)