package medical.system.config;

import jakarta.annotation.PostConstruct;
import medical.system.model.Especialidad;
import medical.system.model.enums.EspecialidadEnum;
import medical.system.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EspecialidadInitializer {

    private final EspecialidadRepository especialidadRepository;

    @Autowired
    public EspecialidadInitializer(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    @PostConstruct
    public void init() {
        Arrays.stream(EspecialidadEnum.values())
                .filter(e -> !especialidadRepository.existsByNombre(e))
                .forEach(e -> especialidadRepository.save(new Especialidad(e)));
    }
}


