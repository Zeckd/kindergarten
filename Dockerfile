# Multi-stage build для оптимизации размера образа
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Копируем pom.xml и загружаем зависимости (кэшируется если pom не изменился)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходный код и собираем приложение
COPY src ./src
RUN mvn clean package -DskipTests -B

# Финальный образ с приложением
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Создаем непривилегированного пользователя
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Находим и копируем jar из stage build (Spring Boot создает JAR с версией в имени)
COPY --from=build /app/target/*.jar app.jar

# Открываем порт
EXPOSE 8080

# Добавляем переменные окружения для оптимизации JVM
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Запускаем приложение
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

