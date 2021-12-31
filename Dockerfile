FROM maven:3.5.2-jdk-8 AS build
WORKDIR /app
COPY src src
COPY pom.xml pom.xml
RUN mvn -f pom.xml clean package
RUN mv target/jachai-map-*.jar target/jachai-map.jar 

FROM openjdk:18-slim-buster
ENV TINI_VERSION=v0.19.3
WORKDIR /app
ENV TZ=Asia/Dhaka
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN apt update && \
    apt install curl -y && \
    curl -sSL https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini --output /usr/sbin/tini && \
    chmod +x /usr/sbin/tini
COPY --from=build /app/target/jachai-map.jar jachai-map.jar
EXPOSE 8080
CMD ["java", "-jar", "jachai-map.jar", "--spring.config=application.properties"]
