package cl.duoc.mascotas.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String rut;
    private String nombre;
    private String correo;
}