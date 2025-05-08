package medical.system.dto.historial;

import io.swagger.v3.oas.annotations.media.Schema;
import medical.system.model.Historial;
import medical.system.model.Paciente;
import medical.system.model.Medico;

import java.time.LocalDateTime;

public record HistorialDTO(
        @Schema(description = "ID del historial médico", example = "1")
        Long id,
        @Schema(description = "Descripción del historial médico", example = "Paciente presentó síntomas de gripe")
        String descripcion,
        @Schema(description = "Fecha y hora del historial médico", example = "2023-04-15T10:30:00")
        LocalDateTime fecha,
        @Schema(description = "ID del paciente asociado al historial médico", example = "1")
        Long pacienteId,
        @Schema(description = "ID del médico asociado al historial médico (opcional)", example = "2")
        Long medicoId
) {
    public static HistorialDTO fromHistorial(Historial historial) {
        return new HistorialDTO(
                historial.getId(),
                historial.getDescripcion(),
                historial.getFecha(),
                historial.getPaciente().getId(),
                historial.getMedico() != null ? historial.getMedico().getId() : null
        );
    }

    public static Historial toEntity(HistorialDTO historialDTO, Paciente paciente, Medico medico) {
        Historial historial = new Historial();
        historial.setId(historialDTO.id());
        historial.setDescripcion(historialDTO.descripcion());
        historial.setFecha(historialDTO.fecha());
        historial.setPaciente(paciente);
        historial.setMedico(medico);
        return historial;
    }
}
