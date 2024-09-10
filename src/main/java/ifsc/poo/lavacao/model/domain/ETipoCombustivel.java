package ifsc.poo.lavacao.model.domain;

public enum ETipoCombustivel {

    GASOLINA("Gasolina"),
    ETANOL("Etanol"),
    FLEX("Flex"),
    DIESEL("Diesel"),
    GNV("GNV"),
    OUTRO("Outro");

    private String nome;

    ETipoCombustivel(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
