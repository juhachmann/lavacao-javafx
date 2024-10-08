package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.domain.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
            stmt.setDouble(1, os.calcularTotal());
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
            stmt.setDouble(1, os.calcularTotal());
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
        String sql = "SELECT os.*, v.placa FROM os " +
                "LEFT JOIN veiculos v ON os.veiculo_id = v.id ;";
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

    public List<OrdemDeServico> getByCliente(String nome) {
        String sql = "SELECT os.*, v.placa FROM os " +
                "LEFT JOIN veiculos v ON os.veiculo_id = v.id " +
                "LEFT JOIN clientes c ON v.cliente_id = c.id " +
                "WHERE c.nome LIKE ? ;";
        List<OrdemDeServico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(populateVO(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    public List<OrdemDeServico> getByDate(LocalDate date) {
        String sql = "SELECT os.*, v.placa FROM os " +
                "LEFT JOIN veiculos v ON os.veiculo_id = v.id " +
                "WHERE agenda BETWEEN ? AND curdate();";
        List<OrdemDeServico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(date));
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(populateVO(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public List<OrdemDeServico> getByPlaca(String placa) {
        String sql = "SELECT os.*, v.placa FROM os " +
                "LEFT JOIN veiculos v ON os.veiculo_id = v.id " +
                "WHERE v.placa LIKE ? ESCAPE '!';";
        List<OrdemDeServico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + placa + "%");
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
        String sql = "SELECT os.*, v.placa FROM os " +
                "LEFT JOIN veiculos v ON os.veiculo_id = v.id WHERE os.id = ?;";
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
        String sql = "SELECT os.*, v.placa FROM os " +
                "LEFT JOIN veiculos v ON os.veiculo_id = v.id WHERE os.id = ?;";
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

//    private OrdemDeServico populateVOEager(ResultSet resultado) throws SQLException {
//        OrdemDeServico os = new OrdemDeServico();
//        os.setId(resultado.getInt("id"));
//        os.setAgenda(resultado.getDate("agenda").toLocalDate());
//        os.setDesconto (resultado.getDouble("desconto"));
//        os.setTotal(resultado.getDouble("total"));
//        os.setStatus(EStatus.valueOf(resultado.getString("status")));
//
//        Marca marca = new Marca();
//        marca.setId(resultado.getInt("marca_id"));
//        marca.setNome(resultado.getString("nome_marca"));
//
//        Modelo modelo = new Modelo();
//        modelo.setId(resultado.getInt("modelo_id"));
//        modelo.setMarca(marca);
//        modelo.setDescricao(resultado.getString("descricao"));
//        modelo.setCategoria(ECategoria.valueOf(resultado.getString("categoria")));
//
//        Cor cor = new Cor();
//        Veiculo veiculo = new Veiculo();
//        veiculo.setId(resultado.getInt("veiculo_id"));
//        veiculo.setPlaca(resultado.getString("placa"));
//        os.setVeiculo(veiculo);
//
//        return os;
//    }


    private OrdemDeServico populateVO(ResultSet resultado) throws SQLException {
        OrdemDeServico os = new OrdemDeServico();
        os.setId(resultado.getInt("id"));
        os.setAgenda(resultado.getDate("agenda").toLocalDate());
        os.setDesconto (resultado.getDouble("desconto"));
        os.setTotal(resultado.getDouble("total"));
        os.setStatus(EStatus.valueOf(resultado.getString("status")));

        Veiculo veiculo = new Veiculo();
        veiculo.setId(resultado.getInt("veiculo_id"));
        veiculo.setPlaca(resultado.getString("placa"));
        os.setVeiculo(veiculo);

        return os;
    }


}
