package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.ClienteDAO;
import ifsc.poo.lavacao.model.domain.Cliente;
import ifsc.poo.lavacao.model.domain.PessoaFisica;
import ifsc.poo.lavacao.model.domain.PessoaJuridica;
import ifsc.poo.lavacao.model.domain.Veiculo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DetailsClientesController extends FormController<Cliente, ClienteDAO> {

    public TextField id;
    public TextField tipo;
    public Label docLabel;
    public Label outroLabel;
    public TextField nome;
    public TextField celular;
    public TextField email;
    public TextField outro;
    public TextField doc;
    public TextField pontuacao;
    public TextField dataCadastro;

    public TitledPane paneVeiculos;

    DialogFormService<Cliente> clienteService = new DialogFormService<>();

    @Override
    protected boolean validarForm() {
        return true;
    }

    @Override
    protected boolean buildModel() {
        return true;
    }

    @Override
    protected void populateFieldsFromModel() {
        id.setText(String.valueOf(model.getId()));
        nome.setText(model.getNome());
        celular.setText(model.getCelular());
        email.setText(model.getEmail());
        dataCadastro.setText(model.getDataCadastro().toString());
        if(model instanceof PessoaFisica pf) {
            tipo.setText("Pessoa Física");
            docLabel.setText("CPF:");
            doc.setText(pf.getCpf());
            outroLabel.setText("Data de nascimento:");
            outro.setText(pf.getDataNascimento().toString());
        } else {
            tipo.setText("Pessoa Jurídica");
            docLabel.setText("CNPJ:");
            outroLabel.setText("Inscrição Estadual:");
            doc.setText(((PessoaJuridica) model).getCnpj());
            outro.setText(((PessoaJuridica) model).getInscricaoEstadual());
        }
    }

    @Override
    protected void onInitializeAction() {
    }

    @Override
    public void setModel(Cliente c) {
        this.model = c;
        populateFieldsFromModel();
        loadTableVeiculoPane();
    }

    private void loadTableVeiculoPane()  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/TableVeiculos.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        ITableVeiculo controller = loader.getController();
        controller.setCliente(model);

        paneVeiculos.setContent(pane);
    }

    @Override
    protected ClienteDAO getDAO() {
        return new ClienteDAO();
    }


    @FXML
    private void handleEditar(ActionEvent event) throws IOException {
        String fxml = this.model instanceof PessoaFisica
                ? "/view/FormPessoaFisica.fxml"
                : "/view/FormPessoaJuridica.fxml";
        var response = clienteService.showEditFormAndWaitResponse(fxml, this.model,
                "Atualizar Registro");

        if(response.isModelUpdated()) {
            this.model = response.getModel();
            populateFieldsFromModel();
            this.modelUpdated = true;
            return;
        }

        if(response.isModelDeleted()) {
            this.modelDeleted = true;
            stage.close();
        }
    }


}
