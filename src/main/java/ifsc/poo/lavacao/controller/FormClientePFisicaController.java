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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author aluno
 */
public class FormClientePFisicaController implements Initializable {


    @FXML
    private TextField inputNome;
    @FXML
    private TextField inputCPF;
    @FXML
    private DatePicker inputDataNascimento;
    @FXML
    private TextField inputEmail;
    @FXML
    private TextField inputTelefone;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleSalvar(ActionEvent event) {
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
    }

}
