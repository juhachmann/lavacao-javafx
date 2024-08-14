package ifsc.poo.lavacao.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CorUnitTest {

    Cor cor;
    String defaultCorName = "vermelho";

    @BeforeEach
    void setUp() {
        cor = new Cor(1, defaultCorName);
    }

    @Test
    void corIsMutable() {
        cor.setId(2);
        cor.setNome("preto");
    }

    @Test
    void whenCorIsChangedThenDTOIsUpdated() {
        var dto = cor.getLocked();
        assertEquals(defaultCorName, cor.getNome());
        assertEquals(defaultCorName, dto.getNome());
        assertEquals(cor.getNome(), dto.getNome());

        var updatedName = "preto";
        cor.setNome(updatedName);
        assertEquals(updatedName, cor.getNome());
        assertEquals(updatedName, dto.getNome());
        assertEquals(cor.getNome(), dto.getNome());
    }

    @Test
    void whenGetLockedThenAlwaysReturnSameObject() {
        var dtoA = cor.getLocked();
        var dtoB = cor.getLocked();
        assertSame(dtoA, dtoB);
    }

    @Test
    void whenIdChangesLockedIdIsUpdated() {
        var dto = cor.getLocked();

        for(int i = 0; i <= 10; i++) {
            cor.setId(i);
            assertEquals(i, cor.getId());
            assertEquals(i, dto.getId());
            assertEquals(dto.getId(), cor.getId());
        }
    }

}
