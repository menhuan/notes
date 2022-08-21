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
import traceback

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
    return driver


driver=get_driver()
url ="https://www.zhihu.com/xen/market/remix/paid_column/1539957599286255616?is_share_data=true"
driver.get(url=url)
time.sleep(3)
#需要做异常处理
isContinue = True
while isContinue:
    try:
        driver.find_element("xpath","//*[text()='查看更多章节']").click()
        sleep(3)
    except Exception as e:
        traceback.print_exc()
        isContinue=False
content = driver.find_elements("xpath",'//div[@class="ChapterItem-root-aNY7j CatalogModule-chapterCard-pWoQ1"]')
module = content[0].get_attribute("data-za-extra-module")
print(module)
