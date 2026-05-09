package cl.duoc.mascotas.repository;

import cl.duoc.mascotas.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    Optional<Mascota> findByMicrochip(String microchip);
}