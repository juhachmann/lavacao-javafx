package ifsc.poo.lavacao.model;

import java.util.Objects;

public class Modelo {

    private int id;
    private String descricao;

    private Marca marca;

    private ECategoria categoria = ECategoria.PADRAO;
    private final Motor motor = new Motor(ETipoCombustivel.GASOLINA);

    public Modelo(String descricao, Marca marca) {
        this.descricao = descricao;
        setMarca(marca);
    }

    public Modelo(int id, String descricao, Marca marca, ECategoria categoria) {
        this.id = id;
        this.descricao = descricao;
        setMarca(marca);
        setCategoria(categoria);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        Objects.requireNonNull(marca);
        this.marca = marca;
    }

    public ECategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ECategoria categoria) {
        Objects.requireNonNull(categoria);
        this.categoria = categoria;
    }

    // Alguns testes para evitar problemas de acoplamento
    // E evitar que o código cliente modifique ou se preocupe diretamente com o objeto Motor
    // Como Motor é uma dependência que nunca é injetada
    // O código cliente não está ciente desta dependência
    // Assim, vamos "isolar" a dependência, a classe cliente NUNCA precisa saber dela
    public int getPotencia() {
        return motor.getPotencia();
    }

    public ETipoCombustivel getCombustivel() {
        return motor.getTipoCombustivel();
    }

    public void setPotencia(int potencia) {
        if(potencia <= 0)
            throw new IllegalArgumentException("Potência precisa ser maior do que zero");
        motor.setPotencia(potencia);
    }

    public void setCombustivel(ETipoCombustivel eTipoCombustivel) {
        Objects.requireNonNull(eTipoCombustivel);
        motor.setTipoCombustivel(eTipoCombustivel);
    }

    @Override
    public String toString() {
        return "Modelo{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", marca=" + marca.getNome() +
                ", categoria=" + categoria.getNome() +
                ", potencia=" + motor.getPotencia() +
                ", combustível=" + motor.getTipoCombustivel().getNome() +
                '}';
    }
}
