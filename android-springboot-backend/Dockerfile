#DOCKERFILE MULTI STAGES
FROM openjdk:16-jdk-alpine AS builder
LABEL maintainer="maintainer@company.com"
WORKDIR application
COPY . .
RUN sed -i 's/\r$//' mvnw \
&& chmod +x mvnw \
&& ./mvnw clean install -DskipTests

FROM openjdk:16-jdk-alpine AS runner
COPY --from=builder /application/target/*.jar /application/android-springboot-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/application/android-springboot-backend-0.0.1-SNAPSHOT.jar"]