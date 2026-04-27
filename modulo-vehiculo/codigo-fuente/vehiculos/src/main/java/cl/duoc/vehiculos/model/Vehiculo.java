package cl.duoc.vehiculos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "vehiculos")
@Data
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El VIN es obligatorio")
    @Size(min = 17, max = 17, message = "El VIN debe tener exactamente 17 caracteres")
    @Column(unique = true, nullable = false)
    private String vin;

    @NotBlank(message = "La patente es obligatoria")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{4}|[A-Z]{4}[0-9]{2}$", message = "Formato de patente chilena no válido (ej: ABCD12 o AB1234)")
    @Column(unique = true, nullable = false)
    private String patente;

    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;

    @NotBlank(message = "El modelo no puede estar vacío")
    private String modelo;

    @Min(value = 1900, message = "Año no válido")
    @Max(value = 2027, message = "El año no puede ser superior al actual")
    private Integer anio;

    @NotBlank(message = "Debe indicar el país de origen")
    private String paisOrigen;
}