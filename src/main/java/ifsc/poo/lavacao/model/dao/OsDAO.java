package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.domain.EStatus;
import ifsc.poo.lavacao.model.domain.ItemOS;
import ifsc.poo.lavacao.model.domain.OrdemDeServico;
import ifsc.poo.lavacao.model.domain.Veiculo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OsDAO extends ACrudDAO<OrdemDeServico> {

    // TODO Total é valor derivado ou fica salvo?

    @Override
    public OrdemDeServico create(OrdemDeServico os) {
        String sql = "INSERT INTO os " +
            "(total, agenda, desconto, status, veiculo_id) VALUES (?, ?, ?, ?, ?);";
        try {
            connection.setAutoCommit(false);

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, os.getTotal());
            stmt.setDate(2, Date.valueOf(os.getAgenda()));
            stmt.setDouble(3, os.getDesconto());
            stmt.setString(4, os.getStatus().name());
            stmt.setInt(5, os.getVeiculo().getId());
            stmt.execute();

            ResultSet result = connection.prepareStatement("SELECT MAX(id) as id from os;")
                    .executeQuery();
            int osID;
            if(result.next())
                osID = result.getInt("id");
            else osID = 0;

            ItemOSDAO itemOSDAO = new ItemOSDAO();
            itemOSDAO.setConnection(connection);
            for(ItemOS itemOS : os.getItems()) {
                itemOSDAO.createWithOSId(itemOS, osID);
            }

            connection.commit();
            connection.setAutoCommit(true);

            return getById(osID);

        } catch (SQLException ex) {
            if(connection != null) {
                try {
                    System.err.println("Transação não foi efetuada, rollback");
                    connection.rollback();
                } catch (SQLException exc) {
                    Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    @Override
    public OrdemDeServico update(OrdemDeServico os) {
        String sql = "UPDATE os SET " +
            "total = ?, agenda = ?, desconto = ?, " +
            "status = ?, veiculo_id = ? " +
            "WHERE id = ?;";
        try {
            connection.setAutoCommit(false);

            // Deleta os items da Ordem de Serviço
            ItemOSDAO itemOSDAO = new ItemOSDAO();
            itemOSDAO.setConnection(connection);
            itemOSDAO.deleteByOS(os);

            // Atualiza o registro da OS com os novos dados
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, os.getTotal());
            stmt.setDate(2, Date.valueOf(os.getAgenda()));
            stmt.setDouble(3, os.getDesconto());
            stmt.setString(4, os.getStatus().toString());
            stmt.setInt(5, os.getVeiculo().getId());
            stmt.setInt(6, os.getId());
            stmt.execute();

            // Recria os items da OS
            for(ItemOS itemOS : os.getItems()) {
                itemOSDAO.createWithOSId(itemOS, os.getId());
            }

            connection.commit();
            connection.setAutoCommit(true);

            return getById(os.getId());

        } catch (SQLException ex) {
            if(connection != null) {
                try {
                    System.err.println("Transação não efetuada, rollback");
                    connection.rollback();
                } catch (SQLException exc) {
                    Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, exc);
                }
            }
            Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    @Override
    public List<OrdemDeServico> getAll() {
        String sql = "SELECT * FROM os;";
        List<OrdemDeServico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(populateVO(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public OrdemDeServico getById(int id) {
        String sql = "SELECT * FROM os WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return populateVO(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public List<OrdemDeServico> getByAgenda(Date agenda) {
        String sql = "SELECT * FROM os WHERE agenda = ?;";
        List<OrdemDeServico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, agenda);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(populateVO(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public boolean delete(OrdemDeServico os) {
        String sql = "DELETE FROM os WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, os.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    public OrdemDeServico getByIdEager(int id) {
        String sql = "SELECT * FROM os WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                OrdemDeServico os = populateVO(resultado);

                ItemOSDAO itemOSDAO = new ItemOSDAO();
                itemOSDAO.setConnection(connection);
                List<ItemOS> items = itemOSDAO.getByOS(os);
                os.setItems(items);

                VeiculoDAO veiculoDAO = new VeiculoDAO();
                veiculoDAO.setConnection(connection);
                Veiculo veiculo = veiculoDAO.getById(os.getVeiculo().getId());
                os.setVeiculo(veiculo);

                return os;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public boolean updateStatus(OrdemDeServico os) {
        String sql = "UPDATE os SET status = ? WHERE id = ? ";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, os.getStatus().name());
            stmt.setInt(2, os.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    private OrdemDeServico populateVO(ResultSet resultado) throws SQLException {
        OrdemDeServico os = new OrdemDeServico();
        os.setId(resultado.getInt("id"));
        os.setAgenda(resultado.getDate("agenda").toLocalDate());
        os.setDesconto (resultado.getDouble("desconto"));
        os.setStatus(EStatus.valueOf(resultado.getString("status")));

        Veiculo veiculo = new Veiculo();
        veiculo.setId(resultado.getInt("veiculo_id"));
        os.setVeiculo(veiculo);

        return os;
    }

}
