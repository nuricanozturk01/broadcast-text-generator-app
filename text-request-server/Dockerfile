FROM eclipse-temurin:21-alpine

RUN addgroup text-request-server && adduser --disabled-password --no-create-home --ingroup text-request-server text-request-server
USER text-request-server

LABEL org.opencontainers.image.description="text-request-server"
ADD target/text-request-server-1.0.0-uber.jar backend.jar

EXPOSE 7000/tcp

ENTRYPOINT ["java", "-jar", "/backend.jar"]
