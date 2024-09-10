package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.ModeloDAO;
import ifsc.poo.lavacao.model.domain.Modelo;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class TableModelosController extends TableViewController<Modelo, ModeloDAO> {


    public TableColumn<Modelo, String> id;
    public TableColumn<Modelo, String> descricao;
    public TableColumn<Modelo, String> marca;
    public TableColumn<Modelo, String> categoria;
    public TableColumn<Modelo, String> potencia;
    public TableColumn<Modelo, String> combustivel;


    @Override
    protected ModeloDAO getDAO() {
        return new ModeloDAO();
    }

    @Override
    protected void onDoubleClickAction(Modelo model) throws IOException {
        defaultDoubleClickAction(model);
    }

    @Override
    protected List<Modelo> getTableData() {
        return dao.getAll();
    }

    @Override
    protected Modelo getNewModelInstance() {
        return new Modelo();
    }

    @Override
    protected String getFormDialogFXML() {
        return "/view/FormModelo.fxml";
    }

    @Override
    protected void setTableCellFactory() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        potencia.setCellValueFactory(data -> new SimpleStringProperty(
                String.valueOf(data.getValue().getMotor().getPotencia())
        ));
        combustivel.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getMotor().getTipoCombustivel().name()
        ));
        marca.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getMarca().getNome()
        ));

    }

}
