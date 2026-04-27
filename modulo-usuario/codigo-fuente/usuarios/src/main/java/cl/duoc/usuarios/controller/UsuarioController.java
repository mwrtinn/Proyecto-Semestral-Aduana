package cl.duoc.usuarios.controller;

import cl.duoc.usuarios.model.Usuario;
import cl.duoc.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        List<Usuario> lista = service.obtenerTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario usuario) {
        Usuario nuevo = service.guardar(usuario);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Usuario> buscarPorRut(@PathVariable String rut) {
        Usuario u = service.buscarPorRut(rut);
        if (u != null) {
            return new ResponseEntity<>(u, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{rut}")
    public ResponseEntity<Usuario> actualizar(@PathVariable String rut, @Valid @RequestBody Usuario datos) {
        Usuario actualizado = service.actualizar(rut, datos);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<String> eliminar(@PathVariable String rut) {
        service.eliminarPorRut(rut);
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
    }
}