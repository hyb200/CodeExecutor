FROM alpine:3.14

ENV LANG=C.UTF-8 \
    JAVA_HOME=/usr/lib/jvm/java-8-openjdk \
    PATH=$PATH:/usr/lib/jvm/java-8-openjdk/bin

# 安装必要工具
RUN apk add --no-cache \
    build-base \
    openjdk8 \
    python3 \
    py3-pip \
    go \
    git \
    bash

# 设置工作目录
WORKDIR /workspace

LABEL image.name="Compiler" \
      image.version="1.0"

# 默认进入工作目录
CMD ["bash"]
