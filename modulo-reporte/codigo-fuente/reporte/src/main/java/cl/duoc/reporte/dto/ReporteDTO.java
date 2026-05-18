package cl.duoc.reporte.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReporteDTO {
    private Long id;
    private String rutGenerador;
    private String tipoReporte;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private String gravedad;
}