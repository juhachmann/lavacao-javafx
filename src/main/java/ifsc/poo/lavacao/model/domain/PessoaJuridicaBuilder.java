package ifsc.poo.lavacao.model.domain;

public class PessoaJuridicaBuilder {

    private ClientBuilder clientBuilder;
    private String cnpj;
    private String inscricaoEstadual;

    PessoaJuridicaBuilder(ClientBuilder clientBuilder) {
        this.clientBuilder = clientBuilder;
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
        pj.setNome(clientBuilder.nome);
        pj.setEmail(clientBuilder.email);
        pj.setCelular(clientBuilder.celular);
        pj.setDataCadastro(clientBuilder.dataCadastro);
        pj.setInscricaoEstadual(inscricaoEstadual);
        pj.setCnpj(cnpj);
        return pj;
    }

}
