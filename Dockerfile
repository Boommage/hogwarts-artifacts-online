# Multistage Docker file - Each FROM instruction is a build stage

# Stage 1: Extract the layers - (Dependencies, Spring-Boot-Loader, Snapshot Dependencies, and Application) from JAR
FROM eclipse-temurin:17-jre as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:17-jre
WORKDIR application
# The ordering of these matter - Content that is least likely to change should be added first
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
# Tells Docker to run the jar launcher class when the container starts
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

EXPOSE 8080