package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.ACrudDAO;
import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.utils.AlertDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public abstract class FormController<T, U extends ACrudDAO<T> > implements Initializable {

    protected final Database db = DatabaseFactory.getDatabase("mysql");
    protected final Connection connection = db.conectar();

    Stage stage;
    T model;
    U dao;

    public Button btnSalvar;
    public Button btnExcluir;
    public Button btnEditar;

    boolean editModel;
    boolean modelUpdated;
    boolean modelDeleted;

    protected abstract boolean validarForm();
    protected abstract boolean buildModel();
    protected abstract void populateFieldsFromModel();
    protected abstract void onInitializeAction();
    protected abstract U getDAO();


    @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        this.dao = getDAO();
        this.dao.setConnection(connection);
        if (this.btnEditar != null) {
            this.btnEditar.setVisible(false);
        }
        if (this.btnExcluir != null)
            this.btnExcluir.setVisible(false);
        onInitializeAction();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setModel(T model) {
        this.model = model;
        populateFieldsFromModel();
    }

    public T getModel() {
        return model;
    }

    public void setEditModel(boolean editModel) {
        this.editModel = editModel;
        if(editModel) {
            if(this.btnExcluir != null)
                this.btnExcluir.setVisible(true);
            this.btnSalvar.setText("Atualizar");
        }
    }

    public boolean isModelUpdated() {
        return modelUpdated;
    }


    public boolean isModelDeleted() {
        return modelDeleted;
    }


    @FXML
    private void handleSalvar(ActionEvent event) {
        boolean dadosValidos = validarForm();
        if(dadosValidos) {
            buildModel();
        } else {
            AlertDialog.exceptionMessage(new Exception("Há dados inválidos no seu formulário! Corrija e tente novamente."));
            return;
        }
        salvar();
        this.modelUpdated = true;
        stage.close();
    }

    protected void salvar() {
        T response;
        if (editModel) {
            response = dao.update(model);
        } else {
            response = dao.create(model);
        }
        updateModel(response);
    }

    protected void updateModel(T model) {
        this.model = model;
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        stage.close();
    }

    @FXML
    private void handleEditar(ActionEvent event) {
    }

    @FXML
    private void handleExcluir(ActionEvent event) {
        if(AlertDialog.confirmarExclusao("")) {
            dao.delete(model);
            this.modelDeleted = true;
            stage.close();
        }
    }

}
