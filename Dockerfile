# Usamos Eclipse Temurin que es la imagen oficial y estable para Java 17
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Usamos un asterisco en la ruta para que Docker encuentre el JAR 
# sin importar si está en 'target/' o 'lynca/target/'
COPY **/target/lynca-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Ejecutamos la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]