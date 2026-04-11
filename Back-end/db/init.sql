CREATE DATABASE IF NOT EXISTS desafio_muralis
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE desafio_muralis;

CREATE TABLE cliente (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         cpf VARCHAR(11) NOT NULL UNIQUE,
                         data_nascimento DATE,
                         endereco VARCHAR(255)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

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
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO cliente (nome, cpf, data_nascimento, endereco) VALUES
    ('Lucas Silva', '12345678909', '1990-05-10', 'Rua A, 123'),
    ('Maria Oliveira', '23456789092', '1985-08-22', 'Rua B, 456'),
    ('Carlos Souza', '34567890175', '1992-11-15', 'Rua C, 789'),
    ('Ana Santos', '45678901249', '1998-03-30', 'Rua D, 101'),
    ('Pedro Lima', '56789012303', '1980-07-19', 'Rua E, 202'),
    ('Juliana Costa', '67890123469', '1995-12-01', 'Rua F, 303'),
    ('Lucas Pereira', '78901234505', '2000-01-25', 'Rua G, 404'),
    ('Fernanda Rocha', '89012345642', '1993-09-14', 'Rua H, 505'),
    ('Rafael Alves', '90123456770', '1987-06-08', 'Rua I, 606'),
    ('Camila Martins', '01234567890', '1999-04-17', 'Rua J, 707');

INSERT INTO contato (cliente_id, tipo, valor, observacao) VALUES
    (2, 'telefone', '11999990001', 'Celular pessoal'),
    (2, 'email', 'maria@email.com', 'Email principal'),
    (2, 'telefone', '11999990003', 'WhatsApp'),
    (2, 'email', 'ana@email.com', 'Trabalho'),
    (2, 'telefone', '11999990005', 'Residencial'),
    (2 'email', 'juliana@email.com', 'Pessoal'),
    (2, 'telefone', '11999990007', 'Contato principal'),
    (2, 'email', 'fernanda@email.com', 'Email secundário'),
    (2, 'telefone', '11999990009', 'Pessoal'),
    (2, 'email', 'camila@email.com', 'Principal');