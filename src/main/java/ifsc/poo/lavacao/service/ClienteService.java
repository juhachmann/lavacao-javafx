package ifsc.poo.lavacao.service;

import ifsc.poo.lavacao.model.dao.ClienteDAO;
import ifsc.poo.lavacao.model.domain.Cliente;

public class ClienteService extends ModelService<Cliente, ClienteDAO> {

    @Override
    protected boolean alreadyCreated(Cliente model) {
        return model.getId() > 0;
    }

    @Override
    protected ClienteDAO getDao() {
        return new ClienteDAO();
    }


}
