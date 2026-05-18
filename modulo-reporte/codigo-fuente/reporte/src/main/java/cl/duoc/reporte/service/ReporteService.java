package cl.duoc.reporte.service;

import cl.duoc.reporte.client.UsuarioFeignClient;
import cl.duoc.reporte.dto.ReporteCreateDTO;
import cl.duoc.reporte.dto.ReporteDTO;
import cl.duoc.reporte.exception.RecursoNoEncontradoException;
import cl.duoc.reporte.exception.ServicioNoDisponibleException;
import cl.duoc.reporte.model.Reporte;
import cl.duoc.reporte.repository.ReporteRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private UsuarioFeignClient usuarioClient;

    private void validarUsuario(String rut) {
        try {
            usuarioClient.obtenerPorRut(rut);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Operación denegada: El usuario/funcionario con RUT " + rut + " no existe.");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("El servicio de Usuarios no responde.");
        }
    }

    public ReporteDTO crearReporte(ReporteCreateDTO request) {
        validarUsuario(request.getRutGenerador());
        
        Reporte reporte = new Reporte();
        reporte.setRutGenerador(request.getRutGenerador());
        reporte.setTipoReporte(request.getTipoReporte());
        reporte.setDescripcion(request.getDescripcion());
        reporte.setFechaCreacion(LocalDateTime.now());
        reporte.setGravedad(request.getGravedad());
        
        return mapearDto(reporteRepository.save(reporte));
    }

    public List<ReporteDTO> listarTodos() {
        return reporteRepository.findAll().stream()
                .map(this::mapearDto)
                .collect(Collectors.toList());
    }

    public ReporteDTO actualizarReporte(Long id, ReporteCreateDTO request) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado con el ID: " + id));

        validarUsuario(request.getRutGenerador());

        reporte.setRutGenerador(request.getRutGenerador());
        reporte.setTipoReporte(request.getTipoReporte());
        reporte.setDescripcion(request.getDescripcion());
        reporte.setGravedad(request.getGravedad());

        return mapearDto(reporteRepository.save(reporte));
    }

    public void eliminarReporte(Long id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado"));
        reporteRepository.delete(reporte);
    }

    private ReporteDTO mapearDto(Reporte r) {
        ReporteDTO dto = new ReporteDTO();
        dto.setId(r.getId());
        dto.setRutGenerador(r.getRutGenerador());
        dto.setTipoReporte(r.getTipoReporte());
        dto.setDescripcion(r.getDescripcion());
        dto.setFechaCreacion(r.getFechaCreacion());
        dto.setGravedad(r.getGravedad());
        return dto;
    }
}