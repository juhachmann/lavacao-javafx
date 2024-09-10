
package ifsc.poo.lavacao.controller;

import java.io.IOException;
import java.util.List;

import ifsc.poo.lavacao.model.dao.VeiculoDAO;
import ifsc.poo.lavacao.model.domain.Cor;
import ifsc.poo.lavacao.model.domain.Marca;
import ifsc.poo.lavacao.model.domain.Modelo;
import ifsc.poo.lavacao.model.domain.Veiculo;
import ifsc.poo.lavacao.service.CorService;
import ifsc.poo.lavacao.service.MarcaService;
import ifsc.poo.lavacao.service.ModeloService;
import ifsc.poo.lavacao.utils.ValidacaoDeFormularioHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;


public class FormVeiculoController extends FormController<Veiculo, VeiculoDAO> {

    @FXML
    private Button btnAdicionarModelo;
    @FXML
    private TextField inputPlaca;
    @FXML
    private TextField inputObservacoes;
    @FXML
    private ComboBox<Cor> selectCor;
    @FXML
    private ComboBox<Modelo> selectModelo;
    @FXML
    private ComboBox<Marca> selectMarca;

    CorService corService = new CorService();
    MarcaService marcaService = new MarcaService();
    ModeloService modeloService = new ModeloService();

    ObservableList<Modelo> observableListModelo;

    @Override
    protected void onInitializeAction() {
        loadCores();
        loadMarcas();
        loadModelos();
        setComboBoxConvertersForCor();
        setComboBoxConvertersForMarca();
        setComboBoxConvertersForModelo();
    }

    @Override
    protected VeiculoDAO getDAO() {
        return new VeiculoDAO();
    }

    private void loadMarcas () {
        List<Marca> listaMarcas = marcaService.getAll();
        ObservableList<Marca> observableListMarcas = FXCollections.observableArrayList(listaMarcas);
        selectMarca.setItems(observableListMarcas);
    }

    private void loadCores () {
        List<Cor> listaCores = corService.getAll();
        ObservableList<Cor> observableListCores = FXCollections.observableArrayList(listaCores);
        selectCor.setItems(observableListCores);
    }

    private void loadModelos() {
        if(selectMarca.getValue() == null) {
            disableModelos();
            return;
        }
        enabelModelos();
        List<Modelo> listaModelos = modeloService.getByMarca(selectMarca.getValue());
        observableListModelo = FXCollections.observableArrayList(listaModelos);
        selectModelo.setItems(observableListModelo);
    }

    private void disableModelos() {
        btnAdicionarModelo.setDisable(true);
        selectModelo.setDisable(true);
    }

    private void enabelModelos() {
        btnAdicionarModelo.setDisable(false);
        selectModelo.setDisable(false);
    }


    // Que inferno de solução
    // https://stackoverflow.com/questions/41634789/javafx-combobox-display-text-but-return-id-on-selection
    private void setComboBoxConvertersForModelo() {
        selectModelo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Modelo modelo) {
                if (modelo != null)
                    return modelo.getDescricao();
                return null;
            }

            @Override
            public Modelo fromString(String string) {
                return selectModelo.getValue();
            }
        });
    }

    private void setComboBoxConvertersForMarca() {
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

    private void setComboBoxConvertersForCor() {
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


    @Override
    protected void populateFieldsFromModel() {
        inputPlaca.setText(model.getPlaca());
        inputObservacoes.setText(model.getObservacoes());
        selectCor.setValue(model.getCor());
        if(model.getModelo() != null)
            selectMarca.setValue(model.getModelo().getMarca());
        if(selectMarca.getValue() != null) {
            loadModelos();
            selectModelo.setValue(model.getModelo());
        }
    }


    @Override
    protected boolean validarForm() {
        return ValidacaoDeFormularioHelper.validar(
                List.of(inputPlaca, inputObservacoes, selectCor, selectModelo));
    }

    @Override
    protected boolean buildModel() {
        model.setPlaca(inputPlaca.getText());
        model.setObservacoes(inputObservacoes.getText());
        model.setModelo(selectModelo.getValue());
        model.setCor(selectCor.getValue());
        return true;
    }

    @FXML
    private void handleMarcaSelected(ActionEvent actionEvent) {
        selectModelo.setValue(null);
        loadModelos();
    }


    @FXML
    private void handleAdicionarModelo(ActionEvent event) throws IOException {
        if(selectMarca.getValue() == null) {
            System.out.println("É preciso selecionar a marca primeiro");
            return;
        }
        Modelo modelo = new Modelo();
        modelo.setMarca(selectMarca.getValue());
        DialogFormService<Modelo> service = new DialogFormService<>();
        var controller = service.showAddFormAndWaitResponse("/view/FormModelo.fxml", modelo, "Cadastro de Modelo");
        if(controller.isModelUpdated()) {
            Modelo updated = controller.getModel();
            selectModelo.getItems().add(updated);
            selectModelo.setValue(updated);
        }
    }

    @FXML
    private void handleAdicionarCor(ActionEvent event) throws IOException {
        Cor cor = new Cor();
        DialogFormService<Cor> service = new DialogFormService<>();
        var controller = service.showAddFormAndWaitResponse("/view/FormCor.fxml", cor, "Cadastro de Cor");
        if(controller.isModelUpdated()) {
            Cor updated = controller.getModel();
            selectCor.getItems().add(updated);
            selectCor.setValue(updated);
        }
    }

    @FXML
    private void handleAdicionarMarca(ActionEvent actionEvent) throws IOException {
        Marca marca = new Marca();
        DialogFormService<Marca> service = new DialogFormService<>();
        var controller = service.showAddFormAndWaitResponse("/view/FormMarca.fxml", marca, "Cadastro de Marca");
        if(controller.isModelUpdated()) {
            Marca updated = controller.getModel();
            selectMarca.getItems().add(updated);
            selectMarca.setValue(updated);
            loadModelos();
            selectModelo.getSelectionModel().clearSelection();
        }
    }

}
