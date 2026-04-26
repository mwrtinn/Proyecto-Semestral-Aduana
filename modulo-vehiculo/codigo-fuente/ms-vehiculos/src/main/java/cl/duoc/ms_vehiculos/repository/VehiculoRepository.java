package cl.duoc.ms_vehiculos.repository;

import cl.duoc.ms_vehiculos.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    
    Optional<Vehiculo> findByPatente(String patente);
    
    @Transactional
    void deleteByPatente(String patente);
}
