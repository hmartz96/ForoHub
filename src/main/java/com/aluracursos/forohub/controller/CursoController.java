package com.aluracursos.forohub.controller;
import com.aluracursos.forohub.domain.cursos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@ResponseBody
@RequestMapping("/curso")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    private CursoRespository cursoRespository;
    @Operation(
            summary = "Agregar un nuevo curso",
            description = "Este endpoint permite agregar un nuevo curso.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosRegistroCurso.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Curso creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping
    public ResponseEntity<DatosListadoCurso> agregarCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(datosRegistroCurso);
        Curso curso = cursoRespository.save(new Curso(datosRegistroCurso));
        DatosListadoCurso datosListadoCurso = new DatosListadoCurso(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );
        URI url = uriComponentsBuilder.path("curso/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(url).body(datosListadoCurso);
    }

    @Operation(
            summary = "Mostrar cursos",
            description = "Este endpoint permite mostrar una lista de cursos."
    )

    @GetMapping
    public ResponseEntity<Page<DatosListadoCurso>> mostrarCursos(@PageableDefault(size = 10) Pageable pageable) {
        Page<DatosListadoCurso> listadoCursos = cursoRespository.findAll(pageable).map(DatosListadoCurso::new);
        return ResponseEntity.ok(listadoCursos);
    }

    @Operation(
            summary = "Mostrar un curso",
            description = "Este endpoint permite mostrar un curso por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso encontrado"),
                    @ApiResponse(responseCode = "404", description = "Curso no encontrado")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoCurso> muestraCurso(@PathVariable Long id) {
        Curso curso = cursoRespository.getReferenceById(id);
        DatosListadoCurso datosListadoCurso = new DatosListadoCurso(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );
        return ResponseEntity.ok(datosListadoCurso);
    }

    @Operation(
            summary = "Actualizar un curso",
            description = "Este endpoint permite actualizar un curso existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosActualizarCurso.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListadoCurso> actualizaCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso) {
        Curso curso = cursoRespository.getReferenceById(datosActualizarCurso.id());
        curso.actualizarCurso(datosActualizarCurso);
        return ResponseEntity.ok(new DatosListadoCurso(curso));
    }

    @Operation(
            summary = "Borrar un curso",
            description = "Este endpoint permite borrar un curso por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Curso borrado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Curso no encontrado")
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity borrarCurso(@PathVariable Long id) {
        cursoRespository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}