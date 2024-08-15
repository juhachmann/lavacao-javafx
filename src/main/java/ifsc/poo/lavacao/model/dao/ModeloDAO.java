package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.ECategoria;
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
        String sql = "INSERT INTO modelos (descricao) VALUES (?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getDescricao());
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
        String sql = "SELECT * FROM modelos;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = new Modelo();
                modelo.setId(resultado.getInt("id"));
                modelo.setCategoria(ECategoria.valueOf(resultado.getString("categoria")));
                modelo.setDescricao(resultado.getString("descricao"));
                // TODO: PEGAR A MARCA!
                retorno.add(modelo);
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
                Modelo modelo = new Modelo();
                modelo.setId(resultado.getInt("id"));
                modelo.setCategoria(ECategoria.valueOf(resultado.getString("categoria")));
                modelo.setDescricao(resultado.getString("descricao"));
                // TODO: PEGAR A MARCA!
                return modelo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    @Override
    public List<Modelo> getByName(String name) {
        String sql = "SELECT * FROM modelos WHERE nome LIKE ?;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = new Modelo();
                modelo.setId(resultado.getInt("id"));
                modelo.setCategoria(ECategoria.valueOf(resultado.getString("categoria")));
                modelo.setDescricao(resultado.getString("descricao"));
                // TODO: PEGAR A MARCA!
                retorno.add(modelo);
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


}