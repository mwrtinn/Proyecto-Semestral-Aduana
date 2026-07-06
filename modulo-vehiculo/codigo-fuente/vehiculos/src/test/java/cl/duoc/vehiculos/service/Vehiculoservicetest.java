package cl.duoc.vehiculos.service;

import cl.duoc.vehiculos.client.UsuarioFeignClient;
import cl.duoc.vehiculos.dto.VehiculoDTO;
import cl.duoc.vehiculos.dto.VehiculoCreateDTO;
import cl.duoc.vehiculos.model.Vehiculo;
import cl.duoc.vehiculos.repository.VehiculoRepository;
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
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository repository;

    @Mock
    private UsuarioFeignClient usuarioClient;

    @InjectMocks
    private VehiculoService service;

    @Test
    @DisplayName("Debe retornar un vehículo cuando se busca por una patente existente")
    void debeRetornarVehiculoPorPatente() {
        // Arrange
        String patenteBuscada = "ABCD12";
        Vehiculo mockVehiculo = new Vehiculo();
        mockVehiculo.setPatente(patenteBuscada);
        mockVehiculo.setMarca("Toyota");

        when(repository.findByPatente(patenteBuscada)).thenReturn(Optional.of(mockVehiculo));

        // Act
        VehiculoDTO resultado = service.buscarPorPatente(patenteBuscada);

        // Assert
        assertNotNull(resultado);
        verify(repository, times(1)).findByPatente(patenteBuscada);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando se busca una patente que no existe")
    void debeLanzarExcepcionPorPatenteNoEncontrada() {
        // Arrange
        String patenteInexistente = "XXXX00";
        when(repository.findByPatente(patenteInexistente)).thenReturn(Optional.empty());

        // Act
        Executable accion = () -> service.buscarPorPatente(patenteInexistente);

        // Assert
        assertThrows(RuntimeException.class, accion);
        verify(repository, times(1)).findByPatente(patenteInexistente);
    }

    @Test
    @DisplayName("Debe retornar la lista completa de vehículos")
    void debeRetornarListaDeVehiculos() {
        // Arrange
        Vehiculo v1 = new Vehiculo(); v1.setMarca("Toyota"); v1.setModelo("Corolla");
        Vehiculo v2 = new Vehiculo(); v2.setMarca("Honda");  v2.setModelo("Civic");
        List<Vehiculo> listaSimulada = Arrays.asList(v1, v2);

        when(repository.findAll()).thenReturn(listaSimulada);

        // Act
        List<VehiculoDTO> resultado = service.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe crear un vehículo exitosamente")
    void debeCrearVehiculo() {
        // Arrange
        VehiculoCreateDTO nuevoVehiculoDTO = new VehiculoCreateDTO();
        nuevoVehiculoDTO.setPatente("ABCD12");
        nuevoVehiculoDTO.setMarca("Toyota");
        nuevoVehiculoDTO.setModelo("Corolla");
        nuevoVehiculoDTO.setAnio(2022);
        nuevoVehiculoDTO.setVin("1HGBH41JXMN109186");
        nuevoVehiculoDTO.setPaisOrigen("Japón");
        nuevoVehiculoDTO.setRutDueno("12345678-9");

        Vehiculo mockVehiculo = new Vehiculo();
        mockVehiculo.setPatente("ABCD12");
        mockVehiculo.setMarca("Toyota");

        // Mock del cliente Feign para que no lance excepción
        when(usuarioClient.obtenerPorRut(anyString())).thenReturn(null);
        when(repository.save(any(Vehiculo.class))).thenReturn(mockVehiculo);

        // Act
        VehiculoDTO resultado = service.crear(nuevoVehiculoDTO);

        // Assert
        assertNotNull(resultado);
        verify(repository, times(1)).save(any(Vehiculo.class));
        verify(usuarioClient, times(1)).obtenerPorRut("12345678-9");
    }

    @Test
    @DisplayName("Debe actualizar un vehículo existente")
    void debeActualizarVehiculo() {
        // Arrange
        String patenteActualizar = "ABCD12";

        VehiculoCreateDTO dtoActualizacion = new VehiculoCreateDTO();
        dtoActualizacion.setMarca("Toyota");
        dtoActualizacion.setModelo("Camry");
        dtoActualizacion.setRutDueno("12345678-9");

        Vehiculo vehiculoExistente = new Vehiculo();
        vehiculoExistente.setPatente(patenteActualizar);
        vehiculoExistente.setMarca("Toyota");
        vehiculoExistente.setModelo("Corolla");
        vehiculoExistente.setRutDueno("12345678-9"); // mismo RUT para no llamar validarDueno

        when(repository.findByPatente(patenteActualizar)).thenReturn(Optional.of(vehiculoExistente));
        when(repository.save(any(Vehiculo.class))).thenReturn(vehiculoExistente);

        // Act
        VehiculoDTO resultado = service.actualizar(patenteActualizar, dtoActualizacion);

        // Assert
        assertNotNull(resultado);
        verify(repository, times(1)).findByPatente(patenteActualizar);
        verify(repository, times(1)).save(any(Vehiculo.class));
    }

    @Test
    @DisplayName("Debe eliminar un vehículo por patente")
    void debeEliminarVehiculo() {
        // Arrange
        String patenteEliminar = "ABCD12";
        Vehiculo vehiculoExistente = new Vehiculo();
        vehiculoExistente.setPatente(patenteEliminar);

        when(repository.findByPatente(patenteEliminar)).thenReturn(Optional.of(vehiculoExistente));
        doNothing().when(repository).delete(vehiculoExistente);

        // Act
        service.eliminar(patenteEliminar);

        // Assert
        verify(repository, times(1)).findByPatente(patenteEliminar);
        verify(repository, times(1)).delete(vehiculoExistente);
    }
}