# 相关docker-compose

1. 启动带分词器的elasticsearch

```yaml
version: '2.2'
services:
  es01:
    image: hub.infervision.com/tools/elassticseach-with-ik:7.1.1
    container_name: es01
    environment:
      - node.name=es01
      - discovery.seed_hosts=es02
      - cluster.initial_master_nodes=es01,es02
      - cluster.name=docker-cluster
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - esdata01:/usr/share/elasticsearch/data
    ports:
      - 9200:9200
    networks:
      - esnet
  es02:
    image: hub.infervision.com/tools/elassticseach-with-ik:7.1.1
    container_name: es02
    environment:
      - node.name=es02
      - discovery.seed_hosts=es01
      - cluster.initial_master_nodes=es01,es02
      - cluster.name=docker-cluster
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - esdata02:/usr/share/elasticsearch/data
    networks:
      - esnet
volumes:
  esdata01:
    driver: local
  esdata02:
    driver: local

networks:
  esnet:
```

2. kibana

```yaml
version: '2'
services:
  kibana:
    image: docker.elastic.co/kibana/kibana:7.1.1
    volumes:
      - ./kibana.yml:/usr/share/kibana/config/kibana.yml
```

3. 封装分词器的dockerfile

```dockerfile
# Docker image of elasticsearch with ik tokenizer
# VERSION 6.4.3
FROM elasticsearch:7.1.1
WORKDIR /app
COPY elasticsearch-analysis-ik-7.1.1.zip /app
RUN unzip elasticsearch-analysis-ik-7.1.1.zip -d analysis-ik 
 
#es插件目录
ENV ES_PLUGINS_PATH /usr/share/elasticsearch/plugins
 
#定义存放ik分词器文件的目录
ENV ik_SRC_COMPILE_PATH /opt/ik_build
 
#创建存放ik分词器文件的目录
RUN mkdir $ik_SRC_COMPILE_PATH && \

#进入编译ik分词器文件的目录
cd $ik_SRC_COMPILE_PATH && \

#构建成功后，将文件移动到插件目录
mv /app/analysis-ik $ES_PLUGINS_PATH/

```

```Dockerfile
from elasticsearch:7.1.1
RUN elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.1.1/elasticsearch-analysis-ik-7.1.1.zip 


```