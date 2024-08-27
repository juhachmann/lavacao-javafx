/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ifsc.poo.lavacao.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author aluno
 */
public class MainController implements Initializable {

    public BorderPane borderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleMenuAdicionarOS(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FormOS.fxml"));
        this.borderPane.setCenter(anchorPane);
    }

    @FXML
    private void handleMenuOSHistorico(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/OS.fxml"));
        this.borderPane.setCenter(anchorPane);
    }

    @FXML
    private void handleMenuClientesAdicionarPF(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FormClientePFisica.fxml"));
        this.borderPane.setCenter(anchorPane);
    }

    @FXML
    private void handleMenuClientesAdicionarPJ(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FormClientePJuridica.fxml"));
        this.borderPane.setCenter(anchorPane);
    }

    @FXML
    private void handleMenuClientesConsultar(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Clientes.fxml"));
        this.borderPane.setCenter(anchorPane);
    }

    @FXML
    private void handleMenuAdminPontuacao(ActionEvent event) throws IOException {
 //       AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FormOS.fxml"));
 //       this.borderPane.setCenter(anchorPane);
    }

    @FXML
    private void handleMenuAdminServicos(ActionEvent event) throws IOException {
 //       AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FormOS.fxml"));
 //       this.borderPane.setCenter(anchorPane);
    }

    @FXML
    private void handleMenuAdminCategorias(ActionEvent event) throws IOException {
 //       AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FormOS.fxml"));
 //       this.borderPane.setCenter(anchorPane);
    }

    @FXML
    private void handleMenuAdminOutros(ActionEvent event) throws IOException {
//        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FormOS.fxml"));
//        this.borderPane.setCenter(anchorPane);
    }

}
