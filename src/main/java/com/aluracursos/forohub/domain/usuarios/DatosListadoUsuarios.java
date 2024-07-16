package com.aluracursos.forohub.domain.usuarios;

public record DatosListadoUsuarios(
        Long id,
        String nombre
) {
    public DatosListadoUsuarios(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre());
    }
}
