package ifsc.poo.lavacao.service;

import ifsc.poo.lavacao.model.dao.VeiculoDAO;
import ifsc.poo.lavacao.model.domain.Cliente;
import ifsc.poo.lavacao.model.domain.Veiculo;

import java.util.List;

public class VeiculoService extends ModelService<Veiculo, VeiculoDAO> {

    @Override
    protected boolean alreadyCreated(Veiculo model) {
        return model.getId() > 0;
    }

    @Override
    protected VeiculoDAO getDao() {
        return new VeiculoDAO();
    }

    public List<Veiculo> getByClient(Cliente model) {
        connection = database.conectar();
        dao.setConnection(connection);
        lista = dao.getByCliente(model);
        database.desconectar(connection);
        return lista;
    }

}
