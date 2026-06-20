
package cl.duoc.menores.controller;

import cl.duoc.menores.dto.MenorCreateDTO;
import cl.duoc.menores.dto.MenorDTO;
import cl.duoc.menores.service.MenorService;

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

@Tag(name = "Menores", description = "Operaciones de gestión de menores del sistema de aduanas")
@RestController
@RequestMapping("/api/v1/menores")
public class MenorController {

    @Autowired
    private MenorService menorService;

    // ── GET /api/v1/menores ──────────────────────────────────────────────────
    @Operation(
        summary = "Listar todos los menores",
        description = "Retorna la lista completa de menores de edad registrados en el sistema."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<MenorDTO>> listar() {
        return ResponseEntity.ok(menorService.listar());
    }

    // ── GET /api/v1/menores/{rut} ────────────────────────────────────────
    @Operation(summary = "Buscar menor por RUT")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Menor encontrado"),
        @ApiResponse(responseCode = "404", description = "Menor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{rut}")
    public ResponseEntity<MenorDTO> buscar(
            @Parameter(description = "RUT del menor (ej: 12345678-9)", required = true)
            @PathVariable String rut) {
        return ResponseEntity.ok(menorService.obtener(rut));
    }

    // ── POST /api/v1/menores ─────────────────────────────────────────────────
    @Operation(
        summary = "Registrar nuevo menor",
        description = "Crea un nuevo registro de menor en el sistema validando sus datos y disponibilidad del servicio."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Menor registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o RUT ya registrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al procesar la solicitud"),
        @ApiResponse(responseCode = "503", description = "Servicio de base de datos no disponible")
    })
    @PostMapping
    public ResponseEntity<MenorDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del nuevo menor a registrar"
            )
            @Valid @RequestBody MenorCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menorService.registrar(request));
    }

    // ── PUT /api/v1/menores/{rut} ────────────────────────────────────────
    @Operation(
        summary = "Actualizar menor",
        description = "Modifica los datos de un menor existente buscando por su RUT."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Menor actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Menor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{rut}")
    public ResponseEntity<MenorDTO> editar(
            @Parameter(description = "RUT del menor a actualizar", required = true)
            @PathVariable String rut,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos para actualizar el registro del menor"
            )
            @Valid @RequestBody MenorCreateDTO request) {
        return ResponseEntity.ok(menorService.actualizar(rut, request));
    }

    // ── DELETE /api/v1/menores/{rut} ─────────────────────────────────────
    @Operation(
        summary = "Eliminar menor",
        description = "Elimina un menor del sistema mediante su RUT."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Menor eliminado exitosamente (No Content)"),
        @ApiResponse(responseCode = "404", description = "Menor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> borrar(
            @Parameter(description = "RUT del menor a eliminar", required = true)
            @PathVariable String rut) {
        menorService.eliminar(rut);
        return ResponseEntity.noContent().build();
    }
}