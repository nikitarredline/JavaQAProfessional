FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /workspace

COPY . .

RUN mvn -q -DskipTests dependency:go-offline

CMD ["mvn", "clean", "test"]