# Cambiamos a la versión 21 para que coincida con tu compilación
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Mantenemos la ruta que ya funcionó para encontrar el JAR
COPY **/lynca-0.0.1-SNAPSHOT.jar app.jar

RUN chmod 644 app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]