package com.aluracursos.forohub.controller;
import com.aluracursos.forohub.domain.usuario_perfil.DatosListado_Usuario_perfil;
import com.aluracursos.forohub.domain.usuario_perfil.DatosRegistro_Usuario_Perfil;
import com.aluracursos.forohub.domain.usuario_perfil.Usuario_Perfil_Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/usuario_perfil")
@SecurityRequirement(name = "bearer-key")
public class Usuario_Perfil_Controller {

    @Autowired
    private Usuario_Perfil_Service usuarioPerfilService;

    @Operation(
            summary = "Agregar un nuevo perfil de usuario",
            description = "Este endpoint permite agregar un nuevo perfil de usuario.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosRegistro_Usuario_Perfil.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Perfil de usuario creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )

    @PostMapping
    public ResponseEntity<DatosListado_Usuario_perfil> agregarUsuariosPerfiles(@RequestBody @Valid DatosRegistro_Usuario_Perfil datosRegistroUsuarioPerfil) {
        return ResponseEntity.ok(usuarioPerfilService.agregarPefil(datosRegistroUsuarioPerfil));
    }

    @Operation(
            summary = "Mostrar perfiles de usuarios",
            description = "Este endpoint permite mostrar una lista de perfiles de usuarios."
    )


    @GetMapping
    public ResponseEntity<List<DatosListado_Usuario_perfil>> mostrarPerfilesUsuarios() {
        return ResponseEntity.ok(usuarioPerfilService.mostrarUsuarioPerfil());
    }

    @Operation(
            summary = "Borrar un perfil de usuario",
            description = "Este endpoint permite borrar un perfil de usuario por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Perfil de usuario borrado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Perfil de usuario no encontrado")
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity borrarUsuarioPerfil(@PathVariable Long id) {
        usuarioPerfilService.borrarUsuarioPerfil(id);
        return ResponseEntity.noContent().build();
    }

}