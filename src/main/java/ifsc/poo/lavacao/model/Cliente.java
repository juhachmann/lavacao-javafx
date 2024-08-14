package ifsc.poo.lavacao.model;

import ifsc.poo.lavacao.model.exceptions.ExceptionLavacao;

import java.time.LocalDate;
import java.util.*;

// TODO Limpar isso aqui (separar a validação e ver o jeito certo de implementar o Builder)
// Ver: https://howtodoinjava.com/design-patterns/creational/builder-pattern-in-java/

public abstract class Cliente implements ICliente {

    protected int id;
    protected String nome;
    protected String email;
    protected String celular;
    protected LocalDate dataCadastro;
    private Set<Veiculo> veiculos = new HashSet<>();
    private Pontuacao pontuacao = new Pontuacao();

    Cliente(String nome, String email, String celular, LocalDate dataCadastro) {
        this.setNome(nome);
        this.setEmail(email);
        this.setCelular(celular);
        this.setDataCadastro(dataCadastro);
    }

    public static ClienteBuilder fromBuilder() {
        return new ClienteBuilder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id <= 0)
            throw new IllegalArgumentException("Id deve ser maior do que zero");
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome.isBlank())
            throw new IllegalArgumentException("Nome não pode estar em branco");
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

    // Por "segurança", retornar um objeto imutável
    // Retorna uma String
    public String getDataCadastro() {
        return dataCadastro.toString();
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        Objects.requireNonNull(dataCadastro);
        this.dataCadastro = dataCadastro;
    }

    // Não é legal fazer isso, mas...
    public Pontuacao getPontuacao() {
        return pontuacao;
    }

    /**
     * Gera uma cópia de segurança com cópias dos objetos da lista de Veículos de Cliente.
     * Mudanças na cópia não afetam a lista original de veículos.
     * Para adicionar ou remover veículos de um Cliente, utilize os métodos fornecidos.
     * @return nova lista com cópia dos objetos Veiculo
     */
    // TODO checar a performance disso, parece que tá horrível...
    // Itera usando esse stream, aí cria lista, aí cria um ArrayList, aff....
    // TODO Estudar mais sobre Stream e Collections do Java
    public List<Veiculo> getVeiculos() {
        var list =  veiculos.stream()
            .map(v -> {
                var copy = new Veiculo(v.getCliente(), v.getPlaca(), v.getModelo(),v.getCor());
                copy.setId(v.getId());
                copy.setCor(v.getCor());
                return copy;
            })
            .toList();
        return new ArrayList<>(list);
    }

    public void addVeiculo(Veiculo veiculo) {
        Objects.requireNonNull(veiculo);
        veiculos.add(veiculo);
    }

    public void removeVeiculo(Veiculo veiculo) {
        if(!veiculos.remove(veiculo))
            throw new ExceptionLavacao("Cliente não possui o veículo informado!");
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", pontuacao=" + pontuacao ;
    }

}
