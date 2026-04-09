package com.desafio.backend.enterprise.cliente;

import com.desafio.backend.enterprise.cliente.valueObjects.CPF;

import java.time.LocalDate;

public class Cliente {
    private Integer id;
    private String nome;
    private CPF cpf;
    private LocalDate dataNascimento;
    private String endereco;

    public Cliente() {}

    public Cliente(Integer id, String nome, CPF cpf, LocalDate dataNascimento, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public CPF getCpf() { return cpf; }
    public void setCpf(CPF cpf) { this.cpf = cpf; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}
