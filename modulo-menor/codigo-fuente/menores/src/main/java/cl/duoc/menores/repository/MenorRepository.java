package cl.duoc.menores.repository;

import cl.duoc.menores.model.Menor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface MenorRepository extends JpaRepository<Menor, Long> {
    Optional<Menor> findByRut(String rut);
    
    @Transactional
    void deleteByRut(String rut);
}