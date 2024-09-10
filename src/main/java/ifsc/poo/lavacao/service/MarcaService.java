package ifsc.poo.lavacao.service;

import ifsc.poo.lavacao.model.dao.MarcaDAO;
import ifsc.poo.lavacao.model.domain.Marca;

public class MarcaService extends ModelService<Marca, MarcaDAO> {

    @Override
    protected boolean alreadyCreated(Marca model) {
        return model.getId() > 0;
    }

    @Override
    protected MarcaDAO getDao() {
        return new MarcaDAO();
    }

}
