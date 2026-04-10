package com.desafio.backend.enterprise.contato.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoContato {
    EMAIL,
    TELEFONE;

    @JsonCreator
    public static TipoContato fromString(String valor) {
        return TipoContato.valueOf(valor.toUpperCase());
    }
}
