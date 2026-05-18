package cl.duoc.codigoqr.service;

import cl.duoc.codigoqr.client.UsuarioFeignClient;
import cl.duoc.codigoqr.dto.PaseQRCreateDTO;
import cl.duoc.codigoqr.dto.PaseQRDTO;
import cl.duoc.codigoqr.exception.RecursoNoEncontradoException;
import cl.duoc.codigoqr.exception.ServicioNoDisponibleException;
import cl.duoc.codigoqr.model.PaseQR;
import cl.duoc.codigoqr.repository.PaseQRRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaseQRService {

    @Autowired
    private PaseQRRepository paseQRRepository;

    @Autowired
    private UsuarioFeignClient usuarioClient;

    private void validarCiudadano(String rut) {
        try {
            usuarioClient.obtenerPorRut(rut);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Operación denegada. El RUT no existe en ms-usuario.");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("El servicio de Usuarios no responde.");
        }
    }

    public PaseQRDTO crearPase(PaseQRCreateDTO request) {
        validarCiudadano(request.getRutCiudadano());
        
        PaseQR pase = new PaseQR();
        pase.setRutCiudadano(request.getRutCiudadano());
        pase.setTipoPase(request.getTipoPase());
        pase.setEstado(request.getEstado());
        
        
        pase.setCodigoUuid(UUID.randomUUID().toString());
        
        return mapearDto(paseQRRepository.save(pase));
    }

    public List<PaseQRDTO> listarPases() {
        return paseQRRepository.findAll().stream()
                .map(this::mapearDto)
                .collect(Collectors.toList());
    }

    public PaseQRDTO actualizarPase(Long id, PaseQRCreateDTO request) {
        PaseQR pase = paseQRRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pase no encontrado con el ID: " + id));

        validarCiudadano(request.getRutCiudadano());

        pase.setRutCiudadano(request.getRutCiudadano());
        pase.setTipoPase(request.getTipoPase());
        pase.setEstado(request.getEstado());
        

        return mapearDto(paseQRRepository.save(pase));
    }

    public void eliminarPase(Long id) {
        PaseQR pase = paseQRRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pase no encontrado"));
        paseQRRepository.delete(pase);
    }

    private PaseQRDTO mapearDto(PaseQR p) {
        PaseQRDTO dto = new PaseQRDTO();
        dto.setId(p.getId());
        dto.setRutCiudadano(p.getRutCiudadano());
        dto.setTipoPase(p.getTipoPase());
        dto.setEstado(p.getEstado());
        dto.setCodigoUuid(p.getCodigoUuid());
        return dto;
    }
}