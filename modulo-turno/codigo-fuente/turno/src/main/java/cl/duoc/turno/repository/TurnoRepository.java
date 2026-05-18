package cl.duoc.turno.repository;

import cl.duoc.turno.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findByRutFuncionario(String rutFuncionario);
}