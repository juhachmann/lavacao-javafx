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
class VeiculoDAOTest {

    Database db = DatabaseFactory.getDatabase("mysql");
    VeiculoDAO dao = new VeiculoDAO();
    Connection conn;
    MockTest mockTest = new MockTest();

    Veiculo veiculo = new Veiculo();

    String placa = "PLACA";
    String outraPlaca = "OUTRA_";

    Veiculo encontrado;



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
    void create() {
        Modelo modelo = mockTest.createAndGetModelo();
        Cor cor = mockTest.createAndGetCor();
        Cliente cliente = mockTest.createAndGetCliente();

        veiculo.setPlaca(placa);
        veiculo.setCor(cor);
        veiculo.setModelo(modelo);
        veiculo.setCliente(cliente);

        Veiculo response = dao.create(veiculo);
        assertNotNull(response);
    }

    @Order(2)
    @Test
    void getByNome() {
        encontrado = null;

        List<Veiculo> veiculos = dao.getByPlaca(placa);
        assertNotEquals(0, veiculos.size());

        for(Veiculo veiculo : veiculos) {
            if(veiculo.getPlaca().equalsIgnoreCase(placa)) {
                encontrado = veiculo;
            }
        }

        assertNotNull(encontrado);
        assertEquals(placa, encontrado.getPlaca());
    }

    @Order(3)
    @Test
    void getById_TrazRelacoes() { // Esse é o EAGER
        int id = 0;

        List<Veiculo> veiculos = dao.getByPlaca(placa);
        for(Veiculo veiculo : veiculos) {
            if(veiculo.getPlaca().equalsIgnoreCase(placa)) {
                id = veiculo.getId();
            }
        }

        encontrado = null;
        encontrado = dao.getById(id);
        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
        assertNotNull(encontrado.getCliente());
        assertNotNull(encontrado.getCor());
        assertNotNull(encontrado.getModelo());
    }

    @Order(4)
    @Test
    void update() {
        int id = 0;

        List<Veiculo> veiculos = dao.getByPlaca(placa);
        for(Veiculo veiculo : veiculos) {
            if(veiculo.getPlaca().equalsIgnoreCase(placa)) {
                id = veiculo.getId();
            }
        }

        veiculo.setId(id);
        veiculo.setPlaca(outraPlaca);

        Modelo modelo = mockTest.createAndGetModelo();
        Cor cor = mockTest.createAndGetCor();
        Cliente cliente = mockTest.createAndGetCliente();

        veiculo.setCor(cor);
        veiculo.setModelo(modelo);
        veiculo.setCliente(cliente);

        Veiculo response = dao.update(veiculo);
        assertNotNull(response);

        encontrado = dao.getById(id);
        assertEquals(veiculo.getPlaca(), encontrado.getPlaca());

        encontrado = null;
        veiculos = dao.getByPlaca(placa);
        for(Veiculo veiculo : veiculos) {
            if(veiculo.getPlaca().equalsIgnoreCase(placa)) {
                encontrado = veiculo;
            }
        }
        assertNull(encontrado);

    }

    @Order(5)
    @Test
    void getAll() {
        List<Veiculo> veiculos = dao.getAll();
        assertNotEquals(0, veiculos.size());
    }

    @Order(6)
    @Test
    void delete() {
        int id = 0;
        List<Veiculo> veiculos = dao.getByPlaca(outraPlaca);
        for(Veiculo veiculo : veiculos) {
            if(veiculo.getPlaca().equalsIgnoreCase(outraPlaca)) {
                id = veiculo.getId();
            }
        }
        veiculo.setId(id);
        boolean resposta = dao.delete(veiculo);
        assertTrue(resposta);

        encontrado = dao.getById(id);
        assertNull(encontrado);

    }

}