
package ifsc.poo.lavacao.controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import ifsc.poo.lavacao.Main;
import ifsc.poo.lavacao.model.domain.OrdemDeServico;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;


public class MainController implements Initializable {

    public BorderPane mainPane;
    public Label loggedUser;
    public FontIcon maximizeIcon;

    public MenuItem createOS;
    public MenuItem showAllOS;
    public MenuItem showAllClients;
    public MenuItem showAllColors;
    public MenuItem showAllBrands;
    public MenuItem showAllModels;
    public MenuItem showAllServices;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        maximizeIcon.setIconLiteral("mdi2w-window-restore");

        this.loggedUser.setText("Usuário: " + Main.loggedUser.getName() +
                " | Logado desde: " + Main.loggedUser.getLoginDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));


        createOS.setOnAction(this::showOSForm);
        showAllOS.setOnAction(this::showOSPane);
        showAllClients.setOnAction(this::showClientesPane);
        showAllColors.setOnAction(this::showCoresPane);
        showAllBrands.setOnAction(this::showMarcasPane);
        showAllModels.setOnAction(this::showModelosPane);
        showAllServices.setOnAction(this::showServicosPane);

        createOS.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN));
        showAllOS.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        showAllClients.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        showAllColors.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
        showAllBrands.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
        showAllModels.setAccelerator(new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN));
        showAllServices.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));

    }

    public void showOSPane(ActionEvent actionEvent) {
        AnchorPane a = null;
        try {
            a = FXMLLoader.load(getClass().getResource("/view/TableOS.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainPane.setCenter(a);
    }


    public void showClientesPane(ActionEvent actionEvent)  {
        try {
            AnchorPane a = FXMLLoader.load(getClass().getResource("/view/TableClientes.fxml"));
            mainPane.setCenter(a);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void showCoresPane(ActionEvent actionEvent)  {
        try {
            AnchorPane a = FXMLLoader.load(getClass().getResource("/view/TableCores.fxml"));
            mainPane.setCenter(a);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showMarcasPane(ActionEvent actionEvent) {
        try {
            AnchorPane a = FXMLLoader.load(getClass().getResource("/view/TableMarcas.fxml"));
            mainPane.setCenter(a);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showModelosPane(ActionEvent actionEvent) {
        try {
            AnchorPane a = FXMLLoader.load(getClass().getResource("/view/TableModelos.fxml"));
            mainPane.setCenter(a);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showServicosPane(ActionEvent actionEvent) {
        try {
            AnchorPane a = FXMLLoader.load(getClass().getResource("/view/TableServicos.fxml"));
            mainPane.setCenter(a);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showOSForm(ActionEvent actionEvent) {
        DialogFormService<OrdemDeServico> dialogFormService = new DialogFormService<>();
        OrdemDeServico os = new OrdemDeServico();
        try {
            dialogFormService.showAddFormAndWaitResponse("/view/FormOS.fxml", os, "Ordem de Serviço");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
