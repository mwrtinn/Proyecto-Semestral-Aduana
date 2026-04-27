package cl.duoc.menores.service;

import cl.duoc.menores.model.Menor;
import cl.duoc.menores.repository.MenorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MenorService {

    @Autowired
    private MenorRepository menorRepository;

    public List<Menor> obtenerTodos() {
        return menorRepository.findAll();
    }

    public Menor guardar(Menor menor) {
        return menorRepository.save(menor);
    }

    public Menor buscarPorRut(String rut) {
        Optional<Menor> opt = menorRepository.findByRut(rut);
        return opt.orElse(null);
    }

    public void eliminarPorRut(String rut) {
        Optional<Menor> menor = menorRepository.findByRut(rut);
        if (menor.isPresent()) {
            menorRepository.deleteByRut(rut);
        }
    }

    public Menor actualizar(String rut, Menor datosNuevos) {
        Optional<Menor> existente = menorRepository.findByRut(rut);
        
        if (existente.isPresent()) {
            Menor m = existente.get();
            m.setNombre(datosNuevos.getNombre());
            m.setEdad(datosNuevos.getEdad());
            m.setNumeroActa(datosNuevos.getNumeroActa());
            m.setRutTutor(datosNuevos.getRutTutor());
            return menorRepository.save(m);
        }
        return null;
    }
}