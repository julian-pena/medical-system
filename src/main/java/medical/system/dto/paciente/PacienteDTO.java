package medical.system.dto.paciente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import medical.system.model.Paciente;

import java.time.LocalDate;

public record PacienteDTO(
        @Schema(description = "ID del paciente", example = "1")
        Long id,
        @Schema(description = "Nombre del paciente", example = "Juan")
        @NotEmpty
        String nombre,
        @Schema(description = "Apellido del paciente", example = "Pérez")
        @NotEmpty
        String apellido,
        @Schema(description = "Documento de identidad del paciente", example = "12345678")
        @NotEmpty
        String documento,
        @Schema(description = "Fecha de nacimiento del paciente", example = "1990-01-01")
        @NotNull
        LocalDate fechaNacimiento,
        @Schema(description = "Teléfono del paciente", example = "123456789")
        @NotNull
        String telefono,
        @Schema(description = "Correo electrónico del paciente", example = "juan.perez@example.com")
        @NotEmpty
        @Email
        String correo,
        @Schema(description = "Contraseña del paciente", example = "mypassword")
        @NotEmpty(message = "Password can not be null nor empty")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$",
                message = "Password must be at least 12 characters long, contain at least one uppercase letter, one lowercase letter, one special character, and one number")
        String password,
        @Schema(description = "Estado del paciente (activo/inactivo)", example = "true")
        Boolean activo
) {
    // Constructor que crea un DTO a partir de una entidad Paciente
    public PacienteDTO(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getDocumento(),
                paciente.getFechaNacimiento(),
                paciente.getTelefono(),
                paciente.getCorreo(),
                paciente.getPassword(),
                paciente.getActivo()
        );
    }

    // Método para convertir un Paciente a DTO
    public static PacienteDTO fromPaciente(Paciente paciente) {
        return new PacienteDTO(paciente);
    }

    // Método para convertir un DTO a entidad Paciente
    public static Paciente toEntity(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setId(pacienteDTO.id());
        paciente.setNombre(pacienteDTO.nombre());
        paciente.setApellido(pacienteDTO.apellido());
        paciente.setDocumento(pacienteDTO.documento());
        paciente.setFechaNacimiento(pacienteDTO.fechaNacimiento());
        paciente.setTelefono(pacienteDTO.telefono());
        paciente.setCorreo(pacienteDTO.correo());
        paciente.setPassword(pacienteDTO.password());
        paciente.setActivo(pacienteDTO.activo());
        return paciente;
    }
}
