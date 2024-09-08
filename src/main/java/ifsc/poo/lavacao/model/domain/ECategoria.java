package ifsc.poo.lavacao.model.domain;

public enum ECategoria {

    PEQUENO("Pequeno"),
    MEDIO("Médio"),
    GRANDE("Grande"),
    MOTO("Moto"),
    PADRAO("Padrão");

    private String nome;

    ECategoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
