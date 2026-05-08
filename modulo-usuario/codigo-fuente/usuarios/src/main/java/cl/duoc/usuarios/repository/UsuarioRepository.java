package cl.duoc.usuarios.repository;

import cl.duoc.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByRut(String rut);
}