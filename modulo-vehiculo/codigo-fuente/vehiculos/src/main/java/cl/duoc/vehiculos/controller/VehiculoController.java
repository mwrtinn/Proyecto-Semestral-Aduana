package cl.duoc.vehiculos.controller;

import cl.duoc.vehiculos.dto.VehiculoCreateDTO;
import cl.duoc.vehiculos.dto.VehiculoDTO;
import cl.duoc.vehiculos.service.VehiculoService;

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

@Tag(name = "Vehículos", description = "Operaciones de gestión de vehículos del sistema de aduanas")
@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService service;

    // ── GET /api/v1/vehiculos ──────────────────────────────────────────────────
    @Operation(
        summary = "Listar todos los vehículos",
        description = "Retorna la lista completa de vehículos registrados en el sistema."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // ── GET /api/v1/vehiculos/{patente} ────────────────────────────────────────
    @Operation(summary = "Buscar vehículo por patente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{patente}")
    public ResponseEntity<VehiculoDTO> obtenerPorPatente(
            @Parameter(description = "Patente del vehículo (ej: ABCD12)", required = true)
            @PathVariable String patente) {
        return ResponseEntity.ok(service.buscarPorPatente(patente));
    }

    // ── POST /api/v1/vehiculos ─────────────────────────────────────────────────
    @Operation(
        summary = "Registrar nuevo vehículo",
        description = "Crea un nuevo vehículo en el sistema validando sus datos y disponibilidad del servicio."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Vehículo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o patente ya registrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al procesar la solicitud"),
        @ApiResponse(responseCode = "503", description = "Servicio de base de datos no disponible")
    })
    @PostMapping
    public ResponseEntity<VehiculoDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del nuevo vehículo a registrar"
            )
            @Valid @RequestBody VehiculoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    // ── PUT /api/v1/vehiculos/{patente} ────────────────────────────────────────
    @Operation(
        summary = "Actualizar vehículo",
        description = "Modifica los datos de un vehículo existente buscando por su patente."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{patente}")
    public ResponseEntity<VehiculoDTO> actualizar(
            @Parameter(description = "Patente del vehículo a actualizar", required = true)
            @PathVariable String patente,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos para actualizar el vehículo"
            )
            @Valid @RequestBody VehiculoCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(patente, dto));
    }

    // ── DELETE /api/v1/vehiculos/{patente} ─────────────────────────────────────
    @Operation(
        summary = "Eliminar vehículo",
        description = "Elimina un vehículo del sistema mediante su patente."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Vehículo eliminado exitosamente (No Content)"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Patente del vehículo a eliminar", required = true)
            @PathVariable String patente) {
        service.eliminar(patente);
        return ResponseEntity.noContent().build();
    }
}