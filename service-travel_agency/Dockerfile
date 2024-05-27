FROM openjdk:21-jdk
COPY data/offer_reservation_data.json /app/data/
ADD target/hotel-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

