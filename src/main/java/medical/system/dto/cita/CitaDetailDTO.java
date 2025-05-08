package medical.system.dto.cita;

import io.swagger.v3.oas.annotations.media.Schema;
import medical.system.model.Cita;

import java.time.LocalDateTime;

/**
 * DTO para mostrar detalles completos de una cita, incluyendo información del paciente y médico
 */
@Schema(description = "DTO para mostrar detalles de una cita")
public record CitaDetailDTO(
        @Schema(description = "ID de la cita", example = "1") Long id,
        @Schema(description = "Fecha y hora de la cita", example = "2023-05-01T10:00:00") LocalDateTime fechaHora,
        @Schema(description = "Motivo de la cita", example = "Revisión general") String motivo,
        @Schema(description = "Estado de la cita", example = "Pendiente") String estado,
        // Información del paciente
        @Schema(description = "ID del paciente", example = "1") Long pacienteId,
        @Schema(description = "Nombre del paciente", example = "Juan") String pacienteNombre,
        @Schema(description = "Apellido del paciente", example = "Pérez") String pacienteApellido,
        @Schema(description = "Documento del paciente", example = "12345678") String pacienteDocumento,
        // Información del médico
        @Schema(description = "ID del médico", example = "2") Long medicoId,
        @Schema(description = "Nombre del médico", example = "María") String medicoNombre,
        @Schema(description = "Apellido del médico", example = "Gómez") String medicoApellido,
        @Schema(description = "Especialidad del médico", example = "Medicina general") String medicoEspecialidad
) {
    // Constructor que crea un DTO detallado a partir de una entidad Cita
    public CitaDetailDTO(Cita cita) {
        this(
                cita.getId(),
                cita.getFechaHora(),
                cita.getMotivo(),
                cita.getEstado().getNombre(),
                // Datos del paciente
                cita.getPaciente().getId(),
                cita.getPaciente().getNombre(),
                cita.getPaciente().getApellido(),
                cita.getPaciente().getDocumento(),
                // Datos del médico
                cita.getMedico().getId(),
                cita.getMedico().getNombre(),
                cita.getMedico().getApellido(),
                cita.getMedico().getEspecialidad().getNombre().getNombre()
        );
    }

    // Método para convertir una Cita a DTO detallado
    public static CitaDetailDTO fromCita(Cita cita) {
        return new CitaDetailDTO(cita);
    }
}

