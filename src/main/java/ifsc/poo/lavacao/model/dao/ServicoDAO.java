package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.domain.ECategoria;
import ifsc.poo.lavacao.model.domain.Servico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServicoDAO extends ACrudDAO<Servico> {

    @Override
    public Servico create(Servico servico) {
        String sql = "INSERT INTO servicos (descricao, valor, categoria) VALUES (?, ?, ?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getValor());
            stmt.setString(3, servico.getCategoria().getNome());
            stmt.execute();

            ResultSet result = connection.prepareStatement("SELECT MAX(id) as id from servicos;")
                    .executeQuery();
            if(result.next())
                return this.getById(result.getInt("id"));
            else return null;

        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    @Override
    public Servico update(Servico servico) {
        String sql = "UPDATE servicos SET descricao = ?, valor = ?, categoria = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getValor());
            stmt.setString(3, servico.getCategoria().getNome());
            stmt.setInt(4, servico.getId());
            stmt.execute();

            return getById(servico.getId());

        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    @Override
    public List<Servico> getAll() {
        String sql = "SELECT * FROM servicos;";
        List<Servico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(mapToServico(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public Servico getById(int id) {
        String sql = "SELECT * FROM servicos WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return mapToServico(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public List<Servico> getByName(String name) {
        String sql = "SELECT * FROM servicos WHERE descricao LIKE ?;";
        List<Servico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(mapToServico(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }



    @Override
    public boolean delete(Servico servico) {
        String sql = "DELETE FROM servicos WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, servico.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private Servico mapToServico(ResultSet resultado) throws SQLException {
        Servico servico = new Servico();
        servico.setId(resultado.getInt("id"));
        servico.setCategoria(ECategoria.valueOf(resultado.getString("categoria")));
        servico.setDescricao(resultado.getString("descricao"));
        servico.setValor(resultado.getDouble("valor"));
        return servico;
    }

}