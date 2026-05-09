package cl.duoc.declaraciones.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "declaraciones")
@Data
public class Declaracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rut_declarante", nullable = false)
    private String rutDeclarante;

    @Column(name = "descripcion_articulos", nullable = false)
    private String descripcionArticulos;

    @Column(name = "valor_estimado_usd", nullable = false)
    private Double valorEstimadoUsd;

    @Column(name = "trae_alimentos", nullable = false)
    private Boolean traeAlimentos;

    @Column(name = "pais_procedencia", nullable = false)
    private String paisProcedencia;
}