import os

import sqlalchemy
from sqlalchemy import Float, DateTime
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import class_mapper
from sqlalchemy.orm.session import sessionmaker
from sqlalchemy.sql.schema import MetaData, Table, Column
from sqlalchemy.types import Integer, String

mysql_host = host = os.getenv("MYSQL_HOST","localhost")
port = 3306
user = "root"
password = "somewordpress"
database = "wordpress"
mysql_url = f"mysql+pymysql://{user}:{password}@{mysql_host}:{port}/{database}"

# echo为True,开启调试功能 用来设置是否输出sql语句执行的日志
engine = sqlalchemy.create_engine(mysql_url, echo=True)
Session = sessionmaker(bind=engine, autoflush=False)
# 数据库实例 ，使用该session连接数据
session = Session()
# 连接数据库
connect = engine.connect()

meta_data = MetaData(engine)

sms = Table('sms', meta_data,
            Column('id', Integer, primary_key=True),
            Column('access_key', String(20)),
            Column('secret_key', String(20)),
            Column('create_at', DateTime),
            Column('update_at', DateTime),

            )
coin_price = Table('coin_price', meta_data,
                   Column('id', Integer, primary_key=True),
                   Column('coin', String(10)),
                   Column('user_phone', String(40)),
                   Column('email_address', String(40)),
                   Column('price', Float(10)),
                   Column('create_at', DateTime),
                   Column('update_at', DateTime),
                   )

coin_price_history = Table('coin_price_history', meta_data,
                           Column('id', Integer, primary_key=True),
                           Column('coin', String(10)),
                           Column('user_phone', String(40)),
                           Column('email_address', String(40)),
                           Column('price', Float(10)),
                           Column('create_at', DateTime),
                           Column('update_at', DateTime),
                           )

meta_data.create_all(engine)

BaseData = declarative_base()


def dao_to_dict(model):
    columns = [c.key for c in class_mapper(model.__class__).columns]
    return dict((c, getattr(model, c)) for c in columns if c != 'create_at' and c != 'update_at')
