package ifsc.poo.lavacao.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteUnitTest {

    Cliente cliente;
    Veiculo veiculoA;
    Veiculo veiculoB;

    @BeforeEach
    void setUp() {
        cliente = new PessoaFisica("Juliana", "juliana@email.com", "5446466", LocalDate.now());
        veiculoA = new Veiculo(cliente, "MMM0101", new Modelo("uhjs", new Marca(1, "jhjh")), new Cor(1, "hjh"));
        veiculoB = new Veiculo(cliente, "YYY0202", new Modelo("jhgjgh", new Marca(2, "jhjhjh")), new Cor(2, "gghghgh"));
        veiculoA.setId(1);
        veiculoB.setId(2);
        cliente.addVeiculo(veiculoA);
        cliente.addVeiculo(veiculoB);
    }

    @Test
    void whenGetDataCadastroThenGetsImmutableString() {

        assertInstanceOf(String.class, cliente.getDataCadastro());

    }

    @Test
    void whenSeeVeiculosThenReturnSecurityCopyOfVeiculos() {

        var copy = cliente.getVeiculos();
        assertFalse(copy.contains(veiculoA));
        assertFalse(copy.contains(veiculoA));
        assertEquals(copy.get(0).getId(), veiculoA.getId());
        assertEquals(copy.get(1).getId(), veiculoB.getId());
        assertEquals(copy.get(0).getCliente(), veiculoA.getCliente());
        assertEquals(copy.get(1).getCliente(), veiculoB.getCliente());

    }

    @Test
    void whenRemovesFromCopyOfVeiculosThenVeiculosIsNotAffected() {

        var copy = cliente.getVeiculos();
        copy.remove(0);
        assertNotEquals(copy.get(0), cliente.getVeiculos().get(0));

    }

    @Test
    void whenSeeVeiculosTheShouldNotBeAbleToChangeOriginalVeiculoProperties() {

        assertSame(cliente, cliente.getVeiculos().get(0).getCliente());

        var securityCopy = cliente.getVeiculos();
        var v1 = securityCopy.get(0);
        v1.setCliente(new PessoaFisica(null, null, null, null));

        assertSame(cliente, cliente.getVeiculos().get(0).getCliente());

    }


}
