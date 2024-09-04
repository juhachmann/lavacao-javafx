package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.domain.Cliente;
import ifsc.poo.lavacao.model.domain.PessoaFisica;
import ifsc.poo.lavacao.model.domain.PessoaJuridica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO implements ICrud<Cliente> {

    private final Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    // TODO Pegar o Id do cliente recém criado e fazer a relação
    // Arrumar tipo

    @Override
    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, celular, email, data_cadastro, tipo) VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCelular());
            stmt.setString(3, cliente.getEmail());
            stmt.setDate(4, Date.valueOf(cliente.getDataCadastro()));
            stmt.setString(5, null);

            if (cliente instanceof PessoaFisica pf) {

            }

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public boolean alterar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, celular = ?, email = ?, data_cadastro = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCelular());
            stmt.setString(3, cliente.getEmail());
            stmt.setDate(4, Date.valueOf(cliente.getDataCadastro()));
            stmt.setInt(5, cliente.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public List<Cliente> listar() {
        String sql = "SELECT * FROM clientes c LEFT JOIN pessoa_fisica pf ON c.id = pf.cliente_id LEFT JOIN pessoa_juridica pj ON c.id = pj.cliente_id;";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Cliente cliente = populateVO(resultado);
                retorno.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public Cliente buscar(int id) {
        String sql = "SELECT * FROM clientes c LEFT JOIN pessoa_fisica pf ON c.id = pf.cliente_id LEFT JOIN pessoa_juridica pj ON c.id = pj.cliente_id WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Cliente cliente = populateVO(resultado);
                return cliente;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    // TODO isso funciona???
    @Override
    public boolean remover(Cliente cliente)  {
        String sql = "START TRANSACTION;\n" +
                "DELETE FROM clientes WHERE id = ?;\n" +
                "DELETE FROM pessoa_fisica WHERE id = ?;\n" +
                "DELETE FROM pessoa_juridica WHERE id = ?;\n" +
                "COMMIT";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            stmt.setInt(2, cliente.getId());
            stmt.setInt(3, cliente.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    private Cliente populateVO(ResultSet result) throws SQLException {
        if(result.getString("cpf") != null) {
            PessoaFisica cliente = new PessoaFisica();
            cliente.setId(result.getInt("id"));
            cliente.setNome(result.getString("nome"));
            cliente.setEmail(result.getString("email"));
            cliente.setCelular(result.getString("celular"));
            cliente.setDataCadastro(result.getDate("data_cadastro").toLocalDate());
            cliente.setCpf(result.getString("cpf"));
            cliente.setDataNascimento(result.getDate("data_nascimento").toLocalDate());
            return cliente;
        } else {
            PessoaJuridica cliente = new PessoaJuridica();
            cliente.setId(result.getInt("id"));
            cliente.setNome(result.getString("nome"));
            cliente.setEmail(result.getString("email"));
            cliente.setCelular(result.getString("celular"));
            cliente.setDataCadastro(result.getDate("data_cadastro").toLocalDate());
            cliente.setInscricaoEstadual(result.getString("inscricao_estadual"));
            cliente.setCnpj(result.getString("cnpj"));
            return cliente;
        }
    }

    
}
