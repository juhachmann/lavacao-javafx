
package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.domain.PessoaJuridica;
import ifsc.poo.lavacao.utils.Regex;
import ifsc.poo.lavacao.utils.ValidacaoDeFormularioHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;


public class FormPessoaJuridicaController extends DialogController<PessoaJuridica> {

    @FXML
    private TextField inputInscricao;
    @FXML
    private Button btnSalvar;
    @FXML
    private TextField inputNome;
    @FXML
    private TextField inputCNPJ;
    @FXML
    private TextField inputEmail;
    @FXML
    private TextField inputTelefone;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @Override
    protected void populateForm() {
        inputNome.setText(model.getNome());
        inputEmail.setText(model.getEmail());
        inputTelefone.setText(model.getCelular());
        inputInscricao.setText(model.getInscricaoEstadual());
        inputCNPJ.setText(model.getCnpj());
    }


    @Override
    protected boolean validarForm() {
        List<Control> controls = List.of(inputNome, inputCNPJ, inputTelefone, inputEmail, inputInscricao);
        if (!ValidacaoDeFormularioHelper.validar(controls))
            return false;
        Matcher matcher = Regex.EMAIL.matcher(inputEmail.getText());
        return matcher.matches();
    }


    @Override
    protected boolean buildModel() {
        model.setCelular(inputTelefone.getText());
        model.setEmail(inputEmail.getText());
        model.setNome(inputNome.getText());
        model.setCnpj(inputCNPJ.getText());
        model.setInscricaoEstadual(inputInscricao.getText());
        return true;
    }



}
