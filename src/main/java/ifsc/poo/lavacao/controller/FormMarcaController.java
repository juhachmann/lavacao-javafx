package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.MarcaDAO;
import ifsc.poo.lavacao.model.domain.Marca;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FormMarcaController extends FormController<Marca, MarcaDAO> {

    @FXML
    private TextField inputNome;

    @Override
    protected void populateFieldsFromModel() {
        inputNome.setText(model.getNome());
    }

    @Override
    protected void onInitializeAction() {

    }

    @Override
    protected MarcaDAO getDAO() {
        return new MarcaDAO();
    }

    @Override
    protected boolean validarForm() {
        return inputNome.getText() != null
                || !inputNome.getText().isBlank();
    }

    @Override
    protected boolean buildModel() {
        model.setNome(inputNome.getText());
        return true;
    }

}
