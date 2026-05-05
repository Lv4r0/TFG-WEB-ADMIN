package tfg.web.lynca.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importación necesaria
import org.springframework.web.bind.annotation.*;
import tfg.web.lynca.modelos.Perfil;
import tfg.web.lynca.repositorios.PerfilRepository;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PerfilRepository perfilRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Inyectamos el codificador para comparar hashes

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Perfil datosLogin) {
        System.out.println(">>> LOGIN INTENTADO CON: " + datosLogin.getEmail());
        
        // Verificamos si la contraseña llegó o si es nula
        String passEnviada = datosLogin.getPassword();
        System.out.println(">>> PASS ENVIADA: " + (passEnviada != null ? passEnviada : "NULL"));

        if (passEnviada == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña no enviada");
        }

        Optional<Perfil> usuarioOpt = perfilRepo.findByEmail(datosLogin.getEmail().trim());

        if (usuarioOpt.isPresent()) {
            Perfil usuario = usuarioOpt.get();
            System.out.println(">>> USUARIO ENCONTRADO EN DB. PASS EN DB ES: " + usuario.getPassword());

            // --- CAMBIO PARA SEGURIDAD PROFESIONAL: BCrypt Matches ---
            String passBD = usuario.getPassword();
            
            // Comparamos el texto plano enviado con el hash almacenado en la base de datos
            if (passBD != null && passwordEncoder.matches(passEnviada.trim(), passBD)) {
                
                // --- VALIDACIÓN DE BANEO ---
                System.out.println(">>> ESTADO DE BANEO EN DB: " + usuario.getEstaBaneado());
                
                if (Boolean.TRUE.equals(usuario.getEstaBaneado())) {
                    System.out.println(">>> LOGIN BLOQUEADO: EL USUARIO ESTÁ BANEADO");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("ACCESO DENEGADO: Tu cuenta ha sido bloqueada por un administrador.");
                }
                
                System.out.println(">>> LOGIN EXITOSO PARA: " + usuario.getEmail());
                return ResponseEntity.ok(usuario);
            } else {
                System.out.println(">>> LA CONTRASEÑA NO COINCIDE (BCrypt Check Failed)");
            }
        } else {
            System.out.println(">>> EL EMAIL NO EXISTE EN LA TABLA PERFILES");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
}