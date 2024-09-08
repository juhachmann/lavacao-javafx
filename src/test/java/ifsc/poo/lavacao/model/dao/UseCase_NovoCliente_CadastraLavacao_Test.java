package ifsc.poo.lavacao.model.dao;

import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.*;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Caso de Uso: um novo cliente deseja lavar seu carro
 * É preciso que o sistema: cadastre o cliente, cadastre seu veículo e cadastre uma nova
 * Ordem de Serviço
 */
public class UseCase_NovoCliente_CadastraLavacao_Test {

    Database db = DatabaseFactory.getDatabase("mysql");

    MockTest mockTest = new MockTest();
    VeiculoDAO veiculoDAO = new VeiculoDAO();
    OsDAO osDAO = new OsDAO();
    Connection conn;

    @Test
    void test() {

        // Cores, marcas e modelos já devem existir no sistema
        Cor cor = mockTest.createAndGetCor("azulão_e_roxo");
        Marca marca = mockTest.createAndGetMarca("uma_marca_qualquer");
        Modelo modelo = mockTest.createAndGetModelo("um_modelo_de_carro");
        Servico servico = mockTest.createAndGetServico("um_servico_bem_dificil");

        // Criando um novo cliente
        Cliente cliente = mockTest.createAndGetCliente("Juliana");

        Veiculo veiculo = new Veiculo();
        veiculo.setCliente(cliente);
        veiculo.setPlaca("GGV5252");
        veiculo.setModelo(modelo);
        veiculo.setCor(cor);
        veiculo.setObservacoes("Um carro muito legalzinho");

        conn = db.conectar();
        veiculoDAO.setConnection(conn);
        Veiculo veiculoCriado = veiculoDAO.create(veiculo);
        db.desconectar(conn);


        // Criando uma OS
        OrdemDeServico os = new OrdemDeServico();
        os.setVeiculo(veiculoCriado);

        ItemOS itemOS = new ItemOS();
        itemOS.setServico(servico);

        os.add(itemOS);
        os.setAgenda(LocalDate.now());
        os.setStatus(EStatus.ABERTA);
        conn = db.conectar();
        osDAO.setConnection(conn);
        OrdemDeServico osCriada = osDAO.create(os);
        db.desconectar(conn);

        // Ver Ordem de Serviço
        conn = db.conectar();
        osDAO.setConnection(conn);
        OrdemDeServico retrieved = osDAO.getByIdEager(osCriada.getId());
        db.desconectar(conn);
        System.out.println(retrieved);

        assertEquals(cliente.getNome(), os.getVeiculo().getCliente().getNome());
        assertEquals(veiculoCriado.getId(), os.getVeiculo().getId());


    }


}
