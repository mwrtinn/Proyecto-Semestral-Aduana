package cl.duoc.turno.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TurnoDTO {
    private Long id;
    private String rutFuncionario;
    private String puesto;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
}