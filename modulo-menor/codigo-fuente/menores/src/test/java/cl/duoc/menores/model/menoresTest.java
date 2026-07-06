package cl.duoc.menores.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MenorTest {

    @Test
    @DisplayName("Debe crear un menor vacío y asignar valores con setters")
    void testSettersYGetters() {
        // Arrange
        Menor menor = new Menor();

        // Act
        menor.setId(1L);
        menor.setNombre("Pedro Mármol");
        menor.setRut("12345678-9");
        menor.setEdad(14);
        menor.setNumeroActa("ACTA-2026-001");
        menor.setRutTutor("98765432-1");

        // Assert
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
        // Arrange
        Menor menor = new Menor();

        // Act
        menor.setId(2L);
        menor.setNombre("Ana López");
        menor.setRut("23456789-0");
        menor.setEdad(10);
        menor.setNumeroActa("ACTA-2026-002");
        menor.setRutTutor("11223344-5");

        // Assert
        assertEquals(2L, menor.getId());
        assertEquals("Ana López", menor.getNombre());
        assertEquals("23456789-0", menor.getRut());
    }
}
package cl.duoc.menores.repository;

import cl.duoc.menores.model.Menor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
class MenorRepositoryTest {

    @Autowired
    private MenorRepository menorRepository;

    @Test
    @DisplayName("Debe guardar y recuperar un menor correctamente usando findByRut")
    void testGuardarYBuscarPorRut() {
        // Arrange
        Menor menor = new Menor();
        menor.setNombre("Juan Pérez");
        menor.setRut("12345678-9");
        menor.setEdad(15);
        menor.setNumeroActa("ACT-001");
        menor.setRutTutor("87654321-0");
        menorRepository.save(menor);

        // Act
        Optional<Menor> guardado = menorRepository.findByRut("12345678-9");

        // Assert
        assertTrue(guardado.isPresent(), "El menor debería estar presente en la base de datos");
        assertEquals("Juan Pérez", guardado.get().getNombre());
        assertEquals("ACT-001", guardado.get().getNumeroActa());
    }

    @Test
    @DisplayName("Debe retornar vacío al buscar un RUT inexistente")
    void testBuscarRutInexistente() {
        // Arrange (No requiere preparación previa de datos)

        // Act
        Optional<Menor> guardado = menorRepository.findByRut("00000000-0");

        // Assert
        assertFalse(guardado.isPresent(), "El resultado debería ser vacío para un RUT inexistente");
    }
}