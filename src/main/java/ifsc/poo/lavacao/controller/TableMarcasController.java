package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.MarcaDAO;
import ifsc.poo.lavacao.model.domain.Marca;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class TableMarcasController extends TableViewController<Marca, MarcaDAO> {

    public TableColumn<Marca, String> id;
    public TableColumn<Marca, String> nome;

    @Override
    protected MarcaDAO getDAO() {
        return new MarcaDAO();
    }

    @Override
    protected void onDoubleClickAction(Marca model) throws IOException {
        defaultDoubleClickAction(model);
    }

    @Override
    protected List<Marca> getTableData() {
        return dao.getAll();
    }

    @Override
    protected Marca getNewModelInstance() {
        return new Marca();
    }

    @Override
    protected String getFormDialogFXML() {
        return "/view/FormMarca.fxml";
    }

    @Override
    protected void setTableCellFactory() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

}
