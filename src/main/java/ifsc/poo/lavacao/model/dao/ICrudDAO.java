package ifsc.poo.lavacao.model.dao;

import java.util.List;

public interface ICrudDAO<T> {

    public boolean create(T object);
    public boolean update(T object);
    public List<T> getAll();
    public T getById(int id);
    public List<T> getByName(String name);
    public boolean delete(T object);

}
