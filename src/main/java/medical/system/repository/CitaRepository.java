package medical.system.repository;

import medical.system.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import medical.system.model.Medico;
import medical.system.model.Paciente;
import medical.system.model.enums.EstadoCita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Buscar citas por paciente
    List<Cita> findByPacienteOrderByFechaHoraDesc(Paciente paciente);

    // Buscar citas por médico
    List<Cita> findByMedicoOrderByFechaHoraDesc(Medico medico);

    // Buscar citas por estado
    List<Cita> findByEstadoOrderByFechaHoraDesc(EstadoCita estado);

    // Buscar citas por rango de fechas
    List<Cita> findByFechaHoraBetweenOrderByFechaHoraAsc(LocalDateTime inicio, LocalDateTime fin);

    // Buscar citas de un médico en un rango de fechas
    List<Cita> findByMedicoAndFechaHoraBetweenOrderByFechaHoraAsc(Medico medico, LocalDateTime inicio, LocalDateTime fin);

    // Buscar citas de un paciente en un rango de fechas
    List<Cita> findByPacienteAndFechaHoraBetweenOrderByFechaHoraAsc(Paciente paciente, LocalDateTime inicio, LocalDateTime fin);

    // Verificar si un médico tiene citas en un horario específico
    boolean existsByMedicoAndFechaHoraAndEstadoNot(Medico medico, LocalDateTime fechaHora, EstadoCita estadoExcluido);

    // Query personalizada para verificar disponibilidad del médico
    @Query("SELECT COUNT(c) > 0 FROM Cita c WHERE c.medico.id = :medicoId " +
            "AND c.fechaHora BETWEEN :inicio AND :fin " +
            "AND c.estado != :estadoExcluido")
    boolean medicoTieneCitaEnRango(
            @Param("medicoId") Long medicoId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin,
            @Param("estadoExcluido") EstadoCita estadoExcluido);
}
