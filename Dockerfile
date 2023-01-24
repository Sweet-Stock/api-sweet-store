FROM openjdk:11
COPY . /app
WORKDIR /app
RUN ./gradlew clean
RUN ./gradlew build
ENTRYPOINT ["java", "-jar", "build/libs/api-sweet-store-0.0.1-SNAPSHOT.jar"]
