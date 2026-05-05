package tfg.web.lynca.repositorios;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import tfg.web.lynca.modelos.ImagenInstalacion;

public interface ImagenInstalacionRepository extends JpaRepository<ImagenInstalacion, UUID> {}
