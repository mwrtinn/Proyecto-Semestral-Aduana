package cl.duoc.vehiculos.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehiculoTest {

    @Test
    @DisplayName("Debe crear un vehículo vacío y asignar valores con setters")
    void testSettersYGetters() {
        // Arrange
        Vehiculo vehiculo = new Vehiculo();

        // Act
        vehiculo.setId(1L);
        vehiculo.setVin("1HGBH41JXMN109186");
        vehiculo.setPatente("ABCD12");
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Corolla");
        vehiculo.setAnio(2022);
        vehiculo.setPaisOrigen("Japón");
        vehiculo.setRutDueno("12345678-9");

        // Assert
        assertEquals(1L, vehiculo.getId());
        assertEquals("1HGBH41JXMN109186", vehiculo.getVin());
        assertEquals("ABCD12", vehiculo.getPatente());
        assertEquals("Toyota", vehiculo.getMarca());
        assertEquals("Corolla", vehiculo.getModelo());
        assertEquals(2022, vehiculo.getAnio());
        assertEquals("Japón", vehiculo.getPaisOrigen());
        assertEquals("12345678-9", vehiculo.getRutDueno());
    }

    @Test
    @DisplayName("Debe instanciar correctamente usando el constructor completo")
    void testConstructorCompleto() {
        // Arrange
        Vehiculo vehiculo = new Vehiculo();
        
        // Act
        vehiculo.setId(2L);
        vehiculo.setVin("2T1BURHE0JC043821");
        vehiculo.setPatente("EFGH34");
        vehiculo.setMarca("Honda");
        vehiculo.setModelo("Civic");
        vehiculo.setAnio(2021);
        vehiculo.setPaisOrigen("México");
        vehiculo.setRutDueno("98765432-1");

        // Assert
        assertEquals(2L, vehiculo.getId());
        assertEquals("Honda", vehiculo.getMarca());
        assertEquals("EFGH34", vehiculo.getPatente());
    }
}