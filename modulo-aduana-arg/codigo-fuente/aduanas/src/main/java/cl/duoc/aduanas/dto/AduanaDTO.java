package cl.duoc.aduanas.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AduanaDTO {
    private Long id;
    private String rutCiudadano;
    private LocalDateTime fechaHoraCruce;
    private String complejoAduanero;
    private String tipoCruce;
}