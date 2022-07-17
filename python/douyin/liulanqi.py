
from selenium import webdriver


from selenium import webdriver
from time import sleep
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities

from selenium import webdriver

from selenium.webdriver.common.desired_capabilities import DesiredCapabilities

from selenium import webdriver

from selenium import webdriver

host = "http://101.43.210.78:50000";
capabilities = DesiredCapabilities.CHROME;
driver = webdriver.Remote(host, capabilities)
driver.get("file:///Users/ruiqi/Downloads/wx-duihua-main/default.html")
html = driver.page_source
print(html)
driver.quit()