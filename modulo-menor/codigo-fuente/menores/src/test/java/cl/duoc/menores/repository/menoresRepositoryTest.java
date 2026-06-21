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
        // Given
        Menor menor = new Menor();
        menor.setNombre("Juan Pérez");
        menor.setRut("12345678-9");
        menor.setEdad(15);
        menor.setNumeroActa("ACT-001");
        menor.setRutTutor("87654321-0");

        // When
        menorRepository.save(menor);
        Optional<Menor> guardado = menorRepository.findByRut("12345678-9");

        // Then
        assertTrue(guardado.isPresent(), "El menor debería estar presente en la base de datos");
        assertEquals("Juan Pérez", guardado.get().getNombre());
        assertEquals("ACT-001", guardado.get().getNumeroActa());
    }

    @Test
    @DisplayName("Debe retornar vacío al buscar un RUT inexistente")
    void testBuscarRutInexistente() {
        // When
        Optional<Menor> guardado = menorRepository.findByRut("00000000-0");

        // Then
        assertFalse(guardado.isPresent(), "El resultado debería ser vacío para un RUT inexistente");
    }
}