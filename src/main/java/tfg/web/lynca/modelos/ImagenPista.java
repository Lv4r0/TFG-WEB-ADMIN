package tfg.web.lynca.modelos;

import jakarta.persistence.*;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "imagenes_pista")
public class ImagenPista {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "url_imagen")
    private String urlImagen;

    @Column(name = "es_principal")
    private Boolean esPrincipal;

    @ManyToOne
    @JoinColumn(name = "pista_id")
    @JsonIgnore // Fundamental para evitar bucles infinitos en el JSON
    private Pista pista;

    // --- CONSTRUCTOR ---
    public ImagenPista() {}

    // --- GETTERS Y SETTERS REALES (SIN EXCEPCIONES) ---
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }

    public Boolean getEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(Boolean esPrincipal) { this.esPrincipal = esPrincipal; }

    public Pista getPista() { return pista; }
    
    // AQUÍ ESTABA EL ERROR: Ahora asignamos la pista correctamente
    public void setPista(Pista pista) { 
        this.pista = pista; 
    }
}