package ifsc.poo.lavacao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdemDeServico {

    private int numero;
    private double desconto;
    private Date agenda;
    private final List<ItemOS> items = new ArrayList<>();
    private Veiculo veiculo;
    private EStatus status;


    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
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
