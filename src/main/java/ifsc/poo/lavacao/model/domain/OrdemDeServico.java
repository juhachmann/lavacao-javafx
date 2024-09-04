package ifsc.poo.lavacao.model.domain;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdemDeServico {

    private int id;

    private double desconto;

    private Date agenda;

    private List<ItemOS> items = new ArrayList<>();

    private Veiculo veiculo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Veiculo getVehicle() {
        return veiculo;
    }

    public void setVehicle(Veiculo veiculo) {
        this.veiculo = veiculo;
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

    public void setItems(List<ItemOS> items) {
        this.items = items;
    }



    // TODO Esses métodos não correspondem a um atributo do banco
    //  => como o Data REST vai lidar com ele?
    public double getTotal() {
        return calcularServico() - desconto;
    }

    public double calcularServico() {
        double servico = 0.0;
        for(ItemOS itemOS : items) {
            servico += itemOS.getValorServico();
        }
        return servico;
    }

    // TODO Isso aqui não sei se vai fazer sentido no backend!
    public void add(ItemOS itemOS) {
        this.items.add(itemOS);
    }

    // TODO Ver comentário de add
    public void remove(ItemOS itemOS) {
        this.items.remove(itemOS);
    }

}
