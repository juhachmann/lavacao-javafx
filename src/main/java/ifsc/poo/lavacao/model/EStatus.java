package ifsc.poo.lavacao.model;

public enum EStatus {
    ABERTA(1, "aberta"),
    FECHADA(2, "fechada"),
    CANCELADA(3, "cancelada");

    private final int code;
    private final String name;

    EStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName(EStatus status) {
        return status.name;
    }

}
