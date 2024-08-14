package ifsc.poo.lavacao.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VeiculoUnitTest {

    Veiculo veiculo;
    Cliente cliente;

    @Test
    void whenConstructWithNullClientThenThrowsError() {
        assertThrows(Throwable.class, () -> veiculo = new Veiculo(null, new Modelo("jgjh", new Marca(1, "hhhh")), new Cor(1, "hhhjhj")));
    }

    @Test
    void whenSetNullClientThenThrowsError() {
        cliente = new PessoaFisica("Joana", "joana@email", "5445454", LocalDate.now());
        veiculo = new Veiculo(cliente, new Modelo("jhh", new Marca(1, "jhj")), new Cor(1, "jhghg"));
        assertThrows(Throwable.class, () -> veiculo.setCliente(null));
    }

}
