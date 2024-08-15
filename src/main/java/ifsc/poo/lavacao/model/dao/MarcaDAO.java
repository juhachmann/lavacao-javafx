package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.Marca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarcaDAO extends ACrudDAO<Marca> {

    public MarcaDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Marca marca) {
        String sql = "INSERT INTO marcas (nome) VALUES (?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, marca.getNome());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Marca marca) {
        String sql = "UPDATE marcas SET nome = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, marca.getNome());
            stmt.setInt(2, marca.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Marca> getAll() {
        String sql = "SELECT * FROM marcas;";
        List<Marca> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Marca marca = new Marca(resultado.getInt("id"), resultado.getString("nome"));
                retorno.add(marca);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    @Override
    public Marca getById(int id) {
        String sql = "SELECT * FROM marcas WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return new Marca(resultado.getInt("id"), resultado.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Marca> getByName(String name) {
        String sql = "SELECT * FROM marcas WHERE nome LIKE ?;";
        List<Marca> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Marca marca = new Marca(resultado.getInt("id"), resultado.getString("nome"));
                retorno.add(marca);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    @Override
    public boolean delete(Marca marca) {
        String sql = "DELETE FROM marcas WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, marca.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
