
package ifsc.poo.lavacao.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ifsc.poo.lavacao.model.dao.CorDAO;
import ifsc.poo.lavacao.model.dao.MarcaDAO;
import ifsc.poo.lavacao.model.dao.ModeloDAO;
import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import ifsc.poo.lavacao.model.domain.Cor;
import ifsc.poo.lavacao.model.domain.Marca;
import ifsc.poo.lavacao.model.domain.Modelo;
import ifsc.poo.lavacao.model.domain.Veiculo;
import ifsc.poo.lavacao.utils.AlertDialog;
import ifsc.poo.lavacao.utils.ValidacaoDeFormularioHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class FormVeiculoController implements Initializable {


    @FXML
    private TextField inputPlaca;
    @FXML
    private TextField inputObservacoes;
    @FXML
    private ComboBox<Cor> selectCor;
    @FXML
    private ComboBox<Modelo> selectModelo;
    public ComboBox<Marca> selectMarca;


    // Cores
    // Ei, isto podiam ser serviços!
    List<Cor> listaCores = new ArrayList<>();
    ObservableList<Cor> observableListCores;
    CorDAO corDAO = new CorDAO();

    // Modelos
    List<Modelo> listaModelos = new ArrayList<>();
    ObservableList<Modelo> observableListModelos;
    ModeloDAO modeloDAO = new ModeloDAO();

    List<Marca> listaMarcas = new ArrayList<>();
    ObservableList<Marca> observableListMarcas;
    MarcaDAO marcaDAO = new MarcaDAO();

    Database db = DatabaseFactory.getDatabase("mysql");
    Connection conn;

    Veiculo veiculo;
    Stage stage;
    boolean btnConfirmarClicked;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMarcas();
        loadCores();

        selectModelo.setConverter(new StringConverter<Modelo>() {
            @Override
            public String toString(Modelo modelo) {
                if (modelo != null)
                    return modelo.getDescricao();
                return null;
            }

            @Override
            public Modelo fromString(String string) {
                return selectModelo.getItems().stream().filter(modelo ->
                        modelo.getDescricao().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void loadMarcas () {
        conn = db.conectar();
        marcaDAO.setConnection(conn);
        listaMarcas = marcaDAO.getAll();
        db.desconectar(conn);
        observableListMarcas = FXCollections.observableArrayList(listaMarcas);
        selectMarca.setItems(observableListMarcas);

        // Que inferno de solução
        // https://stackoverflow.com/questions/41634789/javafx-combobox-display-text-but-return-id-on-selection
        selectMarca.setConverter(new StringConverter<>() {
            @Override
            public String toString(Marca marca) {
                if(marca != null)
                    return marca.getNome();
                return null;
            }

            @Override
            public Marca fromString(String string) {
                return selectMarca.getValue();
            }
        });

    }

    private void loadCores () {
        conn = db.conectar();
        corDAO.setConnection(conn);
        listaCores = corDAO.getAll();
        db.desconectar(conn);
        observableListCores = FXCollections.observableArrayList(listaCores);
        selectCor.setItems(observableListCores);

        // Que inferno de solução
        // https://stackoverflow.com/questions/41634789/javafx-combobox-display-text-but-return-id-on-selection
        selectCor.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cor cor) {
                if(cor != null)
                    return cor.getNome();
                return null;
            }

            @Override
            public Cor fromString(String string) {
                return selectCor.getItems().stream().filter(cor ->
                        cor.getNome().equals(string)).findFirst().orElse(null);
            }
        });

    }

    private void loadModelos(Marca marca) {
        conn = db.conectar();
        modeloDAO.setConnection(conn);
        listaModelos = modeloDAO.getByMarca(marca);
        db.desconectar(conn);
        observableListModelos = FXCollections.observableArrayList(listaModelos);
        selectModelo.setItems(observableListModelos);
    }
    
    @FXML
    private void handleAdicionarModelo(ActionEvent event) {
        // Abrir Dialog
    }

    @FXML
    private void handleAdicionarCor(ActionEvent event) {
        // Abrir Dialog
    }

    public void handleAdicionarMarca(ActionEvent actionEvent) {
        // Abrir Dialog
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        stage.close();
    }

    @FXML
    private void handleSalvar(ActionEvent event) {
        if(dadosValidos()) {
            veiculo.setCor(selectCor.getValue());
            veiculo.setModelo(selectModelo.getValue());
            veiculo.setPlaca(inputPlaca.getText());
            veiculo.setObservacoes(inputObservacoes.getText());

            btnConfirmarClicked = true;
            stage.close();
        }
        else {
            AlertDialog.exceptionMessage(new Exception("Há dados inválidos no seu formulário. Corrija e tente novamente!"));
        }
    }

    private boolean dadosValidos() {
        return ValidacaoDeFormularioHelper.validar(List.of(inputPlaca, inputObservacoes, selectCor, selectModelo));
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        loadVeiculoData();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadVeiculoData() {
        inputPlaca.setText(veiculo.getPlaca());
        inputObservacoes.setText(veiculo.getObservacoes());
        selectCor.setValue(veiculo.getCor());
        selectModelo.setValue(veiculo.getModelo());
    }

    public boolean isBtnConfirmarClicked() {
        return btnConfirmarClicked;
    }

    public void handleMarcaSelected(ActionEvent actionEvent) {
        Marca marca = selectMarca.getValue();
        if(marca != null) {
            loadModelos(marca);
        }
    }

}
