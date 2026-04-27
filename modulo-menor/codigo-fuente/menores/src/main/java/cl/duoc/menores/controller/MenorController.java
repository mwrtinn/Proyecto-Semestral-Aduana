package cl.duoc.menores.controller;

import cl.duoc.menores.model.Menor;
import cl.duoc.menores.service.MenorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menores")
public class MenorController {

    @Autowired
    private MenorService service;

    @GetMapping
    public ResponseEntity<List<Menor>> obtenerTodos() {
        List<Menor> lista = service.obtenerTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/registro")
    public ResponseEntity<Menor> registrar(@Valid @RequestBody Menor menor) {
        Menor nuevo = service.guardar(menor);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Menor> buscarPorRut(@PathVariable String rut) {
        Menor m = service.buscarPorRut(rut);
        if (m != null) {
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{rut}")
    public ResponseEntity<Menor> actualizar(@PathVariable String rut, @Valid @RequestBody Menor datos) {
        Menor actualizado = service.actualizar(rut, datos);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<String> eliminar(@PathVariable String rut) {
        service.eliminarPorRut(rut);
        return new ResponseEntity<>("Registro de menor eliminado correctamente", HttpStatus.OK);
    }
}