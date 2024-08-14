package ifsc.poo.lavacao.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testes com String e StringBuilder
 * Conclusão: stringBuilder sempre performa melhor
 * Porém, formatação de texto em bloco é melhor com String
 * Avaliar bem cada necessidade
 *
 */

class StringBuilderTest {

    String texto;
    long startTime;
    long endTime;
    double duration;

    @BeforeEach
    void setUp() {
        texto = "";
        startTime = System.nanoTime();
    }

    @AfterEach
    void tearDown() {
        endTime = System.nanoTime();
        duration = (double) (endTime - startTime) / 1000000;
        System.out.println("Duração: " + duration + " ms");
        System.out.println();
    }

    @Test
    // Concatenação "simples" dentro de loop é má prática!
    // Está demorando 20ms a mais, para um loop de 10 iterações
    void usandoString () {
        System.out.println("Concat em loop Usando String");
        for (int i = 0; i < 10; i++) {
            texto += ("Linha " + i + "\n");
        }
       // System.out.println(texto);
    }

    @Test
    void usandoStringBuilder() {
        System.out.println("Concat em loop com StringBuilder");
        StringBuilder stringBuilder = new StringBuilder(texto);
        for (int i = 0; i < 10; i++) {
            stringBuilder.append("Linha ").append(i).append("\n");
        }
        //System.out.println(stringBuilder);
    }

    @Test
    void usandoStringBuilderECalculo() {
        System.out.println("Concat em loop com StringBuilder e cálculo");
        int j = 0;
        StringBuilder stringBuilder = new StringBuilder(texto);
        for (int i = 0; i < 10; i++) {
            stringBuilder.append("Linha ").append(j + i).append("\n");
        }
        //System.out.println(stringBuilder);
    }

    @Test
    // Esse parece que tá demorando
    void concatenarSemLoop() {
        System.out.println("Concat sem loop usando String, sem variáveis");
        texto += "linha " + 0 + "\n" +
                "linha " + 1 + "\n" +
                "linha " + 2 + "\n" +
                "linha " + 3 + "\n" +
                "linha " + 4 + "\n" +
                "linha " + 5 + "\n" +
                "linha " + 6 + "\n" +
                "linha " + 7 + "\n" +
                "linha " + 8 + "\n" +
                "linha " + 9 + "\n";
        //System.out.println(texto);
    }

    @Test
        // Esse parece que tá demorando
    void concatenarSemLoopComVariaveis() {
        int i = 0;
        System.out.println("Concat sem loop usando String, com variáveis e cálculo");
        texto += "linha " + i + "\n" +
                "linha " + i+1 + "\n" +
                "linha " + i+2 + "\n" +
                "linha " + i+3 + "\n" +
                "linha " + i+4 + "\n" +
                "linha " + i+5 + "\n" +
                "linha " + i+6 + "\n" +
                "linha " + i+7 + "\n" +
                "linha " + i+8 + "\n" +
                "linha " + i+9 + "\n";
        //System.out.println(texto);
    }


    @Test
    // Esse tá sendo mais rápido
    void concatenarComTextBlock() {
        System.out.println("Usando bloco simples, sem variáveis");
        texto += """
                linha 0
                linha 1
                linha 2
                linha 3
                linha 4
                linha 5
                linha 6
                linha 7
                linha 8
                linha 9
                """;
        //System.out.println(texto);
    }

    @Test
    // Demorando tbm
    void comStringFormatter() {
        System.out.println("Usando bloco com variáveis e String Formatter");
        texto += """
                Linha %d
                Linha %d
                Linha %d
                Linha %d
                Linha %d
                Linha %d
                Linha %d
                Linha %d
                Linha %d
                Linha %d
                """.formatted(0, 1, 2, 3, 4, 5,
                6, 7, 8, 9);
        //System.out.println(texto);
    }

}
