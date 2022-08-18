import os
import time
import json
import traceback
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains

from liulanqi import COOKING_PATH, get_driver, get_map4, get_publish_date


KUAISHOU_COOKING = os.path.join(COOKING_PATH, "kuaishou.json")


def kuaishou_login(driver):
    if (os.path.exists(KUAISHOU_COOKING)):
        print("cookies存在")
        with open(KUAISHOU_COOKING) as f:
            cookies = json.loads(f.read())
            driver.get('https://cp.kuaishou.com/article/publish/video')
            driver.implicitly_wait(10)
            driver.delete_all_cookies()
            time.sleep(8)
            # 遍历cook
            print("加载cookie")
            for cookie in cookies:
                if 'expiry' in cookie:
                    del cookie["expiry"]
                # 添加cook
                driver.add_cookie(cookie)
            time.sleep(5)
            # 刷新
            print("开始刷新")
            driver.refresh()

            driver.refresh()
            driver.get('https://cp.kuaishou.com/article/publish/video')
            time.sleep(10)
    else:
        print("cookies不存在")
        driver.get('https://passport.kuaishou.com/pc/account/login/?sid=kuaishou.web.cp.api&callback=https%3A%2F%2Fcp.kuaishou.com%2Frest%2Finfra%2Fsts%3FfollowUrl%3Dhttps%253A%252F%252Fcp.kuaishou.com%252Farticle%252Fpublish%252Fvideo%26setRootDomain%3Dtrue')
        driver.find_element(
            "xpath", '//*[@placeholder="请输入手机号"]').send_keys("15612856610")
        # driver.find_element(
        #     "xpath", '//*[@placeholder="请输入密码"]').send_keys("")
        driver.find_element("xpath", '//button[text()="登录"]').click()
        time.sleep(10)
        cookies = driver.get_cookies()
        with open(KUAISHOU_COOKING, 'w') as f:
            f.write(json.dumps(cookies))
        print(cookies)
        time.sleep(1)


def publish_kuaishou(driver, mp4, index):
    time.sleep(3)
    driver.find_element("xpath", '//*[@id="app"]/div[1]/div[1]/div/section/ul/div/span/span').click()
    print("开始上传文件")
    time.sleep(3)
    # ### 上传视频
    vidoe = driver.find_element("xpath", '//input[@type="file"]')
    vidoe.send_keys(mp4[0])
    
    # 填写描述
    content = mp4[1].replace('.mp4','')
    driver.find_element(
        "xpath", '//*[@placeholder="添加合适的话题和描述，作品能获得更多推荐～"]').send_keys(content + " #虐心小说 #虐文 #小说推荐 ")

    time.sleep(1)
    # 定时发布
    driver.find_element("xpath", '//span[text()="定时发布"]').click()
    time.sleep(3)
    input_data=driver.find_element("xpath", "//input[@placeholder='选择日期时间']")
    input_data.send_keys(Keys.CONTROL,'a')     #全选
    input_data.send_keys(get_publish_date(mp4[1],index)) 
    time.sleep(2)
    tempStr = get_publish_date(mp4[1],index).split(' ')
    day = tempStr[0].split('-')[2]
    hour = tempStr[1].split(':')[0]
    time.sleep(1)
     # 选择日
    day_elm = driver.find_element(
          "xpath", "//table[@class='ant-picker-content']")
    days = day_elm.find_elements("tag name", "td")

    # ### for 循环遍历 ，选择25号这一天
    for cell in days:
        # 可能会有上个月的和下个月的日期，不能点击，所以需要过滤一下
        val = cell.get_attribute("class")
        cell_day = ''
        if len(cell.text) == 1:
            cell_day = '0' + cell.text
        else:
            cell_day = cell.text

        if ((cell_day == day) & (val == 'ant-picker-cell ant-picker-cell-in-view')):
            print("clickclick_day:",day)
            cell.click()
            break
        time.sleep(1)
        hour_elm = driver.find_element("xpath","//ul[@class='ant-picker-time-panel-column']")
        hours = hour_elm.find_elements("tag name","li")
        int_hour = int(hour)
        if int_hour <= 8:
            cell = hours[int_hour]
            cell.click()
        elif int_hour <= 12:
            hours[8].click()
            hours = hour_elm.find_elements("tag name","li")
            cell = hours[int_hour]
            cell.click()
        elif int_hour <= 18:
            hours[8].click()
            hours = hour_elm.find_elements("tag name","li")
            hours[12].click()
            hours = hour_elm.find_elements("tag name","li")
            cell = hours[int_hour]
            cell.click()
        else:
            hours[8].click()
            hours = hour_elm.find_elements("tag name","li")
            hours[12].click()
            hours = hour_elm.find_elements("tag name","li")
            hours[18].click()
            hours = hour_elm.find_elements("tag name","li")
            cell = hours[int_hour]
            cell.click()
            print("小时",cell.text)
    #等待视频上传完成
    while True:
        time.sleep(3)
        try:
            driver.find_element("xpath",'//*[text()="上传成功"]')
            break;
        except Exception as e:
            traceback.print_exc()
            print("视频还在上传中···")
    
    print("视频已上传完成！")
    time.sleep(3)
    
    driver.find_element("xpath", '//*[text()="确定"]').click()
    time.sleep(1)
    # 发布
    driver.find_element("xpath", '//*[text()="发布"]').click()
    time.sleep(1)
    driver.find_element("xpath", '//*[text()="确认发布"]').click()
    time.sleep(1)
    print("视频发布完成！")

if __name__ == "__main__":
    try:
        driver = get_driver()
        kuaishou_login(driver=driver)
        mp4s = get_map4()
        for index, mp4 in enumerate(mp4s):
            publish_kuaishou(driver, mp4, index)
            time.sleep(10)
    finally:
        driver.quit()
