package ifsc.poo.lavacao.model;

public class Cor {

    private int id;
    private String nome;
    private final CorDTO locked = new CorDTO(this);

    public Cor(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CorDTO getLocked() {
        return locked;
    }

    @Override
    public String toString() {
        return "Cor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

    // Pensando em "cópias de segurança" e em imutabilidade,
    // fiz este teste de um objeto que não pode ser modificado pelo cliente,
    // mas que sempre está atualizado com a referência
    // Imutável por fora, mutável por dentro
    // E é um tipo de singleton
    // Qual o problema aqui? É o acoplamento mesmo, ou seja,
    // Sempre que mudar uma propriedade de cor, tem que mudar aqui junto
    public class CorDTO {

        private final Cor cor;

        private CorDTO(Cor cor) {
            this.cor = cor;
        }

        public int getId() {
            return cor.id;
        }

        public String getNome() {
            return cor.nome;
        }

        @Override
        public String toString() {
            return cor.toString();
        }

    }

}
