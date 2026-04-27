package cl.duoc.mascotas.service;

import cl.duoc.mascotas.model.Mascota;
import cl.duoc.mascotas.repository.MascotaRepository;
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
        return m.orElse(null);
    }

    public Mascota actualizar(String microchip, Mascota nuevosDatos) {
        return repository.findByMicrochip(microchip).map(mascotaExistente -> {
            mascotaExistente.setNombre(nuevosDatos.getNombre());
            mascotaExistente.setEspecie(nuevosDatos.getEspecie());
            mascotaExistente.setRaza(nuevosDatos.getRaza());
            mascotaExistente.setEdad(nuevosDatos.getEdad());
            return repository.save(mascotaExistente);
        }).orElse(null); 
    }

    public void eliminarPorMicrochip(String microchip) {
        Optional<Mascota> m = repository.findByMicrochip(microchip);
        if (m.isPresent()) {
            repository.deleteByMicrochip(microchip);
        }
    }
}