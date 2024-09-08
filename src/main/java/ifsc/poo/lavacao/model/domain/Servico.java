package ifsc.poo.lavacao.model.domain;



public class Servico {

    private static int pontos;

    private int id;

    private String descricao;

    private double valor;

    private ECategoria categoria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao( String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public ECategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ECategoria categoria) {
        this.categoria = categoria;
    }

    public static int getPontos() {
        return pontos;
    }

    public static void setPontos( int pontos) {
        Servico.pontos = pontos;
    }

}
