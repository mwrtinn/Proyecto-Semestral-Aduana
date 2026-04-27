package cl.duoc.vehiculos.controller;

import cl.duoc.vehiculos.model.Vehiculo;
import cl.duoc.vehiculos.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService service;

    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerTodos() {
        return new ResponseEntity<>(service.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping("/registro")
    public ResponseEntity<Vehiculo> registrar(@Valid @RequestBody Vehiculo vehiculo) {
        Vehiculo nuevo = service.guardar(vehiculo);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping("/{patente}")
    public ResponseEntity<Vehiculo> buscarPorPatente(@PathVariable String patente) {
        Vehiculo v = service.buscarPorPatente(patente);
        if (v != null) {
            return new ResponseEntity<>(v, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{patente}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable String patente, @Valid @RequestBody Vehiculo datos) {
        Vehiculo actualizado = service.actualizar(patente, datos);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<String> eliminar(@PathVariable String patente) {
        service.eliminarPorPatente(patente);
        return new ResponseEntity<>("Vehículo eliminado", HttpStatus.OK);
    }
}