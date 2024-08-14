package ifsc.poo.lavacao.model;

public class PessoaJuridicaBuilder {

    private ClienteBuilder clienteBuilder;
    private String cnpj;
    private String inscricaoEstadual;

    PessoaJuridicaBuilder(ClienteBuilder clienteBuilder) {
        this.clienteBuilder = clienteBuilder;
    }

    public PessoaJuridicaBuilder setCnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public PessoaJuridicaBuilder setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
        return this;
    }

    public PessoaJuridica build() {
        PessoaJuridica pj = new PessoaJuridica(clienteBuilder.nome, clienteBuilder.email, clienteBuilder.celular, clienteBuilder.dataCadastro);
        pj.setInscricaoEstadual(inscricaoEstadual);
        pj.setCnpj(cnpj);
        return pj;
    }

}
