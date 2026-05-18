package cl.duoc.correos.service;

import cl.duoc.correos.client.UsuarioFeignClient;
import cl.duoc.correos.dto.EncomiendaCreateDTO;
import cl.duoc.correos.dto.EncomiendaDTO;
import cl.duoc.correos.exception.RecursoNoEncontradoException;
import cl.duoc.correos.exception.ServicioNoDisponibleException;
import cl.duoc.correos.model.Encomienda;
import cl.duoc.correos.repository.EncomiendaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EncomiendaService {

    @Autowired
    private EncomiendaRepository encomiendaRepository;

    @Autowired
    private UsuarioFeignClient usuarioClient;

    private void validarDestinatario(String rut) {
        try {
            usuarioClient.obtenerPorRut(rut);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Operación denegada. El RUT del destinatario no existe en ms-usuario.");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("El servicio de Usuarios no responde.");
        }
    }

    public EncomiendaDTO registrar(EncomiendaCreateDTO request) {
        if (encomiendaRepository.findByTrackingNumber(request.getTrackingNumber()).isPresent()) {
            throw new ServicioNoDisponibleException("El número de tracking ya se encuentra registrado.");
        }
        
        validarDestinatario(request.getRutDestinatario());

        Encomienda encomienda = new Encomienda();
        mapearEntidad(request, encomienda);
        return mapearDto(encomiendaRepository.save(encomienda));
    }

    public List<EncomiendaDTO> listar() {
        return encomiendaRepository.findAll().stream()
                .map(this::mapearDto)
                .collect(Collectors.toList());
    }

    public EncomiendaDTO obtener(String trackingNumber) {
        Encomienda e = encomiendaRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RecursoNoEncontradoException("Encomienda no encontrada"));
        return mapearDto(e);
    }

    public EncomiendaDTO actualizar(String trackingNumber, EncomiendaCreateDTO request) {
        Encomienda e = encomiendaRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RecursoNoEncontradoException("Encomienda no encontrada"));

        if (!e.getRutDestinatario().equals(request.getRutDestinatario())) {
            validarDestinatario(request.getRutDestinatario());
        }

        mapearEntidad(request, e);
        return mapearDto(encomiendaRepository.save(e));
    }

    public void eliminar(String trackingNumber) {
        Encomienda e = encomiendaRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RecursoNoEncontradoException("Encomienda no encontrada"));
        encomiendaRepository.delete(e);
    }

    private void mapearEntidad(EncomiendaCreateDTO dto, Encomienda e) {
        e.setTrackingNumber(dto.getTrackingNumber());
        e.setRutDestinatario(dto.getRutDestinatario());
        e.setPesoKg(dto.getPesoKg());
        e.setDescripcionContenido(dto.getDescripcionContenido());
        e.setEstado(dto.getEstado());
    }

    private EncomiendaDTO mapearDto(Encomienda e) {
        EncomiendaDTO dto = new EncomiendaDTO();
        dto.setTrackingNumber(e.getTrackingNumber());
        dto.setRutDestinatario(e.getRutDestinatario());
        dto.setPesoKg(e.getPesoKg());
        dto.setDescripcionContenido(e.getDescripcionContenido());
        dto.setEstado(e.getEstado());
        return dto;
    }
}