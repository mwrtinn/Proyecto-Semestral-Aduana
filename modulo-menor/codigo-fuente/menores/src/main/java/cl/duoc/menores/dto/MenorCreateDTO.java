package cl.duoc.menores.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MenorCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT no válido (ej: 12345678-9)")
    private String rut;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 17, message = "Debe ser menor de 18 años")
    private Integer edad;

    @NotBlank(message = "El número de acta notarial es obligatorio")
    private String numeroActa;

    @NotBlank(message = "El RUT del tutor es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT del tutor no válido")
    private String rutTutor;
}