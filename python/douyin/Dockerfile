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
ADD ./video/  /app/video
COPY requirements.txt /app/
ADD ./output/douyin/fonts /app/output/douyin/fonts
ADD ./output/douyin/backgroundmusic /app/output/douyin/backgroundmusic

RUN /usr/local/bin/python -m pip install --upgrade pip && /usr/local/bin/pip install -r /app/requirements.txt -i https://mirrors.aliyun.com/pypi/simple/

ENV TZ=Asia/Shanghai

CMD ["python /workspaces/notes/python/douyin/video/add_music_to_video.py"]


