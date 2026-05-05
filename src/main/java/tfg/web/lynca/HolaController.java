package tfg.web.lynca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Necesario para redirecciones
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody; // Necesario para seguir enviando datos
import tfg.web.lynca.modelos.Pista;
import tfg.web.lynca.repositorios.PistaRepository;
import java.util.List;

@Controller
public class HolaController {

    @Autowired
    private PistaRepository pistaRepository;

    // Ruta de prueba: seguirá devolviendo texto gracias a @ResponseBody
    @GetMapping("/hola")
    @ResponseBody
    public String hola() {
        return "Si ves esto, el radar de Spring funciona.";
    }

    // Ruta de datos: seguirá devolviendo el JSON de las pistas
    @GetMapping("/pistas")
    @ResponseBody
    public List<Pista> verPistas() {
        return pistaRepository.findAll();
    }

    // RUTA PRINCIPAL: Esta es la que arregla el error 404
    @GetMapping("/")
    public String home() {
        // Al entrar a la raíz, te manda automáticamente a tu index.html
        return "redirect:/vistas/publico/index.html";
    }
}