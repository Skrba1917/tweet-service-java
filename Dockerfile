FROM adoptopenjdk/maven-openjdk11
COPY ./ ./
RUN mvn clean package
ENTRYPOINT ["java","-jar","target/tweet-service.jar"]
EXPOSE 8084