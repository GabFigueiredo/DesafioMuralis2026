package com.desafio.backend.unit.valueObjects;

import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CPFTest {

    // -------------------------------------------------------------------------
    // CPFs VÁLIDOS
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Valid CPFs")
    class ValidCPFs {

        @Test
        @DisplayName("Should accept a clean valid CPF")
        void shouldAcceptCleanValidCPF() {
            assertDoesNotThrow(() -> new CPF("52998224725"));
        }

        @Test
        @DisplayName("Should accept a formatted valid CPF with dots and dash")
        void shouldAcceptFormattedValidCPF() {
            assertDoesNotThrow(() -> new CPF("529.982.247-25"));
        }

        @Test
        @DisplayName("Should store only digits internally")
        void shouldStoreOnlyDigitsInternally() {
            CPF cpf = new CPF("529.982.247-25");
            assertEquals("52998224725", cpf.getValue());
        }

        @Test
        @DisplayName("Should return formatted CPF correctly")
        void shouldReturnFormattedCPF() {
            CPF cpf = new CPF("52998224725");
            assertEquals("529.982.247-25", cpf.getFormatted());
        }

        @ParameterizedTest
        @DisplayName("Should accept multiple known valid CPFs")
        @ValueSource(strings = {
                "52998224725",
                "111.444.777-35",
                "63929247011",
                "47899793858",
                "478.997.938-58"
        })
        void shouldAcceptMultipleValidCPFs(String raw) {
            assertDoesNotThrow(() -> new CPF(raw));
        }
    }

    // -------------------------------------------------------------------------
    // NULO E VAZIO
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Null and blank inputs")
    class NullAndBlankInputs {

        @Test
        @DisplayName("Should throw when CPF is null")
        void shouldThrowWhenNull() {
            assertThrows(IllegalArgumentException.class, () -> new CPF(null));
        }

        @Test
        @DisplayName("Should throw when CPF is empty string")
        void shouldThrowWhenEmpty() {
            assertThrows(IllegalArgumentException.class, () -> new CPF(""));
        }

        @Test
        @DisplayName("Should throw when CPF is blank (only spaces)")
        void shouldThrowWhenBlank() {
            assertThrows(IllegalArgumentException.class, () -> new CPF("   "));
        }

        @Test
        @DisplayName("Should throw when CPF contains only special characters")
        void shouldThrowWhenOnlySpecialChars() {
            assertThrows(IllegalArgumentException.class, () -> new CPF("...-"));
        }
    }

    // -------------------------------------------------------------------------
    // VIOLAÇÕES DE TAMANHO
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Length violations")
    class LengthViolations {

        @Test
        @DisplayName("Should throw when CPF has fewer than 11 digits")
        void shouldThrowWhenTooShort() {
            assertThrows(IllegalArgumentException.class, () -> new CPF("1234567890"));
        }

        @Test
        @DisplayName("Should throw when CPF has more than 11 digits")
        void shouldThrowWhenTooLong() {
            assertThrows(IllegalArgumentException.class, () -> new CPF("123456789012"));
        }

        @Test
        @DisplayName("Should throw when CPF has only 1 digit")
        void shouldThrowWhenSingleDigit() {
            assertThrows(IllegalArgumentException.class, () -> new CPF("1"));
        }

        @Test
        @DisplayName("Should throw for formatted CPF with wrong length")
        void shouldThrowWhenFormattedButWrongLength() {
            // Looks formatted but has only 10 digits
            assertThrows(IllegalArgumentException.class, () -> new CPF("529.982.247-2"));
        }
    }

    // -------------------------------------------------------------------------
    // DÍGITOS IDÊNTICOS (INVÁLIDOS CONHECIDOS)
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("All identical digits")
    class IdenticalDigits {

        @ParameterizedTest
        @DisplayName("Should throw for all sequences of identical digits")
        @ValueSource(strings = {
                "00000000000",
                "11111111111",
                "22222222222",
                "33333333333",
                "44444444444",
                "55555555555",
                "66666666666",
                "77777777777",
                "88888888888",
                "99999999999"
        })
        void shouldThrowForIdenticalDigits(String cpf) {
            assertThrows(IllegalArgumentException.class, () -> new CPF(cpf));
        }
    }

    // -------------------------------------------------------------------------
    // DÍGITOS VERIFICADORES INVÁLIDOS
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Invalid check digits")
    class InvalidCheckDigits {

        @Test
        @DisplayName("Should throw when first check digit is wrong")
        void shouldThrowWhenFirstCheckDigitIsWrong() {
            // Valid CPF: 52998224725 — altering 10th digit (7 → 8)
            assertThrows(IllegalArgumentException.class, () -> new CPF("52998224825"));
        }

        @Test
        @DisplayName("Should throw when second check digit is wrong")
        void shouldThrowWhenSecondCheckDigitIsWrong() {
            // Valid CPF: 52998224725 — altering 11th digit (5 → 6)
            assertThrows(IllegalArgumentException.class, () -> new CPF("52998224726"));
        }

        @Test
        @DisplayName("Should throw when both check digits are wrong")
        void shouldThrowWhenBothCheckDigitsAreWrong() {
            assertThrows(IllegalArgumentException.class, () -> new CPF("52998224700"));
        }

        @Test
        @DisplayName("Should throw for a sequential number that passes length but fails check digits")
        void shouldThrowForSequentialCPF() {
            assertThrows(IllegalArgumentException.class, () -> new CPF("12345678900"));
        }
    }

    // -------------------------------------------------------------------------
    // CASOS LIMITE DE FORMATAÇÃO
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Formatting edge cases")
    class FormattingEdgeCases {

        @Test
        @DisplayName("Should accept CPF with extra spaces around it")
        void shouldAcceptCPFWithSurroundingSpaces() {
            // Spaces are stripped by replaceAll non-digits
            assertDoesNotThrow(() -> new CPF("529.982.247-25"));
        }

        @Test
        @DisplayName("Should accept CPF with mixed separators")
        void shouldAcceptCPFWithMixedSeparators() {
            // All non-digit chars are stripped
            assertDoesNotThrow(() -> new CPF("529-982-247.25"));
        }

        @Test
        @DisplayName("Should throw for CPF with letters mixed in")
        void shouldThrowWhenLettersAreMixed() {
            // After stripping non-digits, length will be wrong
            assertThrows(IllegalArgumentException.class, () -> new CPF("529.982.24A-25"));
        }

        @Test
        @DisplayName("Should throw for alphabetic input")
        void shouldThrowForAlphabeticInput() {
            assertThrows(IllegalArgumentException.class, () -> new CPF("abcdefghijk"));
        }

        @Test
        @DisplayName("Should throw for CPF with emoji characters")
        void shouldThrowForEmojiInput() {
            assertThrows(IllegalArgumentException.class, () -> new CPF("5299824725🔥"));
        }
    }

    // -------------------------------------------------------------------------
    // IGUALDADE E CONTRATO DE OBJETO DE VALOR
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Value Object contract")
    class ValueObjectContract {

        @Test
        @DisplayName("Two CPFs with same number in different formats should have equal values")
        void shouldHaveEqualValuesForSameNumber() {
            CPF cpf1 = new CPF("52998224725");
            CPF cpf2 = new CPF("529.982.247-25");
            assertEquals(cpf1.getValue(), cpf2.getValue());
        }

        @Test
        @DisplayName("Two different valid CPFs should not have equal values")
        void shouldNotBeEqualForDifferentCPFs() {
            CPF cpf1 = new CPF("52998224725");
            CPF cpf2 = new CPF("111.444.777-35");
            assertNotEquals(cpf1.getValue(), cpf2.getValue());
        }
    }
}