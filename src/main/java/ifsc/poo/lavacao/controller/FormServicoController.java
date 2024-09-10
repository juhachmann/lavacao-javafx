package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.ServicoDAO;
import ifsc.poo.lavacao.model.domain.ECategoria;
import ifsc.poo.lavacao.model.domain.Servico;
import ifsc.poo.lavacao.utils.FormatadorDeTextoBuilder;
import ifsc.poo.lavacao.utils.Regex;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.*;

public class FormServicoController extends FormController<Servico, ServicoDAO> {

    private final List<Servico> servicos = new ArrayList<>();
    private final Map<ECategoria, TextField> controls = new HashMap<>();

    FormatadorDeTextoBuilder formatador = new FormatadorDeTextoBuilder();

    public TextField descricao;
    public TextField peq;
    public TextField med;
    public TextField pad;
    public TextField gde;
    public TextField moto;


    @Override
    protected void onInitializeAction() {
        controls.put(ECategoria.PEQUENO, peq);
        controls.put(ECategoria.MEDIO, med);
        controls.put(ECategoria.PADRAO, pad);
        controls.put(ECategoria.GRANDE, gde);
        controls.put(ECategoria.MOTO, moto);
        controls.values().forEach(c -> c.setTextFormatter(formatador.fromRegex(Regex.DECIMAL_POSITIVO)));
    }

    @Override
    protected ServicoDAO getDAO() {
        return new ServicoDAO();
    }

    @Override
    protected boolean validarForm() {
        if (descricao.getText() == null || descricao.getText().isBlank()) return false;

        if(editModel) {
            var control = controls.get(model.getCategoria());
            return control.getText() != null || control.getText().isBlank();
        }

//        for(TextField c : controls.values()) {
//            var text = c.getText();
//            if(text == null || text.isBlank()) {
//                return false;
//            }
//        }

        return true;
    }

    @Override
    protected boolean buildModel() {
        if(editModel) {
            model.setDescricao(descricao.getText());
            model.setValor(
                    Double.parseDouble(controls.get(model.getCategoria()).getText()));
        }
        else {
            for(Map.Entry<ECategoria, TextField> c : controls.entrySet()) {
                if (c.getValue().getText() != null & !c.getValue().getText().isBlank()) {
                    var servico = new Servico();
                    servico.setDescricao(descricao.getText());
                    servico.setValor(Double.parseDouble(c.getValue().getText()));
                    servico.setCategoria(c.getKey());
                    servicos.add(servico);
                }
            }
        }
        return true;
    }

    @Override
    protected void salvar() {
        if(editModel) {
            var updated = dao.update(model);
            setModel(updated);
            return;
        }
        dao.create(servicos);
    }

    @Override
    protected void populateFieldsFromModel() {
        System.out.println("Entrei aqui...");
        if(this.editModel) {
            System.out.println("E aqui no if EditModel...");
            System.out.println(model);
            descricao.setText(model.getDescricao());
            controls.values().forEach(c -> c.setDisable(true));
            ECategoria categoria = model.getCategoria();
            controls.get(categoria).setText(String.valueOf(model.getValor()));
            controls.get(categoria).setDisable(false);
        }
    }


}
