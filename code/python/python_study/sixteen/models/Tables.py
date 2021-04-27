from datetime import datetime

from sqlalchemy import Column, Integer, String, DateTime, Float

from sixteen.models import BaseData


class BaseDate(object):
    create_at = Column(DateTime, nullable=False, default=datetime.utcnow)
    update_at = Column(DateTime, nullable=False, default=datetime.utcnow, onupdate=datetime.utcnow)


class Sms(BaseData, BaseDate):
    # 指定表名
    __tablename__ = "sms"
    id = Column(Integer, primary_key=True)
    access_key = Column(String(20))
    secret_key = Column(String(20))


class CoinPrice(BaseData, BaseDate):
    __tablename__ = "coin_price"
    id = Column(Integer, primary_key=True)
    coin = Column(String(10))
    price = Column(Float(10))
    user_phone = Column(String(40))
    email_address = Column(String(40))


class CoinPriceHistory(BaseDate, BaseData):
    __tablename__ = "coin_price_history"
    id = Column(Integer, primary_key=True)
    coin = Column(String(10))
    price = Column(Float(10))
    user_phone = Column(String(40))
    email_address = Column(String(40))
