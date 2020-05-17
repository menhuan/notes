import json
from time import sleep, time

from sixteen import logger
from sixteen.constant import NOTICE_COINS
from sixteen.models import session, dao_to_dict
from sixteen.models.Tables import CoinPrice, CoinPriceHistory
from sixteen.util.caches import put_zset_pip, get_list, get_queue_message, add_list, add_list_default, \
    clear_cache_by_key
from sixteen.util.notice import send_sms, send_notice
from sixteen.util.request import HuoRequest


def update_price():
    while (True):
        start = time()
        try:
            logger.info("start acquire tickers")
            tickers = HuoRequest(url='/market/tickers').get_data()
            logger.info(f"start acquire tickers,tickers is {tickers}")

            if tickers is not None:
                results = [tickers[i:i + 50] for i in range(0, len(tickers), 50)]
                for result in results:
                    cache_result = []
                    for value in result:
                        cache_result.append({value['close']: value})
                    put_zset_pip('tickers', result)
            logger.info("success acquire data")
        except Exception as e:
            logger.exception("batch update tickers error", e)
        finally:
            # 睡5秒再访问
            sleep_time = time() - start
            if 0 < sleep_time < 10:
                sleep(10 - sleep_time)


def watch_price():
    """
    监听价格的变化，达到设定的价格值之后，发送短信与邮件通知
    :return:
    """
    # 每次服务启动时初始化下
    logger.info("__init__ notice_coins")
    clear_cache_by_key(NOTICE_COINS)
    coins = session.query(CoinPrice).all()
    for coin in coins:
        add_list_default(NOTICE_COINS, json.dumps(dao_to_dict(coin)))

    while True:
        try:
            logger.info("start acquire notice")
            notice_coin = get_queue_message(NOTICE_COINS)
            logger.info(f"start acquire notice,tickers is {notice_coin}")
            data = HuoRequest(url=f'/market/detail/merged?symbol={notice_coin.get("coin")}').get_data()
            close = data.get("close")
            open = data.get("open")
            messages = [notice_coin.get('coin'), str(notice_coin.get('price')), str(data.get('close'))]
            if close - open >= 0 and close >= notice_coin.get('price'):
                send_notice(messages, notice_coin.get('user_phone'), notice_coin.get('email_address'))
            elif close - open < 0 and close <= notice_coin.get('price'):
                send_notice(messages, notice_coin.get('user_phone'), notice_coin.get('email_address'))
            else:
                logger.info("没有达到发送要求，请等待下次发送")
                add_list(NOTICE_COINS, data)
                sleep(1)
                continue
            con_price = session.query(CoinPrice).filter(CoinPrice.id == notice_coin.get('id')).first()
            if con_price is not None:
                logger.info(f"===============coin_price {con_price}")
                con_price_history = CoinPriceHistory(coin=con_price.coin, price=con_price.coin,
                                                     user_phone=con_price.user_phone,
                                                     email_address=con_price.email_address)
                session.delete(con_price)
                session.commit()
                session.add(con_price_history)
                session.commit()


        except Exception as e:
            logger.exception("send notice exception ", e)
            sleep(1)


if __name__ == '__main__':
    watch_price()
