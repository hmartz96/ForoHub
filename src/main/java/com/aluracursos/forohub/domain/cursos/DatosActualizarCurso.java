package com.aluracursos.forohub.domain.cursos;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarCurso(
        @NotNull
        Long id,
        String nombre,
        String categoria
) {
}