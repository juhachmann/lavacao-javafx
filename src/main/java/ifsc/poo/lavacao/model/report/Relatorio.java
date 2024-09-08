package ifsc.poo.lavacao.model.report;


import ifsc.poo.lavacao.model.domain.Cliente;

public class Relatorio {

    public String imprimir(Cliente client) {
        return "RElATORIO DO CLIENTE " + client.getNome();
    }

}
