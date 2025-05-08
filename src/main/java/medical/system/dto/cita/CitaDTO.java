package medical.system.dto.cita;


import io.swagger.v3.oas.annotations.media.Schema;
import medical.system.model.Cita;

import java.time.LocalDateTime;

@Schema(description = "DTO para una cita")
public record CitaDTO(
        @Schema(description = "ID de la cita", example = "1") Long id,
        @Schema(description = "Fecha y hora de la cita", example = "2023-05-01T10:00:00") LocalDateTime fechaHora,
        @Schema(description = "Motivo de la cita", example = "Revisión general") String motivo,
        @Schema(description = "Estado de la cita", example = "Pendiente") String estado,
        @Schema(description = "ID del paciente", example = "1") Long pacienteId,
        @Schema(description = "ID del médico", example = "2") Long medicoId
) {
    // Constructor que crea un DTO a partir de una entidad Cita
    public CitaDTO(Cita cita) {
        this(
                cita.getId(),
                cita.getFechaHora(),
                cita.getMotivo(),
                cita.getEstado().getNombre(),
                cita.getPaciente().getId(),
                cita.getMedico().getId()
        );
    }

    // Método para convertir una Cita a DTO
    public static CitaDTO fromCita(Cita cita) {
        return new CitaDTO(cita);
    }
}
