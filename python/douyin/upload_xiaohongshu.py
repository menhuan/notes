import os
import time
import json
import traceback
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains

from liulanqi import COOKING_PATH, get_driver, get_map4, get_publish_date


XIAOHONGSHU_COOKING = os.path.join(COOKING_PATH, "xiaohongshu.json")


def xiaohongshu_login(driver):
    if (os.path.exists(XIAOHONGSHU_COOKING)):
        print("cookies存在")
        with open(XIAOHONGSHU_COOKING) as f:
            cookies = json.loads(f.read())
            driver.get("https://creator.xiaohongshu.com/creator/post")
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
            driver.get("https://creator.xiaohongshu.com/publish/publish")
            time.sleep(10)
    else:
        print("cookies不存在")
        driver.get('https://creator.xiaohongshu.com/creator/post')
        # driver.find_element(
        #     "xpath", '//*[@placeholder="请输入手机号"]').send_keys("15612856610")
        # # driver.find_element(
        # #     "xpath", '//*[@placeholder="请输入密码"]').send_keys("")
        # driver.find_element("xpath", '//button[text()="登录"]').click()
        print("等待登录")
        time.sleep(30)
        print("登录完毕")
        cookies = driver.get_cookies()
        with open(XIAOHONGSHU_COOKING, 'w') as f:
            f.write(json.dumps(cookies))
        print(cookies)
        time.sleep(1)


def publish_xiaohongshu(driver, mp4, index):
    time.sleep(3)
    driver.find_element("xpath", '//*[text()="发布笔记"]').click()
    print("开始上传文件",mp4[0])
    time.sleep(3)
    # ### 上传视频
    vidoe = driver.find_element("xpath", '//input[@type="file"]')
    vidoe.send_keys(mp4[0])
    
    # 填写标题
    content = mp4[1].replace('.mp4','')
    driver.find_element(
        "xpath", '//*[@placeholder="填写标题，可能会有更多赞哦～"]').send_keys(content)

    time.sleep(1)
    # 填写描述
    driver.find_element(
        "xpath", '//*[@placeholder="填写更全面的描述信息，让更多的人看到你吧！"]').send_keys(content + "#虐文 #虐文推荐 #知乎小说 #知乎文 ")
    
    time.sleep(3)

    # 定时发布
    dingshi = driver.find_elements(
        "xpath", '//*[@class="css-1v54vzp"]')
    time.sleep(4)
    print("点击定时发布")
    dingshi[3].click()
    time.sleep(5)
    input_data = driver.find_element("xpath", '//*[@placeholder="请选择日期"]')
    input_data.send_keys(Keys.CONTROL,'a')     #全选
    #input_data.send_keys(Keys.DELETE)
    input_data.send_keys(get_publish_date(content,index))    
    time.sleep(3)
    #driver.find_element("xpath", '//*[text()="确定"]').click()

    #等待视频上传完成
    while True:
        time.sleep(10)
        try:
            driver.find_element("xpath",'//*[@id="publish-container"]/div/div[2]/div[2]/div[6]/div/div/div[1]//*[contains(text(),"重新上传")]')
            break;
        except Exception as e:
            traceback.print_exc()
            print("视频还在上传中···")
    
    print("视频已上传完成！")
    time.sleep(3)
    # 发布
    driver.find_element("xpath", '//*[text()="发布"]').click()
    print("视频发布完成！")
    time.sleep(10)

def run(driver):
    xiaohongshu_login(driver=driver)
    mp4s = get_map4()
    for index, mp4 in enumerate(mp4s):
        publish_xiaohongshu(driver, mp4, index)
        time.sleep(10)

if __name__ == "__main__":
    try:
        driver = get_driver()
        xiaohongshu_login(driver=driver)
        mp4s = get_map4()
        for index, mp4 in enumerate(mp4s):
            publish_xiaohongshu(driver, mp4, index)
            time.sleep(10)
    finally:
        driver.quit()
