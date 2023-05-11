FROM openjdk:11

ADD target/holiday-api-*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]