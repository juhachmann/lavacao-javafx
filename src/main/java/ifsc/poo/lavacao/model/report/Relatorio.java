package ifsc.poo.lavacao.model.report;

import ifsc.poo.lavacao.model.Cliente;

public class Relatorio {

    public String imprimir(Cliente cliente) {
        return "RElATORIO DO CLIENTE " + cliente.getNome();
    }

}
