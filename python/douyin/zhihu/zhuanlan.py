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
agent ='Mozilla/5.0 (Macintosh; Linux) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36'

def get_driver():
    chrome_options = webdriver.ChromeOptions()
    chrome_options.add_argument('--no-sandbox')  # 解决DevToolsActivePort文件不存在的报错
    chrome_options.add_argument('--disable-dev-shm-usage')
    chrome_options.add_argument('--disable-web-security	')
    chrome_options.add_experimental_option('excludeSwitches', ['enable-automation'])
    chrome_options.add_argument(f'user-agent={agent}')
    chrome_options.add_argument('sec-ch-ua-platform=macOS')
    chrome_options.add_argument('sec-fetch-site=same-site')
    chrome_options.add_argument('x-zse-83=101_3_3.0')
    chrome_options.add_argument('x-zse-96=2.0_AIdQpBm84Gp=4bZqDFqWs9fmMq+q0zUl0=dkQaHvRb4Jc9JwpALhpHClWGw5sflS')

   # chrome_options.add_argument('--headless')
   # chrome_options.add_argument('--disable-gpu')  # 谷歌文档提到需要加上这个属性来规避bug
    driver = webdriver.Remote(
        command_executor='http://101.43.210.78:50000',
        desired_capabilities=chrome_options.to_capabilities()
    )
    return driver

driver=get_driver()

try:
    url ="https://www.zhihu.com/xen/market/remix/paid_column/1467897508667637760?is_share_data=true"
    driver.get(url=url)
    time.sleep(3)
    #需要做异常处理
    isContinue = True
    js = "document.documentElement.scrollTop=1000"

    driver.execute_script(js)
    driver.add_cookie({"Hm_lpvt_98beee57fd2ef70ccdd5ca52b9740c49":"1661069242","NOT_UNREGISTER_WAITING":1})

    time.sleep(100)
    while isContinue:
        try:
            time.sleep(5)
            driver.find_element("xpath","//*[contains(text(),'查看更多章节')]").click()
            sleep(3)
        except Exception as e:
            traceback.print_exc()
            isContinue=False
    content = driver.find_elements("xpath",'//div[@class="ChapterItem-root-aNY7j CatalogModule-chapterCard-pWoQ1"]')
    module = content[0].get_attribute("data-za-extra-module")
    print(module)
finally:

    sleep(20)
    driver.quit()
