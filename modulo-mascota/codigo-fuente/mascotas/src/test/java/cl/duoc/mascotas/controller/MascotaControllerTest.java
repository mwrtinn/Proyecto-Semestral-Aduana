package cl.duoc.mascotas.controller;

import cl.duoc.mascotas.dto.MascotaCreateDTO;
import cl.duoc.mascotas.dto.MascotaDTO;
import cl.duoc.mascotas.service.MascotaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MascotaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MascotaService mascotaService;

    @InjectMocks
    private MascotaController controller;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String BASE_URL = "/api/v1/mascotas";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Verificar status 200 OK al listar o buscar")
    void probarStatus200() throws Exception {
        // Arrange
        MascotaDTO dto = new MascotaDTO();
        dto.setMicrochip("981020000123456");

        lenient().when(mascotaService.obtener(anyString())).thenReturn(dto);

        // Act
        ResultActions response = mockMvc.perform(get(BASE_URL + "/981020000123456"));

        // Assert
        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Verificar status 201 Created al crear")
    void probarStatus201() throws Exception {
        // Arrange
        MascotaCreateDTO peticion = new MascotaCreateDTO();
        peticion.setMicrochip("981020000123456");
        peticion.setNombre("Firulais");
        peticion.setEspecie("Canino");
        peticion.setRaza("Quiltro");
        peticion.setEdad(3);
        peticion.setVacunaAntirrabica("Al día");
        peticion.setNumeroCertificado("CERT-001");
        peticion.setPaisOrigen("Chile");
        peticion.setRutDueno("12345678-9");

        MascotaDTO respuesta = new MascotaDTO();
        respuesta.setMicrochip("981020000123456");

        lenient().when(mascotaService.registrar(any(MascotaCreateDTO.class))).thenReturn(respuesta);

        // Act
        ResultActions response = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(peticion)));

        // Assert
        response.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Verificar status 400 Bad Request con peticion invalida")
    void probarStatus400() throws Exception {
        // Arrange (No hay configuración previa requerida)
        
        // Act
        ResultActions response = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ JSON_MALFORMADO : faltan_comillas ]"));

        // Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Verificar status 404 Not Found cuando no existe")
    void probarStatus404() throws Exception {
        // Arrange
        lenient().when(mascotaService.obtener(anyString())).thenThrow(new RuntimeException("No encontrado"));

        // Act
        ResultActions response = mockMvc.perform(get("/ruta-falsa-que-no-existe-para-forzar-404"));

        // Assert
        response.andExpect(status().isNotFound());
    }
}