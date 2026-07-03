package cl.duoc.vehiculos.controller;

import cl.duoc.vehiculos.dto.VehiculoCreateDTO;
import cl.duoc.vehiculos.dto.VehiculoDTO;
import cl.duoc.vehiculos.service.VehiculoService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VehiculoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VehiculoService vehiculoService;

    @InjectMocks
    private VehiculoController controller;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String BASE_URL = "/api/v1/vehiculos";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Verificar status 200 OK al listar o buscar")
    void probarStatus200() throws Exception {
        VehiculoDTO dto = new VehiculoDTO();
        dto.setPatente("AB1234");

        lenient().when(vehiculoService.buscarPorPatente(anyString())).thenReturn(dto);

        mockMvc.perform(get(BASE_URL + "/AB1234"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Verificar status 201 Created al crear")
    void probarStatus201() throws Exception {
        VehiculoCreateDTO peticion = new VehiculoCreateDTO();
        peticion.setVin("1HGBH41JXMN109186");
        peticion.setPatente("AB1234");
        peticion.setMarca("Toyota");
        peticion.setModelo("Corolla");
        peticion.setAnio(2022);
        peticion.setPaisOrigen("Japón");
        peticion.setRutDueno("12345678-9");

        VehiculoDTO respuesta = new VehiculoDTO();
        respuesta.setPatente("AB1234");

        lenient().when(vehiculoService.crear(any(VehiculoCreateDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(peticion)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Verificar status 400 Bad Request con peticion invalida")
    void probarStatus400() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ JSON_MALFORMADO : faltan_comillas ]"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Verificar status 404 Not Found cuando no existe")
    void probarStatus404() throws Exception {
        lenient().when(vehiculoService.buscarPorPatente(anyString())).thenThrow(new RuntimeException("No encontrado"));

        mockMvc.perform(get("/ruta-falsa-que-no-existe-para-forzar-404"))
                .andExpect(status().isNotFound());
    }
}