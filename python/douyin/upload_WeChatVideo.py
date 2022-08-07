
# 运行程序 python3 upload_WeChatVideo.py
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
book_name = '我爱你哦'

project_root = os.path.abspath(os.path.dirname(__file__))

driver_bin= os.path.join(project_root, 'chromedriver')

browser = webdriver.Chrome(executable_path = driver_bin)
browser.get('https://channels.weixin.qq.com/platform')
# browser.get('https://channels.weixin.qq.com/platform/post/create')
time.sleep(10)
# browser.maximize_window()  
# # weChatVideoCookies = 'weChatVideoCookies.json'

# # if(os.path.exists(weChatVideoCookies)):
# # 	print("存在")
# # 	with open(weChatVideoCookies) as f:
# # 		cookies = json.loads(f.read())
# # 		cookie = cookies[0]
# # 		print(cookie)

# # 		browser.add_cookie()
	

# # 	# for cookie in cookies:
# # 	# 	print(cookie)
# # 	# 	# 

# # else:
# # 	print("不存在")
# # with open(weChatVideoCookies, 'w') as f:
# #     f.write(json.dumps(cookies))



def uploadVideo(describe,path_mp4,sendTime):
	time.sleep(10)
	
	while True:
		time.sleep(1)
		try:
			element = browser.find_element("xpath","//button[text()='发表动态']")
			action = ActionChains(browser)
			action.move_to_element(element).click().perform()
			##https://blog.csdn.net/gufenchen/article/details/90274169

		except Exception as e:
			break;
		
	print("视频打开发布页面")
	# ##描述
	browser.find_element("xpath","//div[@data-placeholder='添加描述']").send_keys(describe)
	
	# ### 位置
	browser.find_element("xpath",'//div[@class="position-display-wrap"]').click()

	time.sleep(2)
	browser.find_element("xpath",'//div[text()="不显示位置"]').click()

	# ### 上传视频
	vidoe = browser.find_element("xpath",'//div[@class="ant-upload ant-upload-drag"]/span[@class="ant-upload ant-upload-btn"]/input')

	vidoe.send_keys(path_mp4)

	# ###等待视频上传完成

	video_clip = VideoFileClip(path_mp4)
	vidoe_duration = video_clip.duration
	print(vidoe_duration)
	print("视频还在上传中···")
	sleep_number = 0
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

	### 下面这个判断有问题了，先用睡眠这种方法来判断吧。
	# while True:
	# 	time.sleep(3)
	# 	try:
	# 		browser.find_element("xpath",'//*[text()="删除"]')
	# 		break;
	# 	except Exception as e:
	# 		print("视频已上传完成！")

	print("视频已上传完成！")

	### 时间
	### 发布时间格式，2022-07-22 23:31
	# browser.execute_script("document.getElementsByClassName('weui-desktop-form__input')[3].value = '2022-07-22 23:31'")
	# browser.execute_script("document.getElementsByClassName('weui-desktop-form__input')[3].value = '" + sendTime + "'")

	tempStr = sendTime.split(' ')
	day = tempStr[0].split('-')[2]
	hour = tempStr[1].split(':')[0]
	minute = tempStr[1].split(':')[1]
	print(day)
	print(hour)
	print(minute)
	browser.find_element("xpath","//input[@placeholder='请选择发表时间']").click()

	###选择日
	day_elm = browser.find_element("xpath","//table[@class='weui-desktop-picker__table']")
	days = day_elm.find_elements("tag name","td")
	### for 循环遍历 ，选择25号这一天
	for cell in days:
		if(cell.text == day):
			cell.click()
			break;

	browser.find_element("xpath","//input[@placeholder='请选择时间']").click()

	###选择小时
	hour_elm = browser.find_element("xpath","//ol[@class='weui-desktop-picker__time__panel weui-desktop-picker__time__hour']")
	hours = hour_elm.find_elements("tag name","li")
	### for 循环遍历 ，选择22点
	for cell in hours:
		if(cell.text == hour):
			cell.click()
			break;

	###分钟
	minute_elm = browser.find_element("xpath","//ol[@class='weui-desktop-picker__time__panel weui-desktop-picker__time__minute']")
	minutes = minute_elm.find_elements("tag name","li")
	### for 循环遍历 ，选择50分钟
	for cell in minutes:
		print(cell)
		if(cell.text == minute):
			cell.click()
			break;

	### 让日期控件消失
	action = ActionChains (browser)
	action.move_by_offset(50, 50).click().perform() #200，100是坐标



	# ###保存视频到草稿箱
	# browser.find_element("xpath","//button[text()='保存草稿']").click() 
	###发布视频
	browser.find_element("xpath","//*[text()='发表']").click()  
	time.sleep(5)
	print("视频发布完成！")

# describe = "我爱你"

# path_mp4 = "/Users/huoqiuliang/Documents/LearnPython/短视频相关/自动化上传视频/视频列表/01.mp4"

# send_time = '2022-07-23 22:31'

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


hours= ['07:00', '11:00','17:00']
now_time=datetime.datetime.now()
send_day_time = (now_time+datetime.timedelta(days=+index_next_day)).strftime("%Y-%m-%d")
print(send_day_time);

for index,path_mp4 in enumerate(video_list):
	describe = "书名《"  + book_name + "》#小说 #讲故事"
	time_date = send_day_time + ' ' + hours[index]
	print(describe)
	print(path_mp4)
	print(time)
	uploadVideo(describe,path_mp4,time_date)
	# uploadVideo(describe,path_mp4,'2022-07-23 23:31')


print("视频全部发布完成！")

playsound.playsound('发布完成的提示音/wc.mp3')

