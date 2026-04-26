package cl.duoc.ms_mascotas.controller;

import cl.duoc.ms_mascotas.model.Mascota;
import cl.duoc.ms_mascotas.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService service;

    @GetMapping
    public ResponseEntity<List<Mascota>> listar() {
        return new ResponseEntity<>(service.obtenerTodas(), HttpStatus.OK);
    }

    @PostMapping("/registro")
    public ResponseEntity<Mascota> registrar(@Valid @RequestBody Mascota mascota) {
        return new ResponseEntity<>(service.guardar(mascota), HttpStatus.CREATED);
    }

    @GetMapping("/{microchip}")
    public ResponseEntity<Mascota> obtener(@PathVariable String microchip) {
        Mascota m = service.buscarPorMicrochip(microchip);
        if (m != null) {
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{microchip}")
    public ResponseEntity<String> eliminar(@PathVariable String microchip) {
        service.eliminarPorMicrochip(microchip);
        return new ResponseEntity<>("Mascota eliminada del registro", HttpStatus.OK);
    }
}