package cl.duoc.codigoqr.controller;

import cl.duoc.codigoqr.dto.PaseQRCreateDTO;
import cl.duoc.codigoqr.dto.PaseQRDTO;
import cl.duoc.codigoqr.service.PaseQRService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pases")
public class PaseQRController {

    @Autowired
    private PaseQRService paseQRService;

    @PostMapping
    public ResponseEntity<PaseQRDTO> crear(@Valid @RequestBody PaseQRCreateDTO request) {
        return new ResponseEntity<>(paseQRService.crearPase(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaseQRDTO>> listar() {
        return new ResponseEntity<>(paseQRService.listarPases(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaseQRDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PaseQRCreateDTO request) {
        return new ResponseEntity<>(paseQRService.actualizarPase(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        paseQRService.eliminarPase(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}