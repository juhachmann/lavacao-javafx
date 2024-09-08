package ifsc.poo.lavacao.model.domain;


public class Modelo {

    private int id;
    private String descricao;
    private Marca marca;
    private ECategoria categoria;
    private Motor motor = new Motor();


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
        this.marca = marca;
    }

    public ECategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ECategoria categoria) {
        this.categoria = categoria;
    }

    public Motor getMotor() {
        return motor;
    }

    @Override
    public String toString() {
        return "Modelo{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", marca=" + marca +
                ", categoria=" + categoria +
                ", motor=" + motor +
                '}';
    }
}
