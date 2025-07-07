# Build stage
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu AS build
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN ./gradlew build -Dquarkus.package.type=fast-jar --no-daemon

# Runtime stage
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu
WORKDIR /work/
COPY --from=build /usr/src/app/application/build/quarkus-app/ ./
EXPOSE 8080
USER 1001
CMD ["java", "-jar", "quarkus-run.jar"]
