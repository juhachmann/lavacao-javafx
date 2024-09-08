package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemOSDAO extends ACrudDAO<ItemOS>{

    @Override
    public ItemOS create(ItemOS itemOS) {
        String sql = "INSERT INTO items_os " +
                "(valor_servico, observacoes, servico_id, os_id) VALUES (?, ?, ?, ?) ;";

        try {
           PreparedStatement stmt = connection.prepareStatement(sql);
           stmt.setDouble(1, itemOS.getValorServico());
           stmt.setString(2, itemOS.getObservacoes());
           stmt.setInt(3, itemOS.getServico().getId());
           stmt.setInt(4, itemOS.getOrdemDeServico().getId());
           stmt.execute();

            ResultSet result = connection.prepareStatement("SELECT MAX(id) as id from items_os;")
                .executeQuery();
            if(result.next())
                return this.getById(result.getInt("id"));
            else return null;

        } catch (SQLException ex) {
            Logger.getLogger(ItemOSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    public boolean createWithOSId(ItemOS itemOS, int osId) {
        String sql = "INSERT INTO items_os " +
                "(valor_servico, observacoes, servico_id, os_id) VALUES (?, ?, ?, ?) ;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, itemOS.getValorServico());
            stmt.setString(2, itemOS.getObservacoes());
            stmt.setInt(3, itemOS.getServico().getId());
            stmt.setInt(4, osId);
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItemOSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public ItemOS update(ItemOS itemOS) {
        String sql = "UPDATE items_os " +
                "SET valor_servico = ?, observacoes = ?, servico_id = ?, os_id = ? " +
                "WHERE id = ? ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, itemOS.getValorServico());
            stmt.setString(2, itemOS.getObservacoes());
            stmt.setInt(3, itemOS.getServico().getId());
            stmt.setInt(4, itemOS.getOrdemDeServico().getId());
            stmt.setInt(5, itemOS.getId());
            stmt.execute();

            return this.getById(itemOS.getId());

        } catch (SQLException ex) {
            Logger.getLogger(ItemOSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    @Override
    public List<ItemOS> getAll() {
        String sql = "SELECT * FROM items_os";
        List<ItemOS> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(populateVO(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemOSDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public ItemOS getById(int id) {
        String sql = "SELECT ios.*, s.* FROM items_os ios " +
                "LEFT JOIN servicos s ON ios.servico_id = s.id " +
                "WHERE id = ? ";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return populateVO(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemOSDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public List<ItemOS> getByOS(OrdemDeServico os) {
        String sql = "SELECT ios.*, s.* FROM items_os ios " +
                "LEFT JOIN servicos s ON ios.servico_id = s.id " +
                "WHERE os_id = ? ";
        List<ItemOS> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, os.getId());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(populateVO(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemOSDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public boolean delete(ItemOS itemOS) {
        String sql = "DELETE FROM items_os WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemOS.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItemOSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteByOS(OrdemDeServico os) {
        String sql = "DELETE FROM items_os WHERE os_id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, os.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItemOSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private ItemOS populateVO(ResultSet resultado) throws SQLException {
        ItemOS itemOS = new ItemOS();
        itemOS.setId(resultado.getInt("ios.id"));
        itemOS.setObservacoes(resultado.getString("ios.observacoes"));
        itemOS.setValorServico(resultado.getDouble("ios.valor_servico"));

        Servico servico = new Servico();
        servico.setId(resultado.getInt("s.id"));
        servico.setCategoria(ECategoria.valueOf(resultado.getString("s.categoria")));
        servico.setDescricao(resultado.getString("s.descricao"));
        servico.setValor(resultado.getDouble("s.valor"));
        itemOS.setServico(servico);

        return itemOS;
    }

}
