FROM openjdk:11-jdk
VOLUME /tmp
EXPOSE 9142
ADD ./target/*.jar /app.jar
ENV JAVA_OPTS "-Djava.security.egd=file:/dev/./urandom"
ADD ./target/*.jar /app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar
