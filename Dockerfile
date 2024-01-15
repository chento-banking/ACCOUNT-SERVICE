FROM openjdk:22-slim as build

MAINTAINER chento_bank

COPY ./build/libs/account-0.0.1.jar account-0.0.1.jar

ENTRYPOINT ["java","-jar", "/account-0.0.1.jar"]
