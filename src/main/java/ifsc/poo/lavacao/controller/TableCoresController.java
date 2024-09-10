package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.CorDAO;
import ifsc.poo.lavacao.model.domain.Cor;
import ifsc.poo.lavacao.model.domain.Marca;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class TableCoresController extends TableViewController<Cor, CorDAO> {

    public TableColumn<Marca, String> id;
    public TableColumn<Marca, String> nome;

    @Override
    protected CorDAO getDAO() {
        return new CorDAO();
    }

    @Override
    protected void onDoubleClickAction(Cor model) throws IOException {
        defaultDoubleClickAction(model);
    }

    @Override
    protected List<Cor> getTableData() {
        return dao.getAll();
    }

    @Override
    protected Cor getNewModelInstance() {
        return new Cor();
    }

    @Override
    protected String getFormDialogFXML() {
        return "/view/FormCor.fxml";
    }

    @Override
    protected void setTableCellFactory() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

}
