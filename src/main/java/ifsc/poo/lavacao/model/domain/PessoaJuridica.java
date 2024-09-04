package ifsc.poo.lavacao.model.domain;



import java.time.format.DateTimeFormatter;

public class PessoaJuridica extends Cliente {

    private String cnpj;

    private String inscricaoEstadual;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    @Override
    public String getDados() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return """
                Id:                 %d
                Nome:               %s
                CNPJ:               %s
                Inscr. Estadual:    %s
                Email:              %s
                Celular:            %s
                Data de Cadastro:   %s
                """.formatted(
                id, nome, cnpj, inscricaoEstadual,
                email, celular, dataCadastro.format(dateTimeFormatter)
        );
    }

    // TODO Pesquisar mais sobre StringBuilder
    @Override
    public String getDados(String observacao) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getDados()).append("\nObservação: ").append(observacao);
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return super.toString() +
                ", cnpj='" + cnpj + '\'' +
                ", inscricaoEstadual='" + inscricaoEstadual + '\'' +
                ", tipo=PESSOA JURÍDICA }";
    }

}
