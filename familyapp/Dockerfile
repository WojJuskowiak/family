FROM maven:3.8.6-amazoncorretto-17
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run