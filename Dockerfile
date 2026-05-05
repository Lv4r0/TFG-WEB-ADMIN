FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiamos el JAR usando la ruta exacta desde la raíz del repo
COPY lynca/target/lynca-0.0.1-SNAPSHOT.jar app.jar

# Damos permisos de lectura al archivo por si acaso
RUN chmod 644 app.jar

EXPOSE 8080

# Usamos la ruta absoluta al JAR dentro del contenedor
ENTRYPOINT ["java", "-jar", "/app/app.jar"]