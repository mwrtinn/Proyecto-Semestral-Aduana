package cl.duoc.aduanas.repository;

import cl.duoc.aduanas.model.Aduana;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AduanaRepository extends JpaRepository<Aduana, Long> {
}