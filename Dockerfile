# ---------- build ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# 1) сначала pom.xml (чтобы кэшировались зависимости)
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# 2) затем исходники
COPY src ./src
RUN mvn -q -DskipTests package

# ---------- run ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# копируем jar
COPY --from=build /workspace/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
