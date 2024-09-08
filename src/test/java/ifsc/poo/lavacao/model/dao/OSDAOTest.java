package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class OSDAOTest {

    Database db = DatabaseFactory.getDatabase("mysql");
    ModeloDAO dao = new ModeloDAO();
    Connection conn;


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


    }


    @Order(2)
    @Test
    void getByNome() {


    }


    @Order(3)
    @Test
    void getById() {


    }


    @Order(4)
    @Test
    void update() {


    }


    @Order(5)
    @Test
    void getAll() {


    }


    @Order(6)
    @Test
    void delete() {


    }


    @Order(7)
    @Test
    void deleteMarca() {


    }


}
