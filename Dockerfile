FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src src
RUN mvn package

FROM openjdk:17-jdk AS runtime
WORKDIR /app
COPY --from=builder /app/target/transaction-processor-0.0.1-SNAPSHOT.jar ./
ENTRYPOINT ["java", "-jar", "transaction-processor-0.0.1-SNAPSHOT.jar"]