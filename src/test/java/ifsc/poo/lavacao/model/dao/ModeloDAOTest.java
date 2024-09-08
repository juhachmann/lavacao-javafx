package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.ECategoria;
import ifsc.poo.lavacao.model.domain.ETipoCombustivel;
import ifsc.poo.lavacao.model.domain.Marca;
import ifsc.poo.lavacao.model.domain.Modelo;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModeloDAOTest {
    
    Database db = DatabaseFactory.getDatabase("mysql");
    ModeloDAO dao = new ModeloDAO();
    MarcaDAO marcaDAO = new MarcaDAO();
    Connection conn;

    Modelo modelo = new Modelo();
    String descricao = "nome_de_modelo";
    String outraDescricao = "outro_nome";
    Modelo encontrado;

    String nomeMarca = "nome_de_marca";

    @BeforeEach
    void setUp() {
        conn = db.conectar();
        dao.setConnection(conn);
    }

    @AfterEach
    void tearDown() {
        db.desconectar(conn);
    }

    private Marca createAndGetMarca() {
        marcaDAO.setConnection(conn);
        Marca m = new Marca();
        m.setNome(nomeMarca);
        marcaDAO.create(m);
        List<Marca> marcas = marcaDAO.getByName(nomeMarca);
        int size = marcas.size();
        return size > 0 ? marcas.get(size - 1) : null;
    }

    @Order(1)
    @Test
    void create() {
        modelo.setDescricao(descricao);
        modelo.setCategoria(ECategoria.GRANDE);
        modelo.setMarca(createAndGetMarca());

        modelo.getMotor().setPotencia(500);
        modelo.getMotor().setTipoCombustivel(ETipoCombustivel.DIESEL);

        System.out.println(modelo);

        Modelo response = dao.create(modelo);
        assertNotNull(response);
    }

    @Order(2)
    @Test
    void getByNome() {
        encontrado = null;

        List<Modelo> modelos = dao.getByName(descricao);
        assertNotEquals(0, modelos.size());

        for(Modelo modelo : modelos) {
            if(modelo.getDescricao().equalsIgnoreCase(descricao)) {
                encontrado = modelo;
            }
        }

        assertNotNull(encontrado);
        assertEquals(descricao, encontrado.getDescricao());
        assertNotNull(encontrado.getMarca());
        assertEquals(ETipoCombustivel.DIESEL, encontrado.getMotor().getTipoCombustivel());
    }

    @Order(3)
    @Test
    void getById() {
        int id = 0;

        List<Modelo> modelos = dao.getByName(descricao);
        for(Modelo modelo : modelos) {
            if(modelo.getDescricao().equalsIgnoreCase(descricao)) {
                id = modelo.getId();
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

        List<Modelo> modelos = dao.getByName(descricao);
        for(Modelo modelo : modelos) {
            if(modelo.getDescricao().equalsIgnoreCase(descricao)) {
                id = modelo.getId();
            }
        }

        modelo.setId(id);
        modelo.setDescricao(outraDescricao);
        modelo.setCategoria(ECategoria.PADRAO);

        marcaDAO.setConnection(conn);
        List<Marca> marcas = marcaDAO.getByName(nomeMarca);
        int size = marcas.size();
        Marca marca = size > 0 ? marcas.get(size - 1) : null;
        modelo.setMarca(marca);

        modelo.getMotor().setTipoCombustivel(ETipoCombustivel.DIESEL);
        modelo.getMotor().setPotencia(200);

        Modelo response = dao.update(modelo);
        assertNotNull(response);

        encontrado = dao.getById(id);
        assertEquals(modelo.getDescricao(), encontrado.getDescricao());

        encontrado = null;
        modelos = dao.getByName(descricao);
        for(Modelo modelo : modelos) {
            if(modelo.getDescricao().equalsIgnoreCase(descricao)) {
                encontrado = modelo;
            }
        }
        assertNull(encontrado);

    }

    @Order(5)
    @Test
    void getAll() {
        List<Modelo> modelos = dao.getAll();
        assertNotEquals(0, modelos.size());
    }

    @Order(6)
    @Test
    void delete() {
        int id = 0;
        List<Modelo> modelos = dao.getByName(outraDescricao);
        for(Modelo modelo : modelos) {
            if(modelo.getDescricao().equalsIgnoreCase(outraDescricao)) {
                id = modelo.getId();
            }
        }
        modelo.setId(id);
        boolean resposta = dao.delete(modelo);
        assertTrue(resposta);

        encontrado = dao.getById(id);
        assertNull(encontrado);

    }


    @Order(7)
    @Test
    void deleteMarca() {
        marcaDAO.setConnection(conn);
        List<Marca> marcas = marcaDAO.getByName(nomeMarca);
        int size = marcas.size();
        Marca marca = size > 0 ? marcas.get(size - 1) : null;
        assert marca != null;
        boolean response = marcaDAO.delete(marca);
        assertTrue(response);
    }


}
