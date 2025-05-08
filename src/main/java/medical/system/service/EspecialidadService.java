package medical.system.service;

import medical.system.model.Especialidad;
import medical.system.model.enums.EspecialidadEnum;
import medical.system.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    @Autowired
    public EspecialidadService(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    public List<Especialidad> getAllEspecialidades() {
        return especialidadRepository.findAll();
    }

    public Especialidad getEspecialidadByNombre(EspecialidadEnum nombre) {
        return especialidadRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada: " + nombre));
    }
}

