package cl.duoc.vehiculos.dto;

import lombok.Data;

@Data
public class VehiculoDTO {
    private Long id;
    private String vin;
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private String paisOrigen;
    private String rutDueno;
}