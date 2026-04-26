package cl.duoc.ms_mascotas.repository;

import cl.duoc.ms_mascotas.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    Optional<Mascota> findByMicrochip(String microchip);
    
    @Transactional
    void deleteByMicrochip(String microchip);
}