package ifsc.poo.lavacao.model.domain;

import java.time.LocalDate;

public class PessoaFisicaBuilder {

    private ClienteBuilder clienteBuilder;
    private LocalDate dataNascimento;
    private String cpf;

    PessoaFisicaBuilder(ClienteBuilder clienteBuilder) {
        this.clienteBuilder = clienteBuilder;
    }

    public PessoaFisicaBuilder setDataNascimento(LocalDate dataNascimento) {
        if(dataNascimento == null)
            throw new IllegalArgumentException("Data de Nascimento não pode ser nula");
        this.dataNascimento = dataNascimento;
        return this;
    }

    public PessoaFisicaBuilder setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public PessoaFisica build() {
        PessoaFisica pf =  new PessoaFisica();
        pf.setId(clienteBuilder.id);
        pf.setNome(clienteBuilder.nome);
        pf.setEmail(clienteBuilder.email);
        pf.setCelular(clienteBuilder.celular);
        pf.setDataCadastro(clienteBuilder.dataCadastro);
        pf.setCpf(cpf);
        pf.setDataNascimento(dataNascimento);
        return pf;
    }

}
