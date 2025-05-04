#### BUILD STAGE ####
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /usr/src/app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

#### PACKAGE STAGE ####
FROM eclipse-temurin:21-jdk
WORKDIR /usr/src/data
COPY --from=build /usr/src/app/target/*.jar ./shrink_it.jar
ENTRYPOINT ["java", "-jar", "shrink_it.jar"]
