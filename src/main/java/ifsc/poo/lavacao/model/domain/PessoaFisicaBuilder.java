package ifsc.poo.lavacao.model.domain;

import java.time.LocalDate;

public class PessoaFisicaBuilder {

    private ClientBuilder clientBuilder;
    private LocalDate dataNascimento;
    private String cpf;

    PessoaFisicaBuilder(ClientBuilder clientBuilder) {
        this.clientBuilder = clientBuilder;
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
        pf.setNome(clientBuilder.nome);
        pf.setEmail(clientBuilder.email);
        pf.setCelular(clientBuilder.celular);
        pf.setDataCadastro(clientBuilder.dataCadastro);
        pf.setCpf(cpf);
        pf.setDataNascimento(dataNascimento);
        return pf;
    }

}
