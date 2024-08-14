package ifsc.poo.lavacao.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class PessoaFisica extends Cliente{

    private String cpf;
    private LocalDate dataNascimento;

    PessoaFisica(String nome, String email, String celular, LocalDate dataCadastro) {
        super(nome, email, celular, dataCadastro);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String getDados() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return """
                Id:                 %d
                Nome:               %s
                Data Nascimento:    %s
                CPF:                %s
                Email:              %s
                Celular:            %s
                Data de Cadastro:   %s
                """.formatted(
                        id, nome, dataNascimento.format(dateTimeFormatter),
                cpf, email, celular, dataCadastro.format(dateTimeFormatter)
        );
    }

    @Override
    public String getDados(String observacao) {
        return getDados() + "\nObservação: " + observacao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, dataNascimento);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", tipo=PESSOA FÍSICA }";
    }
}
