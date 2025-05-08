package medical.system.model;

import jakarta.persistence.*;
import lombok.*;
import medical.system.model.enums.EspecialidadEnum;

@Entity
@Table(name = "especialidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @Enumerated(EnumType.STRING)
    private EspecialidadEnum nombre;

    public Especialidad(EspecialidadEnum nombre) {
        this.nombre = nombre;
    }
}
