package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.ACrudDAO;
import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public abstract class TableViewController<T, U extends ACrudDAO<T>> implements Initializable {

    @FXML
    private TableView<T> tableView;

    protected final DialogFormService<T> service = new DialogFormService<>();

    private final Database db = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = db.conectar();

    protected U dao;

    protected abstract U getDAO();
    protected abstract void onDoubleClickAction(T model) throws IOException;
    protected abstract void setTableCellFactory();
    protected abstract List<T> getTableData();
    protected abstract T getNewModelInstance();
    protected abstract String getFormDialogFXML();


    @Override
    public final void initialize(URL url, ResourceBundle rb) {
        this.dao = getDAO();
        dao.setConnection(connection);
        addDoubleClickListener();
        setTableCellFactory();
        loadTable();
    }

    private void addDoubleClickListener() {
        tableView.setRowFactory(table -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        T model = row.getItem();
                        onDoubleClickAction(model);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row ;
        });
    }

    protected final void loadTable() {
        List<T> tableData = getTableData();
        updateTableData(tableData);
    }

    @FXML
    protected void handleAdicionar(ActionEvent actionEvent) throws IOException {
        T model = getNewModelInstance();
        String fxml = getFormDialogFXML();
        FormController<T, ?> controller = service.showAddFormAndWaitResponse(fxml, model, "Novo Cadastro");
        if(controller.isModelUpdated() || controller.isModelDeleted())
            loadTable();
    }


    protected final void defaultDoubleClickAction(T model) throws IOException {
        String fxml = getFormDialogFXML();
        System.out.println("Aqui no double click");
        System.out.println(model.toString());
        //System.out.println();
        FormController<T, ?> controller = service.showEditFormAndWaitResponse(fxml, model, "Ver Cadastro");
        if(controller.isModelUpdated() || controller.isModelDeleted())
            loadTable();
    }

    protected void updateTableData(List<T> model) {
        ObservableList<T> observableList = FXCollections.observableArrayList(model);
        tableView.setItems(observableList);
    }
}
