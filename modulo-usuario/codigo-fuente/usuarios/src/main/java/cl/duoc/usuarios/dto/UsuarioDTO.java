package cl.duoc.usuarios.dto;

import cl.duoc.usuarios.model.Rol; 
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String rut;
    private String email;
    private Rol rol; 
}