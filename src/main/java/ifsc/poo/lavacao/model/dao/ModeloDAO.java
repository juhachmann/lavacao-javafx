package ifsc.poo.lavacao.model.dao;


import ifsc.poo.lavacao.model.domain.ECategoria;
import ifsc.poo.lavacao.model.domain.ETipoCombustivel;
import ifsc.poo.lavacao.model.domain.Marca;
import ifsc.poo.lavacao.model.domain.Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModeloDAO extends ACrudDAO<Modelo> {


    @Override
    public Modelo create(Modelo modelo) {
        String sqlA = "INSERT INTO modelos (descricao, categoria, marca_id) VALUES (?, ?, ?) ;";
        String sqlB = "INSERT INTO motores (modelo_id, potencia, tipo_combustivel) " +
                "VALUES ( (SELECT MAX(id) FROM modelos), ?, ?);";

        try ( PreparedStatement createModelo = connection.prepareStatement(sqlA);
              PreparedStatement createMotor = connection.prepareStatement(sqlB))
        {
            connection.setAutoCommit(false);
            createModelo.setString(1, modelo.getDescricao());
            createModelo.setString(2, modelo.getCategoria().name());
            createModelo.setInt(3, modelo.getMarca().getId());
            createModelo.execute();

            createMotor.setInt(1, modelo.getMotor().getPotencia());
            createMotor.setString(2, modelo.getMotor().getTipoCombustivel().name());
            createMotor.execute();

            connection.commit();
            connection.setAutoCommit(true);

            ResultSet result = connection.prepareStatement("SELECT MAX(id) as id from modelos;")
                    .executeQuery();
            if(result.next())
                return this.getById(result.getInt("id"));
            else return null;

        } catch (SQLException ex) {
            if(connection != null) {
                Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
                try{
                    System.err.println("Transação está sendo desfeita (rollback)");
                    connection.rollback();
                } catch (SQLException exc) {
                    Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, exc);
                }
            }
            return null;
        }
    }


    @Override
    public Modelo update(Modelo modelo) {
        String sqlA = "UPDATE modelos SET descricao = ?, marca_id = ?, categoria = ? WHERE id = ?;";
        String sqlB = "UPDATE motores SET potencia = ?, tipo_combustivel = ? " +
                "WHERE modelo_id = ?;";
        try (PreparedStatement updateModelo = connection.prepareStatement(sqlA);
             PreparedStatement updateMotor = connection.prepareStatement(sqlB);)
        {
            connection.setAutoCommit(false);

            updateModelo.setString(1, modelo.getDescricao());
            updateModelo.setInt(2, modelo.getMarca().getId());
            updateModelo.setString(3, modelo.getCategoria().name());
            updateModelo.setInt(4, modelo.getId());
            updateModelo.execute();

            updateMotor.setInt(1, modelo.getMotor().getPotencia());
            updateMotor.setString(2, modelo.getMotor().getTipoCombustivel().name());
            updateMotor.setInt(3, modelo.getId());
            updateMotor.execute();

            connection.commit();
            connection.setAutoCommit(true);

            return this.getById(modelo.getId());

        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            if(connection != null) {
                try {
                    System.err.println("Transação está sendo desfeita (rollback)");
                    connection.rollback();
                } catch (SQLException exc) {
                    Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, exc);
                }
            }
            return null;
        }
    }


    @Override
    public List<Modelo> getAll() {
        String sql = "SELECT m.*, mar.*, mot.* FROM modelos m " +
                "LEFT JOIN marcas mar ON m.marca_id = mar.id " +
                "LEFT JOIN motores mot ON m.id = mot.modelo_id;";
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
        String sql = "SELECT m.*, mar.*, mot.* FROM modelos m " +
                "LEFT JOIN marcas mar ON m.marca_id = mar.id " +
                "LEFT JOIN motores mot ON m.id = mot.modelo_id " +
                "WHERE m.id = ?";
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
        String sql = "SELECT m.*, mar.*, mot.* FROM modelos m " +
                "LEFT JOIN marcas mar ON m.marca_id = mar.id " +
                "LEFT JOIN motores mot ON m.id = mot.modelo_id " +
                "WHERE m.descricao LIKE ?;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(mapToModelo(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public List<Modelo> getByMarca(Marca marca) {
        String sql = "SELECT m.*, mar.*, mot.* FROM modelos m " +
                "LEFT JOIN marcas mar ON m.marca_id = mar.id " +
                "LEFT JOIN motores mot ON m.id = mot.modelo_id " +
                "WHERE mar.id = ?;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, marca.getId());
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
        modelo.getMotor().setPotencia(resultado.getInt("mot.potencia"));
        modelo.getMotor().setTipoCombustivel(ETipoCombustivel.valueOf(resultado.getString("mot.tipo_combustivel")));

        return modelo;
    }


}
