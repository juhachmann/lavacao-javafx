package ifsc.poo.lavacao.model.dao;

import java.sql.Connection;

public abstract class DAO<T> implements ICrudDAO<T>{

    protected final Connection connection;

    public DAO(Connection connection) {
        this.connection = connection;
    }

}
