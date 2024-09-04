package ifsc.poo.lavacao.model.domain;

public enum ETipoCombustivel {

    GAS("Gasolina"),
    ETHANOL("Etanol"),
    FLEX("Flex"),
    DIESEL("Diesel"),
    GNV("GNV"),
    OTHER("Outro");

    private String nome;

    ETipoCombustivel(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
