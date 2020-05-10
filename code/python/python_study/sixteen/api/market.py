import json

from sixteen.constant import TICKERS_MARKETS, TICKERS_DETAIL, TICKERS_KLINE_DETAIL, NOTICE_COINS
from sixteen.model import Kline, MarketMerge
from sixteen.model.tickers import MarketTicker
from sixteen.models import dao_to_dict, session
from sixteen.models.Tables import CoinPrice
from sixteen.util.caches import get_key, set_key, add_list, add_list_default
from sixteen.util.request import HuoRequest

from flask import Blueprint, jsonify, current_app, request

api_root = Blueprint("api", __name__)


@api_root.route("/tickers")
def tickers():
    current_app.logger.debug("acquire tickers")
    result = []
    tickers = get_key(TICKERS_MARKETS)
    if tickers is None:
        tickers = HuoRequest(url='/market/tickers').get_data()
        if tickers is not None:
            set_key(TICKERS_MARKETS, tickers)

    for index, ticker in enumerate(tickers):
        market_ticker = MarketTicker()
        usdt_coin = market_ticker.acquire_market_tickers(ticker)
        if usdt_coin:
            result.append(market_ticker)

    return jsonify(result)


@api_root.route("/tickers/<symbol>", methods=["GET"])
def acquire_ticker_merged(symbol):
    current_app.logger.debug(f"acquire {symbol} detail merged ")
    symbol_key = TICKERS_DETAIL + symbol
    detail = get_key(symbol_key)
    if detail is None or len(detail) == 0:
        detail = HuoRequest(url=f'/market/detail/merged?symbol={symbol}').get_data()
        if tickers is not None:
            set_key(symbol_key, detail)
    market = MarketMerge()
    market.build_merge(detail['tick'])
    return jsonify(market)


@api_root.route("klines/<symbol>", methods=["GET"])
def acquire_kline(symbol):
    size = request.args.get("size")
    time = request.args.get("time")

    current_app.logger.debug(f"acquire {symbol} detail K line ")
    symbol_key = TICKERS_KLINE_DETAIL + symbol + str(size) + time
    klines = []
    klines = get_key(symbol_key)
    if klines is None or len(klines) == 0:
        klines = HuoRequest(url=f'/market/history/kline?symbol={symbol}&period={time}&size={size}').get_data()
        if tickers is not None:
            set_key(symbol_key, klines)
    result = [None] * len(klines)
    for index, lines in enumerate(klines):
        kline = Kline()
        kline.build_klines(lines)
        result[index] = kline

    return jsonify(result)


@api_root.route('/price/notice', methods=["POST", 'PUT'])
def add_notice_price():
    data = request.json
    coin = CoinPrice(coin=data.get("symbol"), price=data.get("price"), user_phone=data.get("user_phone"),
                     email_address=data.get("email_address"))
    session.add(coin)
    session.commit()
    add_list_default(NOTICE_COINS, json.dumps(dao_to_dict(coin)))
    return jsonify(dao_to_dict(coin))
