import sqlalchemy
from sqlalchemy import or_, and_, exists
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm.session import sessionmaker
from sqlalchemy.sql.schema import MetaData, Table, Column
from sqlalchemy.types import Integer, String

mysql_host = "localhost"
port = 3306
user = "root"
password = "somewordpress"
database = "learn"
mysql_url = f"mysql+pymysql://{user}:{password}@{mysql_host}:{port}/{database}"

# echo为True,开启调试功能 用来设置是否输出sql语句执行的日志
engine = sqlalchemy.create_engine(mysql_url, echo=True)
Session = sessionmaker(bind=engine, autoflush=False)
# 数据库实例 ，使用该session连接数据
session = Session()
# 连接数据库
connect = engine.connect()


def model_data_to_dict(self):
    return {c.name: getattr(self, c.name, None)
            for c in self.__table__.columns}


BaseData = declarative_base()


class User(BaseData):
    # 指定表名
    __tablename__ = "users"

    id = Column(Integer, primary_key=True)
    name = Column(String(20))
    sex = Column(Integer)
    desc = Column(String(200))
    phone = Column(String(200))


class Order(BaseData):
    # 指定表名s
    __tablename__ = "order"
    id = Column(Integer, primary_key=True)
    order_id = Column(String(32))
    user_id = Column(Integer)


Order.model_data_to_dict = model_data_to_dict
User.model_data_to_dict = model_data_to_dict

id = 1
order = session.query(Order).get(id)
print(order and order.model_data_to_dict())
order = session.query(Order).filter(Order.id == id).first()
print(order and order.model_data_to_dict())

user = session.query(User).get(id)
print(user and user.model_data_to_dict())
user = session.query(User).filter(User.id == id).first()
print(user and user.model_data_to_dict())

session.query(Order)
print("==============")
user_id = 11
id = 10
orders = session.query(Order).filter(Order.id == id, Order.user_id == user_id).all()
print([order.model_data_to_dict() for order in orders])
print("==============")
phone_num = '17666501319'
users = session.query(User).filter(User.id == 11, User.phone == phone_num).all()
print([user.model_data_to_dict() for user in users])

user_id = 11
id = 10
orders = session.query(Order).filter_by(id=id, user_id=user_id).all()
print([order.model_data_to_dict() for order in orders])
print("========or查询")

users = session.query(User).filter(or_(User.id == 10, User.phone == phone_num)).all()
print([user.model_data_to_dict() for user in users])

orders = session.query(Order).filter(or_(Order.id == 13, Order.user_id == user_id)).all()
print([order.model_data_to_dict() for order in orders])
print("==========and 和or")
users = session.query(User).filter(or_(User.id == 10, User.phone == phone_num), User.sex == 0).all()
print([user.model_data_to_dict() for user in users])

orders = session.query(Order).filter(or_(and_(Order.id == 11, Order.user_id == user_id), Order.user_id == 15)).all()
print([order.model_data_to_dict() for order in orders])
print("======订单======")
orders = session.query(Order).filter(Order.id.in_([1, 2, 3, 4, 5])).all()
print([order.model_data_to_dict() for order in orders])
users = session.query(User).filter(User.id.in_([1, 2, 3, 4, 5])).all()
print([user.model_data_to_dict() for order in users])
print("=========模糊查询")
orders = session.query(Order).filter(Order.order_id.like('%a34%')).all()
print([order.model_data_to_dict() for order in orders])
users = session.query(User).filter(User.name.like('江%')).all()
print([user.model_data_to_dict() for order in users])

print("======not in ")
orders = session.query(Order).filter(~Order.id.in_([1, 2, 3, 4, 5])).all()
print([order.model_data_to_dict() for order in orders])
users = session.query(User).filter(~User.id.in_([1, 2, 3, 4, 5])).all()
print([user.model_data_to_dict() for user in users])
print("======返回指定列数据======")
orders = session.query(Order.id, Order.order_id).filter(Order.id.in_([1, 2, 3, 4, 5])).all()
print([dict(zip(order.keys(), order)) for order in orders])
users = session.query(User.id, User.name).filter(User.id.in_([1, 2, 3, 4, 5])).all()
print([dict(zip(user.keys(), user)) for user in users])
print("=========有一条数据=====")
order = session.query(Order).filter_by(id=1).one()
print(order and order.model_data_to_dict())
user = session.query(User).filter_by(id=1).one()
print(user and user.model_data_to_dict())
# user = session.query(User).filter_by(id=100).one()
# print(user and user.model_data_to_dict())
print("=========查询数据查询的条数")
order_count = session.query(Order).filter(Order.id != 1).count()
print(order_count)
user_count = session.query(User).filter(User.id != 1).count()
print(user_count)
print("============limit")
orders = session.query(Order.id, Order.order_id).filter(Order.id.in_([1, 2, 3, 4, 5])).limit(3).all()
print([dict(zip(order.keys(), order)) for order in orders])
users = session.query(User.id, User.name).filter(User.id.in_([1, 2, 3, 4, 5])).limit(2).all()
print([dict(zip(user.keys(), user)) for user in users])
print("============")
orders = session.query(Order).distinct(Order.id).all()
print(orders and [order.model_data_to_dict() for order in orders])
users = session.query(User.id).distinct(User.id).all()
print(users)
print("==========")
orders = session.query(Order).distinct(Order.id).all()
print(orders and [order.model_data_to_dict() for order in orders])
users = session.query(User.id).distinct(User.id).all()
print(users)
print("===========")
order = session.query(exists().where(Order.id >15)).scalar()
print(order)
print("========")
users = session.query(User.id,User.name).filter(User.id>10).order_by(User.id.desc()).all()
print([dict(zip(user.keys(), user)) for user in users])
users = session.query(User.id,User.name).filter(User.id>10).order_by(User.id.asc()).all()
print([dict(zip(user.keys(), user)) for user in users])
print("========关联查询")
order_users = session.query(Order.id,Order.order_id,User.id,User.phone,User.name).join(Order,User.id==Order.user_id).filter(User.id>10,Order.id >15).all()
print([dict(zip(order_user.keys(), order_user)) for order_user in order_users])

order_users = session.query(Order.id,Order.order_id,User.id,User.phone,User.name).outerjoin(Order,User.id==Order.user_id).filter(User.id>10,Order.id >15).all()
print([dict(zip(order_user.keys(), order_user)) for order_user in order_users])