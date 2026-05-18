package cl.duoc.turno.controller;

import cl.duoc.turno.dto.TurnoCreateDTO;
import cl.duoc.turno.dto.TurnoDTO;
import cl.duoc.turno.service.TurnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @PostMapping
    public ResponseEntity<TurnoDTO> crear(@Valid @RequestBody TurnoCreateDTO request) {
        return new ResponseEntity<>(turnoService.asignarTurno(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listar() {
        return new ResponseEntity<>(turnoService.listarTurnos(), HttpStatus.OK);
    }

    @GetMapping("/funcionario/{rut}")
    public ResponseEntity<List<TurnoDTO>> listarPorFuncionario(@PathVariable String rut) {
        return new ResponseEntity<>(turnoService.turnosPorFuncionario(rut), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody TurnoCreateDTO request) {
        return new ResponseEntity<>(turnoService.actualizarTurno(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        turnoService.eliminarTurno(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}