package cl.duoc.menores.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "menores")
@Data
public class Menor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String rut;

    @Column(nullable = false)
    private Integer edad;

    @Column(name = "numero_acta", nullable = false)
    private String numeroActa;

    @Column(name = "rut_tutor", nullable = false)
    private String rutTutor;
}