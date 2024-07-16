package com.aluracursos.forohub.domain.respuestas;
import java.time.LocalDateTime;

public record DTopicoRespuestas(
        String mensaje,
        LocalDateTime fecha,
        String nombre,
        Boolean solucion
) {
    public DTopicoRespuestas(Respuesta respuesta) {
        this(respuesta.getMensaje(), respuesta.getFecha(), respuesta.getUsuario().getNombre(), respuesta.getSolucion());
    }
}