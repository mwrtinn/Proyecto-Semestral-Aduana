package cl.duoc.menores.controller;

import cl.duoc.menores.dto.MenorCreateDTO;
import cl.duoc.menores.dto.MenorDTO;
import cl.duoc.menores.service.MenorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menores")
public class MenorController {

    @Autowired
    private MenorService menorService;

    @PostMapping
    public ResponseEntity<MenorDTO> crear(@Valid @RequestBody MenorCreateDTO request) {
        return new ResponseEntity<>(menorService.registrar(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MenorDTO>> listar() {
        return new ResponseEntity<>(menorService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<MenorDTO> buscar(@PathVariable String rut) {
        return new ResponseEntity<>(menorService.obtener(rut), HttpStatus.OK);
    }

    @PutMapping("/{rut}")
    public ResponseEntity<MenorDTO> editar(@PathVariable String rut, @Valid @RequestBody MenorCreateDTO request) {
        return new ResponseEntity<>(menorService.actualizar(rut, request), HttpStatus.OK);
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> borrar(@PathVariable String rut) {
        menorService.eliminar(rut);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}