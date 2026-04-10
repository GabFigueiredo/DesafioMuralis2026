package com.desafio.backend.enterprise.contato.valueObjects;

import com.desafio.backend.enterprise.contato.enums.TipoContato;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContatoValor {
    private String value;
    private TipoContato tipo;

    @JsonCreator
    public ContatoValor(@JsonProperty("tipo") TipoContato tipo, @JsonProperty("value") String valor) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo não pode ser nulo ou vazio.");
        }

        if (valor == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo ou vazio");
        }

        this.tipo = tipo;
        this.value = this.validate(this.tipo, valor);
    }

    private String validate(TipoContato tipo, String valor) {
        return switch (tipo) {
            case EMAIL -> new Email(valor).getValue();
            case TELEFONE -> new Telefone(valor).getValue();
        };
    }

    public String getValue() {
        return this.value;
    }

    public TipoContato getTipo() {
        return this.tipo;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}