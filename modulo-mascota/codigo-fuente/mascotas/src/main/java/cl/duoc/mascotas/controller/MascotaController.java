package cl.duoc.mascotas.controller;

import cl.duoc.mascotas.dto.MascotaCreateDTO;
import cl.duoc.mascotas.dto.MascotaDTO;
import cl.duoc.mascotas.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<MascotaDTO> crear(@Valid @RequestBody MascotaCreateDTO request) {
        return new ResponseEntity<>(mascotaService.registrar(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MascotaDTO>> listar() {
        return new ResponseEntity<>(mascotaService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{microchip}")
    public ResponseEntity<MascotaDTO> buscar(@PathVariable String microchip) {
        return new ResponseEntity<>(mascotaService.obtener(microchip), HttpStatus.OK);
    }

    @PutMapping("/{microchip}")
    public ResponseEntity<MascotaDTO> editar(@PathVariable String microchip, @Valid @RequestBody MascotaCreateDTO request) {
        return new ResponseEntity<>(mascotaService.actualizar(microchip, request), HttpStatus.OK);
    }

    @DeleteMapping("/{microchip}")
    public ResponseEntity<Void> borrar(@PathVariable String microchip) {
        mascotaService.eliminar(microchip);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}