package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.*;
import ifsc.poo.lavacao.model.Cliente;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClienteDAO extends ACrudDAO<Cliente> {
    
    public ClienteDAO(Connection connection) {
        super(connection);
    }


    // TODO: Descobrir como se faz uma transaction para o caso de salvar cliente E sua pontuação ao mesmo tempo
    @Override
    public boolean create(Cliente cliente) {
        String sql = "INSERT INTO clientes " +
            "(nome, celular, email, data_cadastro, tipo, " +
            "cpf, data_nascimento, cnpj, inscricao_estadual) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCelular());
            stmt.setString(3, cliente.getEmail());
            stmt.setDate(4, (Date) Date.from(cliente.getDataCadastro().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            if (cliente instanceof PessoaFisica pf) {
                stmt.setString(5, "PF");
                stmt.setString(6, pf.getCpf());
                stmt.setDate(7, (Date) Date.from(pf.getDataNascimento().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                stmt.setString(8, null);
                stmt.setString(9, null);
            } else {
                PessoaJuridica pj = (PessoaJuridica) cliente;
                stmt.setString(5, "PJ");
                stmt.setString(6,null);
                stmt.setDate(7, null);
                stmt.setString(8, pj.getCnpj());
                stmt.setString(9, pj.getInscricaoEstadual());
            }
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public boolean update(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, celular = ?, " +
                "email = ?, data_cadastro = ?, " +
                "tipo = ?, cpf = ?, data_nascimento = ?," +
                "cnpj = ?, inscricao_estadual = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCelular());
            stmt.setString(3, cliente.getEmail());
            stmt.setDate(4, (Date) Date.from(cliente.getDataCadastro().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            if (cliente instanceof PessoaFisica pf) {
                stmt.setString(5, "PF");
                stmt.setString(6, pf.getCpf());
                stmt.setDate(7, (Date) Date.from(pf.getDataNascimento().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                stmt.setString(8, null);
                stmt.setString(9, null);
            } else {
                PessoaJuridica pj = (PessoaJuridica) cliente;
                stmt.setString(5, "PJ");
                stmt.setString(6,null);
                stmt.setDate(7, null);
                stmt.setString(8, pj.getCnpj());
                stmt.setString(9, pj.getInscricaoEstadual());
            }
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Cliente> getAll() {
        String sql = "SELECT * FROM clientes;";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(mapToCliente(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public Cliente getById(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return mapToCliente(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public List<Cliente> getByName(String name) {
        String sql = "SELECT * FROM clientes WHERE nome LIKE ?;";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(mapToCliente(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    

    @Override
    public boolean delete(Cliente cliente) {
        String sql = "DELETE FROM clientes WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updatePontuacao(Cliente cliente) {
        String sql = "UPDATE pontuacao SET quantidade = ? WHERE cliente_id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,cliente.getPontuacao().saldo());
            stmt.setInt(2, cliente.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Pass alias with ".". Example: "cliente."
     * @param resultado
     * @param aliasWithDot
     * @return
     * @throws SQLException
     */
    public Cliente mapToCliente(ResultSet resultado, String aliasWithDot) throws SQLException {
        Cliente cliente;
        ClienteBuilder builder = Cliente.fromBuilder();
        builder.setId(resultado.getInt(aliasWithDot + "id"));
        builder.setNome(resultado.getString(aliasWithDot + "nome"));
        builder.setCelular(resultado.getString(aliasWithDot + "celular"));
        builder.setEmail(resultado.getString(aliasWithDot + "email"));
        builder.setDataCadastro(resultado.getDate(aliasWithDot + "data_cadastro").toLocalDate());
        if(resultado.getString(aliasWithDot + "tipo").equalsIgnoreCase("pf")) {
            PessoaFisicaBuilder pessoaFisica = builder.pessoaFisica();
            pessoaFisica.setCpf(resultado.getString(aliasWithDot + "cpf"));
            pessoaFisica.setDataNascimento(resultado.getDate(aliasWithDot + "data_nascimento").toLocalDate());
            cliente = pessoaFisica.build();
        } else {
            PessoaJuridicaBuilder pessoaJuridica = builder.pessoaJuridica();
            pessoaJuridica.setCnpj(resultado.getString(aliasWithDot + "cnpj"));
            pessoaJuridica.setInscricaoEstadual(resultado.getString(aliasWithDot + "inscricao_estadual"));
            cliente = pessoaJuridica.build();
        }
        return cliente;
    }

    private Cliente mapToCliente(ResultSet resultado) throws SQLException {
        return mapToCliente(resultado, null);
    }
    
}
