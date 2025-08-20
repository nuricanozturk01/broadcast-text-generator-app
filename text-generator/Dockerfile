FROM eclipse-temurin:21-alpine

RUN addgroup text-generator-server && adduser --disabled-password --no-create-home --ingroup text-generator-server text-generator-server
USER text-generator-server

LABEL org.opencontainers.image.description="text-generator"
ADD target/text-generator-1.0.0-uber.jar backend.jar
EXPOSE 7001/udp


ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/backend.jar"]
