package tfg.web.lynca.controladores;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional; // Importante para la Opción B
import tfg.web.lynca.modelos.Entidad;
import tfg.web.lynca.modelos.Perfil;
import tfg.web.lynca.repositorios.EntidadRepository;
import tfg.web.lynca.repositorios.PerfilRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final PerfilRepository perfilRepository;
    private final EntidadRepository entidadRepository;

    // Recomendado usar un solo constructor para inyectar todo
    AdminController(PerfilRepository perfilRepository, EntidadRepository entidadRepository) {
        this.perfilRepository = perfilRepository;
        this.entidadRepository = entidadRepository;
    }

    @GetMapping("/entidades")
    public List<Entidad> listarEntidades() {
        return entidadRepository.findAll();
    }

    // 1. Listar solo usuarios con rol USER
    @GetMapping("/usuarios")
    public List<Perfil> listarUsuarios() {
        // Buscamos los que tienen rol USER (como Victor en tu captura)
        List<Perfil> lista = perfilRepository.findByRol("USER");
        
        // CHIVATO: Mira tu consola de Java al cargar la página
        System.out.println(">>> LYNCA DEBUG: Usuarios 'USER' encontrados en DB: " + lista.size());
        
        return lista;
    }

    // 2. Método para banear/desbanear por ID
    @PutMapping("/usuarios/{id}/toggle-ban")
    @org.springframework.transaction.annotation.Transactional
    public Perfil toggleBan(@PathVariable UUID id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        perfil.setEstaBaneado(!perfil.getEstaBaneado());
        return perfilRepository.save(perfil);
    }

    @PostMapping("/registro-publico")
    public Entidad solicitarRegistro(@RequestBody Entidad nuevaEntidad) {
        // HASHEO PROFESIONAL: Encriptamos antes de guardar en la tabla 'entidades'
        String passHasheada = passwordEncoder.encode(nuevaEntidad.getPassword());
        nuevaEntidad.setPassword(passHasheada);
        
        nuevaEntidad.setEstado("PENDIENTE"); 
        return entidadRepository.save(nuevaEntidad);
    }

    // 1. Añade la inyección al principio de la clase
@Autowired
private BCryptPasswordEncoder passwordEncoder;

    @PutMapping("/entidades/{id}/activar")
    @Transactional 
    public Entidad activarEntidad(@PathVariable UUID id) {
        Entidad entidad = entidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entidad no encontrada"));

        entidad.setEstado("ALTA");
        entidadRepository.save(entidad);

        Perfil nuevoPerfil = new Perfil();
        nuevoPerfil.setId(id); 
        nuevoPerfil.setEmail(entidad.getEmail());
        
        // IMPORTANTE: Como ya viene hasheada de la tabla 'entidades', solo la copiamos
        nuevoPerfil.setPassword(entidad.getPassword()); 
        
        nuevoPerfil.setNombreCompleto(entidad.getNombre());
        nuevoPerfil.setRol("GESTOR");
        nuevoPerfil.setEstaBaneado(false);

        perfilRepository.save(nuevoPerfil);
        return entidad;
    }
}