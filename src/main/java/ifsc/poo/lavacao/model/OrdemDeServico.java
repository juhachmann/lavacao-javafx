package ifsc.poo.lavacao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdemDeServico {

    private long numero;
    private double desconto;
    private Date agenda;
    private final List<ItemOS> items = new ArrayList<>();
    private Veiculo veiculo;


    public long getNumero() {
        return numero;
    }

    public double getTotal() {
        return calcularServico() - desconto;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public Date getAgenda() {
        return agenda;
    }

    public void setAgenda(Date agenda) {
        this.agenda = agenda;
    }

    public List<ItemOS> getItems() {
        return items;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public double calcularServico() {
        double servico = 0.0;
        for(ItemOS itemOS : items) {
            servico += itemOS.getValorServico();
        }
        return servico;
    }

    public void add(ItemOS itemOS) {
        this.items.add(itemOS);
    }

    public void remove(ItemOS itemOS) {
        this.items.remove(itemOS);
    }

}
