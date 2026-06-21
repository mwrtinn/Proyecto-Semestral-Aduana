package cl.duoc.turno.controller;

import cl.duoc.turno.dto.TurnoCreateDTO;
import cl.duoc.turno.dto.TurnoDTO;
import cl.duoc.turno.service.TurnoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Turnos", description = "Operaciones de gestión de turnos de funcionarios del sistema de aduanas")
@RestController
@RequestMapping("/api/v1/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    // ── POST /api/v1/turnos ───────────────────────────────────────────────────
    @Operation(
        summary = "Registrar nuevo turno",
        description = "Asigna un nuevo turno a un funcionario validando que esté registrado en el sistema."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Turno creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Funcionario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor"),
        @ApiResponse(responseCode = "503", description = "Servicio de usuarios no disponible")
    })
    @PostMapping
    public ResponseEntity<TurnoDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del nuevo turno a registrar"
            )
            @Valid @RequestBody TurnoCreateDTO request) {
        return new ResponseEntity<>(turnoService.asignarTurno(request), HttpStatus.CREATED);
    }

    // ── GET /api/v1/turnos ────────────────────────────────────────────────────
    @Operation(
        summary = "Listar todos los turnos",
        description = "Retorna la lista completa de turnos registrados en el sistema."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listar() {
        return new ResponseEntity<>(turnoService.listarTurnos(), HttpStatus.OK);
    }

    // ── GET /api/v1/turnos/funcionario/{rut} ──────────────────────────────────
    @Operation(
        summary = "Listar turnos por funcionario",
        description = "Retorna todos los turnos asignados a un funcionario según su RUT."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Turnos encontrados"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/funcionario/{rut}")
    public ResponseEntity<List<TurnoDTO>> listarPorFuncionario(
            @Parameter(description = "RUT del funcionario (ej: 12345678-9)", required = true)
            @PathVariable String rut) {
        return new ResponseEntity<>(turnoService.turnosPorFuncionario(rut), HttpStatus.OK);
    }

    // ── PUT /api/v1/turnos/{id} ───────────────────────────────────────────────
    @Operation(
        summary = "Actualizar turno",
        description = "Modifica los datos de un turno existente buscando por su ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Turno actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TurnoDTO> actualizar(
            @Parameter(description = "ID del turno a actualizar", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos para actualizar el turno"
            )
            @Valid @RequestBody TurnoCreateDTO request) {
        return new ResponseEntity<>(turnoService.actualizarTurno(id, request), HttpStatus.OK);
    }

    // ── DELETE /api/v1/turnos/{id} ────────────────────────────────────────────
    @Operation(
        summary = "Eliminar turno",
        description = "Elimina un turno del sistema mediante su ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Turno eliminado exitosamente (No Content)"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(
            @Parameter(description = "ID del turno a eliminar", required = true)
            @PathVariable Long id) {
        turnoService.eliminarTurno(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}