package cl.duoc.usuarios.repository;

import cl.duoc.usuarios.model.Usuario;
import cl.duoc.usuarios.model.Rol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    @DisplayName("Debe guardar un usuario (Prueba de save)")
    void probarSave() {
        Usuario usuario = new Usuario();
        usuario.setRut("11111111-1");
        usuario.setNombre("Prueba Save");
        usuario.setEmail("correo1@aduana.cl");
        usuario.setPassword("123456");
        usuario.setRol(Rol.VIAJERO); 

        Usuario guardado = repository.save(usuario);

        assertNotNull(guardado.getId());
        assertEquals("11111111-1", guardado.getRut());
    }

    @Test
    @DisplayName("Debe buscar un usuario por ID (Prueba de findById)")
    void probarFindById() {
        Usuario usuario = new Usuario();
        usuario.setRut("22222222-2");
        usuario.setNombre("Prueba Buscar");
        usuario.setEmail("correo2@aduana.cl");
        usuario.setPassword("123456");
        usuario.setRol(Rol.VIAJERO); 
        Usuario guardado = repository.save(usuario);
        Long idBuscado = guardado.getId();

        Optional<Usuario> encontrado = repository.findById(idBuscado);

        assertTrue(encontrado.isPresent());
        assertEquals(idBuscado, encontrado.get().getId());
    }

    @Test
    @DisplayName("Debe listar todos los usuarios (Prueba de findAll)")
    void probarFindAll() {
        Usuario u1 = new Usuario(); 
        u1.setRut("33333333-3"); 
        u1.setNombre("Usuario Uno");
        u1.setEmail("correo3@aduana.cl"); 
        u1.setPassword("123456");
        u1.setRol(Rol.VIAJERO);      
        Usuario u2 = new Usuario(); 
        u2.setRut("44444444-4"); 
        u2.setNombre("Usuario Dos"); 
        u2.setEmail("correo4@aduana.cl"); 
        u2.setPassword("123456");
        u2.setRol(Rol.VIAJERO);     
        repository.save(u1);
        repository.save(u2);

        List<Usuario> lista = repository.findAll();

        assertTrue(lista.size() >= 2);
    }
}