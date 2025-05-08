package medical.system.dto.paciente;

import io.swagger.v3.oas.annotations.media.Schema;
import medical.system.model.Paciente;

import java.time.LocalDate;

/**
 * DTO para retornar información pública de pacientes (sin contraseña ni datos sensibles)
 */
public record PacientePublicDTO(
        @Schema(description = "ID del paciente", example = "1")
        Long id,
        @Schema(description = "Nombre del paciente", example = "Juan")
        String nombre,
        @Schema(description = "Apellido del paciente", example = "Pérez")
        String apellido,
        @Schema(description = "Documento de identidad del paciente", example = "12345678")
        String documento,
        @Schema(description = "Fecha de nacimiento del paciente", example = "1990-01-01")
        LocalDate fechaNacimiento,
        @Schema(description = "Teléfono del paciente", example = "123456789")
        String telefono,
        @Schema(description = "Correo electrónico del paciente", example = "juan.perez@example.com")
        String correo,
        @Schema(description = "Estado del paciente (activo/inactivo)", example = "true")
        Boolean activo
) {
    // Constructor que crea un DTO público a partir de una entidad Paciente
    public PacientePublicDTO(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getDocumento(),
                paciente.getFechaNacimiento(),
                paciente.getTelefono(),
                paciente.getCorreo(),
                paciente.getActivo()
        );
    }

    // Método para convertir un Paciente a DTO público
    public static PacientePublicDTO fromPaciente(Paciente paciente) {
        return new PacientePublicDTO(paciente);
    }
}

