package cl.duoc.declaraciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "declaraciones")
@Data
public class Declaracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUT del declarante es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT no válido")
    private String rutDeclarante;

    @NotBlank(message = "La descripción de los artículos es obligatoria")
    private String descripcionArticulos;

    @NotNull(message = "El valor estimado es obligatorio")
    @PositiveOrZero(message = "El valor debe ser mayor o igual a cero")
    private Double valorEstimadoUsd;

    @NotNull(message = "Debe declarar si trae alimentos o productos de origen animal/vegetal")
    private Boolean traeAlimentos;

    @NotBlank(message = "El país de procedencia es obligatorio")
    private String paisProcedencia;
}