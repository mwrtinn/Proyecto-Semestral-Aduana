package cl.duoc.codigoqr.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "pases_qr")
@Data
public class PaseQR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_uuid", unique = true, nullable = false, updatable = false)
    private String codigoUuid;

    @Column(name = "rut_ciudadano", nullable = false)
    private String rutCiudadano;

    @Column(name = "tipo_pase", nullable = false)
    private String tipoPase; 

    @Column(nullable = false)
    private String estado; 

    @PrePersist
    public void generarUuid() {
        if (this.codigoUuid == null) {
            this.codigoUuid = UUID.randomUUID().toString();
        }
    }
}