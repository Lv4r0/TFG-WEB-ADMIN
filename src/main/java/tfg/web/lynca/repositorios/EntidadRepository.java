package tfg.web.lynca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import tfg.web.lynca.modelos.Entidad;
import java.util.UUID;

public interface EntidadRepository extends JpaRepository<Entidad, UUID> {
}