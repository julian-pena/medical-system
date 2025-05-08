package medical.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import medical.system.dto.historial.HistorialDTO;
import medical.system.service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
public class HistorialController {

    private final HistorialService historialService;

    @Autowired
    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    @Operation(summary = "Crear un nuevo historial médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Historial médico creado exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistorialDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Datos de historial médico inválidos")
    })
    @PostMapping
    public ResponseEntity<HistorialDTO> createHistorial(@RequestBody @Valid HistorialDTO historialDTO) {
        HistorialDTO createdHistorial = historialService.createHistorial(historialDTO);
        return new ResponseEntity<>(createdHistorial, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los historiales médicos de un paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de historiales médicos obtenida exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistorialDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No se encontraron historiales médicos para el paciente")
    })
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistorialDTO>> getAllHistorialByPaciente(@PathVariable Long pacienteId) {
        List<HistorialDTO> historialList = historialService.getAllHistorialByPaciente(pacienteId);
        return new ResponseEntity<>(historialList, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un historial médico por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial médico obtenido exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistorialDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Historial médico no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HistorialDTO> getHistorialById(@PathVariable Long id) {
        HistorialDTO historial = historialService.getHistorialById(id);
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }


    @Operation(summary = "Actualizar un historial médico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial médico actualizado exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistorialDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Historial médico no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de historial médico inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<HistorialDTO> updateHistorial(@PathVariable Long id, @RequestBody @Valid HistorialDTO historialDTO) {
        HistorialDTO updatedHistorial = historialService.updateHistorial(id, historialDTO);
        return new ResponseEntity<>(updatedHistorial, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un historial médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Historial médico eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Historial médico no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorial(@PathVariable Long id) {
        historialService.deleteHistorial(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

