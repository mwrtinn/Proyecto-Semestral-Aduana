package cl.duoc.mascotas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MascotaCreateDTO {

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    private String nombre;

    @NotBlank(message = "El número de Microchip es obligatorio")
    @Size(min = 15, max = 15, message = "El microchip debe tener exactamente 15 dígitos")
    private String microchip;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    @NotBlank(message = "La raza es obligatoria")
    private String raza;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer edad;

    @NotBlank(message = "Debe registrar la vacuna Antirrábica")
    private String vacunaAntirrabica;

    @NotBlank(message = "El certificado sanitario es obligatorio")
    private String numeroCertificado;

    @NotBlank(message = "País de procedencia es obligatorio")
    private String paisOrigen;

    @NotBlank(message = "El RUT del dueño es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "El formato del RUT debe ser 12345678-9")
    private String rutDueno;
}