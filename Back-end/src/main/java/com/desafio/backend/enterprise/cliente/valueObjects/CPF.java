package com.desafio.backend.enterprise.cliente.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class CPF {
    private String value;

    @JsonCreator
    public CPF(String rawCPF) {
        this.value = this.validate(rawCPF);
    }

    private String validate(String rawCPF) {
        if (rawCPF == null || rawCPF.isBlank()) {
            throw new IllegalArgumentException("CPF cannot be null or empty.");
        }

        /*
         * Remove todos os não-digitos (pontos, traços, espaços).
         * "123.456.789-09" → "12345678909"
         */
        String cleaned = rawCPF.replaceAll("[^\\d]", "");

        /*
         * Confere que o CPF tem exatos 11 dígitos.
         * Rejeita valores como "123" or "123456789012" (muito pequeno/longo).
         */
        if (!cleaned.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF must have exactly 11 digits. Received: " + rawCPF);
        }

        /*
         * Rejeita CPFs com todos digitos iguais (e.g., "00000000000", "11111111111").
         */
        if (cleaned.matches("(\\d)\\1{10}")) {
            throw new IllegalArgumentException("CPF cannot have all identical digits: " + rawCPF);
        }

        if (!hasValidCheckDigits(cleaned)) {
            throw new IllegalArgumentException("CPF has invalid check digits: " + rawCPF);
        }

        return cleaned;
    }

    /*
     * Valida os dois dígitos verificadores (posições 9 e 10) usando o
     * algoritmo oficial brasileiro do CPF (soma ponderada + módulo 11).
     *
     * Primeiro dígito: pesos de 10 até 2 sobre os primeiros 9 dígitos.
     * Segundo dígito: pesos de 11 até 2 sobre os primeiros 10 dígitos.
     *
     * Regra: resto = (soma % 11) < 2 → dígito é 0, caso contrário o dígito é (11 - resto).
     */
    private boolean hasValidCheckDigits(String cpf) {
        int[] digits = cpf.chars()
                .map(Character::getNumericValue)
                .toArray();

        // Validate first check digit
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }
        int firstCheck = (sum % 11) < 2 ? 0 : 11 - (sum % 11);
        if (firstCheck != digits[9]) return false;

        // Validate second check digit
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digits[i] * (11 - i);
        }
        int secondCheck = (sum % 11) < 2 ? 0 : 11 - (sum % 11);
        return secondCheck == digits[10];
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    /*
     * Retorna o CPF formatado como "XXX.XXX.XXX-XX" para fins de exibição.
     * O valor armazenado é sempre a string bruta com 11 dígitos.
     */
    public String getFormatted() {
        return value.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    @Override
    public String toString() {
        return getFormatted();
    }
}