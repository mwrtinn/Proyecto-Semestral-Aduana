package cl.duoc.declaraciones.dto;

import lombok.Data;

@Data
public class DeclaracionDTO {
    private Long id; 
    private String rutDeclarante;
    private String descripcionArticulos;
    private Double valorEstimadoUsd;
    private Boolean traeAlimentos;
    private String paisProcedencia;
}