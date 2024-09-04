package ifsc.poo.lavacao.model.domain;

public enum ECategoria {

    SMALL("Pequeno"),
    MEDIUM("Médio"),
    LARGE("Grande"),
    MOTOCYCLE("Moto"),
    DEFAULT("Padrão");

    private String nome;

    ECategoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
