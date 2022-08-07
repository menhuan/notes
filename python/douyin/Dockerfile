#FROM docker.infervision.com/xqdocker/ubuntu-openjdk:8
FROM python:buster
WORKDIR /app

RUN sed -i s@/archive.debian.org/@/mirrors.huaweicloud.com/@g /etc/apt/sources.list  && \
    sed -i s@/security.debian.org/@/mirrors.huaweicloud.com/@g /etc/apt/sources.list && \
    sed -i s@/deb.debian.org/@/mirrors.huaweicloud.com/@g /etc/apt/sources.list

RUN apt-get update && apt-get install -y vim \
    curl \
    ffmpeg \
    imagemagick \
    && apt-get clean
ADD ./ /app
COPY requirements.txt /app/

RUN /usr/local/bin/python -m pip install --upgrade pip && /usr/local/bin/pip install -r /app/requirements.txt -i https://pypi.douban.com/simple

ENV TZ=Asia/Shanghai

CMD ["python /workspaces/notes/python/douyin/video/add_music_to_video.py"]


