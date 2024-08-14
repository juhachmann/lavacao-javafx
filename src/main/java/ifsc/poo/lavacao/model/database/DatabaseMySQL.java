package ifsc.poo.lavacao.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseMySQL implements Database  {

    private Connection connection;

    @Override
    public Connection connect() {
        try {
            final String DRIVER = "com.mysql.cj.jdbc.Driver";//MySQL 8
            //String driver = "com.mysql.jdbc.Driver"; //MySQL 5
            final String URL = "jdbc:mysql://localhost:3306/lavacao?useTimezone=true&serverTimezone=UTC";//MySQL 8
            //String url = "jdbc:mysql://localhost:3306/db_vendas?useTimezone=true&serverTimezone=America/Sao_Paulo";
            //String url = "jdbc:mysql://localhost:3306/db_vendas";//MySQL 5
            final String USER = "root";
            final String PASS = "root";
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexão realizada com sucesso!");
            return this.connection;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseMySQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Falha na conexão com o banco de dados.");
            return null;
        }
    }

    @Override
    public void disconnect(Connection con) {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
