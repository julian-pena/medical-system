package medical.system.repository;

import medical.system.model.Especialidad;
import medical.system.model.enums.EspecialidadEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    boolean existsByNombre(EspecialidadEnum nombre);

    Optional<Especialidad> findByNombre(EspecialidadEnum nombre);
}
