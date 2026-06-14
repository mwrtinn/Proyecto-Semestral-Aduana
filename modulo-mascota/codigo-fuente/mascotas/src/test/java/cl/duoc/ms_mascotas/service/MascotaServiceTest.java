package cl.duoc.ms_mascotas.service;

import cl.duoc.mascotas.client.UsuarioFeignClient;
import cl.duoc.mascotas.dto.MascotaCreateDTO;
import cl.duoc.mascotas.dto.MascotaDTO;
import cl.duoc.mascotas.exception.RecursoNoEncontradoException;
import cl.duoc.mascotas.exception.ServicioNoDisponibleException;
import cl.duoc.mascotas.model.Mascota;
import cl.duoc.mascotas.repository.MascotaRepository;
import cl.duoc.mascotas.service.MascotaService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MascotaServiceTest {

    @Mock
    private MascotaRepository repository;

    @Mock
    private UsuarioFeignClient usuarioClient;

    @InjectMocks
    private MascotaService service;

    @Test
    @DisplayName("Debe retornar una mascota cuando se busca por un microchip existente")
    void debeRetornarMascotaPorMicrochip() {
        // Given
        String microchipBuscado = "981020000123456";
        Mascota mockMascota = new Mascota();
        mockMascota.setMicrochip(microchipBuscado);
        mockMascota.setNombre("Firulais");

        when(repository.findByMicrochip(microchipBuscado)).thenReturn(Optional.of(mockMascota));

        // When
        MascotaDTO resultado = service.obtener(microchipBuscado);

        // Then
        assertNotNull(resultado);
        verify(repository, times(1)).findByMicrochip(microchipBuscado);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando se busca un microchip que no existe")
    void debeLanzarExcepcionPorMicrochipNoEncontrado() {
        // Given
        String microchipInexistente = "000000000000000";
        when(repository.findByMicrochip(microchipInexistente)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> {
            service.obtener(microchipInexistente);
        });

        verify(repository, times(1)).findByMicrochip(microchipInexistente);
    }

    @Test
    @DisplayName("Debe retornar la lista completa de mascotas")
    void debeRetornarListaDeMascotas() {
        // Given
        Mascota m1 = new Mascota(); m1.setNombre("Firulais"); m1.setEspecie("Canino");
        Mascota m2 = new Mascota(); m2.setNombre("Michi");  m2.setEspecie("Felino");
        List<Mascota> listaSimulada = Arrays.asList(m1, m2);

        when(repository.findAll()).thenReturn(listaSimulada);

        // When
        List<MascotaDTO> resultado = service.listar();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe crear una mascota exitosamente")
    void debeCrearMascota() {
        // Given
        MascotaCreateDTO nuevaMascotaDTO = new MascotaCreateDTO();
        nuevaMascotaDTO.setMicrochip("981020000123456");
        nuevaMascotaDTO.setNombre("Firulais");
        nuevaMascotaDTO.setEspecie("Canino");
        nuevaMascotaDTO.setRaza("Pastor Alemán");
        nuevaMascotaDTO.setEdad(3);
        nuevaMascotaDTO.setVacunaAntirrabica("Al día");
        nuevaMascotaDTO.setNumeroCertificado("CERT-001");
        nuevaMascotaDTO.setPaisOrigen("Argentina");
        nuevaMascotaDTO.setRutDueno("12345678-9");

        Mascota mockMascota = new Mascota();
        mockMascota.setMicrochip("981020000123456");
        mockMascota.setNombre("Firulais");

        // El servicio primero verifica que el microchip no exista
        when(repository.findByMicrochip(anyString())).thenReturn(Optional.empty());
        // Mock del cliente Feign para que no lance excepción al validar dueño
        when(usuarioClient.obtenerPorRut(anyString())).thenReturn(null);
        when(repository.save(any(Mascota.class))).thenReturn(mockMascota);

        // When
        MascotaDTO resultado = service.registrar(nuevaMascotaDTO);

        // Then
        assertNotNull(resultado);
        verify(repository, times(1)).save(any(Mascota.class));
        verify(usuarioClient, times(1)).obtenerPorRut("12345678-9");
    }

    @Test
    @DisplayName("Debe actualizar una mascota existente")
    void debeActualizarMascota() {
        // Given
        String microchipActualizar = "981020000123456";

        MascotaCreateDTO dtoActualizacion = new MascotaCreateDTO();
        dtoActualizacion.setMicrochip(microchipActualizar); // Mismo microchip
        dtoActualizacion.setNombre("Firulais Actualizado");
        dtoActualizacion.setRutDueno("12345678-9");

        Mascota mascotaExistente = new Mascota();
        mascotaExistente.setMicrochip(microchipActualizar);
        mascotaExistente.setNombre("Firulais");
        mascotaExistente.setRutDueno("12345678-9"); // mismo RUT para no llamar validarDueno

        when(repository.findByMicrochip(microchipActualizar)).thenReturn(Optional.of(mascotaExistente));
        when(repository.save(any(Mascota.class))).thenReturn(mascotaExistente);

        // When
        MascotaDTO resultado = service.actualizar(microchipActualizar, dtoActualizacion);

        // Then
        assertNotNull(resultado);
        verify(repository, times(1)).findByMicrochip(microchipActualizar);
        verify(repository, times(1)).save(any(Mascota.class));
        // Verificamos que no llamó a Feign porque el RUT no cambió
        verify(usuarioClient, never()).obtenerPorRut(anyString());
    }

    @Test
    @DisplayName("Debe eliminar una mascota por microchip")
    void debeEliminarMascota() {
        // Given
        String microchipEliminar = "981020000123456";
        Mascota mascotaExistente = new Mascota();
        mascotaExistente.setMicrochip(microchipEliminar);

        when(repository.findByMicrochip(microchipEliminar)).thenReturn(Optional.of(mascotaExistente));
        doNothing().when(repository).delete(mascotaExistente);

        // When
        service.eliminar(microchipEliminar);

        // Then
        verify(repository, times(1)).findByMicrochip(microchipEliminar);
        verify(repository, times(1)).delete(mascotaExistente);
    }
}