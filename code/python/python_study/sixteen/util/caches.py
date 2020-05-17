import json
import os

import redis

# https://github.com/qishibo/AnotherRedisDesktopManager redis 下载地址
host = os.getenv("REDIS_HOST","localhost")
port = 6379
redis_pool = redis.ConnectionPool(host=host, port=port, db=0)
redis_conn = redis.Redis(connection_pool=redis_pool)


def set_key(key, value):
    redis_conn.set(key, json.dumps(value))


def get_key(key):
    value = redis_conn.get(key)
    if value is not None:
        return json.loads(value)
    return value


def put_zset_pip(key, values):
    """
    :param key: values 中必须有这个key
    :param values:
    :return:
    """
    with redis_conn.pipeline() as p:
        for value in values:
            result = {
                json.dumps(value): value['close'],
            }
            redis_conn.zadd(key, result)
        p.execute()


def get_list(key):
    email = json.loads(redis_conn.brpoplpush(key, key))
    return email


def get_queue_message(key):
    value = redis_conn.brpop(key)
    return json.loads(value[1])


def acqure_list_key_len(key):
    result = redis_conn.llen(key)
    return result


def add_list(key, values):
    redis_conn.lpush(key, json.dumps(values))


def add_list_default(key, value):
    redis_conn.lpush(key, value)

def clear_cache_by_key(key):
    redis_conn.delete(key)