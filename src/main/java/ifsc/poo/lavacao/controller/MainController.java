
package ifsc.poo.lavacao.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
        AnchorPane a = FXMLLoader.load(getClass().getResource("/view/OSMain.fxml"));
        mainPane.setCenter(a);
    }


    public void showClientesPane(ActionEvent actionEvent) throws IOException {
        AnchorPane a = FXMLLoader.load(getClass().getResource("/view/ClientesMain.fxml"));
        mainPane.setCenter(a);
    }


    public void showCadastrosPane(ActionEvent actionEvent) throws IOException {

    }


    public void showConfigPane(ActionEvent actionEvent) throws IOException {

    }

}
