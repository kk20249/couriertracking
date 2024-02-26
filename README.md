# Courier Tracking Case

This is a simple Spring Boot project to track couriers and stores. It has a REST API to track couriers and stores. It also has a service to calculate the distance between a courier and a store.

## Technologies

* Spring Boot, Java 17
* H2 Database

## How to start the project

Using docker:

```shell
mvn clean package
docker compose up couriertracking
```

It will be started on port 8080. Although it is not the best way to do it, I also added a CommandLineRunner to populate the database with some data.
It read stores.json and populates the store table with it. Also, sends several request for courier logs.

## Postman

Postman collection can be found under the resources.

## Assumptions

* Courier flow is not implemented. It is assumed that there is a courier service that sends logs to the system. 
* Euclidean distance is used to calculate the distance between two points. It is assumed that the world is flat. Although there is a Haversine formula to calculate the distance between two points on a sphere, it is not used but with Strategy pattern, calculation method can be changed easily.
* Focused mostly on the happy path. It is assumed that the input is mostly correct but there are some validations and error handling on the controller and service level.
* Distance calculations made in meters.
