package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteDAOTest {

    Database db = DatabaseFactory.getDatabase("mysql");
    ClienteDAO dao = new ClienteDAO();
    Connection conn;

    Cliente cliente;
    String nomePF = "nome_de_cliente";
    String outroNomePF = "outro_nome";
    String nomePJ = "nome_pj";


    Cliente encontrado;

    @BeforeEach
    void setUp() {
        conn = db.conectar();
        dao.setConnection(conn);
    }

    @AfterEach
    void tearDown() {
        db.desconectar(conn);
    }

    @Order(1)
    @Test
    void createPessoaFisica() {

        cliente = new PessoaFisica();

        cliente.setNome(nomePF);
        cliente.setEmail("email");
        cliente.setCelular("123456");

        PessoaFisica pf = (PessoaFisica) cliente;
        pf.setCpf("52223655");
        pf.setDataNascimento(LocalDate.of(2010, 1, 1));

        Cliente response = dao.create(cliente);
        assertNotNull(response);
    }


    @Order(2)
    @Test
    void createPessoaJuridica() {

        cliente = new PessoaJuridica();

        cliente.setNome(nomePJ);
        cliente.setEmail("email222");
        cliente.setCelular("1234asdas56");

        PessoaJuridica pj = (PessoaJuridica) cliente;
        pj.setCnpj("5465464646546465466565");
        pj.setInscricaoEstadual("46464sds6df54sd654sdf654sdf");

        Cliente response = dao.create(cliente);
        assertNotNull(response);
    }


    @Order(3)
    @Test
    void getByNomePF() {
        encontrado = null;

        List<Cliente> clientes = dao.getByName(nomePF);
        assertNotEquals(0, clientes.size());

        for(Cliente cliente : clientes) {
            if(cliente.getNome().equalsIgnoreCase(nomePF)) {
                encontrado = cliente;
            }
        }

        assertNotNull(encontrado);
        assertNotNull(encontrado.getPontuacao());
        assertEquals(nomePF, encontrado.getNome());
        assertInstanceOf(PessoaFisica.class, encontrado);
    }


    @Order(4)
    @Test
    void getByNomePJ() {
        encontrado = null;

        List<Cliente> clientes = dao.getByName(nomePJ);
        assertNotEquals(0, clientes.size());

        for(Cliente cliente : clientes) {
            if(cliente.getNome().equalsIgnoreCase(nomePJ)) {
                encontrado = cliente;
            }
        }

        assertNotNull(encontrado);
        assertEquals(nomePJ, encontrado.getNome());
        assertInstanceOf(PessoaJuridica.class, encontrado);
    }


    @Order(5)
    @Test
    void getById() {
        int id = 0;

        List<Cliente> clientes = dao.getByName(nomePF);
        for(Cliente cliente : clientes) {
            System.out.println(cliente);
            System.out.println(cliente.getNome());
            System.out.println(cliente.getId());
            if(cliente.getNome().equalsIgnoreCase(nomePF)) {
                id = cliente.getId();
            }
        }

        System.out.println(id);

        encontrado = null;
        encontrado = dao.getById(id);
        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
    }


    @Order(6)
    @Test
    void update() {
        int id = 0;

        List<Cliente> clientes = dao.getByName(nomePF);
        for(Cliente cliente : clientes) {
            if(cliente.getNome().equalsIgnoreCase(nomePF)) {
                id = cliente.getId();
            }
        }

        cliente = new PessoaFisica();

        cliente.setId(id);
        cliente.setNome(outroNomePF);
        cliente.setCelular("novo_cel");
        cliente.setEmail("lkjlkjkl");
        cliente.setDataCadastro(LocalDate.of(2024, 9, 6));
        PessoaFisica pf = (PessoaFisica) cliente;
        pf.setCpf("65465464");
        pf.setDataNascimento(LocalDate.of(2000, 5, 6));

        Cliente response = dao.update(cliente);
        assertNotNull(response);

        encontrado = dao.getById(id);
        assertEquals(cliente.getNome(), encontrado.getNome());

        encontrado = null;
        clientes = dao.getByName(nomePF);
        for(Cliente cliente : clientes) {
            if(cliente.getNome().equalsIgnoreCase(nomePF)) {
                encontrado = cliente;
            }
        }
        assertNull(encontrado);

    }

    @Order(7)
    @Test
    void getAll() {
        List<Cliente> clientes = dao.getAll();
        assertNotEquals(0, clientes.size());
    }

    @Order(8)
    @Test
    void delete() {
        int id = 0;
        List<Cliente> clientes = dao.getByName(outroNomePF);
        for(Cliente cliente : clientes) {
            if(cliente.getNome().equalsIgnoreCase(outroNomePF)) {
                id = cliente.getId();
            }
        }

        cliente = new PessoaFisica();
        cliente.setId(id);

        boolean resposta = dao.delete(cliente);
        assertTrue(resposta);

        encontrado = dao.getById(id);
        assertNull(encontrado);

    }

}
