package com.aluracursos.forohub.controller;
import com.aluracursos.forohub.domain.perfiles.*;
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
@RequestMapping("/perfil")
@SecurityRequirement(name = "bearer-key")
public class PerfilController {

    @Autowired
    private PerfilRespository perfilRespository;

    @Operation(
            summary = "Agregar un nuevo perfil",
            description = "Este endpoint permite agregar un nuevo perfil.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosRegistroPerfil.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Perfil creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )

    @PostMapping
    public ResponseEntity<DatosListadoPerfil> agregarPerfil(@RequestBody @Valid DatosRegistroPerfil datosRegistroPerfil, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(datosRegistroPerfil);
        Perfil perfil = perfilRespository.save(new Perfil(datosRegistroPerfil));
        DatosListadoPerfil datosListadoPerfil = new DatosListadoPerfil(
                perfil.getId(),
                perfil.getNombre()
        );
        URI url = uriComponentsBuilder.path("perfil/{id}").buildAndExpand(perfil.getId()).toUri();
        return ResponseEntity.created(url).body(datosListadoPerfil);
    }

    @Operation(
            summary = "Mostrar perfiles",
            description = "Este endpoint permite mostrar una lista de perfiles."
    )

    @GetMapping
    public ResponseEntity<Page<DatosListadoPerfil>> mostrarPerfil(@PageableDefault(size = 10) Pageable pageable) {
        Page<DatosListadoPerfil> listadoPerfils = perfilRespository.findAll(pageable).map(DatosListadoPerfil::new);
        return ResponseEntity.ok(listadoPerfils);
    }

    @Operation(
            summary = "Mostrar un perfil",
            description = "Este endpoint permite mostrar un perfil por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
                    @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
            }
    )


    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoPerfil> muestraPerfil(@PathVariable Long id) {
        Perfil perfil = perfilRespository.getReferenceById(id);
        DatosListadoPerfil datosListadoPerfil = new DatosListadoPerfil(
                perfil.getId(),
                perfil.getNombre()
        );
        return ResponseEntity.ok(datosListadoPerfil);
    }

    @Operation(
            summary = "Actualizar un perfil",
            description = "Este endpoint permite actualizar un perfil existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosActualizarPerfil.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Perfil actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListadoPerfil> actualizaPerfil(@RequestBody @Valid DatosActualizarPerfil datosActualizarPerfil) {
        Perfil perfil = perfilRespository.getReferenceById(datosActualizarPerfil.id());
        perfil.actualizarPerfil(datosActualizarPerfil);
        return ResponseEntity.ok(new DatosListadoPerfil(perfil));
    }

    @Operation(
            summary = "Borrar un perfil",
            description = "Este endpoint permite borrar un perfil por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Perfil borrado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity borrarPerfil(@PathVariable Long id) {
        perfilRespository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
