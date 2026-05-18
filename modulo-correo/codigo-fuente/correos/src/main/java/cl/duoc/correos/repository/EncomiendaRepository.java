package cl.duoc.correos.repository;

import cl.duoc.correos.model.Encomienda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EncomiendaRepository extends JpaRepository<Encomienda, Long> {
    Optional<Encomienda> findByTrackingNumber(String trackingNumber);
}