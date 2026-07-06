package cl.duoc.menores.controller;

import cl.duoc.menores.dto.MenorCreateDTO;
import cl.duoc.menores.dto.MenorDTO;
import cl.duoc.menores.service.MenorService;
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

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MenorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MenorService menorService;

    @InjectMocks
    private MenorController controller;

    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/v1/menores";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Vital para manejar fechas
    }

    @Test
    @DisplayName("Verificar status 200 OK al listar o buscar por RUT")
    void probarStatus200() throws Exception {
        // Arrange
        MenorDTO dto = new MenorDTO();
        dto.setRut("12345678-9");
        dto.setNombre("Juan");

        lenient().when(menorService.obtener(anyString())).thenReturn(dto);

        // Act
        ResultActions response = mockMvc.perform(get(BASE_URL + "/12345678-9"));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.rut").value("12345678-9"));
    }

    @Test
    @DisplayName("Verificar status 201 Created al registrar un menor")
    void probarStatus201() throws Exception {
        // Arrange
        MenorCreateDTO peticion = new MenorCreateDTO();
        peticion.setRut("99999999-9");
        peticion.setNombre("Ana");
        peticion.setRutTutor("11111111-1");
        peticion.setNumeroActa("ACT-001"); 
        peticion.setEdad(10);
        
        MenorDTO respuesta = new MenorDTO();
        respuesta.setRut("99999999-9");

        lenient().when(menorService.registrar(any(MenorCreateDTO.class))).thenReturn(respuesta);

        // Act
        ResultActions response = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(peticion)));

        // Assert
        response.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Verificar status 400 Bad Request con JSON malformado")
    void probarStatus400() throws Exception {
        // Arrange (No hay configuración previa requerida)
        
        // Act
        ResultActions response = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ JSON_INVALIDO : [ 123 ]"));

        // Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Verificar status 404 Not Found")
    void probarStatus404() throws Exception {
        // Arrange
        lenient().when(menorService.obtener(anyString()))
                 .thenThrow(new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));

        // Act
        ResultActions response = mockMvc.perform(get(BASE_URL + "/00000000-0"));

        // Assert
        response.andExpect(status().isNotFound());
    }
}