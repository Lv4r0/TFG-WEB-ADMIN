package tfg.web.lynca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import tfg.web.lynca.modelos.Pista;
import java.util.UUID;
import java.util.List;

public interface PistaRepository extends JpaRepository<Pista, UUID> {
    // Para listar las pistas de un polideportivo concreto
    List<Pista> findByInstalacionId(UUID instalacionId);
}