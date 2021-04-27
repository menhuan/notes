import scrapy
from bs4 import BeautifulSoup
from ..items import TdItem


class IpScrapy(scrapy.Spider):
    name = "ip_scrapy_666"
    # 设置爬取的url 列表
    start_urls = ["http://www.66ip.cn"]

    # 继承自Spider,每抓取一个网页的资源信息，就会调用该方法
    def parse(self,response):
        results = response.xpath("/html/body//div[@align='center']/table/tr").extract()
        items = []
        for result in results:
            soup = BeautifulSoup(result,"lxml")
            trs = soup.html.body.contents
            for tr in trs:
                tr_result = tr.contents
                td = TdItem()
                td["ip_addr"] =tr_result[0].string
                td["port"] =tr_result[1].string
                td["province"] = tr_result[2].string 
                td["time"] = tr_result[4].string
                yield td



