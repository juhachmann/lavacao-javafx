package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.CorDAO;
import ifsc.poo.lavacao.model.domain.Cor;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FormCorController extends FormController<Cor, CorDAO> {

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
    protected CorDAO getDAO() {
        return new CorDAO();
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
