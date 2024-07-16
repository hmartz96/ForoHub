package com.aluracursos.forohub.controller;
import com.aluracursos.forohub.domain.usuarios.*;
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
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Operation(
            summary = "Agregar un nuevo usuario",
            description = "Esto permite agregar un nuevo usuario.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosRegistroUsuario.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )

    @PostMapping
    public ResponseEntity<DatosListadoUsuarios> agregarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(datosRegistroUsuario);
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));
        DatosListadoUsuarios datosListadoUsuarios = new DatosListadoUsuarios(
                usuario.getId(),
                usuario.getNombre()
        );
        URI url = uriComponentsBuilder.path("usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosListadoUsuarios);
    }

    @Operation(
            summary = "Mostrar usuarios",
            description = "Esto permite mostrar una lista de usuarios."
    )

    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuarios>> mostrarUsuarios(@PageableDefault(size = 10) Pageable pageable) {
        Page<DatosListadoUsuarios> listadoUsuarios = usuarioRepository.findAll(pageable).map(DatosListadoUsuarios::new);
        return ResponseEntity.ok(listadoUsuarios);
    }

    @Operation(
            summary = "Mostrar un usuario",
            description = "Esto permite mostrar un usuario por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoUsuarios> muestraUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        DatosListadoUsuarios datosListadoUsuarios = new DatosListadoUsuarios(
                usuario.getId(),
                usuario.getNombre()
        );
        return ResponseEntity.ok(datosListadoUsuarios);
    }

    @Operation(
            summary = "Actualizar un usuario",
            description = "Esto permite actualizar un usuario existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosActualizarUsuario.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListadoUsuarios> actualizaUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());
        usuario.actualizarUsuario(datosActualizarUsuario);
        return ResponseEntity.ok(new DatosListadoUsuarios(usuario));
    }

    @Operation(
            summary = "Borrar un usuario",
            description = "Esto permite borrar un usuario por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuario borrado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity borrarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}