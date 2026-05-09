package cl.duoc.mascotas.client;

import cl.duoc.mascotas.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuario", url = "${usuarios.service.url}")
public interface UsuarioFeignClient {
    @GetMapping("/api/v1/usuarios/{rut}")
    UsuarioDTO obtenerPorRut(@PathVariable("rut") String rut);
}