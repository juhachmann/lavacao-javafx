/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ifsc.poo.lavacao.controller;

import java.net.URL;
import java.util.ResourceBundle;

import ifsc.poo.lavacao.model.domain.OrdemDeServico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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


    Stage dialogStage;
    OrdemDeServico os;
    boolean btnConfirmarClicked;
    boolean isStatusUpdated;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void handleAdicionarItem(ActionEvent event) {
        // Adicionar item na lista da OS
    }


    @FXML
    private void handleStatusChange(ActionEvent event) {
        // Este é para o caso de editar
        // Setar que é para atualizar o status da venda
    }


    @FXML
    private void handleCancelar(ActionEvent event) {
        // Fechar Dialog
        dialogStage.close();
    }


    @FXML
    private void handleSalvar(ActionEvent event) {
        // Validar Dados
        // BtnClicked
        // Fechar Dialog
        dialogStage.close();
    }


    public void setDialogStage(Stage dialog) {
        this.dialogStage = dialog;
    }


    public void setOS(OrdemDeServico os) {
        this.os = os;
    }


    public boolean isBtConfirmarClicked() {
        return btnConfirmarClicked;
    }


    public boolean isStatusUpdated() {
        return isStatusUpdated;
    }


}
