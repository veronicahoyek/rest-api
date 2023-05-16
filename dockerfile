FROM maven:3.9.0-amazoncorretto-17 as builder

WORKDIR /app

COPY ./src ./src
COPY ./pom.xml ./pom.xml

RUN mvn clean install -DskipTests

FROM amazoncorretto:17
COPY --from=builder "/app/target/oop1-veronica-hoyek-0.0.1-SNAPSHOT.jar" app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]


