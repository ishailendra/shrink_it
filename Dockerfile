#### BUILD STAGE ####
FROM maven:latest AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

#### PACKAGE STAGE ####
FROM openjdk:latest
VOLUME /usr/src/data
COPY --from=build /usr/src/app/target/*.jar /usr/src/data/shrink_it.jar

ENTRYPOINT ["java", "-jar","/usr/src/data/shrink_it.jar"]