package ifsc.poo.lavacao.model;

import ifsc.poo.lavacao.data.MockCor;
import ifsc.poo.lavacao.data.MockMarca;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MockCorUnitTest {

    Map<String,Cor> cores = MockCor.cores;

    @BeforeEach
    void setUp() {
        MockCor.cores.clear();
    }

    @Test
    void whenTryToCreateListMoreThanOnceLogsInfoAndReturns() {
        assertEquals(0, cores.size());
        MockCor.criarCores();
        int size = cores.size();
        MockCor.criarCores();
        assertEquals(size, cores.size());
        System.out.println(cores);
    }


    @Test
    void whenAddCorThenNewColourIsAdded() {
        MockCor.criarCores();
        assertFalse(cores.containsKey("rosa"));
        MockCor.addByName("rosa");
        assertTrue(cores.containsKey("rosa"));
        System.out.println(cores);
    }

    @Test
    void whenTryToCreateWithInvalidNameThrowsException() {
        assertThrows(Exception.class, () -> MockCor.addByName(null));
        assertThrows(Exception.class, () -> MockCor.addByName("    "));
        assertThrows(Exception.class, () -> MockCor.addByName(""));
    }

}
