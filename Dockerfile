# Etapa 1: Compilar la app con Maven
FROM eclipse-temurin:21.0.3_9-jdk AS builder

WORKDIR /app

# Copiamos los archivos necesarios para descargar dependencias
COPY ./pom.xml .
COPY ./.mvn .mvn
COPY ./mvnw .

# Descargar dependencias sin compilar
RUN ./mvnw dependency:go-offline

# Copiar código fuente y compilar
COPY ./src ./src
RUN ./mvnw clean install -DskipTests

# Etapa 2: Imagen final con solo el .jar
FROM eclipse-temurin:21.0.3_9-jre

WORKDIR /app

COPY --from=builder /app/target/gimnasio-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
