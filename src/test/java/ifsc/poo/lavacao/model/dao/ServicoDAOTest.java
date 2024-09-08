package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.ECategoria;
import ifsc.poo.lavacao.model.domain.Marca;
import ifsc.poo.lavacao.model.domain.Modelo;
import ifsc.poo.lavacao.model.domain.Servico;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServicoDAOTest {


    Database db = DatabaseFactory.getDatabase("mysql");
    ServicoDAO dao = new ServicoDAO();
    Connection conn;

    Servico servico = new Servico();
    String descricao = "nome_de_servico";
    String outraDescricao = "outro_nome";
    Servico encontrado;


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
        servico.setDescricao(descricao);
        servico.setCategoria(ECategoria.GRANDE);
        servico.setValor(200.50);

        Servico response = dao.create(servico);
        assertNotNull(response);
    }

    @Order(2)
    @Test
    void getByNome() {
        encontrado = null;

        List<Servico> servicos = dao.getByName(descricao);
        assertNotEquals(0, servicos.size());

        for(Servico servico : servicos) {
            if(servico.getDescricao().equalsIgnoreCase(descricao)) {
                encontrado = servico;
            }
        }

        assertNotNull(encontrado);
        assertEquals(descricao, encontrado.getDescricao());
    }

    @Order(3)
    @Test
    void getById() {
        int id = 0;

        List<Servico> servicos = dao.getByName(descricao);
        for(Servico servico : servicos) {
            if(servico.getDescricao().equalsIgnoreCase(descricao)) {
                id = servico.getId();
            }
        }


        encontrado = null;
        encontrado = dao.getById(id);
        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
    }

    @Order(4)
    @Test
    void update() {
        int id = 0;

        List<Servico> servicos = dao.getByName(descricao);
        for(Servico servico : servicos) {
            if(servico.getDescricao().equalsIgnoreCase(descricao)) {
                id = servico.getId();
            }
        }

        servico.setId(id);
        servico.setDescricao(outraDescricao);
        servico.setCategoria(ECategoria.PADRAO);

        Servico response = dao.update(servico);
        assertNotNull(response);

        encontrado = dao.getById(id);
        assertEquals(servico.getDescricao(), encontrado.getDescricao());

        encontrado = null;
        servicos = dao.getByName(descricao);
        for(Servico servico : servicos) {
            if(servico.getDescricao().equalsIgnoreCase(descricao)) {
                encontrado = servico;
            }
        }
        assertNull(encontrado);

    }

    @Order(5)
    @Test
    void getAll() {
        List<Servico> servicos = dao.getAll();
        assertNotEquals(0, servicos.size());
    }

    @Order(6)
    @Test
    void delete() {
        int id = 0;
        List<Servico> servicos = dao.getByName(outraDescricao);
        for(Servico servico : servicos) {
            if(servico.getDescricao().equalsIgnoreCase(outraDescricao)) {
                id = servico.getId();
            }
        }
        servico.setId(id);
        boolean resposta = dao.delete(servico);
        assertTrue(resposta);

        encontrado = dao.getById(id);
        assertNull(encontrado);
    }


}
