package cl.duoc.codigoqr.dto;

import lombok.Data;

@Data
public class PaseQRDTO {
    private Long id;
    private String rutCiudadano;
    private String tipoPase;
    private String estado;
    private String codigoUuid;
}