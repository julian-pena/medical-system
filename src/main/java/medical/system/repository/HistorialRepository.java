package medical.system.repository;

import medical.system.model.Historial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialRepository extends JpaRepository<Historial, Long> {
    List<Historial> findByPacienteId(Long pacienteId);
}
