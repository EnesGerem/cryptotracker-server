FROM openjdk:17-oracle
ARG JAR_FILE=target/cryptotracker-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /cryptotracker.jar
ENTRYPOINT ["java", "-jar", "cryptotracker.jar"]