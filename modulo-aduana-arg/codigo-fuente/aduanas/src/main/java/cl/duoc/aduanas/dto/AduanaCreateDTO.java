package cl.duoc.aduanas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AduanaCreateDTO {

    @NotBlank(message = "El RUT del ciudadano es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "El formato del RUT debe ser válido")
    private String rutCiudadano;

    @NotNull(message = "La fecha y hora del cruce son obligatorias")
    private LocalDateTime fechaHoraCruce;

    @NotBlank(message = "El complejo aduanero es obligatorio")
    private String complejoAduanero;

    @NotBlank(message = "El tipo de cruce (INGRESO/EGRESO) es obligatorio")
    private String tipoCruce;
}