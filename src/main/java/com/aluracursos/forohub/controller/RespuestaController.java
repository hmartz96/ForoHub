package com.aluracursos.forohub.controller;
import com.aluracursos.forohub.domain.respuestas.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/respuesta")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @Operation(
            summary = "Agregar una nueva respuesta",
            description = "Este endpoint permite agregar una nueva respuesta.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosRegistroRespuesta.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )

    @PostMapping
    public ResponseEntity<DatosListadoRespuestas> agregarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta) {

        DatosListadoRespuestas datosListadoRespuestas = respuestaService.agregarRespuesta(datosRegistroRespuesta);
        return ResponseEntity.ok(datosListadoRespuestas);

    }

    @Operation(
            summary = "Actualizar una respuesta",
            description = "Este endpoint permite actualizar una respuesta existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosActualizarRespuesta.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta actualizada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )

    @PutMapping
    public ResponseEntity<DatosListadoRespuestas> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        DatosListadoRespuestas datosListadoRespuestas = respuestaService.actualizaRespuesta(datosActualizarRespuesta);
        return ResponseEntity.ok(datosListadoRespuestas);
    }

    @Operation(
            summary = "Mostrar respuestas",
            description = "Este endpoint permite mostrar una lista de respuestas."
    )

    @GetMapping
    public ResponseEntity<Page<DatosListadoRespuestas>> mostrarRespuestas(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(respuestaService.getRespuestas(pageable));
    }

    @Operation(
            summary = "Mostrar una respuesta",
            description = "Este endpoint permite mostrar una respuesta por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta encontrada"),
                    @ApiResponse(responseCode = "404", description = "Respuesta no encontrada")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoRespuestas> mostrarRespuesta(@PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.getRespuesta(id));
    }

    @Operation(
            summary = "Borrar una respuesta",
            description = "Este endpoint permite borrar una respuesta por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Respuesta borrada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Respuesta no encontrada")
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity borrarRespuesta(@PathVariable Long id) {
        respuestaService.deleteRespuesta(id);
        return ResponseEntity.noContent().build();
    }

}