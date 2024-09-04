package ifsc.poo.lavacao.model.dao;

import java.util.List;

public interface ICrud<T> {

    T buscar(int id);
    boolean inserir(T t);
    boolean alterar(T t);
    boolean remover(T t);
    List<T> listar();

}
