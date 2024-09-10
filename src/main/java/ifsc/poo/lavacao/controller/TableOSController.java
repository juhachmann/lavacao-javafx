package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.OsDAO;
import ifsc.poo.lavacao.model.domain.OrdemDeServico;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class TableOSController extends TableViewController<OrdemDeServico, OsDAO> {

    public TableColumn<OrdemDeServico, String> id;
    public TableColumn<OrdemDeServico, String> veiculo;
    public TableColumn<OrdemDeServico, String> data;
    public TableColumn<OrdemDeServico, String> total;
    public TableColumn<OrdemDeServico, String> status;

    public TextField buscaCliente;
    public TextField buscaPlaca;
    public DatePicker buscaData;

    @Override
    protected OsDAO getDAO() {
        return new OsDAO();
    }

    @Override
    protected void onDoubleClickAction(OrdemDeServico model) throws IOException {
        defaultDoubleClickAction(model);
    }

    @Override
    protected void setTableCellFactory() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        data.setCellValueFactory(new PropertyValueFactory<>("agenda"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        total.setCellValueFactory(data -> {
            var total = data.getValue().getTotal();
            System.out.println(total);
            return new SimpleStringProperty(String.format("R$ %.2f", total));
        });

        veiculo.setCellValueFactory(data -> {
            System.out.println(data.getValue().getVeiculo().getPlaca());
            return new SimpleStringProperty(
            data.getValue().getVeiculo().getPlaca());
        });

    }

    @Override
    protected List<OrdemDeServico> getTableData() {
        return dao.getAll();
    }

    @Override
    protected OrdemDeServico getNewModelInstance() {
        return new OrdemDeServico();
    }

    @Override
    protected String getFormDialogFXML() {
        return "/view/FormOS.fxml";
    }

    public void buscarOS(ActionEvent actionEvent) {
        if(buscaCliente.getText() != null & !buscaCliente.getText().isBlank()) {
            updateTableData(dao.getByCliente(buscaCliente.getText()));
        }
        else if(buscaPlaca.getText() != null & !buscaPlaca.getText().isBlank()) {
            updateTableData(dao.getByPlaca(buscaPlaca.getText()));
        }
        else if(buscaData.getValue() != null) {
            updateTableData(dao.getByDate(buscaData.getValue()));
        }
        else {
            updateTableData(dao.getAll());
        }
    }

}
