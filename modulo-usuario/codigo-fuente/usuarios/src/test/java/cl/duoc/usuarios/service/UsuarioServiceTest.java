package cl.duoc.usuarios.service;

import cl.duoc.usuarios.dto.UsuarioDTO;
import cl.duoc.usuarios.dto.UsuarioCreateDTO;
import cl.duoc.usuarios.model.Usuario;
import cl.duoc.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    @Test
    @DisplayName("Debe retornar un usuario cuando se busca por un RUT existente")
    void debeRetornarUsuarioPorRut() {
        String rutBuscado = "12345678-9";
        Usuario mockUsuario = new Usuario();
        mockUsuario.setRut(rutBuscado);
        mockUsuario.setNombre("Martin Faundez");
        when(repository.findByRut(rutBuscado)).thenReturn(Optional.of(mockUsuario));

        UsuarioDTO resultado = service.buscarPorRut(rutBuscado);

        assertNotNull(resultado);
        assertEquals("Martin Faundez", resultado.getNombre());
        verify(repository, times(1)).findByRut(rutBuscado);
    }

    @Test
    @DisplayName("Debe lanzar excepcion cuando se busca un RUT que no existe")
    void debeLanzarExcepcionPorRutNoEncontrado() {
        String rutFalso = "00000000-0";
        when(repository.findByRut(rutFalso)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.buscarPorRut(rutFalso);
        });
        
        verify(repository, times(1)).findByRut(rutFalso);
    }

    @Test
    @DisplayName("Debe retornar la lista completa de usuarios")
    void debeRetornarListaDeUsuarios() {
        Usuario user1 = new Usuario(); user1.setNombre("Martin Faundez");
        Usuario user2 = new Usuario(); user2.setNombre("Vicente Vega");
        List<Usuario> listaSimulada = Arrays.asList(user1, user2);
        when(repository.findAll()).thenReturn(listaSimulada);

        List<UsuarioDTO> resultado = service.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Martin Faundez", resultado.get(0).getNombre());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe crear un usuario exitosamente")
    void debeCrearUsuario() {
        UsuarioCreateDTO nuevoUsuarioDTO = new UsuarioCreateDTO();
        nuevoUsuarioDTO.setRut("99999999-9");
        nuevoUsuarioDTO.setNombre("Martin Faundez");
        Usuario mockUsuario = new Usuario();
        mockUsuario.setRut("99999999-9");
        mockUsuario.setNombre("Martin Faundez");
        when(repository.save(any(Usuario.class))).thenReturn(mockUsuario);

        UsuarioDTO resultado = service.crear(nuevoUsuarioDTO); 

        assertNotNull(resultado);
        assertEquals("99999999-9", resultado.getRut());
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe actualizar un usuario existente")
    void debeActualizarUsuario() {
        String rutActualizar = "12345678-9";
        UsuarioCreateDTO dtoActualizacion = new UsuarioCreateDTO();
        dtoActualizacion.setNombre("Martin Faundez 2");
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setRut(rutActualizar);
        usuarioExistente.setNombre("Martin Faundez");
        when(repository.findByRut(rutActualizar)).thenReturn(Optional.of(usuarioExistente));
        when(repository.save(any(Usuario.class))).thenReturn(usuarioExistente);

        UsuarioDTO resultado = service.actualizar(rutActualizar, dtoActualizacion); 

        assertNotNull(resultado);
        verify(repository, times(1)).findByRut(rutActualizar);
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe eliminar un usuario por RUT")
    void debeEliminarUsuario() {
        String rutEliminar = "12345678-9";
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setRut(rutEliminar);
        when(repository.findByRut(rutEliminar)).thenReturn(Optional.of(usuarioExistente));
        doNothing().when(repository).delete(usuarioExistente);

        service.eliminar(rutEliminar); 

        verify(repository, times(1)).findByRut(rutEliminar);
        verify(repository, times(1)).delete(usuarioExistente);
    }
}