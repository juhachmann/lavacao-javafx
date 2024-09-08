package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.OsDAO;
import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.OrdemDeServico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class OSController implements Initializable {


    // Elementos FXML
    @FXML
    private TableView tableOS;
    @FXML
    private TextField buscaCliente;
    @FXML
    private TextField buscaPlaca;
    @FXML
    private DatePicker buscaData;


    // Outros atributos do controller
    Database db = DatabaseFactory.getDatabase("mysql");
    OsDAO osDAO = new OsDAO();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarTabela();
    }


    private void carregarTabela() {
        // TODO Carregar Tabela
    }


    // Que interessante: no caso de um dialog, o controller atual é mantido com todas os seus objetos
    // O Dialog vai ser uma interação do usuário para alterar o estado do objeto OS, mas ele vai ser gravado etc
    // Aqui por este controller
    @FXML
    public void showCadastroOS(ActionEvent actionEvent) throws IOException {
        OrdemDeServico os = new OrdemDeServico();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/FormOS.fxml"));
        AnchorPane page = loader.load();

        //criação de um estágio de diálogo (StageDialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registrar Ordem de Serviço");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //enviando o obejto OS para o controller do dialog
        FormOSController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setOS(os);

        //apresenta o diálogo e aguarda a confirmação do usuário
        dialogStage.showAndWait();

        if (controller.isBtConfirmarClicked())
            saveOSAndReloadTable(os);

    }


    private void saveOSAndReloadTable(OrdemDeServico os) {
        osDAO.create(os);
        carregarTabela();
    }


    @FXML
    private void buscarCliente(ActionEvent actionEvent) {
        // TODO busca por cliente, placa e/ou data
    }



}