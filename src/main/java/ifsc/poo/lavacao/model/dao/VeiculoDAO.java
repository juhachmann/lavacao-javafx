package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class VeiculoDAO extends ACrudDAO<Veiculo> {

    private ClienteDAO clienteDAO;

    public VeiculoDAO(Connection connection) {
        super(connection);
        clienteDAO = new ClienteDAO(connection);
    }

    @Override
    public boolean create(Veiculo veiculo) {
        String sql = "INSERT INTO veiculos " +
            "(placa, observacoes, modelo_id, cor_id, cliente_id) " +
            "VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getObservacoes());
            stmt.setInt(3, veiculo.getModelo().getId());
            stmt.setInt(4, veiculo.getCor().getId());
            stmt.setInt(5, veiculo.getCliente().getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public boolean update(Veiculo veiculo) {
        String sql = "UPDATE veiculos SET " +
                "placa = ?, observacoes = ?, modelo_id = ?, " +
                "cor_id = ?, cliente_id = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getObservacoes());
            stmt.setInt(3, veiculo.getModelo().getId());
            stmt.setInt(4, veiculo.getCor().getId());
            stmt.setInt(5, veiculo.getCliente().getId());
            stmt.setInt(6, veiculo.getId());
            return stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    // TODO: Descobrir como testar adequadamente estes métodos DAO com o banco de dados
    @Override
    public List<Veiculo> getAll() {
        String sql = "SELECT v.id as vid, v.placa as vplaca, v.observacoes as vobservacoes, " +
                "c.id as cid, c.nome as cnome, c.tipo as ctipo, c.inscricao_estadual as cinscricao_estadual, " +
                "c.cnpj as ccnpj, c.data_cadastro as cdata_cadastro, c.cpf as ccpf, c.email as cemail," +
                "c.celular as ccelular, c.data_nascimento as cdata_nascimento, " +
                "cor.id as corid, cor.nome as cornome, " +
                "m.id as mid, m.categoria as mcategoria, m.descricao as mdescricao, " +
                "mar.id as marid, mar.nome as marnome " +
                "from veiculos v LEFT JOIN clientes c ON v.cliente_id = c.id " +
                "LEFT JOIN cores cor ON v.cor_id = cor.id " +
                "LEFT JOIN modelos m ON v.modelo_id = m.id " +
                "LEFT JOIN marcas mar ON m.marca_id = mar.id ;";
//        String sql = "SELECT * FROM veiculos " +
//                "LEFT JOIN clientes ON veiculos.cliente_id = clientes.id " +
//                "LEFT JOIN cores on veiculos.cor_id = cores.id " +
//                "LEFT JOIN modelos on veiculos.modelo_id = modelos.id " +
//                "LEFT JOIN marcas on modelos.marca_id = marcas.id;";
        List<Veiculo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(mapToVeiculo(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public Veiculo getById(int id) {
        String sql = "SELECT v.id as vid, v.placa as vplaca, v.observacoes as vobservacoes, " +
                "c.id as cid, c.nome as cnome, c.tipo as ctipo, c.inscricao_estadual as cinscricao_estadual, " +
                "c.cnpj as ccnpj, c.data_cadastro as cdata_cadastro, c.cpf as ccpf, c.email as cemail," +
                "c.celular as ccelular, c.data_nascimento as cdata_nascimento, cor.id as corid, cor.nome as cornome, " +
                "m.id as mid, m.categoria as mcategoria, m.descricao as mdescricao, " +
                "mar.id as marid, mar.nome as marnome " +
                "from veiculos v LEFT JOIN clientes c ON v.cliente_id = c.id " +
                "LEFT JOIN cores cor ON v.cor_id = cor.id " +
                "LEFT JOIN modelos m ON v.modelo_id = m.id " +
                "LEFT JOIN marcas mar ON m.marca_id = mar.id " +
                "WHERE v.id = ?;";
//        String sql = "SELECT * FROM veiculos " +
//                "LEFT JOIN clientes ON veiculos.cliente_id = clientes.id " +
//                "LEFT JOIN cores on veiculos.cor_id = cores.id " +
//                "LEFT JOIN modelos on veiculos.modelo_id = modelos.id " +
//                "LEFT JOIN marcas on modelos.marca_id = marcas.id " +
//                "WHERE veiculos.id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return mapToVeiculo(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public List<Veiculo> getByPlaca(String name) {
        String sql = "SELECT v.id as vid, v.placa as vplaca, v.observacoes as vobservacoes, " +
                "c.id as cid, c.nome as cnome, c.tipo as ctipo, c.inscricao_estadual as cinscricao_estadual, " +
                "c.cnpj as ccnpj, c.data_cadastro as cdata_cadastro, c.cpf as ccpf, c.email as cemail," +
                "c.celular as ccelular, c.data_nascimento as cdata_nascimento, cor.id as corid, cor.nome as cornome, " +
                "m.id as mid, m.categoria as mcategoria, m.descricao as mdescricao, " +
                "mar.id as marid, mar.nome as marnome " +
                "from veiculos v LEFT JOIN clientes c ON v.cliente_id = c.id " +
                "LEFT JOIN cores cor ON v.cor_id = cor.id " +
                "LEFT JOIN modelos m ON v.modelo_id = m.id " +
                "LEFT JOIN marcas mar ON m.marca_id = mar.id " +
                "WHERE v.placa = ?;";
//        String sql = "SELECT v.placa, c.nome FROM veiculos v " +
//                "LEFT JOIN clientes c ON v.cliente_id = c.id " +
//                "LEFT JOIN cores cor on v.cor_id = cor.id " +
//                "LEFT JOIN modelos m on v.modelo_id = m.id " +
//                "LEFT JOIN marcas mar on m.marca_id = mar.id " +
//                "WHERE placa = ?;";
        List<Veiculo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(mapToVeiculo(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }



    @Override
    public boolean delete(Veiculo veiculo) {
        String sql = "DELETE FROM veiculos WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, veiculo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private Veiculo mapToVeiculo(ResultSet resultado) throws SQLException {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(resultado.getInt("vid"));
        veiculo.setPlaca(resultado.getString("vplaca"));
        veiculo.setObservacoes(resultado.getString("vobservacoes"));

        // Cliente
        Cliente cliente = clienteDAO.mapToCliente(resultado, "c");

        // Marca
        Marca marca = new Marca();
        marca.setId(resultado.getInt("marid"));
        marca.setNome(resultado.getString("marnome"));

        // Cor
        Cor cor = new Cor();
        cor.setId(resultado.getInt("corid"));
        cor.setNome(resultado.getString("cornome"));

        // Modelo
        Modelo modelo = new Modelo();
        modelo.setId(resultado.getInt("mid"));
        modelo.setDescricao(resultado.getString("mdescricao"));
        modelo.setCategoria(ECategoria.valueOf(resultado.getString("mcategoria")));
        modelo.setMarca(marca);

        veiculo.setCor(cor);
        veiculo.setCliente(cliente);
        veiculo.setModelo(modelo);

        return veiculo;
    }
    
}
