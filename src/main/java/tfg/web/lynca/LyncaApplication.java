package tfg.web.lynca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LyncaApplication {

    public static void main(String[] args) {
        // Esta línea es la que arranca todo el motor de Spring Boot
        SpringApplication.run(LyncaApplication.class, args);
        System.out.println("¡Lynca está funcionando! Entra en http://localhost:8080/vistas/publico/login.html");
    }
}
