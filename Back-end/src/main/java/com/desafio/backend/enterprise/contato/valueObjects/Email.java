package com.desafio.backend.enterprise.contato.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Email {
    private String value;

    @JsonCreator
    public Email(String rawEmail) {
        this.value = this.validate(rawEmail);
    }

    private String validate(String rawEmail) {
        if (rawEmail == null || rawEmail.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        String trimmed = rawEmail.trim().toLowerCase();

        /*
         * Valida o formato completo do email: local@dominio.tld
         *
         * ^[a-z0-9+_.-]+ → parte local: permite letras, dígitos, +, _, ., -
         * @ → separador obrigatório
         * [a-z0-9.-]+ → domínio: letras, dígitos, pontos e hífens
         * \. → ponto obrigatório antes do TLD
         * [a-z]{2,}$ → TLD: pelo menos 2 letras (com, io, br, museum, etc.)
         */
        if (!trimmed.matches("^[a-z0-9+_.-]+@[a-z0-9.-]+\\.[a-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format: " + rawEmail);
        }

        return trimmed;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}