package cl.duoc.mascotas.controller;

import cl.duoc.mascotas.dto.MascotaCreateDTO;
import cl.duoc.mascotas.dto.MascotaDTO;
import cl.duoc.mascotas.service.MascotaService;

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

@Tag(name = "Mascotas", description = "Operaciones de gestión y registro de mascotas en el sistema")
@RestController
@RequestMapping("/api/v1/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService service;

    // ── GET /api/v1/mascotas ──────────────────────────────────────────────────
    @Operation(
        summary = "Listar todas las mascotas",
        description = "Retorna la lista completa de mascotas registradas en el sistema."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ── GET /api/v1/mascotas/{microchip} ────────────────────────────────────────
    @Operation(summary = "Buscar mascota por microchip")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{microchip}")
    public ResponseEntity<MascotaDTO> obtenerPorMicrochip(
            @Parameter(description = "Código de microchip de la mascota (ej: 981020000000000)", required = true)
            @PathVariable String microchip) {
        return ResponseEntity.ok(service.obtener(microchip));
    }

    // ── POST /api/v1/mascotas ─────────────────────────────────────────────────
    @Operation(
        summary = "Registrar nueva mascota",
        description = "Crea un nuevo registro de mascota en el sistema validando sus datos y disponibilidad del servicio."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Mascota creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o microchip ya registrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al procesar la solicitud"),
        @ApiResponse(responseCode = "503", description = "Servicio de base de datos no disponible")
    })
    @PostMapping
    public ResponseEntity<MascotaDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos de la nueva mascota a registrar"
            )
            @Valid @RequestBody MascotaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto));
    }

    // ── PUT /api/v1/mascotas/{microchip} ────────────────────────────────────────
    @Operation(
        summary = "Actualizar mascota",
        description = "Modifica los datos de una mascota existente buscando por su código de microchip."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{microchip}")
    public ResponseEntity<MascotaDTO> actualizar(
            @Parameter(description = "Código de microchip de la mascota a actualizar", required = true)
            @PathVariable String microchip,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos para actualizar el registro de la mascota"
            )
            @Valid @RequestBody MascotaCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(microchip, dto));
    }

    // ── DELETE /api/v1/mascotas/{microchip} ─────────────────────────────────────
    @Operation(
        summary = "Eliminar mascota",
        description = "Elimina una mascota del sistema de forma permanente mediante su microchip."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Mascota eliminada exitosamente (No Content)"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{microchip}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Código de microchip de la mascota a eliminar", required = true)
            @PathVariable String microchip) {
        service.eliminar(microchip);
        return ResponseEntity.noContent().build();
    }
}