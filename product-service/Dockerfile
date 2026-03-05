FROM maven:3.10.1-jdk-21-slim AS MAVEN_BUILD

COPY pom.xml /build/

COPY src /build/src/

WORKDIR /build/

RUN mvn install -q

FROM openjdk:21-jre-slim

WORKDIR /app

ARG JAR_FILE=/build/target/*.jar

COPY asset /app/asset/

COPY --from=MAVEN_BUILD ${JAR_FILE} /app/app.jar

ENV JAVA_OPTS="-Xmx512m -Xms256m"

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]