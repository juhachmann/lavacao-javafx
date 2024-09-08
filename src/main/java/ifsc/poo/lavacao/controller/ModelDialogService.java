package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.ACrudDAO;
import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public abstract class ModelDialogService<T extends ACrudDAO<U>, U> {

    Database database = DatabaseFactory.getDatabase("mysql");
    Connection connection;
    T dao;
    U model;
    List<U> lista;

    protected abstract boolean alreadyCreated(U model);
    protected abstract String getFXMLDialog();
    public abstract void setModel(U model);
    public abstract U getModel();

    public List<U> getAll() {
        connection = database.conectar();
        dao.setConnection(connection);
        lista = dao.getAll();
        database.desconectar(connection);
        return lista;
    }

    public void save(U model) {
        connection = database.conectar();
        dao.setConnection(connection);
        if(alreadyCreated(model))
            dao.update(model);
        else dao.create(model);
        database.desconectar(connection);
    }

    public void excluir(U model) {
        connection = database.conectar();
        dao.setConnection(connection);
        dao.delete(model);
        database.desconectar(connection);
    }

    public void showDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(getFXMLDialog()));
        AnchorPane page = loader.load();

        DialogController<U> controller = loader.getController();

        U model = getModel();
        controller.setModel(model);

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Novo Registro");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        controller.setStage(dialogStage);

        dialogStage.showAndWait();

        if (controller.isBtnConfirmarClicked())
            saveModelAndReloadTable(model);
    }

    private void saveModelAndReloadTable(U model) {
        connection = database.conectar();
        dao.setConnection(connection);
        if(alreadyCreated(model))
            dao.update(model);
        else dao.create(model);
        lista = dao.getAll();
        database.desconectar(connection);
    }

}
