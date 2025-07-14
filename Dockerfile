# Build stage
FROM registry.access.redhat.com/ubi8/openjdk-21:1.19 AS build
WORKDIR /usr/src/app
COPY --chown=185:0 . .
USER 185
RUN ./gradlew build \
    -Dquarkus.package.jar.enabled=true \
    -Dquarkus.package.jar.type=fast-jar \
    --no-daemon

# Runtime stage
FROM registry.access.redhat.com/ubi8/openjdk-21:1.19
WORKDIR /work/
COPY --chown=185:0 --from=build /usr/src/app/application/build/quarkus-app/ ./
USER 185
EXPOSE 8080
CMD ["java", "-jar", "quarkus-run.jar"]
