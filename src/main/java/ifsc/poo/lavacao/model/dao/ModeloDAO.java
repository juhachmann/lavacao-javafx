package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.domain.*;
import ifsc.poo.lavacao.model.domain.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeloDAO implements ICrud<Modelo> {

    
    private final Connection connection;

    public ModeloDAO(Connection connection) {
        this.connection = connection;
    }


    // TODO Fazer pegando o id que acabou de ser inserido

    @Override
    public boolean inserir(Modelo modelo) {
        String sql = "INSERT INTO modelos(descricao, categoria, marca_id) VALUES(?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getDescricao());
            stmt.setString(2, modelo.getCategoria().name());
            stmt.setInt(3, modelo.getMarca().getId());
            // TODO Fazer o motor
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public boolean alterar(Modelo modelo) {
        String sql = "UPDATE modelos SET descricao = ?, categoria = ?, marca_id = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getDescricao());
            stmt.setString(2, modelo.getCategoria().name());
            stmt.setInt(3, modelo.getMarca().getId());
            stmt.setInt(4, modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public List<Modelo> listar() {
        String sql = "SELECT * FROM modelos m LEFT JOIN motores mt ON m.id = mt.modelo_id";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = populateVO(resultado);
                retorno.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public Modelo buscar(int id) {
        String sql = "SELECT * FROM modelos WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return populateVO(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    @Override
    public boolean remover(Modelo modelo) {
        String sql = "DELETE FROM modelos WHERE id = ?";
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


    private Modelo populateVO(ResultSet resultado) throws SQLException {
        Modelo modelo = new Modelo();
        modelo.setId(resultado.getInt("id"));
        modelo.setDescricao(resultado.getString("descricao"));
        modelo.setCategoria(ECategoria.valueOf(resultado.getString("categoria")));

        int marca_id = resultado.getInt("marca_id");
        Marca marca = new Marca();
        marca.setId(marca_id);
        modelo.setMarca(marca);

        modelo.getMotor().setPotencia(resultado.getInt("potencia"));
        modelo.getMotor().setTipoCombustivel(ETipoCombustivel.valueOf(resultado.getString("tipo_combustivel")));

        return modelo;
    }

    
}
