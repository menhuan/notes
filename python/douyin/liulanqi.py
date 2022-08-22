
import datetime
from operator import index
import traceback
from selenium import webdriver

from time import sleep

from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
import selenium
from selenium import webdriver
import pathlib
import time
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.options import Options
import json
import os

ROOT_PATH = os.getenv(
    "ROOT_PATH", "/workspaces/notes/python/douyin/output/douyin")
VIDEO_PATH = os.path.join(ROOT_PATH, "output")
COOKING_PATH = os.path.join(ROOT_PATH, "cooking")
COOKING_TXT = os.path.join(COOKING_PATH, "douyin.txt")


def get_driver():
    chrome_options = webdriver.ChromeOptions()
    chrome_options.add_argument('--no-sandbox')  # 解决DevToolsActivePort文件不存在的报错
    chrome_options.add_argument('--disable-dev-shm-usage')
   # chrome_options.add_argument('--headless')
   # chrome_options.add_argument('--disable-gpu')  # 谷歌文档提到需要加上这个属性来规避bug
    driver = webdriver.Remote(
        command_executor='http://101.43.210.78:50000',
        desired_capabilities=chrome_options.to_capabilities()
    )
    print("链接上")
    return driver


def wait_login(driver):
    driver.get("https://creator.douyin.com/")
    time.sleep(2)
    driver.find_element("xpath", '//*[text()="登录"]').click()
    time.sleep(2)
    driver.find_element("xpath", '//*[text()="确认"]').click()
    time.sleep(2)
    # 手机登录
    driver.find_element(
        "xpath", '//*[text()="手机号登录"]').click()
    time.sleep(10)
    driver.find_element(
        "xpath", '//*[@id="dialog-0"]/div/div[2]/div/div[2]/div[2]/div/form/div[3]/span').click()
    # driver.find_element("xpath",'//input[@type="file"]').send_keys(path_mp4)
    driver.find_element(
        "xpath", '//*[@placeholder="请输入手机号"]').send_keys("15612856610")
    time.sleep(3)
    # driver.find_element(
    #     "xpath", '//*[@placeholder="请输入密码"]').send_keys("")
    time.sleep(3)
    driver.find_element(
        "xpath", '//*[@id="dialog-0"]/div/div[2]/div/div[2]/div[2]/div/form/div[4]/img').click()
    time.sleep(3)
    driver.find_element(
        "xpath", '//*[@id="dialog-0"]/div/div[2]/div/div[2]/div[2]/div/form/button').click()
    print("等待登录")
    # 延迟一会，此时你需要登录,60秒应该是够操作了
    time.sleep(60)
    # 读取cook
    cook = driver.get_cookies()
    # 保存cook，我是写到txt文件的，后期可以写成http的，收集大量的，然后就可以*****（你懂的）***
    with open(COOKING_TXT, 'w') as f:
        f.write(json.dumps(cook, ensure_ascii=True))  # 格式转化，这不管你是学的那种语言都必须要知道的


def login(driver):
    if os.path.exists(COOKING_TXT):
        get_cookie(driver)
    else:
        try:
            wait_login(driver)
        except Exception as e:
            traceback.print_exc()
            wait_login(driver)


def get_map4():

    # 基本信息
    # 视频存放路径
    mp4_result = []
    catalog_mp4 = VIDEO_PATH
    # 视频描述
    path = pathlib.Path(catalog_mp4)

    # 视频地址获取
    for path_mp4 in path.iterdir():
        if(".mp4" in str(path_mp4)):
            map4_path = str(path_mp4)
            mp4_result.append((map4_path, path_mp4.name))

    if(len(mp4_result) > 0):
        print("检查到视频路径：", mp4_result)
    else:
        print("未检查到视频路径，程序终止！")
        exit()
    return mp4_result

    # 封面地址获取
    # path_cover = ""
    # for i in path.iterdir():
    #     if(".png" in str(i) or ".jpg" in str(i)):
    #         path_cover = str(i);
    #         break;

    # if(path_cover != ""):
    #     print("检查到封面路径：" + path_cover)
    # else:
    #     print("未检查到封面路径，程序终止！")
    #     exit()


def get_cookie(driver):
    with open(COOKING_TXT) as f:
        data = json.loads(f.read())
    # 打开浏览器
    time.sleep(2)
    # 打开网址
    driver.get("https://creator.douyin.com/creator-micro/home")
    driver.implicitly_wait(10)
    # 清楚cook
    driver.delete_all_cookies()
    time.sleep(8)
    # 遍历cook
    print("加载cookie")
    for cookie in data:
        if 'expiry' in cookie:
            del cookie["expiry"]
        # 添加cook
        driver.add_cookie(cookie)
    time.sleep(5)
    # 刷新
    print("开始刷新")
    driver.refresh()


def get_publish_date(title, index):
    # 代表的是 加一天时间
    time_long = int(index/3) * 24
    now = datetime.datetime.today()
    tomorrowemp = now + datetime.timedelta(hours=time_long)
    print("title:", title)
    # 暂时注释掉+ datetime.timedelta(hours = 24)
    if title.find("(1)") > 0 or title.find("(4)") > 0 or title.find("(7)") > 0:
        tomorrow = tomorrowemp.replace(hour=9, minute=0, second=0)
    elif title.find("(2)") > 0 or title.find("(5)") > 0:
        tomorrow = tomorrowemp.replace(hour=12, minute=0, second=0)
    elif title.find("(3)") > 0 or title.find("(6)") > 0:
        tomorrow = tomorrowemp.replace(hour=18, minute=0, second=0)
    print("输出的时间是:", tomorrow.strftime("%Y-%m-%d %H:%M"))
    return tomorrow.strftime("%Y-%m-%d %H:%M")


