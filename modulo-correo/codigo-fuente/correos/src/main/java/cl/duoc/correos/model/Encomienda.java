package cl.duoc.correos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "encomiendas")
@Data
public class Encomienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_number", unique = true, nullable = false)
    private String trackingNumber;

    @Column(name = "rut_destinatario", nullable = false)
    private String rutDestinatario;

    @Column(nullable = false)
    private Double pesoKg;

    @Column(name = "descripcion_contenido", nullable = false)
    private String descripcionContenido;

    @Column(nullable = false)
    private String estado; 
}