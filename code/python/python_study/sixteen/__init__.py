import os

from flask import  Flask

from sixteen.api.market import api_root
from sixteen.logger_config import logger


def register_blurprints(app):
    # 创建蓝图信息
    app.register_blueprint(api_root,url_prefix="/apis/v1")


def register_logger(app):
    app.logger=logger


def create_app(name):
    """
    创建app 实例
    :return:
    """
    if name is None:
        name = os.getenv("FLASK_ENV", 'dev')
    app = Flask(name)
    register_blurprints(app)
    register_logger(app)
    return app
