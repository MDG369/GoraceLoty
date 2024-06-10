FROM openjdk:21-jdk
COPY data/offers.json /app/data/
ADD target/offer-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
