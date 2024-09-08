package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.VeiculoDAO;
import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.Cliente;
import ifsc.poo.lavacao.model.domain.PessoaFisica;
import ifsc.poo.lavacao.model.domain.PessoaJuridica;
import ifsc.poo.lavacao.model.domain.Veiculo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class ClientsDetailsController implements Initializable {

    public Label clienteId;
    public Label clienteTipo;
    public Label docLabel;
    public Label outroLabel;
    public Label nome;
    public Label celular;
    public Label email;
    public Label outro;
    public Label doc;
    public Label pontuacao;
    public Label dataCadastro;

    public TableView<Veiculo> veiculos;
    public TableColumn<Veiculo, String> placa;
    public TableColumn<Veiculo, String> modelo;
    public TableColumn<Veiculo, String> marca;
    public TableColumn<Veiculo, String> cor;
    public TableColumn<Veiculo, String> categoria;

    Cliente cliente;
    Stage stage;

    Database db = DatabaseFactory.getDatabase("mysql");
    VeiculoDAO veiculoDAO = new VeiculoDAO();
    List<Veiculo> veiculosList;
    ObservableList<Veiculo> observableListVeiculos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        populateLabels();
        setTableCellFactory();
        Connection conn = db.conectar();
        loadTable(conn);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void populateLabels() {
        clienteId.setText(String.valueOf(cliente.getId()));
        clienteTipo.setText(cliente instanceof PessoaFisica ? "Pessoa Física" : "Pessoa Jurídica");
        nome.setText(cliente.getNome());
        celular.setText(cliente.getCelular());
        email.setText(cliente.getEmail());
        dataCadastro.setText(cliente.getDataCadastro().toString());
        if(cliente instanceof PessoaFisica pf) {
            clienteTipo.setText("Pessoa Física");
            docLabel.setText("CPF:");
            doc.setText(pf.getCpf());
            outroLabel.setText("Data de nascimento:");
            outro.setText(pf.getDataNascimento().toString());
        } else {
            clienteTipo.setText("Pessoa Jurídica");
            docLabel.setText("CNPJ:");
            outroLabel.setText("Inscrição Estadual:");
            doc.setText(((PessoaJuridica) cliente).getCnpj());
            outro.setText(((PessoaJuridica) cliente).getInscricaoEstadual());
        }
    }

    public void showCadastroVeiculo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/FormVeiculo.fxml"));
        AnchorPane page = loader.load();

        FormVeiculoController controller = loader.getController();

        Veiculo veiculo = new Veiculo();
        veiculo.setCliente(cliente);
        controller.setVeiculo(veiculo);

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registrar Veículo");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        controller.setStage(dialogStage);

        dialogStage.showAndWait();

        if (controller.isBtnConfirmarClicked())
            saveVeiculoAndReloadTable(veiculo);

    }


    private void saveVeiculoAndReloadTable(Veiculo veiculo) {
        Connection conn = db.conectar();
        veiculoDAO.setConnection(conn);
        if(veiculo.getId() == 0)
            veiculoDAO.create(veiculo);
        else
            veiculoDAO.update(veiculo);

        loadTable(conn);
    }

    private void loadTable(Connection conn) {
        veiculoDAO.setConnection(conn);
        veiculosList = veiculoDAO.getByCliente(cliente);

        observableListVeiculos = FXCollections.observableArrayList(veiculosList);
        veiculos.setItems(observableListVeiculos);
        db.desconectar(conn);
    }


    private void setTableCellFactory() {
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

    }



}
