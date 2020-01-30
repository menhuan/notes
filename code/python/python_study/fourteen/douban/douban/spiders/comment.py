
import random
from time import sleep

import bs4 as bs4
import scrapy
from bs4 import BeautifulSoup


# /html/body/div[3]/div[1]/div/div[1]/div[4]/div[1]
# //*[@id="comments"]

# //*[@id="comments"]/div[1]
from fourteen.douban.douban.items import CommentItem


class CommentSpider(scrapy.Spider):
    # 设置名字
    name = "comments"
    # 设置爬取的路口
    start_urls = ["https://movie.douban.com/subject/26363254/comments?status=P"]

    # 数据解析
    def parse(self, response):

        comments = response.xpath('//*[@id="comments"]').extract()
        for commnets_item in comments:
            soup = BeautifulSoup(commnets_item, "lxml")
            comment_div = soup.find_all("div", attrs={"class": "comment"})
            for comment in comment_div:
                # 获取评论相关数据
                try:
                    # 评论内容
                    comment_content = comment.find(name="span", attrs={"class": "short"}).string
                    item = CommentItem()
                    item["comment"] = comment_content

                    span_comment = comment.find(name="span",attrs={"class":"comment-info"})
                    item["user_name"]=span_comment.a.string

                    spans = span_comment.find_all(name="span")
                    evaluate = spans[1].get("title")
                    content = spans[1].get("class")
                    evaluate_num = int(spans[1].get("class")[0][7:9])
                    data = next(spans[2].stripped_strings)
                    item["comment_data"] = data
                    item["comment_score_num"] = evaluate_num
                    item["comment_score"] = evaluate
                    # 获取评论想过投票
                    span_vote = comment.find(name="span",attrs = {"class":"comment-vote"})
                    vote_value = span_vote.span.string
                    vote_content = span_vote.a.text
                    item["comment_vote"] = vote_value
                    item["comment_vote_text"] = vote_content

                    yield item
                except Exception as e:
                    print(e)
        next_page_args = response.xpath('//*[@id="paginator"]/a[last()]/@href').extract()

        if next_page_args is not None and len(next_page_args)>0:
            next_page_arg = next_page_args[0].rsplit("&",1)[0]
            next_page = f"https://movie.douban.com/subject/26363254/comments{next_page_arg}"
            print(next_page)
            slep = random.randint(3,8)
            sleep(slep)
            yield scrapy.Request(next_page,callback=self.parse)
