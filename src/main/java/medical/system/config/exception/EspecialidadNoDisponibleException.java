package medical.system.config.exception;

import medical.system.model.enums.EspecialidadEnum;

public class EspecialidadNoDisponibleException extends RuntimeException {
    public EspecialidadNoDisponibleException(String especialidad) {
        super(especialidad + "' no está disponible. Las especialidades disponibles son: " + getEspecialidadesDisponibles());
    }

    private static String getEspecialidadesDisponibles() {
        StringBuilder sb = new StringBuilder();
        for (EspecialidadEnum especialidad : EspecialidadEnum.values()) {
            sb.append(especialidad.getNombre()).append(", ");
        }
        return sb.substring(0, sb.length() - 2); // Eliminar la última coma y espacio
    }
}

