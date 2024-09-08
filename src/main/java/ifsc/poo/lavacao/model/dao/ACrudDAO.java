package ifsc.poo.lavacao.model.dao;

import java.sql.Connection;
import java.util.List;

public abstract class ACrudDAO<T> {

    protected Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public abstract T create(T object);
    public abstract T update(T object);
    public abstract List<T> getAll();
    public abstract T getById(int id);
    public abstract boolean delete(T object);

}
