package cl.duoc.usuarios.service;

import cl.duoc.usuarios.dto.UsuarioCreateDTO;
import cl.duoc.usuarios.dto.UsuarioDTO;
import cl.duoc.usuarios.exception.RecursoNoEncontradoException;
import cl.duoc.usuarios.model.Usuario;
import cl.duoc.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    
    public List<UsuarioDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

   
    public UsuarioDTO buscarPorRut(String rut) {
        Usuario usuario = repository.findByRut(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con RUT: " + rut));
        return toDTO(usuario);
    }

   
    public UsuarioDTO crear(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setRut(dto.getRut());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword()); 
        usuario.setRol(dto.getRol());

        Usuario guardado = repository.save(usuario);
        return toDTO(guardado);
    }

    
    public UsuarioDTO actualizar(String rut, UsuarioCreateDTO dto) {
        Usuario usuarioExistente = repository.findByRut(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede actualizar. Usuario no encontrado con RUT: " + rut));

        usuarioExistente.setNombre(dto.getNombre());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setPassword(dto.getPassword());
        usuarioExistente.setRol(dto.getRol());

        Usuario actualizado = repository.save(usuarioExistente);
        return toDTO(actualizado);
    }

   
    public void eliminar(String rut) {
        Usuario usuarioExistente = repository.findByRut(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede eliminar. Usuario no encontrado con RUT: " + rut));
        
        repository.delete(usuarioExistente);
    }

    
    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(
                u.getId(),
                u.getNombre(),
                u.getRut(),
                u.getEmail(),
                u.getRol()
        );
    }
}