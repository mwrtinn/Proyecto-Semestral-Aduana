package cl.duoc.declaraciones.controller;

import cl.duoc.declaraciones.dto.DeclaracionCreateDTO;
import cl.duoc.declaraciones.dto.DeclaracionDTO;
import cl.duoc.declaraciones.service.DeclaracionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/declaraciones")
public class DeclaracionController {

    @Autowired
    private DeclaracionService declaracionService;

    @PostMapping
    public ResponseEntity<DeclaracionDTO> crear(@Valid @RequestBody DeclaracionCreateDTO request) {
        return new ResponseEntity<>(declaracionService.registrar(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DeclaracionDTO>> listar() {
        return new ResponseEntity<>(declaracionService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeclaracionDTO> buscar(@PathVariable Long id) {
        return new ResponseEntity<>(declaracionService.obtenerPorId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeclaracionDTO> editar(@PathVariable Long id, @Valid @RequestBody DeclaracionCreateDTO request) {
        return new ResponseEntity<>(declaracionService.actualizar(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        declaracionService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}