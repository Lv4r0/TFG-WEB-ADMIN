package tfg.web.lynca.modelos;

import jakarta.persistence.*;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "instalaciones")
public class Instalacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nombre;
    private String direccion;
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "entidad_id")
    @JsonIgnore
    private Entidad entidad;

    @OneToMany(mappedBy = "instalacion", cascade = CascadeType.ALL)
    private List<ImagenInstalacion> imagenes;

    // --- CONSTRUCTOR VACÍO (Obligatorio para Hibernate) ---
    public Instalacion() {}

    // --- CONSTRUCTOR COMPLETO ---
    public Instalacion(UUID id, String nombre, String direccion, String descripcion, Entidad entidad, List<ImagenInstalacion> imagenes) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.entidad = entidad;
        this.imagenes = imagenes;
    }

    // --- GETTERS Y SETTERS MANUALES (Los de toda la vida) ---
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Entidad getEntidad() { return entidad; }
    public void setEntidad(Entidad entidad) { this.entidad = entidad; }

    public List<ImagenInstalacion> getImagenes() { return imagenes; }
    public void setImagenes(List<ImagenInstalacion> imagenes) { this.imagenes = imagenes; }
}