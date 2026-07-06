package cl.duoc.turno.service;

import cl.duoc.turno.client.UsuarioFeignClient;
import cl.duoc.turno.dto.TurnoDTO;
import cl.duoc.turno.dto.TurnoCreateDTO;
import cl.duoc.turno.model.Turno;
import cl.duoc.turno.repository.TurnoRepository;
import cl.duoc.turno.service.TurnoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private UsuarioFeignClient usuarioClient;

    @InjectMocks
    private TurnoService service;

    @Test
    @DisplayName("Debe retornar un turno cuando se busca por un ID existente")
    void debeRetornarTurnoPorId() {
        // Arrange
        Long idBuscado = 1L;
        Turno mockTurno = new Turno();
        mockTurno.setId(idBuscado);
        mockTurno.setRutFuncionario("12345678-9");
        mockTurno.setPuesto("Control de Aduana");

        when(turnoRepository.findById(idBuscado)).thenReturn(Optional.of(mockTurno));
        doNothing().when(turnoRepository).delete(mockTurno);

        // Act
        service.eliminarTurno(idBuscado);

        // Assert
        verify(turnoRepository, times(1)).findById(idBuscado);
        verify(turnoRepository, times(1)).delete(mockTurno);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando se busca un ID que no existe")
    void debeLanzarExcepcionPorIdNoEncontrado() {
        // Arrange
        Long idInexistente = 99L;
        when(turnoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act
        Executable accion = () -> service.eliminarTurno(idInexistente);

        // Assert
        assertThrows(RuntimeException.class, accion);
        verify(turnoRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debe retornar la lista completa de turnos")
    void debeRetornarListaDeTurnos() {
        // Arrange
        Turno t1 = new Turno(); t1.setRutFuncionario("12345678-9"); t1.setPuesto("Control de Aduana");
        Turno t2 = new Turno(); t2.setRutFuncionario("98765432-1"); t2.setPuesto("Revisión de Equipaje");

        when(turnoRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        // Act
        List<TurnoDTO> resultado = service.listarTurnos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(turnoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe crear un turno exitosamente")
    void debeCrearTurno() {
        // Arrange
        TurnoCreateDTO nuevoTurnoDTO = new TurnoCreateDTO();
        nuevoTurnoDTO.setRutFuncionario("12345678-9");
        nuevoTurnoDTO.setPuesto("Control de Aduana");
        nuevoTurnoDTO.setFechaInicio(LocalDateTime.of(2026, 6, 1, 8, 0));
        nuevoTurnoDTO.setFechaFin(LocalDateTime.of(2026, 6, 1, 16, 0));
        nuevoTurnoDTO.setEstado("ACTIVO");

        Turno mockTurno = new Turno();
        mockTurno.setId(1L);
        mockTurno.setRutFuncionario("12345678-9");

        when(usuarioClient.obtenerPorRut(anyString())).thenReturn(null);
        when(turnoRepository.save(any(Turno.class))).thenReturn(mockTurno);

        // Act
        TurnoDTO resultado = service.asignarTurno(nuevoTurnoDTO);

        // Assert
        assertNotNull(resultado);
        verify(turnoRepository, times(1)).save(any(Turno.class));
        verify(usuarioClient, times(1)).obtenerPorRut("12345678-9");
    }

    @Test
    @DisplayName("Debe actualizar un turno existente")
    void debeActualizarTurno() {
        // Arrange
        Long idActualizar = 1L;

        TurnoCreateDTO dtoActualizacion = new TurnoCreateDTO();
        dtoActualizacion.setRutFuncionario("12345678-9");
        dtoActualizacion.setPuesto("Revisión de Equipaje");
        dtoActualizacion.setFechaInicio(LocalDateTime.of(2026, 6, 2, 8, 0));
        dtoActualizacion.setFechaFin(LocalDateTime.of(2026, 6, 2, 16, 0));
        dtoActualizacion.setEstado("ACTIVO");

        Turno turnoExistente = new Turno();
        turnoExistente.setId(idActualizar);
        turnoExistente.setRutFuncionario("12345678-9");
        turnoExistente.setPuesto("Control de Aduana");

        when(turnoRepository.findById(idActualizar)).thenReturn(Optional.of(turnoExistente));
        when(usuarioClient.obtenerPorRut(anyString())).thenReturn(null);
        when(turnoRepository.save(any(Turno.class))).thenReturn(turnoExistente);

        // Act
        TurnoDTO resultado = service.actualizarTurno(idActualizar, dtoActualizacion);

        // Assert
        assertNotNull(resultado);
        verify(turnoRepository, times(1)).findById(idActualizar);
        verify(turnoRepository, times(1)).save(any(Turno.class));
    }

    @Test
    @DisplayName("Debe eliminar un turno por ID")
    void debeEliminarTurno() {
        // Arrange
        Long idEliminar = 1L;
        Turno turnoExistente = new Turno();
        turnoExistente.setId(idEliminar);

        when(turnoRepository.findById(idEliminar)).thenReturn(Optional.of(turnoExistente));
        doNothing().when(turnoRepository).delete(turnoExistente);

        // Act
        service.eliminarTurno(idEliminar);

        // Assert
        verify(turnoRepository, times(1)).findById(idEliminar);
        verify(turnoRepository, times(1)).delete(turnoExistente);
    }
}