package cl.duoc.usuarios.service;

import cl.duoc.usuarios.model.Usuario;
import cl.duoc.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorRut(String rut) {
        Optional<Usuario> lista = usuarioRepository.findByRut(rut);
        if (lista.isPresent()) {
            return lista.get(); 
        }
        return null; 
    }

    public void eliminarPorRut(String rut) {
        Optional<Usuario> usuario = usuarioRepository.findByRut(rut);
        if (usuario.isPresent()) {
            usuarioRepository.deleteByRut(rut);
        }
    }

    public Usuario actualizar(String rut, Usuario datosNuevos) {
        Optional<Usuario> existente = usuarioRepository.findByRut(rut);
        
        if (existente.isPresent()) {
            Usuario u = existente.get();
            u.setNombre(datosNuevos.getNombre());
            u.setEmail(datosNuevos.getEmail());
            u.setRol(datosNuevos.getRol());
            u.setPassword(datosNuevos.getPassword());
            return usuarioRepository.save(u);
        }
        return null;
    }
}