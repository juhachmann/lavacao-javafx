package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO Quebrar esta classe em pedaços menores

public class ClienteDAO extends ACrudDAO<Cliente> {


    @Override
    public Cliente create(Cliente cliente) {
        String sqlCliente = "INSERT INTO clientes " +
                "(nome, celular, email, data_cadastro) " +
                "VALUES (?, ?, ?, ?);";
        String sqlPontuacao = "INSERT INTO pontuacao " +
                "(cliente_id, quantidade) " +
                "VALUES ( (SELECT MAX(id) from clientes), ?);";

        PreparedStatement createTipoCliente;
        if (cliente instanceof PessoaFisica pf) {
            createTipoCliente = getStmtCreatePessoaFisica(pf);
        }
        else {
            createTipoCliente = getStmtCreatePessoaJuridica((PessoaJuridica) cliente);
        }

        try (
            PreparedStatement createCliente = connection.prepareStatement(sqlCliente);
            PreparedStatement createPontuacao = connection.prepareStatement(sqlPontuacao);
        )
        {
            connection.setAutoCommit(false);

            createCliente.setString(1, cliente.getNome());
            createCliente.setString(2, cliente.getCelular());
            createCliente.setString(3, cliente.getEmail());
            createCliente.setDate(4, Date.valueOf(LocalDate.now()));
            createCliente.execute();

            createPontuacao.setInt(1, 0);
            createPontuacao.execute();

            assert createTipoCliente != null;
            createTipoCliente.execute();

            connection.commit();
            connection.setAutoCommit(true);

            ResultSet result = connection.prepareStatement("SELECT MAX(id) as id from clientes")
                    .executeQuery();
            if(result.next())
                return this.getById(result.getInt("id"));
            else return null;

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            if(connection != null) {
                try {
                    System.err.println("Transação será desfeita (rollback)");
                    connection.rollback();
                } catch (SQLException exc) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null;
        }
    }

    private PreparedStatement getStmtCreatePessoaFisica(PessoaFisica pf) {
        String sqlPF = "INSERT INTO pessoa_fisica " +
                "(cliente_id, cpf, data_nascimento) " +
                "VALUES ( (SELECT MAX(id) from clientes), ?, ?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sqlPF);
            stmt.setString(1, pf.getCpf());
            stmt.setDate(2, Date.valueOf(pf.getDataNascimento()));
            return stmt;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    private PreparedStatement getStmtCreatePessoaJuridica(PessoaJuridica pj) {
        String sqlPJ = "INSERT INTO pessoa_juridica " +
                "(cliente_id, cnpj, inscricao_estadual) " +
                "VALUES ( (SELECT MAX(id) from clientes), ?, ?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sqlPJ);
            stmt.setString(1, pj.getCnpj());
            stmt.setString(2, pj.getInscricaoEstadual());
            return stmt;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    @Override
    public Cliente update(Cliente cliente) {
        String sqlCliente = "UPDATE clientes " +
                "set nome = ?, celular = ?, email = ?, data_cadastro = ? " +
                "WHERE id = ?;";

        PreparedStatement updateTipoCliente;
        if (cliente instanceof PessoaFisica pf) {
            updateTipoCliente = getStmtUpdatePessoaFisica(pf);
        }
        else {
            updateTipoCliente = getStmtUpdatePessoaJuridica((PessoaJuridica) cliente);
        }

        try (
            PreparedStatement updateCliente = connection.prepareStatement(sqlCliente);
        )
        {
            connection.setAutoCommit(false);

            updateCliente.setString(1, cliente.getNome());
            updateCliente.setString(2, cliente.getCelular());
            updateCliente.setString(3, cliente.getEmail());
            updateCliente.setDate(4, Date.valueOf(cliente.getDataCadastro()));
            updateCliente.setInt(5, cliente.getId());
            updateCliente.execute();

            assert updateTipoCliente != null;
            updateTipoCliente.execute();

            connection.commit();
            connection.setAutoCommit(true);

            return this.getById(cliente.getId());

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            if(connection != null) {
                try {
                    System.err.println("Transação será desfeita (rollback)");
                    connection.rollback();
                } catch (SQLException exc) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null;
        }
    }

    private PreparedStatement getStmtUpdatePessoaFisica(PessoaFisica pf) {
        String sqlPF = "UPDATE pessoa_fisica " +
                "set cpf = ?, data_nascimento = ? " +
                "WHERE cliente_id = ? ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sqlPF);
            stmt.setString(1, pf.getCpf());
            stmt.setDate(2, Date.valueOf(pf.getDataNascimento()));
            stmt.setInt(3, pf.getId());
            return stmt;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    private PreparedStatement getStmtUpdatePessoaJuridica(PessoaJuridica pj) {
        String sqlPJ = "UPDATE pessoa_juridica " +
                "SET cnpj = ?, inscricao_estadual = ? " +
                "WHERE clientes_id = ? ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sqlPJ);
            stmt.setString(1, pj.getCnpj());
            stmt.setString(2, pj.getInscricaoEstadual());
            stmt.setInt(3, pj.getId());
            return stmt;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    @Override
    public List<Cliente> getAll() {
        String sql = "SELECT * FROM clientes c " +
                "LEFT JOIN pessoa_fisica pf ON c.id = pf.cliente_id " +
                "LEFT JOIN pessoa_juridica pj ON c.id = pj.cliente_id " +
                "LEFT JOIN pontuacao pt ON c.id = pt.cliente_id " +
                "WHERE c.deleted_at IS NULL;";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(populateVOFull(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public Cliente getById(int id) {
        String sql = "SELECT * FROM clientes c " +
                "LEFT JOIN pessoa_fisica pf ON c.id = pf.cliente_id " +
                "LEFT JOIN pessoa_juridica pj ON c.id = pj.cliente_id " +
                "LEFT JOIN pontuacao pt ON c.id = pt.cliente_id " +
                "WHERE c.id = ? AND c.deleted_at IS NULL ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return populateVOFull(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public List<Cliente> getByName(String name) {
        String sql = "SELECT * FROM clientes c " +
                "LEFT JOIN pessoa_fisica pf ON c.id = pf.cliente_id " +
                "LEFT JOIN pessoa_juridica pj ON c.id = pj.cliente_id " +
                "LEFT JOIN pontuacao pt ON c.id = pt.cliente_id " +
                "WHERE c.nome LIKE ? AND c.deleted_at IS NULL ;";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(populateVOFull(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public List<Cliente> getByDocument(String doc) {
        String sql = "SELECT * FROM clientes c " +
                "LEFT JOIN pessoa_fisica pf ON c.id = pf.cliente_id " +
                "LEFT JOIN pessoa_juridica pj ON c.id = pj.cliente_id " +
                "LEFT JOIN pontuacao pt ON c.id = pt.cliente_id " +
                "WHERE (pf.cpf = ? OR pj.cnpj = ?) AND c.deleted_at IS NULL ;";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, doc);
            stmt.setString(2, doc);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(populateVOFull(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }



    @Override
    public boolean delete(Cliente cliente) {
        String sql = "UPDATE clientes SET deleted_at = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, cliente.getId());
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

    public Cliente populateVOFull(ResultSet result) throws SQLException {
        Cliente cliente;
        ClienteBuilder builder = Cliente.fromBuilder();

        builder.setId(result.getInt("id"));
        builder.setNome(result.getString("nome"));
        builder.setCelular(result.getString("celular"));
        builder.setEmail(result.getString("email"));
        builder.setDataCadastro(result.getDate("data_cadastro").toLocalDate());

        if(result.getString("cpf") != null) {
            PessoaFisicaBuilder pessoaFisica = builder.pessoaFisica();
            pessoaFisica.setCpf(result.getString("cpf"));
            pessoaFisica.setDataNascimento(result.getDate("data_nascimento").toLocalDate());
            cliente = pessoaFisica.build();
        } else {
            PessoaJuridicaBuilder pessoaJuridica = builder.pessoaJuridica();
            pessoaJuridica.setCnpj(result.getString("cnpj"));
            pessoaJuridica.setInscricaoEstadual(result.getString("inscricao_estadual"));
            cliente = pessoaJuridica.build();
        }

        Pontuacao pontuacao = new Pontuacao();
        pontuacao.setQuantidade(result.getInt("quantidade"));
        cliente.setPontuacao(pontuacao);

        return cliente;
    }

    
}
