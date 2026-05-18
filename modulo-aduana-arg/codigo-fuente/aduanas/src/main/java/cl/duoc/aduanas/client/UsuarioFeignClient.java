package cl.duoc.aduanas.client;

import cl.duoc.aduanas.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuario", url = "${usuarios.service.url}")
public interface UsuarioFeignClient {
    
    @GetMapping("/{rut}")
    UsuarioDTO obtenerPorRut(@PathVariable("rut") String rut);
}