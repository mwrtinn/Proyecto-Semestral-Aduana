package cl.duoc.turno.service;

import cl.duoc.turno.client.UsuarioFeignClient;
import cl.duoc.turno.dto.TurnoCreateDTO;
import cl.duoc.turno.dto.TurnoDTO;
import cl.duoc.turno.exception.RecursoNoEncontradoException;
import cl.duoc.turno.exception.ServicioNoDisponibleException;
import cl.duoc.turno.model.Turno;
import cl.duoc.turno.repository.TurnoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private UsuarioFeignClient usuarioClient;

    private void validarFuncionario(String rut) {
        try {
            usuarioClient.obtenerPorRut(rut);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Operación denegada: El funcionario con RUT " + rut + " no existe.");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("El servicio de Usuarios no responde.");
        }
    }

    public TurnoDTO asignarTurno(TurnoCreateDTO request) {
        validarFuncionario(request.getRutFuncionario());
        
        Turno turno = new Turno();
        turno.setRutFuncionario(request.getRutFuncionario());
        turno.setPuesto(request.getPuesto());
        turno.setFechaInicio(request.getFechaInicio());
        turno.setFechaFin(request.getFechaFin());
        turno.setEstado(request.getEstado());
        
        return mapearDto(turnoRepository.save(turno));
    }

    public List<TurnoDTO> listarTurnos() {
        return turnoRepository.findAll().stream()
                .map(this::mapearDto)
                .collect(Collectors.toList());
    }

    public List<TurnoDTO> turnosPorFuncionario(String rut) {
        return turnoRepository.findByRutFuncionario(rut).stream()
                .map(this::mapearDto)
                .collect(Collectors.toList());
    }

    public TurnoDTO actualizarTurno(Long id, TurnoCreateDTO request) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Turno no encontrado con el ID: " + id));

        // Valida que el RUT del funcionario asignado al cambio también exista
        validarFuncionario(request.getRutFuncionario());

        turno.setRutFuncionario(request.getRutFuncionario());
        turno.setPuesto(request.getPuesto());
        turno.setFechaInicio(request.getFechaInicio());
        turno.setFechaFin(request.getFechaFin());
        turno.setEstado(request.getEstado());

        return mapearDto(turnoRepository.save(turno));
    }

    public void eliminarTurno(Long id) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Turno no encontrado"));
        turnoRepository.delete(turno);
    }

    private TurnoDTO mapearDto(Turno t) {
        TurnoDTO dto = new TurnoDTO();
        dto.setId(t.getId());
        dto.setRutFuncionario(t.getRutFuncionario());
        dto.setPuesto(t.getPuesto());
        dto.setFechaInicio(t.getFechaInicio());
        dto.setFechaFin(t.getFechaFin());
        dto.setEstado(t.getEstado());
        return dto;
    }
}