FROM eclipse-temurin:17-jdk-alpine
ADD target/superHero-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8081
WORKDIR /app
ENTRYPOINT ["java","-jar","/app.jar"]
