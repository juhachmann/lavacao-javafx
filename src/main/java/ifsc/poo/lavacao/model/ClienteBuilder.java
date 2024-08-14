package ifsc.poo.lavacao.model;

import java.time.LocalDate;

public class ClienteBuilder {

    String nome;
    String email;
    String celular;
    LocalDate dataCadastro;

    public ClienteBuilder setNome(String nome) {
        if(nome == null || nome.isEmpty() || nome.isBlank())
            throw new IllegalArgumentException("Nome não pode estar em branco");
        this.nome = nome;
        return this;
    }

    public ClienteBuilder setEmail(String email) {
        if(email == null || email.isEmpty() || email.isBlank())
            throw new IllegalArgumentException("Email não pode estar em branco");
        this.email = email;
        return this;
    }

    public ClienteBuilder setCelular(String celular) {
        if(celular == null || celular.isEmpty() || celular.isBlank())
            throw new IllegalArgumentException("Celular não pode estar em branco");
        this.celular = celular;
        return this;
    }

    public ClienteBuilder setDataCadastro(LocalDate dataCadastro) {
        if(dataCadastro == null)
            throw new IllegalArgumentException("Data de cadastro não pode estar vazia");
        this.dataCadastro = dataCadastro;
        return this;
    }

    public PessoaFisicaBuilder pessoaFisica() {
        return new PessoaFisicaBuilder(this);
    }

    public PessoaJuridicaBuilder pessoaJuridica() {
        return new PessoaJuridicaBuilder(this);
    }

}
