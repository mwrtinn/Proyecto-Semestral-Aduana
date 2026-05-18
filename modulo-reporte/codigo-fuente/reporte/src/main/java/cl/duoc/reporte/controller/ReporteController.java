package cl.duoc.reporte.controller;

import cl.duoc.reporte.dto.ReporteCreateDTO;
import cl.duoc.reporte.dto.ReporteDTO;
import cl.duoc.reporte.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @PostMapping
    public ResponseEntity<ReporteDTO> crear(@Valid @RequestBody ReporteCreateDTO request) {
        return new ResponseEntity<>(reporteService.crearReporte(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReporteDTO>> listar() {
        return new ResponseEntity<>(reporteService.listarTodos(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ReporteCreateDTO request) {
        return new ResponseEntity<>(reporteService.actualizarReporte(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        reporteService.eliminarReporte(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}