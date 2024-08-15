package ifsc.poo.lavacao.model;

// tantas linhas pra tão pouca lógica =/

public class Veiculo {

    private int id;
    private String placa;
    private String observacoes;
    private Modelo modelo;
    private Cor cor;
    private Cliente cliente;

    public Veiculo() {}

    Veiculo(Cliente cliente, Modelo modelo, Cor cor) {
        setCliente(cliente);
        setModelo(modelo);
        setCor(cor);
        VeiculoValidation.validate(this);
    }

    Veiculo(VeiculoBuilder builder) {
        this.id = builder.id;
        this.placa = builder.placa;
        this.cliente = builder.cliente;
        this.cor = builder.cor;
        this.observacoes = builder.observacoes;
        this.modelo = builder.modelo;
    }

    // Construtor com muitos argumentos, já daria de usar um "Builder"
    Veiculo(Cliente cliente, String placa, Modelo modelo, Cor cor) {
        setCliente(cliente);
        setModelo(modelo);
        setCor(cor);
        this.placa = placa;
        VeiculoValidation.validate(this);
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

    @Override
    public String toString() {
        return "Veiculo{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", modelo=" + modelo +
                ", cor=" + cor +
                ", cliente=" + cliente + // para não dar loop infinito
                '}';
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
            Veiculo veiculo = new Veiculo(this);
            VeiculoValidation.validate(veiculo);
            return veiculo;
        }

    }


    private static class VeiculoValidation {

        public static void validate(Veiculo veiculo) {
            if(veiculo.getCliente() == null) {
                throw new IllegalArgumentException("Cliente não pode ser nulo");
            }
            if(veiculo.getModelo() == null) {
                throw new IllegalArgumentException("Modelo não pode ser nulo");
            }
            if (veiculo.getPlaca().isBlank()) {
                throw new IllegalArgumentException("Placa não pode estar em branco");
            }
            if(veiculo.getId() < 1) {
                throw new IllegalArgumentException("ID deve ser um inteiro maior do que zero");
            }
        }

    }

}
