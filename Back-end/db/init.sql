CREATE DATABASE IF NOT EXISTS desafio_muralis;
USE desafio_muralis;

CREATE TABLE cliente (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         cpf VARCHAR(11) NOT NULL UNIQUE,
                         data_nascimento DATE,
                         endereco VARCHAR(255)
);

CREATE TABLE contato (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         cliente_id INT NOT NULL,
                         tipo VARCHAR(50),
                         valor VARCHAR(255) NOT NULL,
                         observacao VARCHAR(255),

                         CONSTRAINT fk_cliente
                             FOREIGN KEY (cliente_id)
                                 REFERENCES cliente(id)
                                 ON DELETE CASCADE
);

INSERT INTO cliente (nome, cpf, data_nascimento, endereco) VALUES
        ('João Silva', '12345678900', '1990-05-10', 'Rua A, 123'),
        ('Maria Oliveira', '23456789011', '1985-08-22', 'Rua B, 456'),
        ('Carlos Souza', '34567890122', '1992-11-15', 'Rua C, 789'),
        ('Ana Santos', '45678901233', '1998-03-30', 'Rua D, 101'),
        ('Pedro Lima', '56789012344', '1980-07-19', 'Rua E, 202'),
        ('Juliana Costa', '67890123455', '1995-12-01', 'Rua F, 303'),
        ('Lucas Pereira', '78901234566', '2000-01-25', 'Rua G, 404'),
        ('Fernanda Rocha', '89012345677', '1993-09-14', 'Rua H, 505'),
        ('Rafael Alves', '90123456788', '1987-06-08', 'Rua I, 606'),
        ('Camila Martins', '01234567899', '1999-04-17', 'Rua J, 707');

INSERT INTO contato (cliente_id, tipo, valor, observacao) VALUES
        (1, 'telefone', '11999990001', 'Celular pessoal'),
        (2, 'email', 'maria@email.com', 'Email principal'),
        (3, 'telefone', '11999990003', 'WhatsApp'),
        (4, 'email', 'ana@email.com', 'Trabalho'),
        (5, 'telefone', '11999990005', 'Residencial'),
        (6, 'email', 'juliana@email.com', 'Pessoal'),
        (7, 'telefone', '11999990007', 'Contato principal'),
        (8, 'email', 'fernanda@email.com', 'Email secundário'),
        (9, 'telefone', '11999990009', 'Emergência'),
        (10, 'email', 'camila@email.com', 'Principal');