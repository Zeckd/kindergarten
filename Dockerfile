FROM openjdk:26-ea-jdk-slim-trixie
ADD target/kindergarden-app.jar kindergarden-app.jar
ENTRYPOINT ["java","-jar","/kindergarden-app.jar"]