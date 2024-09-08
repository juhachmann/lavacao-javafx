package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.Cor;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CorDAOTest {

    Database db = DatabaseFactory.getDatabase("mysql");
    CorDAO dao = new CorDAO();
    Connection conn;

    Cor cor = new Cor();
    String nome = "nome_de_cor";
    String outroNome = "outro_nome";
    Cor encontrada;

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
        cor.setNome(nome);
        Cor response = dao.create(cor);
        assertNotNull(response);
    }

    @Order(2)
    @Test
    void getByNome() {
        encontrada = null;

        List<Cor> cores = dao.getByName(nome);
        assertNotEquals(0, cores.size());

        for(Cor cor : cores) {
            if(cor.getNome().equalsIgnoreCase(nome)) {
                encontrada = cor;
            }
        }

        assertNotNull(encontrada);
        assertEquals(nome, encontrada.getNome());
    }

    @Order(3)
    @Test
    void getById() {
        int id = 0;

        List<Cor> cores = dao.getByName(nome);
        for(Cor cor : cores) {
            if(cor.getNome().equalsIgnoreCase(nome)) {
                id = cor.getId();
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
        List<Cor> cores = dao.getByName(nome);
        for(Cor cor : cores) {
            if(cor.getNome().equalsIgnoreCase(nome)) {
                id = cor.getId();
            }
        }
        cor.setId(id);
        cor.setNome(outroNome);

        Cor response = dao.update(cor);
        assertNotNull(response);

        encontrada = dao.getById(id);
        assertEquals(cor.getNome(), encontrada.getNome());

        encontrada = null;
        cores = dao.getByName(nome);
        for(Cor cor : cores) {
            if(cor.getNome().equalsIgnoreCase(nome)) {
                encontrada = cor;
            }
        }
        assertNull(encontrada);

    }

    @Order(5)
    @Test
    void getAll() {
        List<Cor> cores = dao.getAll();
        assertNotEquals(0, cores.size());
    }

    @Order(6)
    @Test
    void delete() {
        int id = 0;
        List<Cor> cores = dao.getByName(outroNome);
        for(Cor cor : cores) {
            if(cor.getNome().equalsIgnoreCase(outroNome)) {
                id = cor.getId();
            }
        }
        cor.setId(id);
        boolean resposta = dao.delete(cor);
        assertTrue(resposta);

        encontrada = dao.getById(id);
        assertNull(encontrada);
    }


}
