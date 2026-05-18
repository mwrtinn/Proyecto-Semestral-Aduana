package cl.duoc.reporte.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReporteCreateDTO {
    @NotBlank(message = "El RUT del generador es obligatorio")
    private String rutGenerador;

    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String tipoReporte;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    @NotBlank(message = "La gravedad es obligatoria")
    private String gravedad;
}