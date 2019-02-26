FROM openjdk:8-jre-alpine
LABEL fruiqi <fruiqi@infervision.com>
ENV TZ=Asia/Shanghai
RUN apk add --update --no-cache tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone
WORKDIR /app
COPY build/libs/onirigi-front-1.0.0.jar /app/
CMD ["java", "-jar", "-Dspring.profiles.active=test", "/app/onirigi-front-1.0.0.jar"]
