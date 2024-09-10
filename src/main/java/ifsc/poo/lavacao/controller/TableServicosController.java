package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.ServicoDAO;
import ifsc.poo.lavacao.model.domain.Servico;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class TableServicosController extends TableViewController<Servico, ServicoDAO> {

    public TableColumn<Servico, String> id;
    public TableColumn<Servico, String> descricao;
    public TableColumn<Servico, String> valor;
    public TableColumn<Servico, String> categoria;


    @Override
    protected ServicoDAO getDAO() {
        return new ServicoDAO();
    }

    @Override
    protected List<Servico> getTableData() {
        return dao.getAll();
    }

    @Override
    protected Servico getNewModelInstance() {
        return new Servico();
    }

    @Override
    protected String getFormDialogFXML() {
        return "/view/FormServico.fxml";
    }

    @Override
    protected void onDoubleClickAction(Servico model) throws IOException {
        defaultDoubleClickAction(model);
    }

    @Override
    protected void setTableCellFactory() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        valor.setCellValueFactory(data -> {
            var valor = data.getValue().getValor();
            return new SimpleStringProperty("R$ %.2f".formatted(valor));
        });

    }

}
