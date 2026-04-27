package cl.duoc.declaraciones.controller;

import cl.duoc.declaraciones.model.Declaracion;
import cl.duoc.declaraciones.service.DeclaracionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/declaraciones")
public class DeclaracionController {

    @Autowired
    private DeclaracionService service;

    @GetMapping
    public ResponseEntity<List<Declaracion>> listar() {
        return new ResponseEntity<>(service.obtenerTodas(), HttpStatus.OK);
    }

    @PostMapping("/registro")
    public ResponseEntity<Declaracion> registrar(@Valid @RequestBody Declaracion declaracion) {
        return new ResponseEntity<>(service.guardar(declaracion), HttpStatus.CREATED);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Declaracion> obtener(@PathVariable String rut) {
        Declaracion d = service.buscarPorRut(rut);
        return (d != null) 
            ? new ResponseEntity<>(d, HttpStatus.OK) 
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{rut}")
    public ResponseEntity<Declaracion> actualizar(@PathVariable String rut, @Valid @RequestBody Declaracion datos) {
        Declaracion actualizada = service.actualizar(rut, datos);
        return (actualizada != null) 
            ? new ResponseEntity<>(actualizada, HttpStatus.OK) 
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<String> eliminar(@PathVariable String rut) {
        service.eliminarPorRut(rut);
        return new ResponseEntity<>("Declaración eliminada correctamente para el RUT: " + rut, HttpStatus.OK);
    }
}