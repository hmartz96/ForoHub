package com.aluracursos.forohub.infra.errores;

public class ErrorDeConsulta extends RuntimeException {
    public ErrorDeConsulta(String string) {
        super(string);
    }
}