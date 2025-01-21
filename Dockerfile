FROM amazoncorretto:17-alpine-jdk
EXPOSE 9092
EXPOSE 9093
COPY out/artifacts/kafkatest_jar/kafkatest.jar kafkatest.jar
ENTRYPOINT ["java","-jar","/kafkatest.jar"]