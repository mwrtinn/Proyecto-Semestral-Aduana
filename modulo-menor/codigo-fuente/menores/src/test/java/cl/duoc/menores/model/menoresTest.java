package cl.duoc.menores.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MenorTest {

    @Test
    @DisplayName("Debe crear un menor vacío y asignar valores con setters")
    void testSettersYGetters() {
        // Given
        Menor menor = new Menor();

        // When
        menor.setId(1L);
        menor.setNombre("Pedro Mármol");
        menor.setRut("12345678-9");
        menor.setEdad(14);
        menor.setNumeroActa("ACTA-2026-001");
        menor.setRutTutor("98765432-1");

        // Then
        assertEquals(1L, menor.getId());
        assertEquals("Pedro Mármol", menor.getNombre());
        assertEquals("12345678-9", menor.getRut());
        assertEquals(14, menor.getEdad());
        assertEquals("ACTA-2026-001", menor.getNumeroActa());
        assertEquals("98765432-1", menor.getRutTutor());
    }

    @Test
    @DisplayName("Debe instanciar correctamente usando el constructor completo")
    void testConstructorCompleto() {
        // When
        Menor menor = new Menor();
        menor.setId(2L);
        menor.setNombre("Ana López");
        menor.setRut("23456789-0");
        menor.setEdad(10);
        menor.setNumeroActa("ACTA-2026-002");
        menor.setRutTutor("11223344-5");

        // Then
        assertEquals(2L, menor.getId());
        assertEquals("Ana López", menor.getNombre());
        assertEquals("23456789-0", menor.getRut());
    }
}