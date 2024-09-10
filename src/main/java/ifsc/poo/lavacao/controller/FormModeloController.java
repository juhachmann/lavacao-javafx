
package ifsc.poo.lavacao.controller;

import java.util.List;

import ifsc.poo.lavacao.model.dao.MarcaDAO;
import ifsc.poo.lavacao.model.dao.ModeloDAO;
import ifsc.poo.lavacao.model.domain.ECategoria;
import ifsc.poo.lavacao.model.domain.ETipoCombustivel;
import ifsc.poo.lavacao.model.domain.Marca;
import ifsc.poo.lavacao.model.domain.Modelo;
import ifsc.poo.lavacao.utils.ValidacaoDeFormularioHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class FormModeloController extends FormController<Modelo, ModeloDAO> {

    @FXML
    private TextField inputModelo;
    @FXML
    private ComboBox<Marca> selectMarca;
    @FXML
    private ComboBox<ECategoria> selectCategoria;
    @FXML
    private TextField inputPotencia;
    @FXML
    private ComboBox<ETipoCombustivel> selectCombustivel;


    private final MarcaDAO marcaDAO = new MarcaDAO();


    @Override
    public void onInitializeAction() {
        marcaDAO.setConnection(connection);
        loadCategorias();
        loadTipoCombustivel();
        setComboBoxConvertersForMarca();
    }

    @Override
    protected ModeloDAO getDAO() {
        return new ModeloDAO();
    }


    private void loadCategorias() {
        ObservableList<ECategoria> observableListCategorias =
                FXCollections.observableArrayList(ECategoria.values());
        selectCategoria.setItems(observableListCategorias);
    }

    private void loadMarcas() {
        List<Marca> marcas = marcaDAO.getAll();
        ObservableList<Marca> observableList =
                FXCollections.observableArrayList(marcas);
        selectMarca.setItems(observableList);
    }

    private void loadTipoCombustivel() {
        ObservableList<ETipoCombustivel> observableListCombustivel =
                FXCollections.observableArrayList(ETipoCombustivel.values());
        selectCombustivel.setItems(observableListCombustivel);
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


    @Override
    protected void populateFieldsFromModel() {
        // Se já vem com marca do contexto, não é para modificar a marca
        if(model.getMarca() != null) {
            selectMarca.setDisable(true);
        } else loadMarcas();
        inputModelo.setText(model.getDescricao());
        inputPotencia.setText(String.valueOf(model.getMotor().getPotencia()));
        selectCategoria.setValue(model.getCategoria());
        selectCombustivel.setValue(model.getMotor().getTipoCombustivel());
        selectMarca.setValue(model.getMarca());
    }

    @Override
    protected boolean validarForm() {
        return ValidacaoDeFormularioHelper.validar(List.of(
            inputModelo, inputPotencia, selectCategoria, selectCombustivel, selectMarca ));
    }

    @Override
    protected boolean buildModel() {
        model.setCategoria(selectCategoria.getValue());
        model.setMarca(selectMarca.getValue());
        model.setDescricao(inputModelo.getText());
        model.getMotor().setTipoCombustivel(selectCombustivel.getValue());
        model.getMotor().setPotencia(Integer.parseInt(inputPotencia.getText()));
        return true;
    }


}
