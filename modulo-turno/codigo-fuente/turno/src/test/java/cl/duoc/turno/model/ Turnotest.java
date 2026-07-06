package cl.duoc.turno.model;

import cl.duoc.turno.model.Turno;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TurnoTest {

    @Test
    @DisplayName("Debe crear un turno vacío y asignar valores con setters")
    void testSettersYGetters() {
        // Arrange
        Turno turno = new Turno();
        LocalDateTime inicio = LocalDateTime.of(2026, 6, 1, 8, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 6, 1, 16, 0);

        // Act
        turno.setId(1L);
        turno.setRutFuncionario("12345678-9");
        turno.setPuesto("Control de Aduana");
        turno.setFechaInicio(inicio);
        turno.setFechaFin(fin);
        turno.setEstado("ACTIVO");

        // Assert
        assertEquals(1L, turno.getId());
        assertEquals("12345678-9", turno.getRutFuncionario());
        assertEquals("Control de Aduana", turno.getPuesto());
        assertEquals(inicio, turno.getFechaInicio());
        assertEquals(fin, turno.getFechaFin());
        assertEquals("ACTIVO", turno.getEstado());
    }

    @Test
    @DisplayName("Debe instanciar correctamente usando el constructor completo")
    void testConstructorCompleto() {
        // Arrange
        Turno turno = new Turno();

        // Act
        turno.setId(2L);
        turno.setRutFuncionario("98765432-1");
        turno.setPuesto("Revisión de Equipaje");
        turno.setEstado("INACTIVO");

        // Assert
        assertEquals(2L, turno.getId());
        assertEquals("98765432-1", turno.getRutFuncionario());
        assertEquals("INACTIVO", turno.getEstado());
    }
}