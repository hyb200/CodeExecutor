FROM jdk17:stable

WORKDIR /data

COPY target/code-executor.jar /data/code-executor.jar

EXPOSE 7654

ENTRYPOINT ["java", "-jar", "code-executor.jar"]