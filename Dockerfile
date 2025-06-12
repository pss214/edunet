# ✅ 1단계: 빌드 이미지
FROM gradle:8.5.0-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# ✅ 2단계: 운영용 이미지
FROM openjdk:21-jdk-alpine

# 시간대 설정 및 dockerize 설치
RUN apk add --no-cache tzdata curl \
    && cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime \
    && echo "Asia/Seoul" > /etc/timezone \
    && curl -sSL https://github.com/jwilder/dockerize/releases/download/v0.9.2/dockerize-linux-amd64-v0.9.2.tar.gz | tar xz -C /usr/local/bin

# 로케일 환경 변수 설정
ENV LANG=ko_KR.UTF-8 \
    LANGUAGE=ko_KR.UTF-8 \
    LC_ALL=ko_KR.UTF-8

WORKDIR /app

# 빌드된 jar 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 애플리케이션 실행 (MySQL 대기 포함)
CMD ["dockerize", "-wait", "tcp://mysql-db:3306", "-timeout", "30s", "java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]