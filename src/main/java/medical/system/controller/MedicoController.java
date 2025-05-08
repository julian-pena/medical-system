package medical.system.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import medical.system.dto.medico.MedicoDTO;
import medical.system.dto.medico.MedicoPublicDTO;
import medical.system.service.MedicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Médicos", description = "Endpoints relacionados con la gestión de médicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @Operation(summary = "Crear un nuevo médico", responses = {
            @ApiResponse(responseCode = "200", description = "Médico creado exitosamente")
    })
    @PostMapping
    public ResponseEntity<MedicoDTO> createMedico(@RequestBody @Valid MedicoDTO medicoDTO) {
        MedicoDTO createdMedico = medicoService.createMedico(medicoDTO);
        return ResponseEntity.ok(createdMedico);
    }

    @Operation(summary = "Obtener todos los médicos (solo información pública)", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de médicos")
    })
    @GetMapping
    public ResponseEntity<List<MedicoPublicDTO>> getAllMedicos() {
        List<MedicoPublicDTO> medicos = medicoService.getAllMedicos();
        return ResponseEntity.ok(medicos);
    }

    @Operation(summary = "Obtener un médico por ID (solo información pública)", responses = {
            @ApiResponse(responseCode = "200", description = "Médico encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MedicoPublicDTO> getMedicoById(@Parameter(name = "id", description = "ID del médico") @PathVariable Long id) {
        MedicoPublicDTO medicoPublicDTO = medicoService.getMedicoById(id);
        return ResponseEntity.ok(medicoPublicDTO);
    }

    @Operation(summary = "Actualizar un médico", responses = {
            @ApiResponse(responseCode = "200", description = "Médico actualizado exitosamente")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MedicoDTO> updateMedico(
            @Parameter(name = "id", description = "ID del médico") @PathVariable Long id,
            @RequestBody @Valid MedicoDTO medicoDTO) {
        MedicoDTO updatedMedico = medicoService.updateMedico(id, medicoDTO);
        return ResponseEntity.ok(updatedMedico);
    }

    @Operation(summary = "Eliminar un médico", responses = {
            @ApiResponse(responseCode = "204", description = "Médico eliminado exitosamente")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@Parameter(name = "id", description = "ID del médico") @PathVariable Long id) {
        medicoService.deleteMedico(id);
        return ResponseEntity.noContent().build();
    }
}


