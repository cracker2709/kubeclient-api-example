FROM adoptopenjdk/openjdk11:jdk-11.0.9.1_1
VOLUME /tmp
EXPOSE 9142
ADD ./target/*.jar /app.jar
ENV JAVA_OPTS "-Djava.security.egd=file:/dev/./urandom"
ADD ./target/*.jar /app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar
