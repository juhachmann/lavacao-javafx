package ifsc.poo.lavacao.service;

import ifsc.poo.lavacao.model.dao.ModeloDAO;
import ifsc.poo.lavacao.model.domain.Marca;
import ifsc.poo.lavacao.model.domain.Modelo;

import java.util.List;

public class ModeloService extends ModelService<Modelo, ModeloDAO> {

    @Override
    protected boolean alreadyCreated(Modelo model) {
        return model.getId() > 0;
    }

    @Override
    protected ModeloDAO getDao() {
        return new ModeloDAO();
    }

    public List<Modelo> getByMarca(Marca marca) {
        connection = database.conectar();
        dao.setConnection(connection);
        List<Modelo> modelos = dao.getByMarca(marca);
        database.desconectar(connection);
        return modelos;
    }

}
