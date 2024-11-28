
package ifsc.poo.lavacao.controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import ifsc.poo.lavacao.Main;
import ifsc.poo.lavacao.model.domain.OrdemDeServico;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;


public class MainController implements Initializable {

    public BorderPane mainPane;
    public Label loggedUser;
    public FontIcon maximizeIcon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        maximizeIcon.setIconLiteral("mdi2w-window-restore");

        this.loggedUser.setText("Usuário: " + Main.loggedUser.getName() +
                " | Logado desde: " + Main.loggedUser.getLoginDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
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

    public void minimize(MouseEvent mouseEvent) {
        FontIcon eventSource = (FontIcon) mouseEvent.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setIconified(true);
    }

    public void maximize(MouseEvent mouseEvent) {
        FontIcon eventSource = (FontIcon) mouseEvent.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        if(stage.isMaximized())
            maximizeIcon.setIconLiteral("mdi2w-window-maximize");
        else
            maximizeIcon.setIconLiteral("mdi2w-window-restore");
        stage.setMaximized(!stage.isMaximized());
    }

    public void exit(MouseEvent mouseEvent) {
        FontIcon eventSource = (FontIcon) mouseEvent.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.close();
    }
}
