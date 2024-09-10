package ifsc.poo.lavacao.model.domain;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdemDeServico {

    private int id;
    private double desconto;
    private LocalDate agenda;
    private List<ItemOS> items = new ArrayList<>();
    private Veiculo veiculo;
    private EStatus status;
    private double total;

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

    public LocalDate getAgenda() {
        return agenda;
    }

    public void setAgenda(LocalDate agenda) {
        this.agenda = agenda;
    }

    public List<ItemOS> getItems() {
        return items;
    }

    public void setItems(List<ItemOS> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    // TODO Esses métodos não correspondem a um atributo do banco
    //  => como o Data REST vai lidar com ele?
    public double calcularTotal() {
        double somaServicos = calcularServico();
        this.total = somaServicos - (somaServicos * desconto / 100);
        return total;
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
        this.calcularTotal();
    }

    // TODO Ver comentário de add
    public void remove(ItemOS itemOS) {
        this.items.remove(itemOS);
        this.calcularTotal();
    }


    @Override
    public String toString() {
        return "OrdemDeServico{" +
                "id=" + id +
                ", desconto=" + desconto +
                ", agenda=" + agenda +
                ", items=" + items +
                ", veiculo=" + veiculo +
                ", status=" + status +
                '}';
    }
}
