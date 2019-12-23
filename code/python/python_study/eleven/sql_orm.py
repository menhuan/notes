import sqlalchemy
from sqlalchemy.orm.session import sessionmaker
from sqlalchemy.sql.schema import MetaData,Table,Column,Integer

mysql_host ="127.0.0.1"
port =3306 
user = "root"
password = "123456"
database = "test"
mysql_url =f"mysql+pymysql://{user}:{password}@{mysql_host}:{port}/{database}" 

# echo为True,开启调试功能 用来设置是否输出sql语句执行的日志
engine = sqlalchemy.create_engine(mysql_url,echo = True)
Session = sessionmaker(bind=engine, autoflush=False)
# 数据库实例 ，使用该session连接数据
session = Session()
# 连接数据库
connect = engine.connect()

meta_data = MetaData(engine)

Table('student',meta_data,
    Column('id',Integer,primary_key= True)
