# mongodb操作
from pymongo import * 

# 链接MongoDB的客户端
client = MongoClient(host="188.131.139.100",port=27017)

# 设置库
test =client.test
# 设置集合
weather = test.weather

sms_history = test.sms_history 

def mongo_insert(collestion,data):
    results = collestion.insert_many(data)
    if len(results.inserted_ids) > 0 :
        return True


# 短信模块
from twilio.rest import Client

account_sid = 'AC826fa27807aefe45abc211455af15493'
auth_token = 'f7c356daa96647c32991603ca068b420'
client = Client(account_sid, auth_token)

def send_message(message,to_phone):
    message = client.messages \
                    .create(
                        body=f"{message}",
                        from_='+12028048563',
                        to=to_phone
                    )
    print(message.error_code)


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

def send_content(weather_json):
    str_prefix = "亲爱的,天气变化多端，请注意身体哦。"
    cityname = weather_json["cityname"]
    temp = weather_json["temp"]
    weather = weather_json["weather"]
    limit_number = weather_json["limitnumber"]
    aqi_pm25 = weather_json["aqi_pm25"]
    result = str_prefix + " " + cityname + "今天当前温度是"+str(temp)+",今天天气"+weather+",今天限行尾号为："+limit_number +",pm25:"+str(aqi_pm25)
    return result 
def deal_message():
    """
    1. 爬取对应的天气网站，获取天气数据
    2. 拼接成想要发送的文字内容
    3. 将天气字段内容和发送的内容存储到MongoDB中
    4. 使用短信模块发送短信
    """
    # 北京城市的code
    city_code = "101010100"
    weather_json = acquire_weather(city_code)
    send_result = send_content(weather_json)
    mongo_insert(weather,[weather_json])
    content = {"sms_content":send_result}
  
    mongo_insert(sms_history,[content])
    send_message(send_result,to_phone="15612856610")

if __name__ == '__main__':
    deal_message()
    