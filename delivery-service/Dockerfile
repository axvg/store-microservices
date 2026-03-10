FROM maven:3.9-eclipse-temurin-21 AS maven-build

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

ARG JAR_FILE=/build/target/*.jar

COPY --from=maven-build ${JAR_FILE} /app/app.jar

ENV JAVA_OPTS="-Xmx512m -Xms256m"

EXPOSE 8083

CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
