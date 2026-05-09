package cl.duoc.vehiculos.service;

import cl.duoc.vehiculos.client.UsuarioFeignClient;
import cl.duoc.vehiculos.dto.*;
import cl.duoc.vehiculos.exception.*;
import cl.duoc.vehiculos.model.Vehiculo;
import cl.duoc.vehiculos.repository.VehiculoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository repository;

    @Autowired
    private UsuarioFeignClient usuarioClient;

    public List<VehiculoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VehiculoDTO buscarPorPatente(String patente) {
        Vehiculo v = repository.findByPatente(patente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Vehículo no encontrado con Patente: " + patente));
        return toDTO(v);
    }
  
    public VehiculoDTO crear(VehiculoCreateDTO dto) {
        validarDueno(dto.getRutDueno());

        Vehiculo v = new Vehiculo();
        v.setVin(dto.getVin());
        v.setPatente(dto.getPatente());
        v.setMarca(dto.getMarca());
        v.setModelo(dto.getModelo());
        v.setAnio(dto.getAnio());
        v.setPaisOrigen(dto.getPaisOrigen());
        v.setRutDueno(dto.getRutDueno());

        return toDTO(repository.save(v));
    }

    public VehiculoDTO actualizar(String patente, VehiculoCreateDTO dto) {
        Vehiculo vehiculoExistente = repository.findByPatente(patente)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede actualizar. Vehículo no encontrado con Patente: " + patente));

       
        if (!vehiculoExistente.getRutDueno().equals(dto.getRutDueno())) {
            validarDueno(dto.getRutDueno());
        }

        vehiculoExistente.setVin(dto.getVin());
        vehiculoExistente.setMarca(dto.getMarca());
        vehiculoExistente.setModelo(dto.getModelo());
        vehiculoExistente.setAnio(dto.getAnio());
        vehiculoExistente.setPaisOrigen(dto.getPaisOrigen());
        vehiculoExistente.setRutDueno(dto.getRutDueno());

        return toDTO(repository.save(vehiculoExistente));
    }
   
    public void eliminar(String patente) {
        Vehiculo vehiculoExistente = repository.findByPatente(patente)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede eliminar. Vehículo no encontrado con Patente: " + patente));
        repository.delete(vehiculoExistente);
    }

    private void validarDueno(String rut) {
        try {
            usuarioClient.obtenerPorRut(rut);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("No se puede registrar el vehículo: El dueño con RUT " + rut + " no está registrado en el sistema.");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("El servicio de Usuarios no responde. Intente registrar el vehículo más tarde.");
        }
    }

    private VehiculoDTO toDTO(Vehiculo v) {
        VehiculoDTO dto = new VehiculoDTO();
        dto.setId(v.getId());
        dto.setVin(v.getVin());
        dto.setPatente(v.getPatente());
        dto.setMarca(v.getMarca());
        dto.setModelo(v.getModelo());
        dto.setAnio(v.getAnio());
        dto.setPaisOrigen(v.getPaisOrigen());
        dto.setRutDueno(v.getRutDueno());
        return dto;
    }
}