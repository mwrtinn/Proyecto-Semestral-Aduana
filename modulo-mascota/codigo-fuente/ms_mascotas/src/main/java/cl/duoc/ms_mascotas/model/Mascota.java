package cl.duoc.ms_mascotas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "mascotas")
@Data
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    private String nombre;

    @NotBlank(message = "El número de Microchip es obligatorio")
    @Size(min = 15, max = 15, message = "El microchip debe tener exactamente 15 dígitos")
    @Column(unique = true, nullable = false)
    private String microchip;

    @NotBlank(message = "La especie es obligatoria (Canino, Felino, etc.)")
    private String especie;

    @NotBlank(message = "La raza es obligatoria")
    private String raza;

    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer edad;

    @NotBlank(message = "Debe registrar la vacuna Antirrábica")
    private String vacunaAntirrabica; 

    @NotBlank(message = "El certificado sanitario es obligatorio")
    private String numeroCertificado;

    @NotBlank(message = "País de procedencia es obligatorio")
    private String paisOrigen;
}