package com.desafio.backend.enterprise.contato;

import com.desafio.backend.enterprise.contato.valueObjects.ContatoValor;

public class Contato {
    private Integer id;
    private Integer clienteId;
    private ContatoValor contatoValor;
    private String observacao;

    public Contato() {}

    public Contato(Integer id, Integer clienteId,ContatoValor contatoValor,String observacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.contatoValor = contatoValor;
        this.observacao = observacao;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public ContatoValor getContatoValor() { return contatoValor; }
    public void setContatoValor(ContatoValor contatoValor) { this.contatoValor = contatoValor; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
