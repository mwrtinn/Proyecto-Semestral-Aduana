package cl.duoc.vehiculos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VehiculoCreateDTO {
    @NotBlank(message = "El VIN es obligatorio")
    @Size(min = 17, max = 17, message = "El VIN debe tener 17 caracteres")
    private String vin;

    @NotBlank(message = "La patente es obligatoria")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{4}|[A-Z]{4}[0-9]{2}$", message = "Formato de patente no válido")
    private String patente;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @Min(value = 1900, message = "Año no válido")
    private Integer anio;

    @NotBlank(message = "El país de origen es obligatorio")
    private String paisOrigen;

    @NotBlank(message = "El RUT del dueño es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT no válido")
    private String rutDueno;
}