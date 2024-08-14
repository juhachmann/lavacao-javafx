package ifsc.poo.lavacao.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectsAndReferencesUnitTest {

    static class Ponto {

        int x;
        int y;

        static void print() {
            System.out.println("Olá Mundo");;
        }

        Ponto(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    @Test
    void multipleReferences() {
        Ponto p1 = new Ponto(1,5);
        Ponto p2 = p1;
        p1 = null;
        p1 = new Ponto(2, 10);
        assertEquals(2, p1.x);
        assertEquals(1, p2.x);
        p2 = null;
        // Apenas agora o Ponto(1,5) foi completamente derreferenciado

    }

    @Test
    void arrayOfStrings() {
        // A String "Peter Parker" não vai pro lixo, pois ainda é referenciada
        String[] students = new String[10];
        String studentName = "Peter Parker";
        students[0] = studentName;
        // studentName é só uma variável, que vai deixar de existir quando
        studentName = null;
        System.out.println(Arrays.toString(students));

    }

}
