package cl.duoc.usuarios.controller;

import cl.duoc.usuarios.dto.UsuarioCreateDTO;
import cl.duoc.usuarios.dto.UsuarioDTO;
import cl.duoc.usuarios.model.Rol; // <-- Importación agregada
import cl.duoc.usuarios.service.UsuarioService;
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
class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController controller;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String BASE_URL = "/api/v1/usuarios"; 

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Verificar status 200 OK al listar o buscar")
    void probarStatus200() throws Exception {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setRut("12345678-9");
        
        lenient().when(usuarioService.buscarPorRut(anyString())).thenReturn(dto);

        mockMvc.perform(get(BASE_URL + "/12345678-9"))
                .andExpect(status().isOk()); 
    }

    @Test
    @DisplayName("Verificar status 201 Created al crear")
    void probarStatus201() throws Exception {
        UsuarioCreateDTO peticion = new UsuarioCreateDTO();
        peticion.setRut("99999999-9");
        peticion.setNombre("Nuevo Ingreso");
        peticion.setEmail("martinf@aduana.cl");
        peticion.setPassword("123456"); 
        peticion.setRol(Rol.VIAJERO); 
        
        UsuarioDTO respuesta = new UsuarioDTO();
        respuesta.setRut("99999999-9");

        lenient().when(usuarioService.crear(any(UsuarioCreateDTO.class))).thenReturn(respuesta);

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
        lenient().when(usuarioService.buscarPorRut(anyString())).thenThrow(new RuntimeException("No encontrado"));

        mockMvc.perform(get("/ruta-falsa-que-no-existe-para-forzar-404"))
                .andExpect(status().isNotFound()); 
    }
}