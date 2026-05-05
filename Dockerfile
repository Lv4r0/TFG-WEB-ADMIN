FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Usamos el comodín asterisco para que busque el JAR en cualquier subcarpeta target
# Esto soluciona problemas si el archivo está en /lynca/target o solo en /target
COPY **/lynca-0.0.1-SNAPSHOT.jar app.jar

RUN chmod 644 app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]