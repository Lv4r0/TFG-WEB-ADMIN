package tfg.web.lynca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tfg.web.lynca.modelos.Pista;
import tfg.web.lynca.repositorios.PistaRepository;
import java.util.List;

@RestController
public class HolaController {

    @Autowired
    private PistaRepository pistaRepository;

    @GetMapping("/hola")
    public String hola() {
        return "Si ves esto, el radar de Spring funciona.";
    }

    @GetMapping("/pistas")
    public List<Pista> verPistas() {
        return pistaRepository.findAll();
    }
}