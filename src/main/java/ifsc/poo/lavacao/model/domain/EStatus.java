package ifsc.poo.lavacao.model.domain;

public enum EStatus {

    ABERTA ("aberta"),
    FECHADA ("fechada"),
    CANCELADA ("cancelada");

    private final String name;

    EStatus(String name) {
        this.name = name;
    }

    public String getName(EStatus status) {
        return status.name;
    }

}
