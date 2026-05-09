package cl.duoc.declaraciones.service;

import cl.duoc.declaraciones.client.UsuarioFeignClient;
import cl.duoc.declaraciones.dto.DeclaracionCreateDTO;
import cl.duoc.declaraciones.dto.DeclaracionDTO;
import cl.duoc.declaraciones.exception.RecursoNoEncontradoException;
import cl.duoc.declaraciones.exception.ServicioNoDisponibleException;
import cl.duoc.declaraciones.model.Declaracion;
import cl.duoc.declaraciones.repository.DeclaracionRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeclaracionService {

    @Autowired
    private DeclaracionRepository declaracionRepository;

    @Autowired
    private UsuarioFeignClient usuarioClient;

    private void validarDeclarante(String rut) {
        try {
            usuarioClient.obtenerPorRut(rut);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("No se puede registrar la declaración: El usuario con RUT " + rut + " no está registrado en el sistema.");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("El servicio de Usuarios no responde. Intente registrar la declaración más tarde.");
        }
    }

    public DeclaracionDTO registrar(DeclaracionCreateDTO request) {
        validarDeclarante(request.getRutDeclarante());
        
        Declaracion declaracion = new Declaracion();
        mapearEntidad(request, declaracion);
        return mapearDto(declaracionRepository.save(declaracion));
    }

    public List<DeclaracionDTO> listar() {
        return declaracionRepository.findAll().stream()
                .map(this::mapearDto)
                .collect(Collectors.toList());
    }

    public DeclaracionDTO obtenerPorId(Long id) {
        Declaracion d = declaracionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Declaración no encontrada con el ID: " + id));
        return mapearDto(d);
    }

    public DeclaracionDTO actualizar(Long id, DeclaracionCreateDTO request) {
        Declaracion d = declaracionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Declaración no encontrada con el ID: " + id));
        
      
        if (!d.getRutDeclarante().equals(request.getRutDeclarante())) {
            validarDeclarante(request.getRutDeclarante());
        }

        mapearEntidad(request, d);
        return mapearDto(declaracionRepository.save(d));
    }

    public void eliminar(Long id) {
        Declaracion d = declaracionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Declaración no encontrada con el ID: " + id));
        declaracionRepository.delete(d);
    }

    // Funciones de mapeo
    private void mapearEntidad(DeclaracionCreateDTO dto, Declaracion d) {
        d.setRutDeclarante(dto.getRutDeclarante());
        d.setDescripcionArticulos(dto.getDescripcionArticulos());
        d.setValorEstimadoUsd(dto.getValorEstimadoUsd());
        d.setTraeAlimentos(dto.getTraeAlimentos());
        d.setPaisProcedencia(dto.getPaisProcedencia());
    }

    private DeclaracionDTO mapearDto(Declaracion d) {
        DeclaracionDTO dto = new DeclaracionDTO();
        dto.setId(d.getId());
        dto.setRutDeclarante(d.getRutDeclarante());
        dto.setDescripcionArticulos(d.getDescripcionArticulos());
        dto.setValorEstimadoUsd(d.getValorEstimadoUsd());
        dto.setTraeAlimentos(d.getTraeAlimentos());
        dto.setPaisProcedencia(d.getPaisProcedencia());
        return dto;
    }
}