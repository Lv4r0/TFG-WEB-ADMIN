package tfg.web.lynca.modelos;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "imagenes_instalacion")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ImagenInstalacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;

    @Column(name = "es_principal")
    private Boolean esPrincipal = false;

    @ManyToOne
    @JoinColumn(name = "instalacion_id")
    @JsonIgnore
    private Instalacion instalacion;
}