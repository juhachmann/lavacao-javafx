CREATE TABLE cores (
  id int NOT NULL AUTO_INCREMENT,
  nome varchar(50),
  CONSTRAINT pk_cor PRIMARY KEY(id)
) engine = InnoDB;

CREATE TABLE marcas (
  id int NOT NULL AUTO_INCREMENT,
  nome varchar(50),
  CONSTRAINT pk_marca PRIMARY KEY(id)
) engine = InnoDB;

CREATE TABLE servicos (
  id int NOT NULL AUTO_INCREMENT,
  descricao varchar(200) NOT NULL,
  valor decimal(10,2) NOT NULL,
  categoria ENUM('PEQUENO', 'MEDIO', 'GRANDE', 'MOTO', 'PADRAO') NOT NULL,
  CONSTRAINT pk_servicos PRIMARY KEY(id)
) engine = InnoDB;


CREATE TABLE modelos (
  id int NOT NULL AUTO_INCREMENT,
  descricao varchar(50),
  marca_id int NOT NULL,
  categoria ENUM('PEQUENO', 'MEDIO', 'GRANDE', 'MOTO', 'PADRAO') NOT NULL,
  CONSTRAINT pk_modelo PRIMARY KEY(id),
  CONSTRAINT fk_marca_modelo FOREIGN KEY (marca_id) REFERENCES marcas(id)
) engine = InnoDB;


# ver a questão da herança!

CREATE TABLE clientes (
  id int NOT NULL AUTO_INCREMENT,
  nome varchar(150) NOT NULL,
  celular varchar(20),
  email varchar(150) NOT NULL,
  data_cadastro DATE NOT NULL,
  cpf varchar(15),
  data_nascimento DATE,
  cnpj varchar(50),
  inscricao_estadual varchar(150),
  CONSTRAINT pk_cliente PRIMARY KEY(id)
) engine = InnoDB;


CREATE TABLE veiculos (
  id int NOT NULL AUTO_INCREMENT,
  placa varchar(10),
  observacoes varchar (200),
  modelo_id int NOT NULL,
  cor_id int NOT NULL, 
  cliente_id int NOT NULL,  
  CONSTRAINT pk_cor PRIMARY KEY(id),
  CONSTRAINT fk_veiculo_modelo FOREIGN KEY (modelo_id) REFERENCES modelos(id),
  CONSTRAINT fk_veiculo_cor FOREIGN KEY (cor_id) REFERENCES cores(id),
  CONSTRAINT fk_veiculo_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id)
) engine = InnoDB;


CREATE TABLE os (
  numero int NOT NULL AUTO_INCREMENT,
  total decimal(10,2) NOT NULL,
  agenda DATE NOT NULL,
  desconto decimal(10,2),
  status enum('ABERTA', 'FECHADA', 'CANCELADA') NOT NULL,
  veiculo_id int NOT NULL,
  CONSTRAINT pk_cor PRIMARY KEY(numero),
  CONSTRAINT fk_veiculo_os FOREIGN KEY (veiculo_id) REFERENCES veiculos(id)  
) engine = InnoDB;


CREATE TABLE items_os (
  id int NOT NULL AUTO_INCREMENT,
  valor_servico decimal(10,2) NOT NULL,
  observacoes varchar(200),
  servico_id int NOT NULL,
  os_id int NOT NULL,
  CONSTRAINT pk_items_os PRIMARY KEY(id),
  CONSTRAINT fk_servico_items_os FOREIGN KEY (servico_id) REFERENCES servicos(id),
  CONSTRAINT fk_os_items_os FOREIGN KEY (os_id) REFERENCES os(numero)
) engine = InnoDB;




