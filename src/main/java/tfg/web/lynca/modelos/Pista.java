package tfg.web.lynca.modelos;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "pistas")
public class Pista {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo_deporte")
    private String tipoDeporte;

    @Column(name = "precio_hora")
    private Double precioHora;

    private String estado; 

    @Column(name = "imagen_url")
    private String imagenUrl; // URL para una imagen rápida/antigua

    @ManyToOne
    @JoinColumn(name = "instalacion_id")
    @JsonIgnore // Evita bucles infinitos en el JSON
    private Instalacion instalacion;

    @OneToMany(mappedBy = "pista", cascade = CascadeType.ALL)
    private List<ImagenPista> imagenes; // Relación para múltiples fotos

    @JdbcTypeCode(SqlTypes.JSON) // <--- ESTO ARREGLA EL ERROR DE LA TERMINAL
    @Column(columnDefinition = "jsonb")
    private String horario;

    // --- CONSTRUCTOR VACÍO ---
    public Pista() {}

    // --- GETTERS Y SETTERS ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipoDeporte() { return tipoDeporte; }
    public void setTipoDeporte(String tipoDeporte) { this.tipoDeporte = tipoDeporte; }

    public Double getPrecioHora() { return precioHora; }
    public void setPrecioHora(Double precioHora) { this.precioHora = precioHora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Instalacion getInstalacion() { return instalacion; }
    public void setInstalacion(Instalacion instalacion) { this.instalacion = instalacion; }

    // --- NUEVOS GETTERS Y SETTERS QUE FALTABAN ---

    public List<ImagenPista> getImagenes() { return imagenes; }
    public void setImagenes(List<ImagenPista> imagenes) { this.imagenes = imagenes; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}