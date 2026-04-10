package com.desafio.backend.unit.valueObjects;

import com.desafio.backend.enterprise.contato.valueObjects.Telefone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TelefoneTest {

    // -------------------------------------------------------------------------
    // TELEFONES VÁLIDOS
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Telefones válidos")
    class TelefonesValidos {

        @Test
        @DisplayName("Deve aceitar celular com formatação completa")
        void deveAceitarCelularFormatado() {
            assertDoesNotThrow(() -> new Telefone("(11) 91234-5678"));
        }

        @Test
        @DisplayName("Deve aceitar celular apenas com dígitos")
        void deveAceitarCelularSomenteDigitos() {
            assertDoesNotThrow(() -> new Telefone("11912345678"));
        }

        @Test
        @DisplayName("Deve aceitar telefone fixo formatado")
        void deveAceitarFixoFormatado() {
            assertDoesNotThrow(() -> new Telefone("(11) 3456-7890"));
        }

        @Test
        @DisplayName("Deve aceitar telefone com código do país +55")
        void deveAceitarComCodigoDoPais() {
            assertDoesNotThrow(() -> new Telefone("+55 11 91234-5678"));
        }

        @Test
        @DisplayName("Deve remover código do país e armazenar apenas DDD + número")
        void deveRemoverCodigoDoPais() {
            Telefone Telefone = new Telefone("+55 11 91234-5678");
            assertEquals("11912345678", Telefone.getValue());
        }

        @Test
        @DisplayName("Deve formatar celular corretamente com 11 dígitos")
        void deveFormatarCelularCorretamente() {
            Telefone Telefone = new Telefone("11912345678");
            System.out.println("VALOR DO TELEFONE");
            System.out.println(Telefone.getFormatted());
            assertEquals("(11) 91234-5678", Telefone.getFormatted());
        }

        @Test
        @DisplayName("Deve formatar fixo corretamente com 10 dígitos")
        void deveFormatarFixoCorretamente() {
            Telefone Telefone = new Telefone("1134567890");
            assertEquals("(11) 3456-7890", Telefone.getFormatted());
        }

        @ParameterizedTest
        @DisplayName("Deve aceitar múltiplos DDDs válidos")
        @ValueSource(strings = {
                "11912345678", // São Paulo
                "21987654321", // Rio de Janeiro
                "31976543210", // Belo Horizonte
                "47912345678", // Blumenau
                "85987654321"  // Fortaleza
        })
        void deveAceitarMultiplosDDDsValidos(String raw) {
            assertDoesNotThrow(() -> new Telefone(raw));
        }
    }

    // -------------------------------------------------------------------------
    // NULO E VAZIO
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Entradas nulas e vazias")
    class EntradasNulasEVazias {

        @Test
        @DisplayName("Deve lançar exceção quando telefone for nulo")
        void deveLancarExcecaoQuandoNulo() {
            assertThrows(IllegalArgumentException.class, () -> new Telefone(null));
        }

        @Test
        @DisplayName("Deve lançar exceção quando telefone for string vazia")
        void deveLancarExcecaoQuandoVazio() {
            assertThrows(IllegalArgumentException.class, () -> new Telefone(""));
        }

        @Test
        @DisplayName("Deve lançar exceção quando telefone for apenas espaços")
        void deveLancarExcecaoQuandoApenasEspacos() {
            assertThrows(IllegalArgumentException.class, () -> new Telefone("   "));
        }
    }

    // -------------------------------------------------------------------------
    // DDD INVÁLIDO
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("DDD inválido")
    class DDDInvalido {

        @Test
        @DisplayName("Deve lançar exceção quando DDD começar com zero")
        void deveLancarExcecaoComDDDComecandoComZero() {
            assertThrows(IllegalArgumentException.class, () -> new Telefone("01912345678"));
        }

        @Test
        @DisplayName("Deve lançar exceção quando DDD for 00")
        void deveLancarExcecaoComDDDZero() {
            assertThrows(IllegalArgumentException.class, () -> new Telefone("00912345678"));
        }
    }

    // -------------------------------------------------------------------------
    // COMPRIMENTO INVÁLIDO
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Comprimento inválido")
    class ComprimentoInvalido {

        @Test
        @DisplayName("Deve lançar exceção quando número for muito curto")
        void deveLancarExcecaoQuandoCurto() {
            assertThrows(IllegalArgumentException.class, () -> new Telefone("1191234"));
        }

        @Test
        @DisplayName("Deve lançar exceção quando número for muito longo")
        void deveLancarExcecaoQuandoLongo() {
            assertThrows(IllegalArgumentException.class, () -> new Telefone("119123456789"));
        }

        @Test
        @DisplayName("Deve lançar exceção para entrada apenas com letras")
        void deveLancarExcecaoParaLetras() {
            assertThrows(IllegalArgumentException.class, () -> new Telefone("abcdefghijk"));
        }
    }

    // -------------------------------------------------------------------------
    // CELULAR INVÁLIDO
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Formato de celular inválido")
    class CelularInvalido {

        @Test
        @DisplayName("Deve lançar exceção quando celular não começar com 9")
        void deveLancarExcecaoSemNoveDígito() {
            // Celular brasileiro exige o nono dígito
            assertThrows(IllegalArgumentException.class, () -> new Telefone("11812345678"));
        }
    }
}