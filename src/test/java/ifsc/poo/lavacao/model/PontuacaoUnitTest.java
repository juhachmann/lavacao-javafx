package ifsc.poo.lavacao.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PontuacaoUnitTest {

    Pontuacao pontuacao;

    @BeforeEach
    void setUp() {
        pontuacao = new Pontuacao();
    }

    @Test
    void whenIsConstructedThenQuantidadeIsAlwaysZero() {
        assertEquals(0, pontuacao.saldo());
    }

    @Test
    void whenAddThenSaldoSums() {
        List<Integer> valores = new ArrayList<>(List.of(0, 1, 50, 1522222));
        valores.forEach(aumento -> {
            var saldo = pontuacao.saldo();
            pontuacao.adicionar(aumento);
            assertEquals(saldo + aumento, pontuacao.saldo());
        });
    }

    @Test
    void whenTryAddNegativeValueThenThrowsException() {
        List<Integer> valores = new ArrayList<>(List.of(-1, -5, -5000));
        valores.forEach(aumentoNegativo -> {
            assertThrows(Exception.class, () -> pontuacao.adicionar(aumentoNegativo));
        });
    }

    @Test
    void whenSubtractThenSaldoGoesDown() {
        pontuacao.adicionar(1000000000);
        List<Integer> valores = new ArrayList<>(List.of(0, 1, 50, 1522222));
        valores.forEach(subtracao -> {
            var saldo = pontuacao.saldo();
            pontuacao.subtrair(subtracao);
            assertEquals(saldo - subtracao, pontuacao.saldo());
        });
    }

    @Test
    void whenTrySubtractNegativeValueThenThrowsException() {
        pontuacao.adicionar(100000000);
        List<Integer> valores = new ArrayList<>(List.of(-1, -5, -5000));
        valores.forEach(negativo -> {
            assertThrows(Exception.class, () -> pontuacao.subtrair(negativo));
        });
    }


    @Test
    void whenResultOfSubtractionWouldBeNegativeThenSaldoIsZero() {
        assertEquals(0, pontuacao.saldo());
        pontuacao.subtrair(10);
        assertEquals(0, pontuacao.saldo());
        pontuacao.adicionar(20);
        pontuacao.subtrair(25);
        assertEquals(0, pontuacao.saldo());
    }

    @Test
    void whenAddingAndSubtractingThenSaldoGetsUpdated() {
        assertEquals(0, pontuacao.saldo());
        var soma = 20;
        pontuacao.adicionar(soma);
        var subtracao = 13;
        pontuacao.subtrair(subtracao);
        assertEquals(soma - subtracao, pontuacao.saldo());

        pontuacao.subtrair(5000000);
        assertEquals(0, pontuacao.saldo());

        var valoresParaAdicionar = List.of(5, 50, 3, 20);
        var valoresParaDiminuir = List.of(3, 15, 18);

        valoresParaAdicionar.forEach(valor -> pontuacao.adicionar(valor));
        valoresParaDiminuir.forEach(valor -> pontuacao.subtrair(valor));

        var totalAdicao = 0;
        for(int valor : valoresParaAdicionar) {
            totalAdicao += valor;
        }

        var totalSubtracao = 0;
        for (int valor : valoresParaDiminuir) {
            totalSubtracao += valor;
        }

        var diferenca = totalAdicao - totalSubtracao;

        if (diferenca > 0) {
            assertEquals(diferenca, pontuacao.saldo());
        } else {
            assertEquals(0, pontuacao.saldo());
        }

    }



}
