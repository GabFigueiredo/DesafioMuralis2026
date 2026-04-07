package com.desafio.backend.enterprise.contato;

public class Contato {
    private Integer id;
    private Integer clienteId;
    private String tipo;
    private String valor;
    private String observacao;

    public Contato() {}

    public Contato(Integer id, Integer clienteId, String tipo, String valor, String observacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.valor = valor;
        this.observacao = observacao;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
