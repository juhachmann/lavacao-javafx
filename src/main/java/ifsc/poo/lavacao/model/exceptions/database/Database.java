package ifsc.poo.lavacao.model.database;
import java.sql.Connection;

public interface Database {

    public Connection connect();
    public void disconnect(Connection con);
}
