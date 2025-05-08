package medical.system.model.enums;

public enum EspecialidadEnum {
    CARDIOLOGIA("Cardiología"),
    PEDIATRIA("Pediatría"),
    GINECOLOGIA("Ginecología"),
    ORTOPEDIA("Ortopedia"),
    NEUROLOGIA("Neurología"),
    PSIQUIATRIA("Psiquiatría"),
    ONCOLOGIA("Oncología"),
    DERMATOLOGIA("Dermatología"),
    CIRUGIA_GENERAL("Cirugía General"),
    OTORRINOLARINGOLOGIA("Otorrinolaringología"),
    MEDICINA_INTERNA("Medicina Interna"),
    REUMATOLOGIA("Reumatología"),
    UROLOGIA("Urología"),
    GASTROENTEROLOGIA("Gastroenterología"),
    OFTALMOLOGIA("Oftalmología"),
    TRAUMATOLOGIA("Traumatología"),
    MEDICINA_FAMILIAR("Medicina Familiar");
    private final String nombre;

    EspecialidadEnum(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

