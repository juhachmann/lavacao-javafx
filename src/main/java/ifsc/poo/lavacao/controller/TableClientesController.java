
package ifsc.poo.lavacao.controller;

import java.io.IOException;
import java.util.List;

import ifsc.poo.lavacao.model.dao.ClienteDAO;
import ifsc.poo.lavacao.model.domain.Cliente;
import ifsc.poo.lavacao.model.domain.PessoaFisica;
import ifsc.poo.lavacao.model.domain.PessoaJuridica;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class TableClientesController extends TableViewController<Cliente, ClienteDAO> {

    @FXML
    public TextField buscaNome;
    public TextField buscaDoc;

    @FXML
    public TableColumn<Cliente, Integer> clienteId;
    public TableColumn<Cliente, String> clienteNome;
    public TableColumn<Cliente, String> clienteTipo;
    public TableColumn<Cliente, String> clienteDoc;
    public TableColumn<Cliente, String> telefone;
    public TableColumn<Cliente, String> email;


    @Override
    protected ClienteDAO getDAO() {
        return new ClienteDAO();
    }

    @Override
    protected List<Cliente> getTableData() {
        return dao.getAll();
    }

    @Override
    protected Cliente getNewModelInstance() {
        return null;
    }

    @Override
    protected String getFormDialogFXML() {
        return null;
    }

    @Override
    protected void onDoubleClickAction(Cliente model) throws IOException {
        boolean modelUpdated = service.showDialogAndWaitResponse("/view/DetailsClientes.fxml", model, "Detalhes de Cliente");
        if (modelUpdated)
            loadTable();
    }


    @Override
    protected void setTableCellFactory() {
        clienteId.setCellValueFactory(new PropertyValueFactory<>("id"));

        clienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        clienteTipo.setCellValueFactory(data -> {
            String tipo = data.getValue() instanceof PessoaFisica ? "Pessoa Física" : "Pessoa Jurídica";
            return new SimpleStringProperty(tipo);
        });

        clienteDoc.setCellValueFactory(data -> {
            Cliente c = data.getValue();
            String doc;
            if (c instanceof PessoaFisica pf) {
                doc = pf.getCpf();
            } else doc = ((PessoaJuridica) c).getCnpj();
            return new SimpleStringProperty(doc);
        });
        telefone.setCellValueFactory(new PropertyValueFactory<>("celular"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    @FXML
    private void handleBuscaCliente(ActionEvent event) {
        if(buscaNome.getText() != null & !buscaNome.getText().isBlank()) {
            List<Cliente> clientes = dao.getByName(buscaNome.getText());
            this.updateTableData(clientes);
        }
        else if(buscaDoc.getText() != null & !buscaDoc.getText().isBlank()){
            List<Cliente> clientes = dao.getByDocument(buscaDoc.getText());
            this.updateTableData(clientes);
        }
        else {
            updateTableData(dao.getAll());
        }
    }


    @FXML
    private void showCadastroPessoaFisica(ActionEvent actionEvent) throws IOException {
        PessoaFisica cliente = new PessoaFisica();
        FormController<Cliente, ?> controller = service.showAddFormAndWaitResponse("/view/FormPessoaFisica.fxml", cliente, "Cadastro de Pessoa Física");
        if(controller.isModelUpdated())
            loadTable();
    }

    @FXML
    private void showCadastroPessoaJuridica(ActionEvent actionEvent) throws IOException {
        PessoaJuridica cliente = new PessoaJuridica();
        FormController<Cliente, ?> controller = service.showAddFormAndWaitResponse("/view/FormPessoaJuridica.fxml", cliente, "Cadastro de Pessoa Jurídica");
        if(controller.isModelUpdated())
            loadTable();
    }


}
