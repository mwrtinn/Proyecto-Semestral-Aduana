package cl.duoc.menores.service;

import cl.duoc.menores.client.UsuarioFeignClient;
import cl.duoc.menores.dto.MenorCreateDTO;
import cl.duoc.menores.dto.MenorDTO;
import cl.duoc.menores.exception.RecursoNoEncontradoException;
import cl.duoc.menores.exception.ServicioNoDisponibleException;
import cl.duoc.menores.model.Menor;
import cl.duoc.menores.repository.MenorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenorServiceTest {

    @Mock
    private MenorRepository repository;

    @Mock
    private UsuarioFeignClient usuarioClient;

    @InjectMocks
    private MenorService service;

    @Test
    @DisplayName("Debe retornar un menor cuando se busca por un RUT existente")
    void debeRetornarMenorPorRut() {
        // Arrange
        String rutBuscado = "12345678-9";
        Menor mockMenor = new Menor();
        mockMenor.setRut(rutBuscado);
        mockMenor.setNombre("Pedro Mármol");

        when(repository.findByRut(rutBuscado)).thenReturn(Optional.of(mockMenor));

        // Act
        MenorDTO resultado = service.obtener(rutBuscado);

        // Assert
        assertNotNull(resultado);
        assertEquals(rutBuscado, resultado.getRut());
        verify(repository, times(1)).findByRut(rutBuscado);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando se busca un RUT que no existe")
    void debeLanzarExcepcionPorRutNoEncontrado() {
        // Arrange
        String rutInexistente = "00000000-0";
        when(repository.findByRut(rutInexistente)).thenReturn(Optional.empty());

        // Act
        Executable accion = () -> service.obtener(rutInexistente);

        // Assert
        assertThrows(RecursoNoEncontradoException.class, accion);
        verify(repository, times(1)).findByRut(rutInexistente);
    }

    @Test
    @DisplayName("Debe retornar la lista completa de menores")
    void debeRetornarListaDeMenores() {
        // Arrange
        Menor m1 = new Menor(); m1.setNombre("Ana"); m1.setRut("11111111-1");
        Menor m2 = new Menor(); m2.setNombre("Luis"); m2.setRut("22222222-2");
        List<Menor> listaSimulada = Arrays.asList(m1, m2);

        when(repository.findAll()).thenReturn(listaSimulada);

        // Act
        List<MenorDTO> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe crear un menor exitosamente")
    void debeCrearMenor() {
        // Arrange
        MenorCreateDTO nuevoMenorDTO = new MenorCreateDTO();
        nuevoMenorDTO.setNombre("Juanito Pérez");
        nuevoMenorDTO.setRut("12345678-9");
        nuevoMenorDTO.setEdad(12);
        nuevoMenorDTO.setNumeroActa("ACT-123");
        nuevoMenorDTO.setRutTutor("98765432-1");

        Menor mockMenor = new Menor();
        mockMenor.setRut("12345678-9");
        mockMenor.setNombre("Juanito Pérez");

        when(repository.findByRut(anyString())).thenReturn(Optional.empty());
        // Mock del cliente Feign para que no lance excepción
        when(usuarioClient.obtenerPorRut(anyString())).thenReturn(null);
        when(repository.save(any(Menor.class))).thenReturn(mockMenor);

        // Act
        MenorDTO resultado = service.registrar(nuevoMenorDTO);

        // Assert
        assertNotNull(resultado);
        verify(repository, times(1)).save(any(Menor.class));
        verify(usuarioClient, times(1)).obtenerPorRut("98765432-1");
    }

    @Test
    @DisplayName("Debe actualizar un menor existente")
    void debeActualizarMenor() {
        // Arrange
        String rutActualizar = "12345678-9";

        MenorCreateDTO dtoActualizacion = new MenorCreateDTO();
        dtoActualizacion.setRut(rutActualizar); // Mismo rut
        dtoActualizacion.setNombre("Juanito Pérez Actualizado");
        dtoActualizacion.setRutTutor("98765432-1"); // Mismo tutor

        Menor menorExistente = new Menor();
        menorExistente.setRut(rutActualizar);
        menorExistente.setNombre("Juanito Pérez");
        menorExistente.setRutTutor("98765432-1"); // Mismo RUT para no llamar validarTutor

        when(repository.findByRut(rutActualizar)).thenReturn(Optional.of(menorExistente));
        when(repository.save(any(Menor.class))).thenReturn(menorExistente);

        // Act
        MenorDTO resultado = service.actualizar(rutActualizar, dtoActualizacion);

        // Assert
        assertNotNull(resultado);
        verify(repository, times(1)).findByRut(rutActualizar);
        verify(repository, times(1)).save(any(Menor.class));
    }

    @Test
    @DisplayName("Debe eliminar un menor por RUT")
    void debeEliminarMenor() {
        // Arrange
        String rutEliminar = "12345678-9";
        Menor menorExistente = new Menor();
        menorExistente.setRut(rutEliminar);

        when(repository.findByRut(rutEliminar)).thenReturn(Optional.of(menorExistente));
        doNothing().when(repository).delete(menorExistente);

        // Act
        service.eliminar(rutEliminar);

        // Assert
        verify(repository, times(1)).findByRut(rutEliminar);
        verify(repository, times(1)).delete(menorExistente);
    }
}