FROM eclipse-temurin

WORKDIR /app

COPY build.gradle .
COPY gradlew .
COPY gradle gradle

RUN ./gradlew  dependencies

COPY src/main src/main

RUN ./gradlew build

CMD ["java", "-jar", "build/libs/app-0.0.1-SNAPSHOT.jar"]