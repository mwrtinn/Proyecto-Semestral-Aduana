package cl.duoc.ms_mascotas.model;

import cl.duoc.mascotas.model.Mascota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MascotaTest {

    @Test
    @DisplayName("Debe crear una mascota vacía y asignar valores con setters")
    void testSettersYGetters() {
        
        Mascota mascota = new Mascota();

        
        mascota.setId(1L);
        mascota.setNombre("Firulais");
        mascota.setMicrochip("981020000123456");
        mascota.setEspecie("Canino");
        mascota.setRaza("Pastor Alemán");
        mascota.setEdad(3);
        mascota.setVacunaAntirrabica("Al día (10/10/2023)");
        mascota.setNumeroCertificado("CERT-2023-998877");
        mascota.setPaisOrigen("Argentina");
        mascota.setRutDueno("12345678-9");

        
        assertEquals(1L, mascota.getId());
        assertEquals("Firulais", mascota.getNombre());
        assertEquals("981020000123456", mascota.getMicrochip());
        assertEquals("Canino", mascota.getEspecie());
        assertEquals("Pastor Alemán", mascota.getRaza());
        assertEquals(3, mascota.getEdad());
        assertEquals("Al día (10/10/2023)", mascota.getVacunaAntirrabica());
        assertEquals("CERT-2023-998877", mascota.getNumeroCertificado());
        assertEquals("Argentina", mascota.getPaisOrigen());
        assertEquals("12345678-9", mascota.getRutDueno());
    }

    @Test
    @DisplayName("Debe instanciar y asignar correctamente los datos de una segunda mascota")
    void testAsignacionSegundaInstancia() {
        
        Mascota mascota = new Mascota();

        
        mascota.setId(2L);
        mascota.setNombre("Michi");
        mascota.setMicrochip("981020000654321");
        mascota.setEspecie("Felino");
        mascota.setRaza("Siamés");
        mascota.setEdad(2);
        mascota.setVacunaAntirrabica("Aplicada 15/01/2024");
        mascota.setNumeroCertificado("CERT-2024-112233");
        mascota.setPaisOrigen("Perú");
        mascota.setRutDueno("98765432-1");

        
        assertEquals(2L, mascota.getId());
        assertEquals("Michi", mascota.getNombre());
        assertEquals("981020000654321", mascota.getMicrochip());
        assertEquals("Felino", mascota.getEspecie());
        assertEquals("Perú", mascota.getPaisOrigen());
    }
}