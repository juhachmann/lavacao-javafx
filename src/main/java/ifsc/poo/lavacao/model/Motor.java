package ifsc.poo.lavacao.model;

import java.util.Objects;

public class Motor {

    private int potencia;
    private ETipoCombustivel tipoCombustivel;

    public Motor(ETipoCombustivel tipoCombustivel) {
        setTipoCombustivel(tipoCombustivel);
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
        Objects.requireNonNull(tipoCombustivel);
        this.tipoCombustivel = tipoCombustivel;
    }

    @Override
    public String toString() {
        return "Motor{" +
                "potencia=" + potencia +
                ", tipoCombustivel=" + tipoCombustivel +
                '}';
    }

}
