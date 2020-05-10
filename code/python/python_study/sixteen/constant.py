"""
常量
"""

HUOBI_API_KEY = "fa9b3d24-dbye2sf5t7-fbca62e9-a2e03"
HUOBI_SECRET_KEY = "a6ca0c7c-552e7406-318b4f1c-ebf5a"

## API连接
BASE_REST_API_URL = "https://api.huobi.pro"
BASE_WSF_API_URL = "wss://api.testnet.huobi.pro/ws/v1"

## 交易单元
USDT = "usdt"
## 交易API

## 所有的交易对
ALL_tickers = "/market/tickers"
MARKET_DETAIL_MERGED = "/market/detail/merged"


## REDIS_KEYS

TICKERS_MARKETS ="tickers_markets"
TICKERS_DETAIL ="tickers_detail"
TICKERS_KLINE_DETAIL ="tickers_kline_detail"
EMAIL_LIST ="email_list"
# 监听数字货币队列监听
NOTICE_COINS="notice_coins"