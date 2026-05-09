package cl.duoc.mascotas.service;

import cl.duoc.mascotas.client.UsuarioFeignClient;
import cl.duoc.mascotas.dto.MascotaCreateDTO;
import cl.duoc.mascotas.dto.MascotaDTO;
import cl.duoc.mascotas.exception.RecursoNoEncontradoException;
import cl.duoc.mascotas.exception.ServicioNoDisponibleException;
import cl.duoc.mascotas.model.Mascota;
import cl.duoc.mascotas.repository.MascotaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private UsuarioFeignClient usuarioClient;

    private void validarDueno(String rut) {
        try {
            usuarioClient.obtenerPorRut(rut);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("No se puede registrar la mascota: El dueño con RUT " + rut + " no está registrado en el sistema.");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("El servicio de Usuarios no responde. Intente registrar la mascota más tarde.");
        }
    }

    public MascotaDTO registrar(MascotaCreateDTO request) {
        if (mascotaRepository.findByMicrochip(request.getMicrochip()).isPresent()) {
            throw new ServicioNoDisponibleException("El microchip ya está registrado en el sistema.");
        }
        validarDueno(request.getRutDueno());
        
        Mascota mascota = new Mascota();
        mapearEntidad(request, mascota);
        return mapearDto(mascotaRepository.save(mascota));
    }

    public List<MascotaDTO> listar() {
        return mascotaRepository.findAll().stream()
                .map(this::mapearDto)
                .collect(Collectors.toList());
    }

    public MascotaDTO obtener(String microchip) {
        Mascota m = mascotaRepository.findByMicrochip(microchip)
                .orElseThrow(() -> new RecursoNoEncontradoException("Mascota no encontrada."));
        return mapearDto(m);
    }

    public MascotaDTO actualizar(String microchip, MascotaCreateDTO request) {
        Mascota m = mascotaRepository.findByMicrochip(microchip)
                .orElseThrow(() -> new RecursoNoEncontradoException("Mascota no encontrada."));
        
        if (!m.getMicrochip().equals(request.getMicrochip()) && mascotaRepository.findByMicrochip(request.getMicrochip()).isPresent()) {
            throw new ServicioNoDisponibleException("El nuevo microchip ya está registrado en otra mascota.");
        }

        if (!m.getRutDueno().equals(request.getRutDueno())) {
            validarDueno(request.getRutDueno());
        }

        mapearEntidad(request, m);
        return mapearDto(mascotaRepository.save(m));
    }

    public void eliminar(String microchip) {
        Mascota m = mascotaRepository.findByMicrochip(microchip)
                .orElseThrow(() -> new RecursoNoEncontradoException("Mascota no encontrada."));
        mascotaRepository.delete(m);
    }

    private void mapearEntidad(MascotaCreateDTO dto, Mascota m) {
        m.setNombre(dto.getNombre());
        m.setMicrochip(dto.getMicrochip());
        m.setEspecie(dto.getEspecie());
        m.setRaza(dto.getRaza());
        m.setEdad(dto.getEdad());
        m.setVacunaAntirrabica(dto.getVacunaAntirrabica());
        m.setNumeroCertificado(dto.getNumeroCertificado());
        m.setPaisOrigen(dto.getPaisOrigen());
        m.setRutDueno(dto.getRutDueno());
    }

    private MascotaDTO mapearDto(Mascota m) {
        MascotaDTO dto = new MascotaDTO();
        dto.setNombre(m.getNombre());
        dto.setMicrochip(m.getMicrochip());
        dto.setEspecie(m.getEspecie());
        dto.setRaza(m.getRaza());
        dto.setEdad(m.getEdad());
        dto.setVacunaAntirrabica(m.getVacunaAntirrabica());
        dto.setNumeroCertificado(m.getNumeroCertificado());
        dto.setPaisOrigen(m.getPaisOrigen());
        dto.setRutDueno(m.getRutDueno());
        return dto;
    }
}