package medical.system.service;

import medical.system.dto.historial.HistorialDTO;
import medical.system.model.Historial;
import medical.system.model.Paciente;
import medical.system.model.Medico;
import medical.system.repository.HistorialRepository;
import medical.system.repository.PacienteRepository;
import medical.system.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialService {

    private final HistorialRepository historialRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    @Autowired
    public HistorialService(HistorialRepository historialRepository, PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
        this.historialRepository = historialRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public HistorialDTO createHistorial(HistorialDTO historialDTO) {
        Paciente paciente = pacienteRepository.findById(historialDTO.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        Medico medico = historialDTO.medicoId() != null
                ? medicoRepository.findById(historialDTO.medicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"))
                : null;

        Historial historial = HistorialDTO.toEntity(historialDTO, paciente, medico);
        Historial savedHistorial = historialRepository.save(historial);
        return HistorialDTO.fromHistorial(savedHistorial);
    }

    public List<HistorialDTO> getAllHistorialByPaciente(Long pacienteId) {
        List<Historial> historialList = historialRepository.findByPacienteId(pacienteId);
        return historialList.stream()
                .map(HistorialDTO::fromHistorial)
                .collect(Collectors.toList());
    }

    public HistorialDTO getHistorialById(Long id) {
        Historial historial = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        return HistorialDTO.fromHistorial(historial);
    }

    public HistorialDTO updateHistorial(Long id, HistorialDTO historialDTO) {
        Historial historial = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));

        Paciente paciente = pacienteRepository.findById(historialDTO.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        Medico medico = historialDTO.medicoId() != null
                ? medicoRepository.findById(historialDTO.medicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"))
                : null;

        historial.setDescripcion(historialDTO.descripcion());
        historial.setFecha(historialDTO.fecha());
        historial.setPaciente(paciente);
        historial.setMedico(medico);

        Historial updatedHistorial = historialRepository.save(historial);
        return HistorialDTO.fromHistorial(updatedHistorial);
    }

    public void deleteHistorial(Long id) {
        historialRepository.deleteById(id);
    }
}
