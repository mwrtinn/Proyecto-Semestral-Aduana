package cl.duoc.vehiculos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vehiculos")
@Data
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 17)
    private String vin;

    @Column(unique = true, nullable = false)
    private String patente;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false)
    private String paisOrigen;

    @Column(nullable = false)
    private String rutDueno;
}