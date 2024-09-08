package ifsc.poo.lavacao.model.domain;

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
        PessoaJuridica pj = new PessoaJuridica();
        pj.setId(clienteBuilder.id);
        pj.setNome(clienteBuilder.nome);
        pj.setEmail(clienteBuilder.email);
        pj.setCelular(clienteBuilder.celular);
        pj.setDataCadastro(clienteBuilder.dataCadastro);
        pj.setInscricaoEstadual(inscricaoEstadual);
        pj.setCnpj(cnpj);
        return pj;
    }

}
