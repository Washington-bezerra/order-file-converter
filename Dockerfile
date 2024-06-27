#Construindo a aplicação
FROM gradle:8.8.0-jdk21 AS build
WORKDIR /home/gradle/src
COPY . .
RUN gradle bootJar --no-daemon

#Executa a aplicação
FROM openjdk:21-jdk-slim
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]