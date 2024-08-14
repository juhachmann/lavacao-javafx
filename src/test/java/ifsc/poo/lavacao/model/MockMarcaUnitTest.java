package ifsc.poo.lavacao.model;

import ifsc.poo.lavacao.data.MockCor;
import ifsc.poo.lavacao.data.MockMarca;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MockMarcaUnitTest {

    Map<String, Marca> marcas = MockMarca.marcas;

    @BeforeEach
    void setUp() {
        MockMarca.marcas.clear();
    }

    @Test
    void whenTryToCreateListMoreThanOnceLogsInfoAndReturns() {
        assertEquals(0, marcas.size());
        MockMarca.criarMarcas();
        int size = marcas.size();
        MockMarca.criarMarcas();
        assertEquals(size, marcas.size());
        System.out.println(marcas);
    }


    @Test
    void whenAddCorThenNewColourIsAdded() {
        MockMarca.criarMarcas();
        assertFalse(marcas.containsKey("honda"));
        MockMarca.addByName("honda");
        assertTrue(marcas.containsKey("honda"));
        System.out.println(marcas);
    }

    @Test
    void whenTryToCreateWithInvalidNameThrowsException() {
        assertThrows(Exception.class, () -> MockMarca.addByName(null));
        assertThrows(Exception.class, () -> MockMarca.addByName("    "));
        assertThrows(Exception.class, () -> MockMarca.addByName(""));
    }

}
