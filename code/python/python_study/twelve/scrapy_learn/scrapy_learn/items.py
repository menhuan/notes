# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class ScrapyLearnItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    pass

class TdItem(scrapy.Item):
    """
    定义td item
    """
    ip_addr =scrapy.Field()
    port = scrapy.Field()
    province = scrapy.Field()
    time = scrapy.Field()