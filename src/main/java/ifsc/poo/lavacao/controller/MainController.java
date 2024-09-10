
package ifsc.poo.lavacao.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ifsc.poo.lavacao.model.domain.OrdemDeServico;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class MainController implements Initializable {


    public BorderPane mainPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    public void showOSPane(ActionEvent actionEvent) throws IOException {
        AnchorPane a = FXMLLoader.load(getClass().getResource("/view/TableOS.fxml"));
        mainPane.setCenter(a);
    }


    public void showClientesPane(ActionEvent actionEvent) throws IOException {
        AnchorPane a = FXMLLoader.load(getClass().getResource("/view/TableClientes.fxml"));
        mainPane.setCenter(a);
    }


    public void showCoresPane(ActionEvent actionEvent) throws IOException {
        AnchorPane a = FXMLLoader.load(getClass().getResource("/view/TableCores.fxml"));
        mainPane.setCenter(a);
    }

    public void showMarcasPane(ActionEvent actionEvent) throws IOException {
        AnchorPane a = FXMLLoader.load(getClass().getResource("/view/TableMarcas.fxml"));
        mainPane.setCenter(a);
    }

    public void showModelosPane(ActionEvent actionEvent) throws IOException {
        AnchorPane a = FXMLLoader.load(getClass().getResource("/view/TableModelos.fxml"));
        mainPane.setCenter(a);
    }

    public void showServicosPane(ActionEvent actionEvent) throws IOException {
        AnchorPane a = FXMLLoader.load(getClass().getResource("/view/TableServicos.fxml"));
        mainPane.setCenter(a);
    }

    public void showOSForm(ActionEvent actionEvent) throws IOException {
        DialogFormService<OrdemDeServico> dialogFormService = new DialogFormService<>();
        OrdemDeServico os = new OrdemDeServico();
        dialogFormService.showAddFormAndWaitResponse("/view/FormOS.fxml", os, "Ordem de Serviço");
    }

}
