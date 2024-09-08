package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.utils.AlertDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;


public abstract class DialogController<T> implements Initializable {

    Stage stage;
    T model;
    boolean btnConfirmarClicked;

    protected abstract void populateForm();
    protected abstract boolean validarForm();
    protected abstract boolean buildModel();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setModel(T model) {
        this.model = model;
        populateForm();
    }

    public boolean isBtnConfirmarClicked() {
        return btnConfirmarClicked;
    }

    @FXML
    private void handleSalvar(ActionEvent event) {
        boolean dadosValidos = validarForm();
        if(dadosValidos) {
            buildModel();
            this.btnConfirmarClicked = true;
            stage.close();
        } else {
            AlertDialog.exceptionMessage(new Exception("Há dados inválidos no seu formulário! Corrija e tente novamente."));
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        stage.close();
    }

}
