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
    private DeclaracionRepository declaracionRepository;

    public List<Declaracion> obtenerTodos() {
        return declaracionRepository.findAll();
    }

    public Declaracion guardar(Declaracion declaracion) {
        return declaracionRepository.save(declaracion);
    }

    public List<Declaracion> buscarPorRut(String rut) {
        return declaracionRepository.findByRutDeclarante(rut);
    }

    public void eliminarPorId(Long id) {
        if (declaracionRepository.existsById(id)) {
            declaracionRepository.deleteById(id);
        }
    }

    public Declaracion actualizar(Long id, Declaracion datosNuevos) {
        Optional<Declaracion> existente = declaracionRepository.findById(id);
        
        if (existente.isPresent()) {
            Declaracion d = existente.get();
            d.setRutDeclarante(datosNuevos.getRutDeclarante());
            d.setDescripcionArticulos(datosNuevos.getDescripcionArticulos());
            d.setValorEstimadoUsd(datosNuevos.getValorEstimadoUsd());
            d.setTraeAlimentos(datosNuevos.getTraeAlimentos());
            d.setPaisProcedencia(datosNuevos.getPaisProcedencia());
            return declaracionRepository.save(d);
        }
        return null;
    }
}