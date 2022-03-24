FROM maven:3.8.4-openjdk-11-slim as build
WORKDIR /usr/src/app
COPY pom.xml /usr/src/app
RUN mvn clean package

COPY . .
RUN mvn clean package install -DskipTests
EXPOSE 80
FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
COPY --from=build
COPY --from=build /usr/src/app/target/be-data-importer-service-*.jar ./be-data-importer-service.jar
CMD java $JAVA_OPTIONS -jar be-data-importer-service.jar
