package cl.duoc.usuarios.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    @DisplayName("Debe crear un usuario vacio y asignar valores con setters")
    void testSettersYGetters() {
        // Given
        Usuario usuario = new Usuario();

        // When
        usuario.setId(1L);
        usuario.setNombre("Juan Perez");
        usuario.setRut("12345678-9");
        usuario.setEmail("juan@aduana.cl");
        usuario.setPassword("secreta123");

        // Then
        assertEquals(1L, usuario.getId());
        assertEquals("Juan Perez", usuario.getNombre());
        assertEquals("12345678-9", usuario.getRut());
        assertEquals("juan@aduana.cl", usuario.getEmail());
        assertEquals("secreta123", usuario.getPassword());
    }

    @Test
    @DisplayName("Debe instanciar correctamente usando el constructor lleno")
    void testConstructorCompleto() {
        // When
        Usuario usuario = new Usuario(2L, "Ana Lopez", "98765432-1", "ana@aduana.cl", "pass456", null);

        // Then
        assertEquals(2L, usuario.getId());
        assertEquals("Ana Lopez", usuario.getNombre());
        assertEquals("98765432-1", usuario.getRut());
    }
}