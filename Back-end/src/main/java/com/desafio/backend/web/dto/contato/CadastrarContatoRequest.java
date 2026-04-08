package com.desafio.backend.web.dto.contato;

public class CadastrarContatoRequest {
    private Integer clienteId;
    private String tipo;
    private String valor;
    private String observacao;

    // Getters and Setters
    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
