package medical.system.service;

import medical.system.config.exception.ResourceNotFoundException;
import medical.system.dto.cita.*;
import medical.system.model.Cita;
import medical.system.model.Medico;
import medical.system.model.Paciente;
import medical.system.model.enums.EstadoCita;
import medical.system.repository.CitaRepository;
import medical.system.repository.MedicoRepository;
import medical.system.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    @Autowired
    public CitaService(CitaRepository citaRepository,
                       PacienteRepository pacienteRepository,
                       MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    // Pendiente actualizar excepciones de estado activo
    @Transactional
    public CitaDetailDTO createCita(CitaCreateDTO citaCreateDTO) {
        // Buscar paciente y médico
        Paciente paciente = pacienteRepository.findById(citaCreateDTO.pacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + citaCreateDTO.pacienteId()));

        Medico medico = medicoRepository.findById(citaCreateDTO.medicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado con ID: " + citaCreateDTO.medicoId()));

        // Verificar que el paciente y el médico estén activos
        if (!paciente.getActivo()) {
            throw new ResourceNotFoundException("El paciente no está activo");
        }
        if (!medico.getActivo()) {
            throw new ResourceNotFoundException("El médico no está activo");
        }

        // Verificar disponibilidad del médico (considera 30 minutos por consulta)
        LocalDateTime inicio = citaCreateDTO.fechaHora();
        LocalDateTime fin = citaCreateDTO.fechaHora().plusMinutes(30);

        if (citaRepository.medicoTieneCitaEnRango(medico.getId(), inicio, fin, EstadoCita.CANCELADA)) {
            throw new ResourceNotFoundException("El médico ya tiene una cita programada en ese horario");
        }

        // Crear y guardar la cita
        Cita cita = new Cita();
        cita.setFechaHora(citaCreateDTO.fechaHora());
        cita.setMotivo(citaCreateDTO.motivo());
        cita.setEstado(EstadoCita.PENDIENTE); // Por defecto las citas se crean en estado PENDIENTE
        cita.setPaciente(paciente);
        cita.setMedico(medico);

        Cita savedCita = citaRepository.save(cita);

        return new CitaDetailDTO(savedCita);
    }

    @Transactional(readOnly = true)
    public List<CitaDetailDTO> getAllCitas() {
        return citaRepository.findAll().stream()
                .map(CitaDetailDTO::fromCita)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CitaDetailDTO getCitaById(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        return new CitaDetailDTO(cita);
    }

    @Transactional(readOnly = true)
    public List<CitaDetailDTO> getCitasByPaciente(Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + pacienteId));

        return citaRepository.findByPacienteOrderByFechaHoraDesc(paciente).stream()
                .map(CitaDetailDTO::fromCita)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CitaDetailDTO> getCitasByMedico(Long medicoId) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado con ID: " + medicoId));

        return citaRepository.findByMedicoOrderByFechaHoraDesc(medico).stream()
                .map(CitaDetailDTO::fromCita)
                .collect(Collectors.toList());
    }

    // Pendiente exepcion para estado de cita
    @Transactional(readOnly = true)
    public List<CitaDetailDTO> getCitasByEstado(String estado) {
        try {
            EstadoCita estadoCita = convertirStringAEstadoCita(estado);

            return citaRepository.findByEstadoOrderByFechaHoraDesc(estadoCita).stream()
                    .map(CitaDetailDTO::fromCita)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Estado de cita no válido: " + estado);
        }
    }

    @Transactional(readOnly = true)
    public List<CitaDetailDTO> getCitasByFecha(LocalDate fecha) {
        LocalDateTime inicioDia = fecha.atStartOfDay();
        LocalDateTime finDia = fecha.atTime(LocalTime.MAX);

        return citaRepository.findByFechaHoraBetweenOrderByFechaHoraAsc(inicioDia, finDia).stream()
                .map(CitaDetailDTO::fromCita)
                .collect(Collectors.toList());
    }

    // Pendiente excepcion estado de cita
    @Transactional
    public CitaDetailDTO actualizarEstadoCita(Long id, String nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));

        try {
            EstadoCita estadoCita = convertirStringAEstadoCita(nuevoEstado);
            cita.setEstado(estadoCita);

            Cita updatedCita = citaRepository.save(cita);
            return new CitaDetailDTO(updatedCita);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Estado de cita no válido: " + nuevoEstado);
        }
    }

    // Pendiente excepcion de disponibilidad de cita
    @Transactional
    public CitaDetailDTO updateCita(Long id, CitaDTO citaDTO) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));

        // Si se cambia el médico o la fecha, verificar disponibilidad
        if (!cita.getMedico().getId().equals(citaDTO.medicoId()) ||
                !cita.getFechaHora().equals(citaDTO.fechaHora())) {

            Medico nuevoMedico = medicoRepository.findById(citaDTO.medicoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado con ID: " + citaDTO.medicoId()));

            LocalDateTime inicio = citaDTO.fechaHora();
            LocalDateTime fin = citaDTO.fechaHora().plusMinutes(30);

            if (citaRepository.medicoTieneCitaEnRango(nuevoMedico.getId(), inicio, fin, EstadoCita.CANCELADA)) {
                throw new ResourceNotFoundException("El médico ya tiene una cita programada en ese horario");
            }

            cita.setMedico(nuevoMedico);
        }

        // Solo actualizamos si el paciente ha cambiado
        if (!cita.getPaciente().getId().equals(citaDTO.pacienteId())) {
            Paciente nuevoPaciente = pacienteRepository.findById(citaDTO.pacienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + citaDTO.pacienteId()));
            cita.setPaciente(nuevoPaciente);
        }

        // Actualizar otros campos
        cita.setFechaHora(citaDTO.fechaHora());
        cita.setMotivo(citaDTO.motivo());

        // Actualizar estado si se proporciona
        if (citaDTO.estado() != null) {
            try {
                EstadoCita estadoCita = convertirStringAEstadoCita(citaDTO.estado());
                cita.setEstado(estadoCita);
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException("Estado de cita no válido: " + citaDTO.estado());
            }
        }

        Cita updatedCita = citaRepository.save(cita);
        return new CitaDetailDTO(updatedCita);
    }

    // Pendiente excepcion de estado de cita
    @Transactional
    public CitaDetailDTO cancelarCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));

        // Verificar que la cita no esté ya cancelada o finalizada
        if (cita.getEstado() == EstadoCita.CANCELADA) {
            throw new ResourceNotFoundException("Esta cita ya ha sido cancelada");
        }
        if (cita.getEstado() == EstadoCita.FINALIZADA) {
            throw new ResourceNotFoundException("No se puede cancelar una cita finalizada");
        }

        cita.setEstado(EstadoCita.CANCELADA);
        Cita canceledCita = citaRepository.save(cita);

        return new CitaDetailDTO(canceledCita);
    }

    private EstadoCita convertirStringAEstadoCita(String estado) {
        return Arrays.stream(EstadoCita.values())
                .filter(e -> e.getNombre().equalsIgnoreCase(estado))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Estado no válido: " + estado));
    }
}