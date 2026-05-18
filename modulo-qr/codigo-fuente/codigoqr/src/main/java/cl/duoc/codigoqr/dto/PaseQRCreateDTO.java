package cl.duoc.codigoqr.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PaseQRCreateDTO {
    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "El formato del RUT debe ser válido")
    private String rutCiudadano;

    @NotBlank(message = "Debe especificar el tipo de pase")
    private String tipoPase;

    @NotBlank(message = "El estado inicial es obligatorio")
    private String estado;
}