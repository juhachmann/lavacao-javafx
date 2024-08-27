/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ifsc.poo.lavacao.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author aluno
 */
public class ClientesController implements Initializable {


    @FXML
    private TableView tableClientes;
    @FXML
    private TextField inputBuscaCliente;
    @FXML
    private TableView tableVeiculosCliente;
    @FXML
    private Button buttonEditarVeiculo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleButtonAdicionarVeiculo(ActionEvent event) {
    }

    @FXML
    private void handleEditarVeiculo(ActionEvent event) {
    }

    @FXML
    private void handleBuscaCliente(ActionEvent event) {
    }

    @Deprecated
    public void handleVerOS(ActionEvent actionEvent) {
    }
}
