package cl.duoc.correos.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EncomiendaCreateDTO {
    @NotBlank(message = "El número de tracking es obligatorio")
    private String trackingNumber;

    @NotBlank(message = "El RUT del destinatario es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "El formato del RUT debe ser válido")
    private String rutDestinatario;

    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser mayor a 0")
    private Double pesoKg;

    @NotBlank(message = "Debe describir el contenido del paquete")
    private String descripcionContenido;

    @NotBlank(message = "El estado (RETENIDO/LIBERADO) es obligatorio")
    private String estado;
}