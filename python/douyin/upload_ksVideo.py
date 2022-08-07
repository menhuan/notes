
# 运行程序 python3 upload_ksVideo.py
#!/usr/bin/python3

import os
import time
import json
import datetime
import playsound
from moviepy.editor import *
from selenium import webdriver 
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains

index_next_day = 1;## 当天是0，第二天是1，以此往后类推

project_root = os.path.abspath(os.path.dirname(__file__))

driver_bin= os.path.join(project_root, 'chromedriver')

browser = webdriver.Chrome(executable_path = driver_bin)
browser.get('https://passport.kuaishou.com/pc/account/login/?sid=kuaishou.web.cp.api&callback=https%3A%2F%2Fcp.kuaishou.com%2Frest%2Finfra%2Fsts%3FfollowUrl%3Dhttps%253A%252F%252Fcp.kuaishou.com%252Farticle%252Fpublish%252Fvideo%26setRootDomain%3Dtrue')

browser.find_element("xpath",'//*[@placeholder="请输入手机号"]').send_keys("手机")
browser.find_element("xpath",'//*[@placeholder="请输入密码"]').send_keys("密码")
browser.find_element("xpath",'//button[text()="登录"]').click()	

time.sleep(5)

def uploadVideo(describe,path_mp4,sendTime):
	time.sleep(3)
	browser.find_element("xpath",'//span[text()=" 发布视频 "]').click()	
	time.sleep(1)
	# ### 上传视频
	vidoe = browser.find_element("xpath",'//input[@type="file"]')
	vidoe.send_keys(path_mp4)
	# ###等待视频上传完成
	video_clip = VideoFileClip(path_mp4)
	vidoe_duration = video_clip.duration
	print(vidoe_duration)
	print("视频还在上传中···")
	# sleep_number = 7
	if(vidoe_duration < 60):
		sleep_number = 20
	elif (vidoe_duration < 120):
		sleep_number = 40
	elif (vidoe_duration < 180):
		sleep_number = 60
	elif (vidoe_duration < 240):
		sleep_number = 80
	else:
		sleep_number = 100

	time.sleep(sleep_number)

	print("视频上传完成!!!")

	### 填写描述
	browser.find_element("xpath",'//*[@placeholder="添加合适的话题和描述，作品能获得更多推荐～"]').send_keys(describe)

	time.sleep(2)
	### 定时发布
	browser.find_element("xpath",'//span[text()="定时发布"]').click()
	browser.find_element("xpath","//input[@placeholder='选择日期时间']").click()


	tempStr = sendTime.split(' ')
	day = tempStr[0].split('-')[2]
	hour = tempStr[1].split(':')[0]
	###选择日
	day_elm = browser.find_element("xpath","//table[@class='ant-picker-content']")
	days = day_elm.find_elements("tag name","td")

	### for 循环遍历 ，选择25号这一天
	for cell in days:
		if(cell.text == day):
			cell.click()
			break;
	###选择小时
	hour_elm = browser.find_element("xpath","//ul[@class='ant-picker-time-panel-column']")
	hours = hour_elm.find_elements("tag name","li")
	int_hour = int(hour)
	if int_hour <= 7:
		cell = hours[int_hour]
		cell.click()
	elif int_hour <= 14:
		hours[7].click()
		hours = hour_elm.find_elements("tag name","li")
		cell = hours[int_hour]
		cell.click()
	elif int_hour <= 21:
		hours[7].click()
		hours = hour_elm.find_elements("tag name","li")
		hours[13].click()
		hours = hour_elm.find_elements("tag name","li")
		cell = hours[int_hour]
		cell.click()
	else:
		hours[7].click()
		hours = hour_elm.find_elements("tag name","li")
		hours[13].click()
		hours = hour_elm.find_elements("tag name","li")
		hours[20].click()
		hours = hour_elm.find_elements("tag name","li")
		cell = hours[int_hour]
		cell.click()
		print(cell.text)

	browser.find_element("xpath",'//span[text()="确定"]').click()	
	time.sleep(1)
	####发布
	browser.find_element("xpath",'//span[text()="发布"]').click()	
	time.sleep(1)
	browser.find_element("xpath",'//span[text()="确认发布"]').click()	
	time.sleep(1)
	print("视频发布完成！")

# describe = "我爱你"

# path_mp4 = "/Users/huoqiuliang/Documents/LearnPython/短视频相关/自动化上传视频/视频列表/01.mp4"

# send_time = '2022-07-24 22'

# uploadVideo(describe,path_mp4,send_time)

####获取视频列表文件夹
dst_path = os.getcwd() + "/视频列表"
f_list = os.listdir(dst_path)
f_list_count = len(f_list);

video_list = []          ## 空列表
for index,val in enumerate(f_list):
        # os.path.splitext():分离文件名与扩展名
        if ((os.path.splitext(val)[1] == '.MP4' or os.path.splitext(val)[1] == '.mp4')):
                video_path = dst_path + '/' + val
                video_list.append(video_path);

video_list.sort()

hours= ['07', '11','17']
now_time=datetime.datetime.now()
send_day_time = (now_time+datetime.timedelta(days=+index_next_day)).strftime("%Y-%m-%d")
for index,path_mp4 in enumerate(video_list):
	# describe = "小说推荐讲故事" 
	describe = "#小说 #讲故事 " 
	time_date = send_day_time + ' ' + hours[index]
	print(describe)
	print(path_mp4)
	print(time)
	uploadVideo(describe,path_mp4,time_date)

print("视频全部发布完成！")

playsound.playsound('发布完成的提示音/ks.mp3')

