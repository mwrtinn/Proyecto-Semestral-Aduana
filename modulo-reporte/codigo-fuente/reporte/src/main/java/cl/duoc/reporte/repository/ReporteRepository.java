package cl.duoc.reporte.repository;

import cl.duoc.reporte.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByRutGenerador(String rutGenerador);
}