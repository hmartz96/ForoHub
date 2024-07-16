package com.aluracursos.forohub.controller;
import com.aluracursos.forohub.domain.respuestas.DTopicoRespuestas;
import com.aluracursos.forohub.domain.topicos.DatosActualizarTopico;
import com.aluracursos.forohub.domain.topicos.DatosListadoTopico;
import com.aluracursos.forohub.domain.topicos.DatosRegistroTopico;
import com.aluracursos.forohub.domain.topicos.TopicoService;
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
@RequestMapping("/topico")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @Operation(
            summary = "Agregar un nuevo tópico",
            description = "Este endpoint permite agregar un nuevo tópico.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosRegistroTopico.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tópico creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )

    @PostMapping
    public ResponseEntity<DatosListadoTopico> agregarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        DatosListadoTopico datosListadoTopico = topicoService.agregarTopico(datosRegistroTopico);
        return ResponseEntity.ok(datosListadoTopico);
    }

    @Operation(
            summary = "Mostrar tópicos",
            description = "Este endpoint permite mostrar una lista de tópicos."
    )

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> mostrarTopicos(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(topicoService.getTopicos(pageable));
    }

    @Operation(
            summary = "Mostrar un tópico",
            description = "Este endpoint permite mostrar un tópico por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tópico encontrado"),
                    @ApiResponse(responseCode = "404", description = "Tópico no encontrado")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopico> mostrarTopico(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.getTopico(id));
    }

    @Operation(
            summary = "Actualizar un tópico",
            description = "Este endpoint permite actualizar un tópico existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosActualizarTopico.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tópico actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )


    @PutMapping
    public ResponseEntity<DatosListadoTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        return ResponseEntity.ok(topicoService.actualizaTopico(datosActualizarTopico));
    }

    @Operation(
            summary = "Borrar un tópico",
            description = "Este endpoint permite borrar un tópico por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tópico borrado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Tópico no encontrado")
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity borraTopico(@PathVariable Long id) {
        topicoService.desactivaTopico(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Mostrar respuestas de un tópico",
            description = "Este endpoint permite mostrar las respuestas de un tópico por su ID."
    )

    @GetMapping("/{id}/respuesta")
    public ResponseEntity<Page<DTopicoRespuestas>>  respuestasTopico(@PathVariable Long id, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(topicoService.getRespuestas(id, pageable));
    }
}
