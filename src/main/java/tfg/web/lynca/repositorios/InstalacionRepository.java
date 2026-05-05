package tfg.web.lynca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import tfg.web.lynca.modelos.Instalacion;
import java.util.UUID;
import java.util.List;

public interface InstalacionRepository extends JpaRepository<Instalacion, UUID> {
    // Spring Data JPA crea la consulta automáticamente si el nombre es correcto
    List<Instalacion> findByEntidadId(UUID entidadId);
}