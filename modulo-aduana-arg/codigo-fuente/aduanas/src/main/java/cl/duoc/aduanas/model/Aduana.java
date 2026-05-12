package cl.duoc.aduanas.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "aduanas")
@Data
public class Aduana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rut_ciudadano", nullable = false)
    private String rutCiudadano;

    @Column(name = "fecha_hora_cruce", nullable = false)
    private LocalDateTime fechaHoraCruce;

    @Column(name = "complejo_aduanero", nullable = false)
    private String complejoAduanero;

    @Column(name = "tipo_cruce", nullable = false)
    private String tipoCruce; // Ej: INGRESO o EGRESO
}