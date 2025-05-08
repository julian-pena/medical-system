package medical.system.dto.medico;

import io.swagger.v3.oas.annotations.media.Schema;
import medical.system.model.Medico;

@Schema(description = "DTO público para un médico")
public record MedicoPublicDTO(
        @Schema(description = "Nombre del médico", example = "Juan") String nombre,
        @Schema(description = "Apellido del médico", example = "Pérez") String apellido,
        @Schema(description = "Correo electrónico del médico", example = "juan.perez@example.com") String correo,
        @Schema(description = "Teléfono del médico", example = "123456789") String telefono,
        @Schema(description = "Especialidad del médico", example = "Medicina general") String especialidad,
        @Schema(description = "Registro profesional del médico", example = "12345") String registroProfesional,
        @Schema(description = "Consultorio del médico", example = "Consultorio A") String consultorio,
        @Schema(description = "Estado del médico (activo/inactivo)", example = "true") Boolean activo
) {
    public MedicoPublicDTO(Medico medico) {
        this(
                medico.getNombre(),
                medico.getApellido(),
                medico.getCorreo(),
                medico.getTelefono(),
                medico.getEspecialidad().getNombre().getNombre(),  // Nombre de la especialidad
                medico.getRegistroProfesional(),
                medico.getConsultorio(),
                medico.getActivo()
        );
    }

    public static MedicoPublicDTO fromMedico(Medico medico) {
        return new MedicoPublicDTO(medico);
    }
}

