CREATE TABLE cliente (
     id INT AUTO_INCREMENT PRIMARY KEY,
     nome VARCHAR(255) NOT NULL,
     cpf VARCHAR(20) NOT NULL UNIQUE,
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