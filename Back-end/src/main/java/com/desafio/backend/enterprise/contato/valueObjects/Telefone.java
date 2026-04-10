package com.desafio.backend.enterprise.contato.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Telefone {
    private String value;

    @JsonCreator
    public Telefone(String rawPhone) {
        this.value = this.validate(rawPhone);
    }

    private String validate(String rawPhone) {
        if (rawPhone == null || rawPhone.isBlank()) {
            throw new IllegalArgumentException("Telefone não pode estar vazio ou nulo");
        }

        /*
         * Remove todos os caracteres que não são dígitos.
         * "(11) 91234-5678" → "11912345678"
         * "+55 11 91234-5678" → "5511912345678"
         */
        String cleaned = rawPhone.replaceAll("[^\\d]", "");

        /*
         * Remove o código do país do Brasil, se presente (55).
         * "5511912345678" → "11912345678"
         * Remove apenas se o tamanho total for 13 (código do país + DDD + celular com 9 dígitos)
         * ou 12 (código do país + DDD + telefone fixo com 8 dígitos).
         */
        if (cleaned.startsWith("55") && (cleaned.length() == 13 || cleaned.length() == 12)) {
            cleaned = cleaned.substring(2);
        }

        /*
         * Valida o formato brasileiro de DDD (código de área) + número:
         *
         * ^[1-9]{2} → DDD: dois dígitos, nenhum pode ser zero (válidos: 11, 21, 47, 85, etc.)
         *
         * Celular: 9[6-9]\d{7}
         *   9 → nono dígito obrigatório para celulares (exigido desde 2012)
         *   [6-9] → segundo dígito deve ser de 6 a 9 (faixa de celulares no Brasil)
         *   \d{7} → 7 dígitos restantes
         *
         * Fixo: [2-5]\d{7}
         *   [2-5] → telefones fixos começam com 2, 3, 4 ou 5
         *   \d{7} → 7 dígitos restantes
         */
        if (!cleaned.matches("^[1-9]{2}(9[1-9]\\d{7}|[2-5]\\d{7})$")) {
            throw new IllegalArgumentException("Invalid Brazilian phone number: " + rawPhone);
        }

        return cleaned;
    }

    /*
     * Retorna o celular formatado como "(XX) 9XXXX-XXXX"
     * Retorna o telefone fixo formatado como "(XX) XXXX-XXXX"
     */
    public String getFormatted() {
        if (value.length() == 11) {
            return value.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        }
        return value.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getFormatted();
    }
}