package cl.duoc.declaraciones.service;

import cl.duoc.declaraciones.model.Declaracion;
import cl.duoc.declaraciones.repository.DeclaracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DeclaracionService {

    @Autowired
    private DeclaracionRepository repository;

    public List<Declaracion> obtenerTodas() {
        return repository.findAll();
    }

    public Declaracion guardar(Declaracion declaracion) {
        return repository.save(declaracion);
    }

    public Declaracion buscarPorRut(String rut) {
        Optional<Declaracion> d = repository.findByRutDeclarante(rut);
        if (d.isPresent()) {
            return d.get();
        }
        return null;
    }

    public Declaracion actualizar(String rut, Declaracion nuevosDatos) {
        Optional<Declaracion> d = repository.findByRutDeclarante(rut);
        if (d.isPresent()) {
            Declaracion existente = d.get();
            existente.setDescripcionArticulos(nuevosDatos.getDescripcionArticulos());
            existente.setValorEstimadoUsd(nuevosDatos.getValorEstimadoUsd());
            existente.setTraeAlimentos(nuevosDatos.getTraeAlimentos());
            existente.setPaisProcedencia(nuevosDatos.getPaisProcedencia());
            return repository.save(existente);
        }
        return null;
    }

    public void eliminarPorRut(String rut) {
        Optional<Declaracion> d = repository.findByRutDeclarante(rut);
        if (d.isPresent()) {
            repository.deleteByRutDeclarante(rut);
        }
    }
}