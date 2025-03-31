# Usa la imagen oficial de OpenJDK 21
FROM openjdk:21-jdk-slim

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR construido (asegúrate de construir tu proyecto con Maven/Gradle primero)
COPY target/*.jar app.jar

# Puerto expuesto (debe coincidir con tu server.port si lo has configurado)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]