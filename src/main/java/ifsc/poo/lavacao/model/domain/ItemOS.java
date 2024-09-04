package ifsc.poo.lavacao.model.domain;



public class ItemOS {


    private int id;


    private Servico servico;

    private double valorServico;

    private String observacoes;

    public ItemOS() {
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
        this.valorServico = this.servico.getValor();
    }

    public double getValorServico() {
        return valorServico;
    }

    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

}
