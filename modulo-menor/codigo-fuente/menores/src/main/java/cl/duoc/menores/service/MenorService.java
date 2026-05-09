package cl.duoc.menores.service;

import cl.duoc.menores.client.UsuarioFeignClient;
import cl.duoc.menores.dto.MenorCreateDTO;
import cl.duoc.menores.dto.MenorDTO;
import cl.duoc.menores.exception.RecursoNoEncontradoException;
import cl.duoc.menores.exception.ServicioNoDisponibleException;
import cl.duoc.menores.model.Menor;
import cl.duoc.menores.repository.MenorRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenorService {

    @Autowired
    private MenorRepository menorRepository;

    @Autowired
    private UsuarioFeignClient usuarioClient;

    private void validarTutor(String rut) {
        try {
            usuarioClient.obtenerPorRut(rut);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("No se puede registrar al menor: El tutor con RUT " + rut + " no está registrado en el sistema de usuarios.");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("El servicio de Usuarios no responde. Intente registrar al menor más tarde.");
        }
    }

    public MenorDTO registrar(MenorCreateDTO request) {
        if (menorRepository.findByRut(request.getRut()).isPresent()) {
            throw new ServicioNoDisponibleException("El menor con RUT " + request.getRut() + " ya está registrado.");
        }
        validarTutor(request.getRutTutor());
        
        Menor menor = new Menor();
        mapearEntidad(request, menor);
        return mapearDto(menorRepository.save(menor));
    }

    public List<MenorDTO> listar() {
        return menorRepository.findAll().stream()
                .map(this::mapearDto)
                .collect(Collectors.toList());
    }

    public MenorDTO obtener(String rut) {
        Menor m = menorRepository.findByRut(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Menor no encontrado con el RUT: " + rut));
        return mapearDto(m);
    }

    public MenorDTO actualizar(String rut, MenorCreateDTO request) {
        Menor m = menorRepository.findByRut(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Menor no encontrado con el RUT: " + rut));
        
    
        if (!m.getRut().equals(request.getRut()) && menorRepository.findByRut(request.getRut()).isPresent()) {
            throw new ServicioNoDisponibleException("El nuevo RUT ya está registrado en otro menor.");
        }

   
        if (!m.getRutTutor().equals(request.getRutTutor())) {
            validarTutor(request.getRutTutor());
        }

        mapearEntidad(request, m);
        return mapearDto(menorRepository.save(m));
    }

    public void eliminar(String rut) {
        Menor m = menorRepository.findByRut(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Menor no encontrado con el RUT: " + rut));
        menorRepository.delete(m);
    }

   
    private void mapearEntidad(MenorCreateDTO dto, Menor m) {
        m.setNombre(dto.getNombre());
        m.setRut(dto.getRut());
        m.setEdad(dto.getEdad());
        m.setNumeroActa(dto.getNumeroActa());
        m.setRutTutor(dto.getRutTutor());
    }

    private MenorDTO mapearDto(Menor m) {
        MenorDTO dto = new MenorDTO();
        dto.setNombre(m.getNombre());
        dto.setRut(m.getRut());
        dto.setEdad(m.getEdad());
        dto.setNumeroActa(m.getNumeroActa());
        dto.setRutTutor(m.getRutTutor());
        return dto;
    }
}