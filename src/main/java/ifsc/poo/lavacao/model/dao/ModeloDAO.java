package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.ECategoria;
import ifsc.poo.lavacao.model.Marca;
import ifsc.poo.lavacao.model.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModeloDAO extends DAO<Modelo> {

    public ModeloDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Modelo modelo) {
        String sql = "INSERT INTO modelos (descricao, categoria, marca_id) VALUES (?, ?, ?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getDescricao());
            stmt.setString(2, modelo.getCategoria().getNome());
            stmt.setInt(3, modelo.getMarca().getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public boolean update(Modelo modelo) {
        String sql = "UPDATE modelos SET descricao = ?, marca_id = ?, categoria = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getDescricao());
            stmt.setInt(2, modelo.getMarca().getId());
            stmt.setString(3, modelo.getCategoria().getNome());
            stmt.setInt(4, modelo.getId());
            return stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public List<Modelo> getAll() {
        String sql = "SELECT m.*, mar.* FROM modelos m LEFT JOIN marcas mar ON m.marca_id = mar.id;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(mapToModelo(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public Modelo getById(int id) {
        String sql = "SELECT * FROM modelos WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return mapToModelo(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public List<Modelo> getByName(String name) {
        String sql = "SELECT * FROM modelos WHERE descricao LIKE ?;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(mapToModelo(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public boolean delete(Modelo modelo) {
        String sql = "DELETE FROM modelos WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private Modelo mapToModelo(ResultSet resultado) throws SQLException {
        Modelo modelo = new Modelo();
        modelo.setId(resultado.getInt("m.id"));
        modelo.setCategoria(ECategoria.valueOf(resultado.getString("m.categoria")));
        modelo.setDescricao(resultado.getString("m.descricao"));
        Marca marca = new Marca();
        marca.setId(resultado.getInt("mar.id"));
        marca.setNome(resultado.getString("mar.nome"));
        modelo.setMarca(marca);
        return modelo;
    }

}
