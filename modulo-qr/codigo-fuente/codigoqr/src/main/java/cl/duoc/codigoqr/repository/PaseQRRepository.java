package cl.duoc.codigoqr.repository;

import cl.duoc.codigoqr.model.PaseQR;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaseQRRepository extends JpaRepository<PaseQR, Long> {
    Optional<PaseQR> findByCodigoUuid(String codigoUuid);
}