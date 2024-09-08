package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.Cor;
import ifsc.poo.lavacao.model.domain.Marca;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MarcaDAOTest {

    Database db = DatabaseFactory.getDatabase("mysql");
    MarcaDAO dao = new MarcaDAO();
    Connection conn;

    Marca marca = new Marca();
    String nome = "nome_de_marca";
    String outroNome = "outro_nome";
    Marca encontrada;

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
        marca.setNome(nome);
        Marca response = dao.create(marca);
        assertNotNull(response);
    }

    @Order(2)
    @Test
    void getByNome() {
        encontrada = null;

        List<Marca> marcas = dao.getByName(nome);
        assertNotEquals(0, marcas.size());

        for(Marca marca : marcas) {
            if(marca.getNome().equalsIgnoreCase(nome)) {
                encontrada = marca;
            }
        }

        assertNotNull(encontrada);
        assertEquals(nome, encontrada.getNome());
    }

    @Order(3)
    @Test
    void getById() {
        int id = 0;

        List<Marca> marcas = dao.getByName(nome);
        for(Marca marca : marcas) {
            if(marca.getNome().equalsIgnoreCase(nome)) {
                id = marca.getId();
            }
        }


        encontrada = null;
        encontrada = dao.getById(id);
        assertNotNull(encontrada);
        assertEquals(id, encontrada.getId());
    }

    @Order(4)
    @Test
    void update() {
        int id = 0;

        List<Marca> marcas = dao.getByName(nome);
        for(Marca marca : marcas) {
            if(marca.getNome().equalsIgnoreCase(nome)) {
                id = marca.getId();
            }
        }

        marca.setId(id);
        marca.setNome(outroNome);

        Marca response = dao.update(marca);
        assertNotNull(response);

        encontrada = dao.getById(id);
        assertEquals(marca.getNome(), encontrada.getNome());

        encontrada = null;
        marcas = dao.getByName(nome);
        for(Marca marca : marcas) {
            if(marca.getNome().equalsIgnoreCase(nome)) {
                encontrada = marca;
            }
        }
        assertNull(encontrada);

    }

    @Order(5)
    @Test
    void getAll() {
        List<Marca> marcas = dao.getAll();
        assertNotEquals(0, marcas.size());
    }

    @Order(6)
    @Test
    void delete() {
        int id = 0;
        List<Marca> marcas = dao.getByName(nome);
        for(Marca marca : marcas) {
            if(marca.getNome().equalsIgnoreCase(nome)) {
                id = marca.getId();
            }
        }
        marca.setId(id);
        boolean resposta = dao.delete(marca);
        assertTrue(resposta);

        encontrada = dao.getById(id);
        assertNull(encontrada);
    }

}
