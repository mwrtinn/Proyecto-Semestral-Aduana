package cl.duoc.vehiculos.service;

import cl.duoc.vehiculos.model.Vehiculo;
import cl.duoc.vehiculos.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> obtenerTodos() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo guardar(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo buscarPorPatente(String patente) {
        Optional<Vehiculo> v = vehiculoRepository.findByPatente(patente);
        if (v.isPresent()) {
            return v.get();
        }
        return null;
    }

    public void eliminarPorPatente(String patente) {
        Optional<Vehiculo> v = vehiculoRepository.findByPatente(patente);
        if (v.isPresent()) {
            vehiculoRepository.deleteByPatente(patente);
        }
    }

    public Vehiculo actualizar(String patente, Vehiculo datosNuevos) {
        Optional<Vehiculo> existente = vehiculoRepository.findByPatente(patente);
        if (existente.isPresent()) {
            Vehiculo v = existente.get();
            v.setMarca(datosNuevos.getMarca());
            v.setModelo(datosNuevos.getModelo());
            v.setAnio(datosNuevos.getAnio());
            v.setPaisOrigen(datosNuevos.getPaisOrigen());
            return vehiculoRepository.save(v);
        }
        return null;
    }
}
