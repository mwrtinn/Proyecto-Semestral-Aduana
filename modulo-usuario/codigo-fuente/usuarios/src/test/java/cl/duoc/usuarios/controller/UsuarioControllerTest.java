package cl.duoc.usuarios.controller;

import cl.duoc.usuarios.dto.UsuarioCreateDTO;
import cl.duoc.usuarios.dto.UsuarioDTO;
import cl.duoc.usuarios.model.Rol;
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

// 👇 AQUI ESTA LA IMPORTACION QUE FALTABA 👇
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        String rutBuscado = "12345678-9";
        UsuarioDTO dto = new UsuarioDTO();
        dto.setRut(rutBuscado);
        dto.setNombre("Juan Perez");
        when(usuarioService.buscarPorRut(rutBuscado)).thenReturn(dto);

        mockMvc.perform(get(BASE_URL + "/{rut}", rutBuscado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rut").value(rutBuscado))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));

        verify(usuarioService, times(1)).buscarPorRut(rutBuscado);
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
        respuesta.setNombre("Nuevo Ingreso");
        when(usuarioService.crear(any(UsuarioCreateDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(peticion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rut").value("99999999-9"))
                .andExpect(jsonPath("$.nombre").value("Nuevo Ingreso"));

        verify(usuarioService, times(1)).crear(any(UsuarioCreateDTO.class));
    }

    @Test
    @DisplayName("Verificar status 400 Bad Request con peticion invalida")
    void probarStatus400() throws Exception {
        UsuarioCreateDTO peticionInvalida = new UsuarioCreateDTO();
        peticionInvalida.setRut(""); 
        peticionInvalida.setNombre("");

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(peticionInvalida)))
                .andExpect(status().isBadRequest());

        verify(usuarioService, times(0)).crear(any());
    }

    @Test
    @DisplayName("Verificar excepcion cuando no existe el RUT (Simulando 404)")
    void probarStatus404() throws Exception {
        String rutInexistente = "00000000-0";
        when(usuarioService.buscarPorRut(rutInexistente)).thenThrow(new RuntimeException("No encontrado"));
        Exception excepcionCapturada = null;

        try {
            mockMvc.perform(get(BASE_URL + "/{rut}", rutInexistente));
        } catch (Exception e) {
            excepcionCapturada = e;
        }

        assertNotNull(excepcionCapturada, "Debe lanzarse una excepcion");
        assertTrue(excepcionCapturada.getCause() instanceof RuntimeException);
        assertEquals("No encontrado", excepcionCapturada.getCause().getMessage());
        
        verify(usuarioService, times(1)).buscarPorRut(rutInexistente);
    }
}