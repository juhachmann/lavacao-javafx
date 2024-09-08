
package ifsc.poo.lavacao.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import ifsc.poo.lavacao.model.dao.ClienteDAO;
import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.Cliente;
import ifsc.poo.lavacao.model.domain.PessoaFisica;
import ifsc.poo.lavacao.model.domain.PessoaJuridica;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ClientesController implements Initializable {

    @FXML
    public TextField buscaNome;
    public TextField buscaCPF;
    public TextField buscaCNPJ;

    @FXML
    private TableView<Cliente> tableClientes;
    public TableColumn<Cliente, Integer> clienteId;
    public TableColumn<Cliente, String> clienteNome;
    public TableColumn<Cliente, String> clienteTipo;
    public TableColumn<Cliente, String> clienteDoc;

    private List<Cliente> listaClientes;
    private ObservableList<Cliente> observableListClientes;
    public Cliente clienteSelecionado;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private Connection connection;
    private final ClienteDAO clienteDAO = new ClienteDAO();


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tableClientes.setRowFactory(table -> {
            TableRow<Cliente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        selecionarCliente(row.getItem());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row ;
        });

        setTableCellFactory();
        loadTable();

    }

    private void loadTable() {
        connection = database.conectar();
        loadTable(connection);
    }

    private void loadTable(Connection conn) {
        clienteDAO.setConnection(connection);
        listaClientes = clienteDAO.getAll();

        observableListClientes = FXCollections.observableArrayList(listaClientes);
        tableClientes.setItems(observableListClientes);
        database.desconectar(connection);
    }

    private void setTableCellFactory() {
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
    }


    private void selecionarCliente(Cliente cliente) throws IOException {
        this.clienteSelecionado = cliente;
        showClientDetailsScreen();
    }


    @FXML
    private void handleBuscaCliente(ActionEvent event) {
    }


    @FXML
    private void showCadastroPessoaFisica(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/FormPessoaFisica.fxml"));
        AnchorPane page = loader.load();
        FormPessoaFisicaController controller = loader.getController();

        PessoaFisica cliente = new PessoaFisica();
        controller.setModel(cliente);

        showCadastroDialog(controller, page, cliente);
    }

    @FXML
    private void showCadastroPessoaJuridica(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/FormPessoaJuridica.fxml"));
        AnchorPane page = loader.load();

        FormPessoaJuridicaController controller = loader.getController();

        PessoaJuridica cliente = new PessoaJuridica();
        controller.setModel(cliente);

        showCadastroDialog(controller, page, cliente);

    }


    private void showCadastroDialog(DialogController<?> controller, Pane page, Cliente cliente) throws IOException {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registrar Cliente");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //enviando o obejto OS para o controller do dialog
        controller.setStage(dialogStage);

        //apresenta o diálogo e aguarda a confirmação do usuário
        dialogStage.showAndWait();

        if (controller.isBtnConfirmarClicked())
            saveClienteAndReloadTable(cliente);
    }


    private void saveClienteAndReloadTable(Cliente cliente) {
        connection = database.conectar();
        clienteDAO.setConnection(connection);
        clienteDAO.create(cliente);
        loadTable(connection);
    }


    public void buscarCliente(ActionEvent actionEvent) {
        // TODO Buscar Cliente
    }


    private void showClientDetailsScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ClientesDetails.fxml"));
        AnchorPane page = loader.load();
        ClientsDetailsController controller = loader.getController();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Detalhes de Cliente");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        controller.setStage(dialogStage);
        controller.setCliente(clienteSelecionado);

        //apresenta o diálogo e aguarda a confirmação do usuário
        dialogStage.showAndWait();

//        if (controller.isBtnConfirmarClicked())
//            saveClienteAndReloadTable(cliente);
        // Ações que vai ter
        // Excluir => exclui
        // Editar => Fecha um dialog e abre outro, passando os valores do cliente
        // E aí pensar em qual é o tipo de ação, se é atualizar ou editar

    }

}
