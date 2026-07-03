package cl.duoc.usuarios.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    @DisplayName("Debe crear un usuario vacio y asignar valores con setters")
    void testSettersYGetters() {
        Usuario usuario = new Usuario();

        usuario.setId(1L);
        usuario.setNombre("Juan Perez");
        usuario.setRut("12345678-9");
        usuario.setEmail("juan@aduana.cl");
        usuario.setPassword("secreta123");

        assertEquals(1L, usuario.getId());
        assertEquals("Juan Perez", usuario.getNombre());
        assertEquals("12345678-9", usuario.getRut());
        assertEquals("juan@aduana.cl", usuario.getEmail());
        assertEquals("secreta123", usuario.getPassword());
    }

    @Test
    @DisplayName("Debe instanciar correctamente usando el constructor lleno")
    void testConstructorCompleto() {
        Long idEsperado = 2L;
        String nombreEsperado = "Ana Lopez";
        String rutEsperado = "98765432-1";
        String emailEsperado = "ana@aduana.cl";
        String passwordEsperada = "pass456";
        Rol rolEsperado = null;

        Usuario usuario = new Usuario(
            idEsperado, 
            nombreEsperado, 
            rutEsperado, 
            emailEsperado, 
            passwordEsperada, 
            rolEsperado
        );

        assertEquals(idEsperado, usuario.getId());
        assertEquals(nombreEsperado, usuario.getNombre());
        assertEquals(rutEsperado, usuario.getRut());
        assertEquals(emailEsperado, usuario.getEmail());
        assertEquals(passwordEsperada, usuario.getPassword());
        assertEquals(rolEsperado, usuario.getRol());
    }
}