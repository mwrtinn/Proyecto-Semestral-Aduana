package cl.duoc.usuarios.controller;

import cl.duoc.usuarios.dto.UsuarioCreateDTO;
import cl.duoc.usuarios.dto.UsuarioDTO;
import cl.duoc.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios") 
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{rut}")
    public ResponseEntity<UsuarioDTO> obtenerPorRut(@PathVariable String rut) {
        return ResponseEntity.ok(service.buscarPorRut(rut));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{rut}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable String rut, @Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(rut, dto));
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> eliminar(@PathVariable String rut) {
        service.eliminar(rut);
        return ResponseEntity.noContent().build();
    }
}