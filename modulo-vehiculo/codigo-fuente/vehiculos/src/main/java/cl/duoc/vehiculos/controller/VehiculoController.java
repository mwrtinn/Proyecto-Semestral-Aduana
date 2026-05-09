package cl.duoc.vehiculos.controller;

import cl.duoc.vehiculos.dto.VehiculoCreateDTO;
import cl.duoc.vehiculos.dto.VehiculoDTO;
import cl.duoc.vehiculos.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService service;

    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{patente}")
    public ResponseEntity<VehiculoDTO> obtenerPorPatente(@PathVariable String patente) {
        return ResponseEntity.ok(service.buscarPorPatente(patente));
    }

    @PostMapping
    public ResponseEntity<VehiculoDTO> crear(@Valid @RequestBody VehiculoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{patente}")
    public ResponseEntity<VehiculoDTO> actualizar(
            @PathVariable String patente, 
            @Valid @RequestBody VehiculoCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(patente, dto));
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminar(@PathVariable String patente) {
        service.eliminar(patente);
        return ResponseEntity.noContent().build(); 
    }
}