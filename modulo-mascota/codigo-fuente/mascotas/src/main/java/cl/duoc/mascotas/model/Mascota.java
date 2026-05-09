package cl.duoc.mascotas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mascotas")
@Data
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false, length = 15)
    private String microchip;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private String raza;

    @Column(nullable = false)
    private Integer edad;

    @Column(name = "vacuna_antirrabica", nullable = false)
    private String vacunaAntirrabica;

    @Column(name = "numero_certificado", nullable = false)
    private String numeroCertificado;

    @Column(name = "pais_origen", nullable = false)
    private String paisOrigen;

    @Column(name = "rut_dueno", nullable = false)
    private String rutDueno;
}