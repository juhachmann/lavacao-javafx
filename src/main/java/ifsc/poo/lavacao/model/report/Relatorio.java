package ifsc.poo.lavacao.model.report;

import ifsc.poo.lavacao.model.Client;

public class Relatorio {

    public String imprimir(Client client) {
        return "RElATORIO DO CLIENTE " + client.getNome();
    }

}
