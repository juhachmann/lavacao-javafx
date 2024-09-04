package ifsc.poo.lavacao.model.domain;


public class Motor {

    private int id;
    private int potencia;
    private ETipoCombustivel tipoCombustivel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public ETipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(ETipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

}
