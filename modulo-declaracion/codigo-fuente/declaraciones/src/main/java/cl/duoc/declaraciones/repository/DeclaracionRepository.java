package cl.duoc.declaraciones.repository;

import cl.duoc.declaraciones.model.Declaracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeclaracionRepository extends JpaRepository<Declaracion, Long> {
    
    List<Declaracion> findByRutDeclarante(String rutDeclarante);
}