package cl.duoc.usuarios.controller;

import cl.duoc.usuarios.dto.UsuarioCreateDTO;
import cl.duoc.usuarios.dto.UsuarioDTO;
import cl.duoc.usuarios.service.UsuarioService;

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

@Tag(name = "Usuarios", description = "Operaciones de gestión de usuarios del sistema de aduanas")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // ── GET /api/v1/usuarios ───────────────────────────────────────────────────
    @Operation(
        summary = "Listar todos los usuarios",
        description = "Retorna la lista completa de usuarios registrados en el sistema."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // ── GET /api/v1/usuarios/{rut} ─────────────────────────────────────────────
    @Operation(summary = "Buscar usuario por RUT")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{rut}")
    public ResponseEntity<UsuarioDTO> obtenerPorRut(
            @Parameter(description = "RUT del usuario (ej: 12345678-9)", required = true) 
            @PathVariable String rut) {
        return ResponseEntity.ok(service.buscarPorRut(rut));
    }

    // ── POST /api/v1/usuarios ──────────────────────────────────────────────────
    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea un nuevo usuario en el sistema validando sus datos y disponibilidad del servicio."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o RUT ya registrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al procesar la solicitud"),
        @ApiResponse(responseCode = "503", description = "Servicio de base de datos no disponible")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del nuevo usuario a registrar"
            )
            @Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    // ── PUT /api/v1/usuarios/{rut} ─────────────────────────────────────────────
    @Operation(
        summary = "Actualizar usuario",
        description = "Modifica los datos de un usuario existente buscando por su RUT."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{rut}")
    public ResponseEntity<UsuarioDTO> actualizar(
            @Parameter(description = "RUT del usuario a actualizar", required = true) 
            @PathVariable String rut, 
            
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos para actualizar al usuario"
            )
            @Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(rut, dto));
    }

    // ── DELETE /api/v1/usuarios/{rut} ──────────────────────────────────────────
    @Operation(
        summary = "Eliminar usuario",
        description = "Elimina a un usuario del sistema mediante su RUT."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente (No Content)"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "RUT del usuario a eliminar", required = true) 
            @PathVariable String rut) {
        service.eliminar(rut);
        return ResponseEntity.noContent().build();
    }
}