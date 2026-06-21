package cl.duoc.turno.repository;

import cl.duoc.turno.model.Turno;
import cl.duoc.turno.repository.TurnoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurnoRepositoryTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Test
    @DisplayName("Debe guardar un turno (Prueba de save)")
    void probarSave() {
        Turno turno = new Turno();
        turno.setRutFuncionario("12345678-9");
        turno.setPuesto("Control de Aduana");
        turno.setFechaInicio(LocalDateTime.of(2026, 6, 1, 8, 0));
        turno.setFechaFin(LocalDateTime.of(2026, 6, 1, 16, 0));
        turno.setEstado("ACTIVO");

        Turno guardado = new Turno();
        guardado.setId(1L);
        guardado.setRutFuncionario("12345678-9");

        when(turnoRepository.save(any(Turno.class))).thenReturn(guardado);

        Turno resultado = turnoRepository.save(turno);

        assertNotNull(resultado.getId());
        verify(turnoRepository, times(1)).save(any(Turno.class));
    }

    @Test
    @DisplayName("Debe buscar un turno por ID (Prueba de findById)")
    void probarFindById() {
        Turno guardado = new Turno();
        guardado.setId(2L);
        guardado.setRutFuncionario("98765432-1");
        guardado.setPuesto("Revisión de Equipaje");

        when(turnoRepository.findById(2L)).thenReturn(Optional.of(guardado));

        Optional<Turno> encontrado = turnoRepository.findById(2L);

        assertTrue(encontrado.isPresent());
        assertEquals(2L, encontrado.get().getId());
        verify(turnoRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("Debe listar todos los turnos (Prueba de findAll)")
    void probarFindAll() {
        Turno t1 = new Turno(); t1.setRutFuncionario("12345678-9"); t1.setPuesto("Control de Aduana");
        Turno t2 = new Turno(); t2.setRutFuncionario("98765432-1"); t2.setPuesto("Revisión de Equipaje");

        when(turnoRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<Turno> lista = turnoRepository.findAll();

        assertTrue(lista.size() >= 2);
        verify(turnoRepository, times(1)).findAll();
    }
}