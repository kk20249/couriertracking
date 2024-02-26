FROM openjdk:17 as build
COPY target/couriertracking-0.0.1-SNAPSHOT.jar couriertracking.jar
ENTRYPOINT ["java","-jar","/couriertracking.jar"]