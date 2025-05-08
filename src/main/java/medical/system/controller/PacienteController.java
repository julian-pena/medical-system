package medical.system.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import medical.system.dto.paciente.PacienteDTO;
import medical.system.dto.paciente.PacientePublicDTO;
import medical.system.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Operation(summary = "Crear un nuevo paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Datos de paciente inválidos")
    })
    @PostMapping
    public ResponseEntity<PacienteDTO> createPaciente(@RequestBody @Valid PacienteDTO pacienteDTO) {
        PacienteDTO createdPaciente = pacienteService.createPaciente(pacienteDTO);
        return new ResponseEntity<>(createdPaciente, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes obtenida exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacientePublicDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No se encontraron pacientes")
    })
    @GetMapping
    public ResponseEntity<List<PacientePublicDTO>> getAllPacientes() {
        List<PacientePublicDTO> pacientes = pacienteService.getAllPacientes();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un paciente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente obtenido exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacientePublicDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacientePublicDTO> getPacienteById(@PathVariable Long id) {
        PacientePublicDTO paciente = pacienteService.getPacienteById(id);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un paciente por documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente obtenido exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacientePublicDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/documento/{documento}")
    public ResponseEntity<PacientePublicDTO> getPacienteByDocumento(@PathVariable String documento) {
        PacientePublicDTO paciente = pacienteService.getPacienteByDocumento(documento);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar un paciente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de paciente inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> updatePaciente(@PathVariable Long id, @RequestBody @Valid PacienteDTO pacienteDTO) {
        PacienteDTO updatedPaciente = pacienteService.updatePaciente(id, pacienteDTO);
        return new ResponseEntity<>(updatedPaciente, HttpStatus.OK);
    }

    @Operation(summary = "Desactivar un paciente (borrado lógico)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente desactivado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> deactivatePaciente(@PathVariable Long id) {
        pacienteService.deactivatePaciente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Eliminar un paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        pacienteService.deletePaciente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
