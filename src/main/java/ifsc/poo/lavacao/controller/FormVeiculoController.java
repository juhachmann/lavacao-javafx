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
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author aluno
 */
public class FormVeiculoController implements Initializable {


    @FXML
    private TextField inputPlaca;
    @FXML
    private TextField inputObservacoes;
    @FXML
    private ComboBox<?> selectCor;
    @FXML
    private ComboBox<?> selectModelo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleAdicionarModelo(ActionEvent event) {
    }

    @FXML
    private void handleAdicionarCor(ActionEvent event) {
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
    }

    @FXML
    private void handleSalvar(ActionEvent event) {
    }

}
