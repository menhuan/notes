import bs4 as bs4
import scrapy
from bs4 import BeautifulSoup
# /html/body/div[3]/div[1]/div/div[1]/div[4]/div[1]
# //*[@id="comments"]

# //*[@id="comments"]/div[1]

class CommentSpider(scrapy.Spider):
    # 设置名字
    name = "comments"
    # 设置爬取的路口
    start_urls= ["https://movie.douban.com/subject/26363254/comments?status=P"]


    # 数据解析
    def parse(self, response):
        comments = response.xpath('//*[@id="comments"]').extract()
        for comment in comments:
            soup=BeautifulSoup(comment,"lxml")
            soup.html.body
            print(comment)




