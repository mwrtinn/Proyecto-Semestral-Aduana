package cl.duoc.mascotas.repository;

import cl.duoc.mascotas.model.Mascota;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
class MascotaRepositoryTest {

    @Autowired
    private MascotaRepository repository;

    @Test
    @DisplayName("Debe guardar una mascota (Prueba de save)")
    void probarSave() {
        // Arrange
        Mascota mascota = new Mascota();
        mascota.setNombre("Firulais");
        mascota.setMicrochip("981020000123456");
        mascota.setEspecie("Canino");
        mascota.setRaza("Quiltro");
        mascota.setEdad(3);
        mascota.setVacunaAntirrabica("Al día");
        mascota.setNumeroCertificado("CERT-001");
        mascota.setPaisOrigen("Chile");
        mascota.setRutDueno("12345678-9");

        // Act
        Mascota guardado = repository.save(mascota);

        // Assert
        assertNotNull(guardado.getId());
    }

    @Test
    @DisplayName("Debe buscar una mascota por ID (Prueba de findById)")
    void probarFindById() {
        // Arrange
        Mascota mascota = new Mascota();
        mascota.setNombre("Michi");
        mascota.setMicrochip("981020000654321");
        mascota.setEspecie("Felino");
        mascota.setRaza("Siamés");
        mascota.setEdad(2);
        mascota.setVacunaAntirrabica("Al día");
        mascota.setNumeroCertificado("CERT-002");
        mascota.setPaisOrigen("Perú");
        mascota.setRutDueno("98765432-1");
        
        Mascota guardado = repository.save(mascota);

        // Act
        Optional<Mascota> encontrado = repository.findById(guardado.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals(guardado.getId(), encontrado.get().getId());
    }

    @Test
    @DisplayName("Debe listar todas las mascotas (Prueba de findAll)")
    void probarFindAll() {
        // Arrange
        Mascota m1 = new Mascota(); 
        m1.setNombre("Mascota Uno");
        m1.setMicrochip("111111111111111");
        m1.setEspecie("Canino");
        m1.setRaza("Pug");
        m1.setEdad(1);
        m1.setVacunaAntirrabica("Sí");
        m1.setNumeroCertificado("CERT-111");
        m1.setPaisOrigen("Chile");
        m1.setRutDueno("11111111-1");
        
        Mascota m2 = new Mascota(); 
        m2.setNombre("Mascota Dos");
        m2.setMicrochip("222222222222222");
        m2.setEspecie("Felino");
        m2.setRaza("Persa");
        m2.setEdad(2);
        m2.setVacunaAntirrabica("Sí");
        m2.setNumeroCertificado("CERT-222");
        m2.setPaisOrigen("Argentina");
        m2.setRutDueno("22222222-2");
        
        repository.save(m1);
        repository.save(m2);

        // Act
        List<Mascota> lista = repository.findAll();

        // Assert
        assertTrue(lista.size() >= 2);
    }
}