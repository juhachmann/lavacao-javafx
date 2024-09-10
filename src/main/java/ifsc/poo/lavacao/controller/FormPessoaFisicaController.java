
package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.ClienteDAO;
import ifsc.poo.lavacao.model.domain.PessoaFisica;
import ifsc.poo.lavacao.utils.Regex;
import ifsc.poo.lavacao.utils.ValidacaoDeFormularioHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.regex.Matcher;

public class FormPessoaFisicaController extends FormClienteController<PessoaFisica> {

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
    protected void populateFieldsFromModel() {
        inputNome.setText(model.getNome());
        inputEmail.setText(model.getEmail());
        inputTelefone.setText(model.getCelular());
        if(model instanceof PessoaFisica pf) {
            inputCPF.setText(pf.getCpf());
            inputDataNascimento.setValue(pf.getDataNascimento());
        }

    }

    @Override
    protected void onInitializeAction() {

    }

    @Override
    protected ClienteDAO getDAO() {
        return new ClienteDAO();
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
        if (model instanceof PessoaFisica pf) {
            pf.setCpf(inputCPF.getText());
            pf.setDataNascimento(inputDataNascimento.getValue());
        }

        return true;
    }


}
