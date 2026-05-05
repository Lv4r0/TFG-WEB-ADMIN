package tfg.web.lynca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import tfg.web.lynca.modelos.ImagenPista;
import java.util.UUID;

public interface ImagenPistaRepository extends JpaRepository<ImagenPista, UUID> {
    // Aquí no necesitamos métodos extra de momento, 
    // JpaRepository ya nos da el save, deleteById, findById, etc.
}