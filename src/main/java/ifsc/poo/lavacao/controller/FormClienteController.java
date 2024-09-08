package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.domain.Cliente;
import ifsc.poo.lavacao.utils.AlertDialog;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class FormClienteController<T extends Cliente> implements Initializable {

    protected Stage dialogStage;
    protected T cliente;
    protected boolean btnConfirmarClicked;

    protected abstract boolean validarDados();
    protected abstract void onInitialize();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        onInitialize();
    }

    public void handleSalvar(ActionEvent event) {
        boolean dadosValidos = validarDados();
        if(dadosValidos) {
            this.btnConfirmarClicked = true;
            dialogStage.close();
        } else {
            AlertDialog.exceptionMessage(new Exception("Há dados inválidos no seu formulário! Corrija e tente novamente."));
        }
    }

    public void handleCancelar(ActionEvent event) {
        dialogStage.close();
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void setCliente(T cliente) {
        this.cliente = cliente;
    }

    public boolean isBtnConfirmarClicked() {
        return this.btnConfirmarClicked;
    }

}
