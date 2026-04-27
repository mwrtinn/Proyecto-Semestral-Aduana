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
    public ResponseEntity<List<Declaracion>> obtenerTodos() {
        List<Declaracion> lista = service.obtenerTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/registro")
    public ResponseEntity<Declaracion> registrar(@Valid @RequestBody Declaracion declaracion) {
        Declaracion nueva = service.guardar(declaracion);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<List<Declaracion>> buscarPorRut(@PathVariable String rut) {
        List<Declaracion> lista = service.buscarPorRut(rut);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Declaracion> actualizar(@PathVariable Long id, @Valid @RequestBody Declaracion datos) {
        Declaracion actualizada = service.actualizar(id, datos);
        if (actualizada != null) {
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminarPorId(id);
        return new ResponseEntity<>("Declaración eliminada correctamente", HttpStatus.OK);
    }
}