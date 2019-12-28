import sqlalchemy
from sqlalchemy.orm.session import sessionmaker
from sqlalchemy.sql.schema import MetaData, Table, Column
from sqlalchemy.types import Integer, String

mysql_host = "188.131.139.100"
port = 3306
user = "root"
password = "somewordpress"
database = "test"
mysql_url = f"mysql+pymysql://{user}:{password}@{mysql_host}:{port}/{database}"

# echo为True,开启调试功能 用来设置是否输出sql语句执行的日志
engine = sqlalchemy.create_engine(mysql_url, echo=True)
Session = sessionmaker(bind=engine, autoflush=False)
# 数据库实例 ，使用该session连接数据
session = Session()
# 连接数据库
connect = engine.connect()

meta_data = MetaData(engine)

student = Table('student', meta_data,
                Column('id', Integer, primary_key=True),
                Column('name', String(20)),
                Column('age', Integer),
                Column('sex', Integer))

teacher = Table('teacher', meta_data,
                Column('id', Integer, primary_key=True),
                Column('name', String(20)),
                Column('age', Integer),
                Column('sex', Integer))
meta_data.create_all(engine)

from sqlalchemy.ext.declarative import declarative_base

BaseData = declarative_base()

class Student(BaseData):
    # 指定表名
    __tablename__ = "student"
    id= Column(Integer,primary_key = True)
    name = Column(String(20))
    age = Column(Integer)
    sex = Column(Integer)
        
class Teacher(BaseData):
    __tablename__ = "teacher"
    id= Column(Integer,primary_key = True)
    name = Column(String(20))
    age = Column(Integer)
    sex = Column(Integer)

from sqlalchemy.orm import sessionmaker,scoped_session
import random
# 创建绑定具体库的session
Session = sessionmaker(bind=engine)
db = Session()

Session_factory = sessionmaker(bind=engine)
Session = scoped_session(Session_factory)
session = Session()

student = Student()
student.name = "test_1"
student.age= random.randint(1,20)
# 1代表男 0 代表女
student.sex = 1

student1 = Student(name="test_2",age= random.randint(1,20),sex = 0)

# 向数据库中增加数据
session.add(student)
session.add(student1)

# 向数据库中提交，只有提交才会生效
session.commit()
print("成功插入")

teacher = Table("teacher",meta_data,atuo_load= True)

data = [
    {
        "name":"teacher_1",
        "age":random.randint(20,40),
    },
    {
        "name":"teacher_2",
        "age":random.randint(20,40),
        "sex":1
    }
]

connect.execute(teacher.insert(),data)