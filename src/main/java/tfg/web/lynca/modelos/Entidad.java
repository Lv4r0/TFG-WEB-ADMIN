package tfg.web.lynca.modelos;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "entidades")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String cif;
    
    @Column(unique = true, nullable = false)
    private String email; 

    @Column(nullable = false)
    private String estado; // PENDIENTE, ALTA, BAJA, BAN 

    @Column(nullable = false) // Añadimos que no sea nula para que no falle el login luego
    private String password; 

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}