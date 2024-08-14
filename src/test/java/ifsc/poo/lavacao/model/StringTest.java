package ifsc.poo.lavacao.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTest {

    String text;

    @BeforeEach
    void setUp() {
        text = null;
    }

    @Test
    void whenIsNullCannotBeBlankOrEmpty() {
        assertNull(text);
    }

    @Test
    void whenIsBlankMayNotBeEmpty() {
        text = "        ";
        assertTrue(text.isBlank());
        assertFalse(text.isEmpty());
    }

    @Test
    void whenIsEmptyIsAlwaysBlank() {
        text = "";
        assertTrue(text.isEmpty());
        assertTrue(text.isBlank());
    }


}
