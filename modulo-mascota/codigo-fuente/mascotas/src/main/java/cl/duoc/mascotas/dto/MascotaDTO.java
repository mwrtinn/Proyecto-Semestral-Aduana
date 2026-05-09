package cl.duoc.mascotas.dto;

import lombok.Data;

@Data
public class MascotaDTO {
    private Long id;
    private String nombre;
    private String microchip;
    private String especie;
    private String raza;
    private Integer edad;
    private String vacunaAntirrabica;
    private String numeroCertificado;
    private String paisOrigen;
    private String rutDueno;
}