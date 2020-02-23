# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class DoubanItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    pass

class CommentItem(scrapy.Item):
    #用户昵称
    user_name = scrapy.Field()
    # 评论时间
    comment_data = scrapy.Field()
    # score分数 中文存储
    comment_score = scrapy.Field()
    # score 数字num
    comment_score_num = scrapy.Field()
    # vote 投票数量
    comment_vote = scrapy.Field()
    # vote 投票中文标准
    comment_vote_text = scrapy.Field()
    # 用户评论内容
    comment = scrapy.Field()