from flask import Flask, json

# Flask实例 __name__当前模块的名字，也可以使用其他名字来传给Flask
from fifteen.data_zhihu import acquire_hot_list, parse_zhihu_hot_list

app = Flask(__name__)


# 创建一个路由
@app.route("/hello")
def hello_work():
    return "hello word"


@app.route("/articles/<id>")
def articles(id):
    return id


@app.route("/hots/<type>")
def zhihu_hot_list(type):
    result = {}
    if type =="zhihu":
        hot_list = acquire_hot_list(url="https://www.zhihu.com/api/v3/feed/topstory/hot-lists/total?limit=50&desktop=true")
        result = parse_zhihu_hot_list(hot_list)
    return json.jsonify(result)


if __name__ == '__main__':
    # 启动flask实例
    app.run("0.0.0.0")
