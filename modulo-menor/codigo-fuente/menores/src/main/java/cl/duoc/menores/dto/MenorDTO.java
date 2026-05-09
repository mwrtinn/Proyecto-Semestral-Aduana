package cl.duoc.menores.dto;

import lombok.Data;

@Data
public class MenorDTO {
    private String nombre;
    private String rut;
    private Integer edad;
    private String numeroActa;
    private String rutTutor;
}