package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.VeiculoDAO;
import ifsc.poo.lavacao.model.domain.Cliente;
import ifsc.poo.lavacao.model.domain.Veiculo;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class TableVeiculosController
        extends TableViewController<Veiculo, VeiculoDAO>
        implements ITableVeiculo {

    public TableColumn<Veiculo, String> placa;
    public TableColumn<Veiculo, String> modelo;
    public TableColumn<Veiculo, String> marca;
    public TableColumn<Veiculo, String> cor;
    public TableColumn<Veiculo, String> categoria;
    public TableColumn<Veiculo, String> observacao;

    private Cliente cliente;

    @Override
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        loadTable();
    }

    @Override
    protected VeiculoDAO getDAO() {
        return new VeiculoDAO();
    }

    @Override
    protected void onDoubleClickAction(Veiculo model) throws IOException {
        defaultDoubleClickAction(model);
    }

    @Override
    protected void setTableCellFactory() {
        placa.setCellValueFactory(new PropertyValueFactory<>("placa"));
        modelo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getModelo().getDescricao())
        );
        marca.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getModelo().getMarca().getNome())
        );
        cor.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getCor().getNome())
        );
        categoria.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getModelo().getCategoria().name())
        );
        observacao.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
    }

    @Override
    protected List<Veiculo> getTableData() {
        System.out.println(this.cliente);
        if(this.cliente != null)
            return dao.getByCliente(cliente);
        return List.of();
    }

    @Override
    protected Veiculo getNewModelInstance() {
        return new Veiculo();
    }

    @Override
    protected String getFormDialogFXML() {
        return "/view/FormVeiculo.fxml";
    }

    @FXML
    @Override
    protected void handleAdicionar(ActionEvent actionEvent) throws IOException {
        System.out.println("Entrei aqui???");
        Veiculo newVeiculo = getNewModelInstance();
        newVeiculo.setCliente(cliente);
        String fxml = getFormDialogFXML();
        FormController<Veiculo, ?> controller = service.showAddFormAndWaitResponse(fxml, newVeiculo, "Novo Cadastro");
        if(controller.isModelUpdated() || controller.isModelDeleted())
            loadTable();
    }

}
