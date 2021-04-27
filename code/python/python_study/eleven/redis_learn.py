import redis

host = "188.131.139.100"
port = 6379
redis_conn = redis.Redis(host=host, port=port, db=0)

redis_pool = redis.ConnectionPool(host=host, port=port, db=0)
redis_conn = redis.Redis(connection_pool=redis_pool)

redis_conn.set(name="student_key", value="student1", ex=100)

value = redis_conn.get(name="student_key")
print(value)

# 设置多个值
content = {
    "student_key1": "student1",
    "student_key2": "studeng2"
}
redis_conn.mset(content)

result = redis_conn.mget(['student_key1', 'student_key2'])
print(result)
