package medical.system.dto.cita;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import medical.system.model.Cita;

import java.time.LocalDateTime;

/**
 * DTO para la creación de citas, contiene solo los campos necesarios para crear una nueva cita
 */
@Schema(description = "DTO para la creación de citas")
public record CitaCreateDTO(
        @Schema(description = "Fecha y hora de la cita", example = "2023-05-01T10:00:00") @NotNull LocalDateTime fechaHora,
        @Schema(description = "Motivo de la cita", example = "Revisión general") String motivo,
        @Schema(description = "ID del paciente", example = "1") @NotNull Long pacienteId,
        @Schema(description = "ID del médico", example = "2") @NotNull Long medicoId
) {
}
