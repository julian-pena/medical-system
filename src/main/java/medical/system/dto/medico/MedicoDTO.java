package medical.system.dto.medico;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import medical.system.model.Medico;
import medical.system.model.Especialidad;

@Schema(description = "DTO para un médico")
public record MedicoDTO(
        @Schema(description = "ID del médico", example = "1") Long id,

        @Schema(description = "Nombre del médico", example = "Juan") @NotEmpty String nombre,
        @Schema(description = "Apellido del médico", example = "Pérez") @NotEmpty String apellido,
        @Schema(description = "Registro profesional del médico", example = "12345") @NotBlank String registroProfesional,
        @Schema(description = "Teléfono del médico", example = "123456789") @NotNull String telefono,
        @Schema(description = "Correo electrónico del médico", example = "juan.perez@example.com") @NotEmpty @Email String correo,
        @Schema(description = "Contraseña del médico", example = "password123")
        @NotEmpty(message = "Password can not be null nor empty")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$",
                message = "Password must be at least 12 characters long, contain at least one uppercase letter, one lowercase letter, one special character, and one number")
        String password,
        @Schema(description = "Consultorio del médico", example = "Consultorio A") String consultorio,
        @Schema(description = "Estado del médico (activo/inactivo)", example = "true") Boolean activo,
        @Schema(description = "Especialidad del médico", example = "Medicina general") @NotNull String especialidad
) {
    public MedicoDTO(Medico medico) {
        this(
                medico.getId(),
                medico.getNombre(),
                medico.getApellido(),
                medico.getRegistroProfesional(),
                medico.getTelefono(),
                medico.getCorreo(),
                medico.getPassword(),
                medico.getConsultorio(),
                medico.getActivo(),
                medico.getEspecialidad().getNombre().getNombre() // Solo el nombre de la especialidad
        );
    }

    public static MedicoDTO fromMedico(Medico medico) {
        return new MedicoDTO(medico);
    }

    public static Medico fromDTO(MedicoDTO medicoDTO, Especialidad especialidad) {
        return new Medico(
                medicoDTO.id(),
                medicoDTO.nombre(),
                medicoDTO.apellido(),
                medicoDTO.registroProfesional(),
                medicoDTO.telefono(),
                medicoDTO.correo(),
                medicoDTO.password(),
                medicoDTO.consultorio(),
                medicoDTO.activo(),
                especialidad  // Usamos la especialidad proporcionada
        );
    }
}

