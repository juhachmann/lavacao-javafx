package ifsc.poo.lavacao.model.domain;

import ifsc.poo.lavacao.model.exceptions.ExceptionLavacao;

import java.time.LocalDate;
import java.util.*;

public abstract class Cliente implements ICliente {

    protected int id;

    protected String nome;

    protected String email;

    protected String celular;

    protected LocalDate dataCadastro;

    private Set<Veiculo> veiculos = new HashSet<>();

    private Pontuacao pontuacao;

    public static ClientBuilder fromBuilder() {
        return new ClientBuilder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Set<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(Set<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public Pontuacao getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Pontuacao pontuacao) {
        this.pontuacao = pontuacao;
    }

    public void addVeiculo(Veiculo veiculo) {
        Objects.requireNonNull(veiculo);
        veiculos.add(veiculo);
    }

    public void removeVeiculo(Veiculo veiculo) {
        if(!veiculos.remove(veiculo))
            throw new ExceptionLavacao("Cliente não possui o veículo informado!");
    }


}
