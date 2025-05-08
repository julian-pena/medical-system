package medical.system.service;


import medical.system.config.exception.ResourceNotFoundException;
import medical.system.dto.paciente.PacienteDTO;
import medical.system.dto.paciente.PacientePublicDTO;
import medical.system.model.Paciente;
import medical.system.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }


    // Crear excepcion personalizada para duplicado
    public PacienteDTO createPaciente(PacienteDTO pacienteDTO) {
        // Verificar si ya existe un paciente con el mismo documento
        if (pacienteRepository.existsByDocumento(pacienteDTO.documento())) {
            throw new ResourceNotFoundException("Ya existe un paciente con el documento: " + pacienteDTO.documento());
        }

        // Verificar si ya existe un paciente con el mismo correo
        if (pacienteRepository.existsByCorreo(pacienteDTO.correo())) {
            throw new ResourceNotFoundException("Ya existe un paciente con el correo: " + pacienteDTO.correo());
        }

        // Convertir DTO a entidad
        Paciente paciente = PacienteDTO.toEntity(pacienteDTO);

        // Si es un nuevo paciente, asegurarse que esté activo por defecto
        if (paciente.getId() == null && paciente.getActivo() == null) {
            paciente.setActivo(true);
        }

        // Guardar el paciente
        Paciente savedPaciente = pacienteRepository.save(paciente);

        // Retornar el DTO del paciente guardado
        return new PacienteDTO(savedPaciente);
    }


    public List<PacientePublicDTO> getAllPacientes() {
        return pacienteRepository.findAll().stream()
                .map(PacientePublicDTO::fromPaciente)
                .collect(Collectors.toList());
    }


    public PacientePublicDTO getPacienteById(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
        return new PacientePublicDTO(paciente);
    }


    public PacientePublicDTO getPacienteByDocumento(String documento) {
        Paciente paciente = pacienteRepository.findByDocumento(documento)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con documento: " + documento));
        return new PacientePublicDTO(paciente);
    }

    // Crear excepcion personalizada para duplicado
    public PacienteDTO updatePaciente(Long id, PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));

        // Verificar si ya existe otro paciente con el mismo documento
        if (!paciente.getDocumento().equals(pacienteDTO.documento()) &&
                pacienteRepository.existsByDocumento(pacienteDTO.documento())) {
            throw new ResourceNotFoundException("Ya existe otro paciente con el documento: " + pacienteDTO.documento());
        }

        // Verificar si ya existe otro paciente con el mismo correo
        if (!paciente.getCorreo().equals(pacienteDTO.correo()) &&
                pacienteRepository.existsByCorreo(pacienteDTO.correo())) {
            throw new ResourceNotFoundException("Ya existe otro paciente con el correo: " + pacienteDTO.correo());
        }

        // Actualizar campos
        paciente.setNombre(pacienteDTO.nombre());
        paciente.setApellido(pacienteDTO.apellido());
        paciente.setDocumento(pacienteDTO.documento());
        paciente.setFechaNacimiento(pacienteDTO.fechaNacimiento());
        paciente.setTelefono(pacienteDTO.telefono());
        paciente.setCorreo(pacienteDTO.correo());
        paciente.setPassword(pacienteDTO.password());
        paciente.setActivo(pacienteDTO.activo());

        // Guardar los cambios
        Paciente updatedPaciente = pacienteRepository.save(paciente);

        // Retornar el DTO actualizado
        return new PacienteDTO(updatedPaciente);
    }


    public void deactivatePaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
        paciente.setActivo(false);
        pacienteRepository.save(paciente);
    }


    public void deletePaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }
}
