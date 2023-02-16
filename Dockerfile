FROM openjdk:17-oraclelinux7
RUN mkdir /app
WORKDIR app
COPY src/ src/
COPY gradle/ gradle/
COPY build.gradle build.gradle
COPY gradlew gradlew
COPY settings.gradle settings.gradle
RUN ./gradlew build
ENTRYPOINT ["./gradlew","bootRun"]