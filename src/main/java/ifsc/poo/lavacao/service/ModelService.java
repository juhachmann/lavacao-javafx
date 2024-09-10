package ifsc.poo.lavacao.service;

import ifsc.poo.lavacao.model.dao.ACrudDAO;
import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;

import java.sql.Connection;
import java.util.List;

public abstract class ModelService<T, U extends ACrudDAO<T>> {

    Database database = DatabaseFactory.getDatabase("mysql");
    Connection connection;
    U dao;
    List<T> lista;

    public ModelService() {
        this.dao = getDao();
    }

    protected abstract boolean alreadyCreated(T model);
    protected abstract U getDao();


    public List<T> getAll() {
        connection = database.conectar();
        dao.setConnection(connection);
        lista = dao.getAll();
        database.desconectar(connection);
        return lista;
    }

    public void excluir(T model) {
        connection = database.conectar();
        dao.setConnection(connection);
        dao.delete(model);
        database.desconectar(connection);
    }

    public T saveModel(T model) {
        connection = database.conectar();
        dao.setConnection(connection);
        T saved;
        if(alreadyCreated(model))
            saved = dao.update(model);
        else saved = dao.create(model);
        database.desconectar(connection);
        return saved;
    }


}
