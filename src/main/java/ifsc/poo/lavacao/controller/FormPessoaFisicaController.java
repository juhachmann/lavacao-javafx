
package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.domain.PessoaFisica;
import ifsc.poo.lavacao.utils.FormatadorDeTextoBuilder;
import ifsc.poo.lavacao.utils.Regex;
import ifsc.poo.lavacao.utils.ValidacaoDeFormularioHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

public class FormPessoaFisicaController extends DialogController<PessoaFisica> {

    @FXML
    private TextField inputNome;
    @FXML
    private TextField inputCPF;
    @FXML
    private DatePicker inputDataNascimento;
    @FXML
    private TextField inputEmail;
    @FXML
    private TextField inputTelefone;
    @FXML
    private Button btnSalvar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    protected void populateForm() {
        inputNome.setText(model.getNome());
        inputEmail.setText(model.getEmail());
        inputTelefone.setText(model.getCelular());
        inputCPF.setText(model.getCpf());
        inputDataNascimento.setValue(model.getDataNascimento());
    }


    @Override
    protected boolean validarForm() {
        List<Control> controls = List.of(inputNome, inputCPF, inputTelefone, inputEmail, inputDataNascimento);
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
        model.setCpf(inputCPF.getText());
        model.setDataNascimento(inputDataNascimento.getValue());
        return true;
    }


}
