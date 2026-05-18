package cl.duoc.correos.dto;
import lombok.Data;
@Data
public class EncomiendaDTO {
    private String trackingNumber;
    private String rutDestinatario;
    private Double pesoKg;
    private String descripcionContenido;
    private String estado;
}