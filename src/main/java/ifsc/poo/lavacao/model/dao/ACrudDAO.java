package ifsc.poo.lavacao.model.dao;

import java.sql.Connection;
import java.util.List;

public abstract class ACrudDAO<T> {

    protected final Connection connection;

    public ACrudDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract boolean create(T object);
    public abstract boolean update(T object);
    public abstract List<T> getAll();
    public abstract T getById(int id);
    public abstract boolean delete(T object);

}
