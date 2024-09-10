package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.domain.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeiculoDAO extends ACrudDAO<Veiculo> {


    @Override
    public Veiculo create(Veiculo veiculo) {
        String sql = "INSERT INTO veiculos (placa, observacoes, modelo_id, cor_id, cliente_id) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getObservacoes());
            stmt.setInt(3, veiculo.getModelo().getId());
            stmt.setInt(4, veiculo.getCor().getId());
            stmt.setInt(5, veiculo.getCliente().getId());
            stmt.execute();

            ResultSet result = connection.prepareStatement("SELECT MAX(id) as id from veiculos;")
                    .executeQuery();
            if(result.next())
                return this.getById(result.getInt("id"));
            else return null;

        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    @Override
    public Veiculo update(Veiculo veiculo) {
        String sql = "UPDATE veiculos SET " +
                "placa = ?, observacoes = ?, modelo_id = ?, " +
                "cor_id = ?, cliente_id = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getObservacoes());
            stmt.setInt(3, veiculo.getModelo().getId());
            stmt.setInt(4, veiculo.getCor().getId());
            stmt.setInt(5, veiculo.getCliente().getId());
            stmt.setInt(6, veiculo.getId());
            stmt.execute();

            return getById(veiculo.getId());

        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    @Override
    public List<Veiculo> getAll() {
        String sql = "SELECT * FROM veiculos WHERE deleted_at IS NULL ;";
        List<Veiculo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(populateVO(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    public List<Veiculo> getByCliente(Cliente cliente) {
        String sql = "SELECT " +
                "  v.*, " +
                "  mod.id as modelo_id, " +
                "  mod.descricao, " +
                "  mod.marca_id, " +
                "  mod.categoria, " +
                "  mar.nome as marca_nome, " +
                "  cor.nome as cor_nome " +
                "FROM " +
                "  veiculos v " +
                "  LEFT JOIN modelos `mod` ON v.modelo_id = mod.id " +
                "  LEFT JOIN cores cor ON v.cor_id = cor.id " +
                "  LEFT JOIN marcas mar ON mod.marca_id = mar.id " +
                "  LEFT JOIN clientes c ON v.cliente_id = c.id " +
                "WHERE " +
                "  v.cliente_id = ? AND v.deleted_at IS NULL ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            ResultSet resultado = stmt.executeQuery();
            List<Veiculo> veiculos = new ArrayList<>();
            while (resultado.next()) {

                Veiculo v = new Veiculo();
                v.setId(resultado.getInt("id"));
                v.setPlaca(resultado.getString("placa"));
                v.setObservacoes(resultado.getString("observacoes"));

                Marca marca = new Marca();
                marca.setId(resultado.getInt("marca_id"));
                marca.setNome(resultado.getString("marca_nome"));

                Modelo modelo = new Modelo();
                modelo.setId(resultado.getInt("modelo_id"));
                modelo.setMarca(marca);
                modelo.setDescricao(resultado.getString("descricao"));
                modelo.setCategoria(ECategoria.valueOf(resultado.getString("categoria")));
                v.setModelo(modelo);

                Cor cor = new Cor();
                cor.setId(resultado.getInt("id"));
                cor.setNome(resultado.getString("cor_nome"));
                v.setCor(cor);

                Cliente c = new PessoaFisica();
                c.setId(resultado.getInt("cliente_id"));
                v.setCliente(c);

                veiculos.add(v);
            }
            return veiculos;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    @Override
    public Veiculo getById(int id) {
        String sql = "SELECT v.id as vid, v.placa as vplaca, v.observacoes as vobservacoes, " +
            "c.id as cid, c.nome as cnome, " +
            "cor.id as corid, cor.nome as cornome, " +
            "m.id as mid, m.categoria as mcategoria, m.descricao as mdescricao, " +
            "mar.id as marid, mar.nome as marnome " +
            "from veiculos v LEFT JOIN clientes c ON v.cliente_id = c.id " +
            "LEFT JOIN cores cor ON v.cor_id = cor.id " +
            "LEFT JOIN modelos m ON v.modelo_id = m.id " +
            "LEFT JOIN marcas mar ON m.marca_id = mar.id " +
            "WHERE v.id = ? AND v.deleted_at IS NULL ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return populateVODetails(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public List<Veiculo> getByPlaca(String name) {
        String sql = "SELECT * FROM veiculos " +
                "WHERE placa LIKE ? AND deleted_at IS NULL ;";
        List<Veiculo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(populateVO(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }



    @Override
    public boolean delete(Veiculo veiculo) {
        String sql = "UPDATE veiculos SET deleted_at = ? WHERE id = ?;";
        System.out.println("Entrei aqui pra deletar");
        System.out.println(veiculo.getId());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, veiculo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    private Veiculo populateVO (ResultSet resultado) throws SQLException {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(resultado.getInt("id"));
        veiculo.setPlaca(resultado.getString("placa"));
        veiculo.setObservacoes(resultado.getString("observacoes"));
        return veiculo;
    }


    private Veiculo populateVODetails (ResultSet resultado) throws SQLException {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(resultado.getInt("vid"));
        veiculo.setPlaca(resultado.getString("vplaca"));
        veiculo.setObservacoes(resultado.getString("vobservacoes"));

        // Cliente
        // TODO Olha o bug em potencial aqui!
        Cliente cliente = new ClienteBuilder()
                .setId(resultado.getInt("cid"))
                .setNome(resultado.getString("cnome"))
                .pessoaFisica()
                .build();

        // Marca
        Marca marca = new Marca();
        marca.setId(resultado.getInt("marid"));
        marca.setNome(resultado.getString("marnome"));

        // Cor
        Cor cor = new Cor();
        cor.setId(resultado.getInt("corid"));
        cor.setNome(resultado.getString("cornome"));

        // Modelo
        Modelo modelo = new Modelo();
        modelo.setId(resultado.getInt("mid"));
        modelo.setDescricao(resultado.getString("mdescricao"));
        modelo.setCategoria(ECategoria.valueOf(resultado.getString("mcategoria")));
        modelo.setMarca(marca);

        veiculo.setCor(cor);
        veiculo.setCliente(cliente);
        veiculo.setModelo(modelo);

        return veiculo;
    }




}
