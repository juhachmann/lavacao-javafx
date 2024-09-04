package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.domain.Cor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CorDAO implements ICrud<Cor>{

    private final Connection connection;

    public CorDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean inserir(Cor cor) {
        String sql = "INSERT INTO cores(nome) VALUES(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cor.getNome());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public boolean alterar(Cor cor) {
        String sql = "UPDATE cores SET nome = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cor.getNome());
            stmt.setInt(2, cor.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public List<Cor> listar() {
        String sql = "SELECT * FROM cores";
        List<Cor> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Cor cor = new Cor();
                cor.setId(resultado.getInt("id"));
                cor.setNome(resultado.getString("nome"));
                retorno.add(cor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public Cor buscar(int id) {
        String sql = "SELECT * FROM cores WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Cor cor = new Cor();
                cor.setId(resultado.getInt("id"));
                cor.setNome(resultado.getString("nome"));
                return cor;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    @Override
    public boolean remover(Cor cor) {
        String sql = "DELETE FROM cores WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cor.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


}
