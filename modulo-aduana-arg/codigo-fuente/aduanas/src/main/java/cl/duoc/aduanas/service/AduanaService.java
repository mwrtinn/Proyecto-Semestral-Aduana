package cl.duoc.aduanas.service;

import cl.duoc.aduanas.client.UsuarioFeignClient;
import cl.duoc.aduanas.dto.AduanaCreateDTO;
import cl.duoc.aduanas.dto.AduanaDTO;
import cl.duoc.aduanas.exception.RecursoNoEncontradoException;
import cl.duoc.aduanas.exception.ServicioNoDisponibleException;
import cl.duoc.aduanas.model.Aduana;
import cl.duoc.aduanas.repository.AduanaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AduanaService {

    @Autowired
    private AduanaRepository aduanaRepository;

    @Autowired
    private UsuarioFeignClient usuarioClient;

    private void validarCiudadano(String rut) {
        try {
            usuarioClient.obtenerPorRut(rut);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Operación denegada. El RUT del ciudadano no existe en ms-usuario.");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("El servicio de Usuarios no responde.");
        }
    }

    public AduanaDTO registrar(AduanaCreateDTO request) {
        validarCiudadano(request.getRutCiudadano());

        Aduana aduana = new Aduana();
        mapearEntidad(request, aduana);
        return mapearDto(aduanaRepository.save(aduana));
    }

    public List<AduanaDTO> listar() {
        return aduanaRepository.findAll().stream()
                .map(this::mapearDto)
                .collect(Collectors.toList());
    }

    public AduanaDTO obtener(Long id) {
        Aduana a = aduanaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Registro de cruce no encontrado"));
        return mapearDto(a);
    }

    public AduanaDTO actualizar(Long id, AduanaCreateDTO request) {
        Aduana a = aduanaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Registro de cruce no encontrado"));

        if (!a.getRutCiudadano().equals(request.getRutCiudadano())) {
            validarCiudadano(request.getRutCiudadano());
        }

        mapearEntidad(request, a);
        return mapearDto(aduanaRepository.save(a));
    }

    public void eliminar(Long id) {
        Aduana a = aduanaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Registro de cruce no encontrado"));
        aduanaRepository.delete(a);
    }

    private void mapearEntidad(AduanaCreateDTO dto, Aduana a) {
        a.setRutCiudadano(dto.getRutCiudadano());
        a.setFechaHoraCruce(dto.getFechaHoraCruce());
        a.setComplejoAduanero(dto.getComplejoAduanero());
        a.setTipoCruce(dto.getTipoCruce());
    }

    private AduanaDTO mapearDto(Aduana a) {
        AduanaDTO dto = new AduanaDTO();
        dto.setId(a.getId());
        dto.setRutCiudadano(a.getRutCiudadano());
        dto.setFechaHoraCruce(a.getFechaHoraCruce());
        dto.setComplejoAduanero(a.getComplejoAduanero());
        dto.setTipoCruce(a.getTipoCruce());
        return dto;
    }
}