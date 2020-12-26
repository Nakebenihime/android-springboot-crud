#DOCKERFILE MULTI STAGES
FROM openjdk:16-jdk-alpine AS builder
LABEL maintainer="maintainer@company.com"
WORKDIR application
COPY android-springboot-backend .
RUN ls -l
RUN sed -e 's/\r$//' mvnw && \
    chmod +x mvnw
RUN ./mvnw clean install -DskipTests

FROM openjdk:16-jdk-alpine AS runner
COPY --from=builder /application/target/*.jar /application/android-springboot-backend-0.0.1-SNAPSHOT.jar
EXPOSE 8282
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/application/android-springboot-backend-0.0.1-SNAPSHOT.jar"]