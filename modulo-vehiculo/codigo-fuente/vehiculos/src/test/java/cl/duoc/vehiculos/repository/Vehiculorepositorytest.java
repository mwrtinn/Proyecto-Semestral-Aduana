package cl.duoc.vehiculos.repository;

import cl.duoc.vehiculos.model.Vehiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoRepositoryTest {

    @Mock
    private VehiculoRepository repository;

    @Test
    @DisplayName("Debe guardar un vehículo (Prueba de save)")
    void probarSave() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setVin("1HGBH41JXMN109186");
        vehiculo.setPatente("AB1234");
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Corolla");
        vehiculo.setAnio(2022);
        vehiculo.setPaisOrigen("Japón");
        vehiculo.setRutDueno("12345678-9");

        Vehiculo guardado = new Vehiculo();
        guardado.setId(1L);
        guardado.setPatente("AB1234");

        when(repository.save(any(Vehiculo.class))).thenReturn(guardado);

        Vehiculo resultado = repository.save(vehiculo);

        assertNotNull(resultado.getId());
        verify(repository, times(1)).save(any(Vehiculo.class));
    }

    @Test
    @DisplayName("Debe buscar un vehículo por ID (Prueba de findById)")
    void probarFindById() {
        Vehiculo guardado = new Vehiculo();
        guardado.setId(2L);
        guardado.setPatente("CD5678");
        guardado.setMarca("Honda");

        when(repository.findById(2L)).thenReturn(Optional.of(guardado));

        Optional<Vehiculo> encontrado = repository.findById(2L);

        assertTrue(encontrado.isPresent());
        assertEquals(2L, encontrado.get().getId());
        verify(repository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("Debe listar todos los vehículos (Prueba de findAll)")
    void probarFindAll() {
        Vehiculo v1 = new Vehiculo(); v1.setMarca("Volkswagen"); v1.setPatente("EF9012");
        Vehiculo v2 = new Vehiculo(); v2.setMarca("Nissan");     v2.setPatente("GH3456");

        when(repository.findAll()).thenReturn(Arrays.asList(v1, v2));

        List<Vehiculo> lista = repository.findAll();

        assertTrue(lista.size() >= 2);
        verify(repository, times(1)).findAll();
    }
}