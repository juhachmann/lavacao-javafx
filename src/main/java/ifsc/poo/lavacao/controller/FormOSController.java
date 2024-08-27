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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author aluno
 */
public class FormOSController implements Initializable {


    @FXML
    private ComboBox<?> selectCliente;
    @FXML
    private ComboBox<?> selectVeiculo;
    @FXML
    private TextField inputData;
    @FXML
    private TableView<?> tableItens;
    @FXML
    private TextField inputDesconto;
    @FXML
    private TextField inputTotal;
    @FXML
    private ComboBox<?> selectStatus;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleAdicionarItem(ActionEvent event) {
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
    }

    @FXML
    private void handleSalvar(ActionEvent event) {
    }

    @FXML
    private void handleStatusChange(ActionEvent event) {
    }

}
