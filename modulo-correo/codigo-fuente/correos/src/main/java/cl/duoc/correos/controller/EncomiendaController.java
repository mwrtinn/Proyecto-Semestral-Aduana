package cl.duoc.correos.controller;

import cl.duoc.correos.dto.EncomiendaCreateDTO;
import cl.duoc.correos.dto.EncomiendaDTO;
import cl.duoc.correos.service.EncomiendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/encomiendas")
public class EncomiendaController {

    @Autowired
    private EncomiendaService encomiendaService;

    @PostMapping
    public ResponseEntity<EncomiendaDTO> crear(@Valid @RequestBody EncomiendaCreateDTO request) {
        return new ResponseEntity<>(encomiendaService.registrar(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EncomiendaDTO>> listar() {
        return new ResponseEntity<>(encomiendaService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{trackingNumber}")
    public ResponseEntity<EncomiendaDTO> buscar(@PathVariable String trackingNumber) {
        return new ResponseEntity<>(encomiendaService.obtener(trackingNumber), HttpStatus.OK);
    }

    @PutMapping("/{trackingNumber}")
    public ResponseEntity<EncomiendaDTO> editar(@PathVariable String trackingNumber, @Valid @RequestBody EncomiendaCreateDTO request) {
        return new ResponseEntity<>(encomiendaService.actualizar(trackingNumber, request), HttpStatus.OK);
    }

    @DeleteMapping("/{trackingNumber}")
    public ResponseEntity<Void> borrar(@PathVariable String trackingNumber) {
        encomiendaService.eliminar(trackingNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}