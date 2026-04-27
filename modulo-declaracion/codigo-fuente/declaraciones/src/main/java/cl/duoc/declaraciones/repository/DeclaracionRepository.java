package cl.duoc.declaraciones.repository;

import cl.duoc.declaraciones.model.Declaracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface DeclaracionRepository extends JpaRepository<Declaracion, Long> {
    
    Optional<Declaracion> findByRutDeclarante(String rutDeclarante);
    
    @Transactional
    void deleteByRutDeclarante(String rutDeclarante);
}