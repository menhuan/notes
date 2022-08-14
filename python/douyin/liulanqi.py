
from selenium import webdriver

from time import sleep

from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
import selenium
from selenium import webdriver
import pathlib
import time
from selenium.webdriver.common.keys import Keys

def get_driver():
    host = "http://101.43.210.78:50000";
    capabilities = DesiredCapabilities.CHROME;
    driver = webdriver.Remote(host, capabilities)
    # driver.get("file:///Users/ruiqi/Downloads/wx-duihua-main/default.html")
    # html = driver.page_source
    # print(html)
    # driver.quit()
    return driver



# 基本信息
# 视频存放路径
catalog_mp4 = "/workspaces/notes/python/douyin/output/douyin/output"
# 视频描述
describe = "裸眼3D看蜘蛛侠 #搞笑 #电影 #视觉震撼"
time.sleep(10)
options = webdriver.ChromeOptions()
options.add_experimental_option("debuggerAddress", "127.0.0.1:5003")
driver = webdriver.Chrome(options = options)

path = pathlib.Path(catalog_mp4)

# 视频地址获取
path_mp4 = ""
for i in path.iterdir():
    if(".mp4" in str(i)):
        path_mp4 = str(i);
        break;

if(path_mp4 != ""):
    print("检查到视频路径：" + path_mp4)
else:
    print("未检查到视频路径，程序终止！")
    exit()

# 封面地址获取
path_cover = ""
for i in path.iterdir():
    if(".png" in str(i) or ".jpg" in str(i)):
        path_cover = str(i);
        break;

if(path_cover != ""):
    print("检查到封面路径：" + path_cover)
else:
    print("未检查到封面路径，程序终止！")
    exit()
    
def publish_douyin(driver):
    '''
     作用：发布抖音视频
    '''
    
    # 进入创作者页面，并上传视频
    driver.get("https://creator.douyin.com/creator-micro/home")
    time.sleep(2)
    driver.find_element_by_xpath('//*[text()="发布视频"]').click()
    time.sleep(2)
    driver.find_element_by_xpath('//input[@type="file"]').send_keys(path_mp4)
    
    # 等待视频上传完成
    while True:
        time.sleep(3)
        try:
            driver.find_element_by_xpath('//*[text()="重新上传"]')
            break;
        except Exception as e:
            print("视频还在上传中···")
    
    print("视频已上传完成！")
    
    # 添加封面
    driver.find_element_by_xpath('//*[text()="编辑封面"]').click()
    time.sleep(1)
    driver.find_element_by_xpath('//div[text()="上传封面"]').click()
    time.sleep(1)
    driver.find_element_by_xpath('//input[@type="file"]').send_keys(path_cover)
    time.sleep(3)
    driver.find_element_by_xpath('//*[text()="裁剪封面"]/..//*[text()="确定"]').click()
    time.sleep(3)
    driver.find_element_by_xpath('//*[text()="设置封面"]/..//*[contains(@class,"upload")]//*[text()="确定"]').click()
    
    time.sleep(5)
    # 输入视频描述
    driver.find_element_by_xpath('//div[@aria-autocomplete="list"]//br').send_keys(describe + " #上热门 #dou上热门 #我要上热门")
    
    # 设置选项
    time.sleep(1)
    driver.find_element_by_xpath('//*[@class="radio--4Gpx6"]').click()
    time.sleep(1)
    driver.find_element_by_xpath('//*[@class="semi-select-selection"]//span[contains(text(),"输入")]').click()
    time.sleep(1)
    driver.find_element_by_xpath('//*[@class="semi-select-selection"]//input').send_keys("中关村人工智能科技")
    time.sleep(1)
    driver.find_element_by_xpath('//*[@class="semi-select-selection"]//input').send_keys("园")
    time.sleep(5)
    driver.find_element_by_xpath('//*[@class="semi-popover-content"]//*[text()="中关村人工智能科技园"]').click()
    
    # 同步到西瓜视频
    time.sleep(1)
    # driver.find_element_by_xpath('//div[@class="preview--27Xrt"]//input').click()   # 默认启用一次后，后面默认启用了。
    time.sleep(1)
    driver.find_element_by_xpath('//*[@class="card-pen--2P8rh"]').click()
    time.sleep(1)
    driver.find_element_by_xpath('//*[@class="DraftEditor-root"]//br').send_keys(describe + " #上热门")
    time.sleep(1)
    driver.find_element_by_xpath('//button[text()="确定"]').click()
    
    # 人工进行检查并发布
    # time.sleep(3)
    # # 点击发布
    # driver.find_element_by_xpath('//button[text()="发布"]').click()

# 开始执行视频发布
publish_douyin()