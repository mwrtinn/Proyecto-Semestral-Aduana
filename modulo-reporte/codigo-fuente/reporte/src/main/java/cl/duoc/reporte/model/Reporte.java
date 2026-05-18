package cl.duoc.reporte.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Data
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rut_generador", nullable = false)
    private String rutGenerador;

    @Column(nullable = false)
    private String tipoReporte; 

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private String gravedad; 
}