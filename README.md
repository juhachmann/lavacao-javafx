# Lavação JavaFX

Um sistema Desktop para gerenciamento de uma Lavação de Carros. Cadastre seus clientes e gerencie suas Ordens de Serviço!

## Recursos
- Interface de usuário ainda "crua", mas funcional
- CRUD de cores, marcas, modelos e serviços da Lavação
- Gerenciamento de Clientes e seus veículos
- Gerenciamento de Ordens de Serviço

## Instalação

### Requisitos: 

- JDK 17+
- Maven ou alguma IDE Java
- MySQL

### Passo a passo:

Faça ou download ou clone este repositório:
```bash
# Clone este repositório
git clone https://github.com/juhachmann/lavacao-javafx
```

#### Banco de Dados:

Com seu cliente MySQL, crie um banco de dados para uso do sistema Lavação e execute o script localizado em:
`<path/to/lavacao-javafx>/src/main/resources/sql/ddl.sql`

Exemplo:
```bash
# Criando um banco de dados pelo terminal de comando
mysql -u <USER> -p -e "create database <NOME_DO_BANCO>"

# Executando o script .sql pelo terminal
mysql -u <USER> -p <NOME_DO_BANCO> < .../lavacao-javafx/src/main/resources/sql/ddl.sql
```

Em seguida, altere as configurações do arquivo **DatabaseMySQL.java** para se ajustar ao seu banco de dados:


```java
// No arquivo .../lavacao-javafx/src/main/java/.../domain/database/DatabaseMySQL.java
// Edite a linha 17: 
	this.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/<SEU_BANCO_DE_DADOS>", "<USUARIO>","<SENHA>");
```

#### Instalação com Maven:

```bash
# Entre na pasta do repositório
cd /path/to/lavacao-javafx

# Execute o script maven e aguarde
mvn clean javafx:run
```

#### Instalação com sua IDE:

Abra o projeto com sua IDE favorita, instale as dependências e execute a partir da classe Main

## Contexto
Desenvolvido como atividade final da disciplina de POO (Programação Orientada a Objetos) do Curso Técnico em Desenvolvimento de Sistemas - 2024.1

A aplicação é o resultado de um exercício de aprendizagem em que praticamos: 
- Conceitos básicos de POO (herança, polimorfismo e encapsulamento)
- Modelagem de classes (tipos de relacionamento e multiplicidade)
- POO com Java (modificadores de acesso, tipos de classes, tipos de atributos, exceções na linguagem Java)
-  Padrão DAO e arquitetura MVC
- Construção de interfaces gráficas com JavaFX

Como exercício, também buscamos experimentar o uso de:
- Interfaces
- Classes parametrizadas
- Padrão *Template Method*
  
### Créditos
Modelagem de classes, requisitos, protótipos de telas: [Prof. Marcos Pisching](https://github.com/mpisching)

Desenvolvido com: 
- [JavaFX](https://openjfx.io/)
- [AtlantaFx](https://github.com/mkpaz/atlantafx)
