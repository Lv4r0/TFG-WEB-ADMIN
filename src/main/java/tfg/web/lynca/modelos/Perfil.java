package tfg.web.lynca.modelos;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "perfiles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Perfil {

    @Id
    private UUID id; 

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // <--- NUEVO: Campo necesario para el login

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(nullable = false)
    private String rol; // ADMIN, GESTOR, USER

    @Column(name = "esta_baneado")
    private Boolean estaBaneado = false;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}