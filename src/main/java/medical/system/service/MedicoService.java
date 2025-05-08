package medical.system.service;

import medical.system.config.exception.EspecialidadNoDisponibleException;
import medical.system.config.exception.ResourceNotFoundException;
import medical.system.dto.medico.MedicoPublicDTO;
import medical.system.dto.medico.MedicoDTO;
import medical.system.model.Medico;
import medical.system.model.Especialidad;
import medical.system.model.enums.EspecialidadEnum;
import medical.system.repository.EspecialidadRepository;
import medical.system.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    public MedicoService(MedicoRepository medicoRepository, EspecialidadRepository especialidadRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadRepository = especialidadRepository;
    }

    // Falta generar validaciones de correo y numero de registro frente a la base de datos
    public MedicoDTO createMedico(MedicoDTO medicoDTO) {
        Especialidad especialidad;

        try {
            // Convertir el nombre visible a la constante del enum
            EspecialidadEnum especialidadEnum = convertirNombreAEnum(medicoDTO.especialidad());

            // Buscamos la especialidad en la base de datos
            especialidad = especialidadRepository.findByNombre(especialidadEnum)
                    .orElseThrow(() -> new EspecialidadNoDisponibleException("Especialidad no encontrada: " + especialidadEnum));
        } catch (IllegalArgumentException e) {
            throw new EspecialidadNoDisponibleException("Especialidad no válida: " + medicoDTO.especialidad());
        }

        // Convertir el DTO a la entidad Medico
        Medico medico = MedicoDTO.fromDTO(medicoDTO, especialidad);

        // Guardar el medico en la base de datos
        Medico savedMedico = medicoRepository.save(medico);

        // Retornar el DTO del Medico creado
        return new MedicoDTO(savedMedico);
    }

    public List<MedicoPublicDTO> getAllMedicos() {
        List<Medico> medicos = medicoRepository.findAll();
        return medicos.stream()
                .map(MedicoPublicDTO::fromMedico)
                .collect(Collectors.toList());
    }

    public MedicoPublicDTO getMedicoById(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medico no encontrado"));
        return new MedicoPublicDTO(medico);
    }

    // Falta generar validaciones de correo y numero de registro frente a la base de datos
    public MedicoDTO updateMedico(Long id, MedicoDTO medicoDTO) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado"));

        Especialidad especialidad;

        try {
            // Convertir el nombre visible a la constante del enum
            EspecialidadEnum especialidadEnum = convertirNombreAEnum(medicoDTO.especialidad());

            // Buscamos la especialidad en la base de datos
            especialidad = especialidadRepository.findByNombre(especialidadEnum)
                    .orElseThrow(() -> new EspecialidadNoDisponibleException("Especialidad no encontrada: " + especialidadEnum));
        } catch (IllegalArgumentException e) {
            throw new EspecialidadNoDisponibleException("Especialidad no válida: " + medicoDTO.especialidad());
        }

        // Actualizar los campos del medico con los valores del DTO
        medico.setNombre(medicoDTO.nombre());
        medico.setApellido(medicoDTO.apellido());
        medico.setRegistroProfesional(medicoDTO.registroProfesional());
        medico.setTelefono(medicoDTO.telefono());
        medico.setCorreo(medicoDTO.correo()); // Falta verificación de email frente a base de datos
        medico.setPassword(medicoDTO.password());
        medico.setConsultorio(medicoDTO.consultorio());
        medico.setActivo(medicoDTO.activo());
        medico.setEspecialidad(especialidad);

        // Guardar el médico actualizado en la base de datos
        Medico updatedMedico = medicoRepository.save(medico);

        // Retornar el DTO actualizado
        return new MedicoDTO(updatedMedico);
    }

    public void deleteMedico(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado"));
        medicoRepository.delete(medico);
    }

    // Método auxiliar para convertir el nombre visible (Pediatría) a la constante del enum (PEDIATRIA)
    private EspecialidadEnum convertirNombreAEnum(String nombreVisible) {
        return Arrays.stream(EspecialidadEnum.values())
                .filter(especialidad -> especialidad.getNombre().equalsIgnoreCase(nombreVisible))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada: " + nombreVisible));
    }
}