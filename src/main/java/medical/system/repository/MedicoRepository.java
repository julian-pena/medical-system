package medical.system.repository;

import medical.system.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByCorreo(String correo);
    boolean existsByRegistroProfesional(String registroProfesional);
}
