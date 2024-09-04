package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.domain.Marca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarcaDAO implements ICrud<Marca>{


    private final Connection connection;

    public MarcaDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean inserir(Marca marca) {
        String sql = "INSERT INTO marcas(nome) VALUES(?)";
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
    public boolean alterar(Marca marca) {
        String sql = "UPDATE marcas SET nome = ? WHERE id = ?";
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
    public List<Marca> listar() {
        String sql = "SELECT * FROM marcas";
        List<Marca> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Marca marca = new Marca();
                marca.setId(resultado.getInt("id"));
                marca.setNome(resultado.getString("nome"));
                retorno.add(marca);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public Marca buscar(int id) {
        String sql = "SELECT * FROM marcas WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Marca marca = new Marca();
                marca.setId(resultado.getInt("id"));
                marca.setNome(resultado.getString("nome"));
                return marca;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    @Override
    public boolean remover(Marca marca) {
        String sql = "DELETE FROM marcas WHERE id = ?";
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
