# Usamos una imagen ligera de Java 17 (o la versión que uses)
FROM openjdk:17-jdk-slim

# Definimos el directorio de trabajo
WORKDIR /app

# Copiamos el archivo JAR generado al contenedor
# Nota: Asegúrate de que el nombre coincida con el de tu carpeta target
COPY target/lynca-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto que usará Render
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]