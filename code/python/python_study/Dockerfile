FROM python:3.7

RUN sed -i s@/archive.ubuntu.com/@/mirrors.aliyun.com/@g /etc/apt/sources.list  && \
    sed -i s@/security.ubuntu.com/@/mirrors.aliyun.com/@g /etc/apt/sources.list

RUN echo [global]'\n'index-url = https://mirrors.aliyun.com/pypi/simple/ > /etc/pip.conf

RUN pip install --upgrade pip

WORKDIR /coin_base

COPY Pipfile Pipfile.lock /coin_base/

RUN pip install --no-cache-dir pipenv

RUN pipenv install --python 3.7 --deploy --system

COPY . /coin_base
ENV PYTHONPATH=/coin_base
CMD cd sixteen && python wsgi.py
