package medical.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import medical.system.dto.cita.CitaCreateDTO;
import medical.system.dto.cita.CitaDetailDTO;
import medical.system.dto.cita.CitaDTO;
import medical.system.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    @Autowired
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @Operation(summary = "Create a new appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitaDetailDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<CitaDetailDTO> createCita(@RequestBody @Valid CitaCreateDTO citaCreateDTO) {
        CitaDetailDTO createdCita = citaService.createCita(citaCreateDTO);
        return new ResponseEntity<>(createdCita, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitaDetailDTO.class))})
    })
    @GetMapping
    public ResponseEntity<List<CitaDetailDTO>> getAllCitas() {
        List<CitaDetailDTO> citas = citaService.getAllCitas();
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @Operation(summary = "Get an appointment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitaDetailDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CitaDetailDTO> getCitaById(@PathVariable Long id) {
        CitaDetailDTO cita = citaService.getCitaById(id);
        return new ResponseEntity<>(cita, HttpStatus.OK);
    }

    @Operation(summary = "Get appointments by patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitaDetailDTO.class))})
    })
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaDetailDTO>> getCitasByPaciente(@PathVariable Long pacienteId) {
        List<CitaDetailDTO> citas = citaService.getCitasByPaciente(pacienteId);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @Operation(summary = "Get appointments by doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitaDetailDTO.class))})
    })
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<CitaDetailDTO>> getCitasByMedico(@PathVariable Long medicoId) {
        List<CitaDetailDTO> citas = citaService.getCitasByMedico(medicoId);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @Operation(summary = "Get appointments by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitaDetailDTO.class))})
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaDetailDTO>> getCitasByEstado(@PathVariable String estado) {
        List<CitaDetailDTO> citas = citaService.getCitasByEstado(estado);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @Operation(summary = "Get appointments by date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitaDetailDTO.class))})
    })
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CitaDetailDTO>> getCitasByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<CitaDetailDTO> citas = citaService.getCitasByFecha(fecha);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @Operation(summary = "Update the status of an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitaDetailDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<CitaDetailDTO> actualizarEstadoCita(
            @PathVariable Long id,
            @RequestParam String estado) {
        CitaDetailDTO updatedCita = citaService.actualizarEstadoCita(id, estado);
        return new ResponseEntity<>(updatedCita, HttpStatus.OK);
    }

    @Operation(summary = "Update an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitaDetailDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CitaDetailDTO> updateCita(
            @PathVariable Long id,
            @RequestBody @Valid CitaDTO citaDTO) {
        CitaDetailDTO updatedCita = citaService.updateCita(id, citaDTO);
        return new ResponseEntity<>(updatedCita, HttpStatus.OK);
    }

    @Operation(summary = "Cancel an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitaDetailDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<CitaDetailDTO> cancelarCita(@PathVariable Long id) {
        CitaDetailDTO canceledCita = citaService.cancelarCita(id);
        return new ResponseEntity<>(canceledCita, HttpStatus.OK);
    }
}


