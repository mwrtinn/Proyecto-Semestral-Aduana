package cl.duoc.ms_mascotas.service;

import cl.duoc.ms_mascotas.model.Mascota;
import cl.duoc.ms_mascotas.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository repository;

    public List<Mascota> obtenerTodas() {
        return repository.findAll();
    }

    public Mascota guardar(Mascota mascota) {
        return repository.save(mascota);
    }

    public Mascota buscarPorMicrochip(String microchip) {
        Optional<Mascota> m = repository.findByMicrochip(microchip);
        if (m.isPresent()) {
            return m.get();
        }
        return null;
    }

    public void eliminarPorMicrochip(String microchip) {
        Optional<Mascota> m = repository.findByMicrochip(microchip);
        if (m.isPresent()) {
            repository.deleteByMicrochip(microchip);
        }
    }
}