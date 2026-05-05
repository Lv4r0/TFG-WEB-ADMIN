package tfg.web.lynca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tfg.web.lynca.modelos.Perfil;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, UUID> {
    
    // Este método es "mágico": Spring Boot entiende que debe buscar por el campo 'email'
    // Lo usaremos en el AuthController para el login
    Optional<Perfil> findByEmail(String email);
    // Para la lista de usuarios
    List<Perfil> findByRol(String rol);
}