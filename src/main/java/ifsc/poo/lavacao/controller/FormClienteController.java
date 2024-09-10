package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.ClienteDAO;
import ifsc.poo.lavacao.model.domain.Cliente;

public abstract class FormClienteController<T extends Cliente> extends FormController<Cliente, ClienteDAO> {

}
