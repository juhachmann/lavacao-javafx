package ifsc.poo.lavacao.model.domain;


public class Veiculo {

    private int id;

    private String placa;

    private String observacoes;


    private Modelo modelo;


    private Cor cor;


    private Cliente cliente;

    public Veiculo() {}

    Veiculo(VeiculoBuilder builder) {
        this.id = builder.id;
        this.placa = builder.placa;
        this.cliente = builder.cliente;
        this.cor = builder.cor;
        this.observacoes = builder.observacoes;
        this.modelo = builder.modelo;
    }

    public static VeiculoBuilder builder() {
        return new VeiculoBuilder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public static class VeiculoBuilder {

        private int id;
        private String placa;
        private String observacoes;
        private Modelo modelo;
        private Cor cor;
        private Cliente cliente;

        public VeiculoBuilder id(int id) {
            this.id = id;
            return this;
        }

        public VeiculoBuilder placa(String placa) {
            this.placa = placa;
            return this;
        }

        public VeiculoBuilder observacoes(String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        public VeiculoBuilder modelo(Modelo modelo) {
            this.modelo = modelo;
            return this;
        }

        public VeiculoBuilder cor(Cor cor) {
            this.cor = cor;
            return this;
        }

        public VeiculoBuilder cliente(Cliente cliente) {
            this.cliente = cliente;
            return this;
        }

        public Veiculo build() {
            return new Veiculo(this);
        }

    }

}
