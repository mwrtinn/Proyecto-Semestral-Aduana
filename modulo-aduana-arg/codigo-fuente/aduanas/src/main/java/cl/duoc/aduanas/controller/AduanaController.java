package cl.duoc.aduanas.controller;

import cl.duoc.aduanas.dto.AduanaCreateDTO;
import cl.duoc.aduanas.dto.AduanaDTO;
import cl.duoc.aduanas.service.AduanaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aduanas")
public class AduanaController {

    @Autowired
    private AduanaService aduanaService;

    @PostMapping
    public ResponseEntity<AduanaDTO> crear(@Valid @RequestBody AduanaCreateDTO request) {
        return new ResponseEntity<>(aduanaService.registrar(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AduanaDTO>> listar() {
        return new ResponseEntity<>(aduanaService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AduanaDTO> buscar(@PathVariable Long id) {
        return new ResponseEntity<>(aduanaService.obtener(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AduanaDTO> editar(@PathVariable Long id, @Valid @RequestBody AduanaCreateDTO request) {
        return new ResponseEntity<>(aduanaService.actualizar(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        aduanaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}