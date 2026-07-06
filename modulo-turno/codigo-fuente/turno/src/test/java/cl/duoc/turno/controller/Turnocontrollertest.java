package cl.duoc.turno.controller;

import cl.duoc.turno.controller.TurnoController;
import cl.duoc.turno.dto.TurnoCreateDTO;
import cl.duoc.turno.dto.TurnoDTO;
import cl.duoc.turno.service.TurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TurnoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TurnoService turnoService;

    @InjectMocks
    private TurnoController controller;

    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/v1/turnos";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Verificar status 200 OK al listar todos los turnos")
    void probarStatus200() throws Exception {
        // Arrange
        lenient().when(turnoService.listarTurnos()).thenReturn(java.util.List.of());

        // Act
        ResultActions response = mockMvc.perform(get(BASE_URL));

        // Assert
        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Verificar status 201 Created al crear un turno")
    void probarStatus201() throws Exception {
        // Arrange
        TurnoCreateDTO peticion = new TurnoCreateDTO();
        peticion.setRutFuncionario("12345678-9");
        peticion.setPuesto("Control de Aduana");
        peticion.setFechaInicio(LocalDateTime.of(2026, 6, 1, 8, 0));
        peticion.setFechaFin(LocalDateTime.of(2026, 6, 1, 16, 0));
        peticion.setEstado("ACTIVO");

        TurnoDTO respuesta = new TurnoDTO();
        respuesta.setId(1L);

        lenient().when(turnoService.asignarTurno(any(TurnoCreateDTO.class))).thenReturn(respuesta);

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
        lenient().when(turnoService.listarTurnos()).thenReturn(java.util.List.of());

        // Act
        ResultActions response = mockMvc.perform(get("/ruta-falsa-que-no-existe-para-forzar-404"));

        // Assert
        response.andExpect(status().isNotFound());
    }
}