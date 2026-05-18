package cl.duoc.turno.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TurnoCreateDTO {
    @NotBlank(message = "El RUT del funcionario es obligatorio")
    private String rutFuncionario;

    @NotBlank(message = "El puesto es obligatorio")
    private String puesto;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDateTime fechaFin;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}