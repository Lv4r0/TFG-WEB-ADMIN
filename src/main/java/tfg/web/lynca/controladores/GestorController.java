package tfg.web.lynca.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tfg.web.lynca.modelos.*;
import tfg.web.lynca.repositorios.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gestor")
public class GestorController {

    @Autowired
    private InstalacionRepository instalacionRepo;

    @Autowired
    private PistaRepository pistaRepo;

    @Autowired
    private ImagenInstalacionRepository imagenRepo;

    @Autowired
    private ImagenPistaRepository imagenPistaRepo; // Nuevo repositorio para fotos de pistas

    // ==========================================
    // 1. GESTIÓN DE INSTALACIONES
    // ==========================================

    @GetMapping("/instalaciones/{entidadId}")
    public List<Instalacion> listarMisInstalaciones(@PathVariable UUID entidadId) {
        System.out.println(">>> Cargando instalaciones para la entidad: " + entidadId);
        return instalacionRepo.findByEntidadId(entidadId);
    }

    @PostMapping("/instalaciones")
    public ResponseEntity<Instalacion> crearInstalacion(@RequestBody Instalacion nuevaInstalacion) {
        System.out.println(">>> Recibida nueva instalación: " + nuevaInstalacion.getNombre());

        if (nuevaInstalacion.getImagenes() != null && !nuevaInstalacion.getImagenes().isEmpty()) {
            for (ImagenInstalacion img : nuevaInstalacion.getImagenes()) {
                img.setInstalacion(nuevaInstalacion);
            }
        }
        
        Instalacion guardada = instalacionRepo.save(nuevaInstalacion);
        return ResponseEntity.ok(guardada);
    }

    @GetMapping("/instalaciones/detalle/{id}")
    public ResponseEntity<Instalacion> obtenerInstalacion(@PathVariable UUID id) {
        Instalacion inst = instalacionRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Instalación no encontrada"));
        return ResponseEntity.ok(inst);
    }

    @PutMapping("/instalaciones/{id}")
    public ResponseEntity<Instalacion> actualizarInstalacion(@PathVariable UUID id, @RequestBody Instalacion datosActualizados) {
        System.out.println(">>> Actualizando instalación ID: " + id);
        
        Instalacion existente = instalacionRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe la instalación"));
        
        existente.setNombre(datosActualizados.getNombre());
        existente.setDireccion(datosActualizados.getDireccion());
        existente.setDescripcion(datosActualizados.getDescripcion());
        
        if (datosActualizados.getImagenes() != null && !datosActualizados.getImagenes().isEmpty()) {
            for (ImagenInstalacion img : datosActualizados.getImagenes()) {
                img.setInstalacion(existente);
                existente.getImagenes().add(img);
            }
        }

        return ResponseEntity.ok(instalacionRepo.save(existente));
    }

    @DeleteMapping("/imagenes/{id}")
    public ResponseEntity<?> borrarImagen(@PathVariable UUID id) {
        imagenRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // ==========================================
    // 2. GESTIÓN DE PISTAS (ACTUALIZADO)
    // ==========================================

    // Listar pistas de una instalación
    @GetMapping("/pistas/{instalacionId}")
    public List<Pista> listarPistasPorInstalacion(@PathVariable UUID instalacionId) {
        return pistaRepo.findByInstalacionId(instalacionId);
    }

    // Obtener detalle de UNA pista específica
    @GetMapping("/pistas/detalle/{id}")
    public ResponseEntity<Pista> obtenerDetallePista(@PathVariable UUID id) {
        Pista pista = pistaRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Pista no encontrada"));
        return ResponseEntity.ok(pista);
    }

    // Añadir pista individual
    @PostMapping("/pistas")
    public ResponseEntity<Pista> añadirPista(@RequestBody Pista nuevaPista) {
        if (nuevaPista.getEstado() == null) {
            nuevaPista.setEstado("Libre");
        }
        Pista guardada = pistaRepo.save(nuevaPista);
        return ResponseEntity.ok(guardada);
    }

    // ACTUALIZACIÓN COMPLETA DE PISTA (Precio, Horario, Estado, Fotos)
    @PutMapping("/pistas/{id}")
    public ResponseEntity<Pista> actualizarPista(@PathVariable UUID id, @RequestBody Pista datos) {
        System.out.println(">>> Actualizando pista ID: " + id);
        Pista existente = pistaRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Pista no encontrada"));

        existente.setNombre(datos.getNombre());
        existente.setTipoDeporte(datos.getTipoDeporte());
        existente.setPrecioHora(datos.getPrecioHora());
        existente.setDescripcion(datos.getDescripcion());
        existente.setEstado(datos.getEstado());
        existente.setHorario(datos.getHorario());

        // Procesar fotos nuevas si las hay
        if (datos.getImagenes() != null && !datos.getImagenes().isEmpty()) {
            for (ImagenPista img : datos.getImagenes()) {
                img.setPista(existente); // <--- ESTA LÍNEA LLAMA AL MÉTODO QUE FALLABA
                existente.getImagenes().add(img);
            }
        }

        return ResponseEntity.ok(pistaRepo.save(existente));
    }

    // Borrar foto específica de una pista
    @DeleteMapping("/pistas/imagenes/{fotoId}")
    public ResponseEntity<?> borrarFotoPista(@PathVariable UUID fotoId) {
        imagenPistaRepo.deleteById(fotoId);
        return ResponseEntity.ok().build();
    }

    // Cambiar estado rápido (mantenemos por compatibilidad)
    @PatchMapping("/pistas/{id}/estado")
    public ResponseEntity<Pista> cambiarEstadoPista(@PathVariable UUID id, @RequestBody String nuevoEstado) {
        String estadoLimpio = nuevoEstado.replace("\"", "");
        Pista pista = pistaRepo.findById(id).orElseThrow();
        pista.setEstado(estadoLimpio);
        return ResponseEntity.ok(pistaRepo.save(pista));
    }
}