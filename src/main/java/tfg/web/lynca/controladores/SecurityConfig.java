package tfg.web.lynca.controladores; // Asegúrate de que la carpeta coincide

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desactivamos CSRF para que el fetch (POST, PUT, DELETE) funcione desde JS
            .csrf(csrf -> csrf.disable()) 
            
            .authorizeHttpRequests(auth -> auth
                // 2. Recursos estáticos y vistas (Libre acceso)
                // Dentro de SecurityConfig.java, en la sección de permitAll():
                .requestMatchers("/", "/index.html", "/favicon.ico", "/estilos/**", "/scripts/**", "/vistas/**").permitAll()
                
                // 3. Endpoints de la API - ACTUALIZADO PARA LYNCA
                // Hemos añadido "/admin/usuarios/**" para que cargue la lista y permita banear
                .requestMatchers("/auth/**", "/gestor/**").permitAll()
                .requestMatchers("/admin/registro-publico", "/admin/entidades/**", "/admin/usuarios", "/admin/usuarios/**").permitAll()

                // 4. El resto requiere autenticación
                .anyRequest().authenticated()
            )
            // 5. Desactivamos el login por defecto de Spring porque usamos el nuestro en AuthController
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}