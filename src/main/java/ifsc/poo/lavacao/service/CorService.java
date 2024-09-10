package ifsc.poo.lavacao.service;

import ifsc.poo.lavacao.model.dao.CorDAO;
import ifsc.poo.lavacao.model.domain.Cor;

public class CorService extends ModelService<Cor, CorDAO> {

    @Override
    protected boolean alreadyCreated(Cor model) {
        return model.getId() > 0;
    }

    @Override
    protected CorDAO getDao() {
        return new CorDAO();
    }

}
