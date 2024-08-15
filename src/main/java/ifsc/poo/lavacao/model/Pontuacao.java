package ifsc.poo.lavacao.model;

import ifsc.poo.lavacao.model.exceptions.ExceptionLavacao;

public class Pontuacao {

    private int quantidade = 0;

    public int adicionar(int valor) {
        validarValor(valor);
        this.quantidade += valor;
        return saldo();
    }

    public int subtrair(int valor) {
        validarValor(valor);
        if((saldo() - valor) < 0) {
            throw new ExceptionLavacao("Quantidade de pontos a subtrair é maior do que o saldo de pontuação do cliente. Máximo a subtrair: " + saldo());
        }
        this.quantidade -= valor;
        return saldo();
    }

    public int saldo() {
        return quantidade;
    }

    private void validarValor(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser um valor negativo");
        }
    }

    @Override
    public String toString() {
        return Integer.toString(quantidade);
    }

}