def publish_douyin(driver, mp4, index):
    '''
     作用：发布抖音视频
    '''

    # 进入创作者页面，并上传视频
    time.sleep(2)
    print("开始点击体验")
    try:
        driver.find_element("xpath", '//*[text()="开始体验"]').click()
        time.sleep(3)
        driver.find_element("xpath", '//*[text()="下一步"]').click()
        time.sleep(3)
        driver.find_element("xpath", '//*[text()="完成"]').click()
        time.sleep(3)
    except Exception as e:
        traceback.print_exc()
    print("开始点击发布视频")
    driver.find_element("xpath", '//*[text()="发布视频"]').click()
    time.sleep(5)
    print("加载视频", mp4[1])
    driver.find_element("xpath", '//input[@type="file"]').send_keys(mp4[0])
    time.sleep(3)
    print("开始输入描述")
    # 添加封面
    # driver.find_element("xpath", '//*[text()="编辑封面"]').click()
    # time.sleep(1)
    # driver.find_element("xpath", '//div[text()="上传封面"]').click()
    # time.sleep(1)
    # driver.find_element("xpath",'//input[@type="file"]').send_keys(path_cover)
    # time.sleep(3)
    # driver.find_element(
    #     "xpath", '//*[text()="裁剪封面"]/..//*[text()="确定"]').click()
    # time.sleep(3)
    # driver.find_element(
    #     "xpath", '//*[text()="设置封面"]/..//*[contains(@class,"upload")]//*[text()="确定"]').click()

    time.sleep(5)
    # 输入视频描述
    input_text = driver.find_element(
        "xpath", '//div[@aria-autocomplete="list"]')

    title = mp4[1].replace(".mp4", "")

    input_text.send_keys(title + " #虐心小说 ")
    input_text.send_keys(" #推文日常 ")
    input_text.send_keys(" #虐妻一时爽追妻火葬场 ")

    # 设置选项
    time.sleep(1)
    driver.find_element("xpath", '//*[@class="radio--4Gpx6"]').click()
    time.sleep(1)
    driver.find_element(
        "xpath", '//*[@class="semi-select-selection"]//span[contains(text(),"输入")]').click()
    time.sleep(1)
    driver.find_element(
        "xpath", '//*[@class="semi-select-selection"]//input').send_keys("小南庄25号")
    time.sleep(1)
    driver.find_element(
        "xpath", '//*[@class="semi-select-selection"]//input').send_keys("院")
    time.sleep(10)
    driver.find_element(
        "xpath", '//*[@class="detail--2prVy"]').click()

    # 同步到西瓜视频
    # time.sleep(1)
    # # driver.find_element("xpath",'//div[@class="preview--27Xrt"]//input').click()   # 默认启用一次后，后面默认启用了。
    # time.sleep(1)
    # driver.find_element("xpath", '//*[@class="card-pen--2P8rh"]').click()
    # time.sleep(1)
    # driver.find_element(
    #     "xpath", '//*[@class="DraftEditor-root"]//br').send_keys("测试下" + " #上热门")
    # time.sleep(1)
    # driver.find_element("xpath", '//button[text()="确定"]').click()

    # 定时发布radio--4Gpx6 one-line--2rHu9
    print("定时发布")
    time.sleep(5)
    dingshi = driver.find_elements(
        "xpath", '//*[@class="radio--4Gpx6 one-line--2rHu9"]')
    time.sleep(4)
    print("点击定时发布")
    dingshi[1].click()
    #driver.find_elements("xpath", '//*[@class="radio--4Gpx6 one-line--2rHu9"]')[1].click()
    time.sleep(3)
    input_data = driver.find_element("xpath", '//*[@placeholder="日期和时间"]')
    input_data.send_keys(Keys.CONTROL, 'a')  # 全选
    # input_data.send_keys(Keys.DELETE)

    input_data.send_keys(get_publish_date(title, index))
    # driver.find_element(
    #     "xpath", '//*[@placeholder="日期和时间"]').send_keys("2022-08-16 22:00")

    # 等待视频上传完成,放到最后,这一步是最慢的.
    while True:
        time.sleep(3)
        try:
            driver.find_element("xpath", '//*[text()="重新上传"]')
            break
        except Exception as e:
            print("视频还在上传中···")

    print("视频已上传完成！")
    # 点击发布
    driver.find_element("xpath", '//button[text()="发布"]').click()
    time.sleep(3)
    try:
        driver.find_element("xpath", '//*[text()="暂不同步"]').click()
    except Exception as e:
        traceback.print_exc()
    print("上传结束")
    time.sleep(10)

# 开始执行视频发布
# publish_douyin(driver=driver)


if __name__ == "__main__":
    try:
        driver = get_driver()
        login(driver=driver)
        mp4s = get_map4()
        for index, mp4 in enumerate(mp4s):
            publish_douyin(driver, mp4, index)
            time.sleep(10)
    finally:
        driver.quit()
