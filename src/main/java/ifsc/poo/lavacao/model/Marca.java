package ifsc.poo.lavacao.model;

public class Marca {

    private int id;
    private String nome;
    private final MarcaDTO dto = new MarcaDTO(this);

    public Marca(int id, String nome) {
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

    public MarcaDTO getDTO() {
        return dto;
    }

    @Override
    public String toString() {
        return "Marca{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }


    // Teste: Ver Cor.java
    public class MarcaDTO {

        private final Marca marca;

        private MarcaDTO(Marca marca) {
            this.marca = marca;
        }

        public int getId() {
            return marca.getId();
        }

        public String getNome() {
            return marca.getNome();
        }

        @Override
        public String toString() {
            return marca.toString();
        }

    }



}

