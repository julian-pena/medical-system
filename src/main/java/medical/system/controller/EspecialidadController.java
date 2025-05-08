package medical.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import medical.system.model.Especialidad;
import medical.system.model.enums.EspecialidadEnum;
import medical.system.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @Autowired
    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @Operation(summary = "Obtener todas las especialidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de especialidades obtenida exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Especialidad.class))}),
            @ApiResponse(responseCode = "404", description = "No se encontraron especialidades")
    })
    @GetMapping
    public ResponseEntity<List<Especialidad>> getAllEspecialidades() {
        List<Especialidad> especialidades = especialidadService.getAllEspecialidades();
        return new ResponseEntity<>(especialidades, HttpStatus.OK);
    }

    @Operation(summary = "Obtener una especialidad por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidad obtenida exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Especialidad.class))}),
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    @GetMapping("/{nombre}")
    public ResponseEntity<Especialidad> getEspecialidadByNombre(@PathVariable EspecialidadEnum nombre) {
        Especialidad especialidad = especialidadService.getEspecialidadByNombre(nombre);
        return new ResponseEntity<>(especialidad, HttpStatus.OK);
    }
}
