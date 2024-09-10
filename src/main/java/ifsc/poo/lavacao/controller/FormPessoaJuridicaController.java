
package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.ClienteDAO;
import ifsc.poo.lavacao.model.domain.PessoaFisica;
import ifsc.poo.lavacao.model.domain.PessoaJuridica;
import ifsc.poo.lavacao.utils.Regex;
import ifsc.poo.lavacao.utils.ValidacaoDeFormularioHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.regex.Matcher;


public class FormPessoaJuridicaController extends FormClienteController<PessoaJuridica> {

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
    protected void populateFieldsFromModel() {
        inputNome.setText(model.getNome());
        inputEmail.setText(model.getEmail());
        inputTelefone.setText(model.getCelular());
        if(model  instanceof PessoaJuridica pj) {
            inputInscricao.setText(pj.getInscricaoEstadual());
            inputCNPJ.setText(pj.getCnpj());
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
        if(model instanceof PessoaJuridica pj) {
            pj.setCnpj(inputCNPJ.getText());
            pj.setInscricaoEstadual(inputInscricao.getText());
        }
        return true;
    }



}
