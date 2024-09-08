package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class MockTest {

    Database db = DatabaseFactory.getDatabase("mysql");

    MarcaDAO marcaDAO = new MarcaDAO();
    ClienteDAO clienteDAO = new ClienteDAO();
    CorDAO corDAO = new CorDAO();
    ModeloDAO modeloDAO = new ModeloDAO();
    VeiculoDAO veiculoDAO = new VeiculoDAO();
    ServicoDAO servicoDAO = new ServicoDAO();
    OsDAO osDAO = new OsDAO();


    public Marca createAndGetMarca() {
        String nomeMarca = "nome_marca";
        Connection newConn = db.conectar();
        marcaDAO.setConnection(newConn);
        Marca m = new Marca();
        m.setNome(nomeMarca);
        Marca created = marcaDAO.create(m);
        db.desconectar(newConn);
        return created;
    }

    public Modelo createAndGetModelo() {
        Marca m = createAndGetMarca();
        Connection newConn = db.conectar();
        modeloDAO.setConnection(newConn);
        Modelo modelo = new Modelo();
        modelo.setDescricao("nome_do_modelo");
        modelo.setCategoria(ECategoria.PADRAO);
        modelo.setMarca(m);
        modelo.getMotor().setTipoCombustivel(ETipoCombustivel.DIESEL);
        Modelo created = modeloDAO.create(modelo);
        db.desconectar(newConn);
        return created;
    }

    public Cor createAndGetCor(String nome) {
        Connection newConn = db.conectar();
        corDAO.setConnection(newConn);
        Cor cor = new Cor();
        cor.setNome(nome);
        Cor created = corDAO.create(cor);
        db.desconectar(newConn);
        return created;
    }

    public Cliente createAndGetCliente(String nome) {
        Connection newConn = db.conectar();
        clienteDAO.setConnection(newConn);
        PessoaFisica cliente = new PessoaFisica();
        cliente.setNome(nome);
        cliente.setDataNascimento(LocalDate.of(2000, 12, 11));
        cliente.setCpf("545");
        cliente.setEmail("sdsdadas");
        cliente.setCelular("666565");
        Cliente created = clienteDAO.create(cliente);
        db.desconectar(newConn);
        return created;
    }

    public Marca createAndGetMarca(String nome) {
        String nomeMarca = "nome_marca";
        Connection newConn = db.conectar();
        marcaDAO.setConnection(newConn);
        Marca m = new Marca();
        m.setNome(nome);
        Marca created = marcaDAO.create(m);
        db.desconectar(newConn);
        return created;
    }

    public Modelo createAndGetModelo(String descricao) {
        Marca m = createAndGetMarca();
        Connection newConn = db.conectar();
        modeloDAO.setConnection(newConn);
        Modelo modelo = new Modelo();
        modelo.setDescricao(descricao);
        modelo.setCategoria(ECategoria.PADRAO);
        modelo.setMarca(m);
        modelo.getMotor().setTipoCombustivel(ETipoCombustivel.DIESEL);
        Modelo created = modeloDAO.create(modelo);
        db.desconectar(newConn);
        return created;
    }

    public Cor createAndGetCor() {
        Connection newConn = db.conectar();
        corDAO.setConnection(newConn);
        Cor cor = new Cor();
        cor.setNome("nome_cor");
        Cor created = corDAO.create(cor);
        db.desconectar(newConn);
        return created;
    }

    public Cliente createAndGetCliente() {
        Connection newConn = db.conectar();
        clienteDAO.setConnection(newConn);
        PessoaFisica cliente = new PessoaFisica();
        cliente.setNome("nova_pessoa_fisica");
        cliente.setDataNascimento(LocalDate.of(2000, 12, 11));
        cliente.setCpf("545");
        cliente.setEmail("sdsdadas");
        cliente.setCelular("666565");
        Cliente created = clienteDAO.create(cliente);
        db.desconectar(newConn);
        return created;
    }

    public Servico createAndGetServico(String descricao) {
        Connection newConn = db.conectar();
        servicoDAO.setConnection(newConn);
        Servico servico = new Servico();
        servico.setDescricao(descricao);
        servico.setCategoria(ECategoria.GRANDE);
        servico.setValor(200.50);
        Servico created = servicoDAO.create(servico);
        db.desconectar(newConn);
        return created;
    }


}
