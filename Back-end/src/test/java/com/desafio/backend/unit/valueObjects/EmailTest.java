package com.desafio.backend.unit.valueObjects;

import com.desafio.backend.enterprise.contato.valueObjects.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    // -------------------------------------------------------------------------
    // EMAILS VÁLIDOS
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Emails válidos")
    class EmailsValidos {

        @Test
        @DisplayName("Deve aceitar email simples e válido")
        void deveAceitarEmailSimples() {
            assertDoesNotThrow(() -> new Email("joao@gmail.com"));
        }

        @Test
        @DisplayName("Deve aceitar email com subdomínio")
        void deveAceitarEmailComSubdominio() {
            assertDoesNotThrow(() -> new Email("joao@mail.empresa.com.br"));
        }

        @Test
        @DisplayName("Deve aceitar email com caracteres especiais permitidos na parte local")
        void deveAceitarEmailComCaracteresEspeciaisPermitidos() {
            assertDoesNotThrow(() -> new Email("joao.silva+filtro@gmail.com"));
        }

        @Test
        @DisplayName("Deve converter email para letras minúsculas")
        void deveConverterParaMinusculas() {
            Email email = new Email("JOAO@GMAIL.COM");
            assertEquals("joao@gmail.com", email.getValue());
        }

        @Test
        @DisplayName("Deve remover espaços nas bordas do email")
        void deveRemoverEspacosNasBordas() {
            Email email = new Email("  joao@gmail.com  ");
            assertEquals("joao@gmail.com", email.getValue());
        }

        @ParameterizedTest
        @DisplayName("Deve aceitar múltiplos formatos válidos de email")
        @ValueSource(strings = {
                "usuario@dominio.com",
                "usuario@dominio.com.br",
                "usuario.nome@dominio.io",
                "usuario+tag@dominio.org",
                "usuario_123@dominio.net",
                "usuario-nome@dominio.co"
        })
        void deveAceitarMultiplosFormatosValidos(String raw) {
            assertDoesNotThrow(() -> new Email(raw));
        }
    }

    // -------------------------------------------------------------------------
    // NULO E VAZIO
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Entradas nulas e vazias")
    class EntradasNulasEVazias {

        @Test
        @DisplayName("Deve lançar exceção quando email for nulo")
        void deveLancarExcecaoQuandoNulo() {
            assertThrows(IllegalArgumentException.class, () -> new Email(null));
        }

        @Test
        @DisplayName("Deve lançar exceção quando email for string vazia")
        void deveLancarExcecaoQuandoVazio() {
            assertThrows(IllegalArgumentException.class, () -> new Email(""));
        }

        @Test
        @DisplayName("Deve lançar exceção quando email for apenas espaços")
        void deveLancarExcecaoQuandoApenasEspacos() {
            assertThrows(IllegalArgumentException.class, () -> new Email("   "));
        }
    }

    // -------------------------------------------------------------------------
    // FORMATO INVÁLIDO
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Formato inválido")
    class FormatoInvalido {

        @Test
        @DisplayName("Deve lançar exceção quando não houver @")
        void deveLancarExcecaoSemArroba() {
            assertThrows(IllegalArgumentException.class, () -> new Email("joaogmail.com"));
        }

        @Test
        @DisplayName("Deve lançar exceção quando não houver domínio após @")
        void deveLancarExcecaoSemDominio() {
            assertThrows(IllegalArgumentException.class, () -> new Email("joao@"));
        }

        @Test
        @DisplayName("Deve lançar exceção quando não houver parte local antes de @")
        void deveLancarExcecaoSemParteLocal() {
            assertThrows(IllegalArgumentException.class, () -> new Email("@gmail.com"));
        }

        @Test
        @DisplayName("Deve lançar exceção quando não houver TLD")
        void deveLancarExcecaoSemTLD() {
            assertThrows(IllegalArgumentException.class, () -> new Email("joao@gmail"));
        }

        @Test
        @DisplayName("Deve lançar exceção quando houver múltiplos @")
        void deveLancarExcecaoComMultiplosArrobas() {
            assertThrows(IllegalArgumentException.class, () -> new Email("joao@@gmail.com"));
        }

        @Test
        @DisplayName("Deve lançar exceção quando houver espaço no meio do email")
        void deveLancarExcecaoComEspacoNoMeio() {
            assertThrows(IllegalArgumentException.class, () -> new Email("joao silva@gmail.com"));
        }

        @Test
        @DisplayName("Deve lançar exceção para email com TLD de apenas 1 caractere")
        void deveLancarExcecaoComTLDCurto() {
            assertThrows(IllegalArgumentException.class, () -> new Email("joao@gmail.c"));
        }

        @ParameterizedTest
        @DisplayName("Deve lançar exceção para múltiplos formatos inválidos")
        @ValueSource(strings = {
                "semdominio",
                "@semparte.com",
                "joao@.com",
                "joao@dominio.",
                "joao @gmail.com",
                "joao@gmail .com"
        })
        void deveLancarExcecaoParaMultiplosFormatosInvalidos(String raw) {
            assertThrows(IllegalArgumentException.class, () -> new Email(raw));
        }
    }
}
