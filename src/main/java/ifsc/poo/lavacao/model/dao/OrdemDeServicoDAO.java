package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.EStatus;
import ifsc.poo.lavacao.model.OrdemDeServico;
import ifsc.poo.lavacao.model.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class OrdemDeServicoDAO extends DAO<OrdemDeServico> {

    public OrdemDeServicoDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(OrdemDeServico os) {
        String sql = "INSERT INTO os " +
            "(total, agenda, desconto, status, veiculo_id) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, os.getTotal());
            stmt.setDate(2, (Date) Date.from(os.getAgenda().toInstant()));
            stmt.setDouble(3, os.getDesconto());
            stmt.setString(4, os.getStatus().toString());
            stmt.setInt(5, os.getVeiculo().getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrdemDeServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public boolean update(OrdemDeServico os) {
        String sql = "UPDATE os SET " +
        "total = ?, agenda = ?, desconto = ?, " +
        "status = ?, veiculo_id = ? " +
        "WHERE numero = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, os.getTotal());
            stmt.setDate(2, (Date) Date.from(os.getAgenda().toInstant()));
            stmt.setDouble(3, os.getDesconto());
            stmt.setString(4, os.getStatus().toString());
            stmt.setInt(5, os.getVeiculo().getId());
            stmt.setInt(6, os.getNumero());
            return stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(OrdemDeServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    // Todo: pegar relações
    @Override
    public List<OrdemDeServico> getAll() {
        String sql = "SELECT * FROM os;";
        List<OrdemDeServico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(mapToOS(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemDeServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public OrdemDeServico getById(int id) {
        String sql = "SELECT * FROM os WHERE numero = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return mapToOS(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemDeServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
                retorno.add(mapToOS(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemDeServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    @Override
    public boolean delete(OrdemDeServico os) {
        String sql = "DELETE FROM os WHERE numero = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, os.getNumero());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrdemDeServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // ToDO: ver a questão do Total!
    private OrdemDeServico mapToOS(ResultSet resultado) throws SQLException {
        OrdemDeServico os = new OrdemDeServico();
        os.setNumero(resultado.getInt("numero"));
        os.setAgenda(resultado.getDate("agenda"));
        os.setDesconto (resultado.getDouble("desconto"));
        os.setStatus(EStatus.valueOf(resultado.getString("status")));

        Veiculo veiculo = new Veiculo();
        veiculo.setId(resultado.getInt(""));

        os.setVeiculo(veiculo);

        return os;
    }

}
