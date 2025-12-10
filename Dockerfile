FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
EXPOSE 9142
ADD ./target/*.jar /app.jar
ADD ./target/*.jar /app.jar
EXPOSE 8080
EXPOSE 8081
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
