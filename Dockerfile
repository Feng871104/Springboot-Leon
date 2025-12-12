FROM eclipse-temurin:25-jdk-jammy as builder

WORKDIR /app

RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package

FROM eclipse-temurin:25-jre-jammy as runtime

RUN groupadd --system springgroup && useradd --system --groups springgroup springuser
USER springuser

WORKDIR /app

COPY --from=builder /app/target/*.jar SpringBoot-Leon-1.0.0.jar

USER springuser

EXPOSE 30678

ENTRYPOINT ["java", "-jar", "SpringBoot-Leon-1.0.0.jar"]