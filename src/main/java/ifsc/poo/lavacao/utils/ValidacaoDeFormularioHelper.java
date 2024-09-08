package ifsc.poo.lavacao.utils;

import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoDeFormularioHelper {

    public static boolean validar(List<Control> campos) {
        var camposValidados = new ArrayList<Boolean>();
        for(Control campo : campos) {
            if (campo instanceof TextField casted) {
                camposValidados.add(validar(casted));
            }
            if (campo instanceof ChoiceBox<?> casted) {
                 camposValidados.add(validar(casted));
            }
            if (campo instanceof ComboBoxBase<?> casted) {
                camposValidados.add(validar(casted));
            }
        }
        for (Boolean campoValidado : camposValidados) {
            if (!campoValidado) return false;
        }
        return true;
    }

    /**
     * Valida um TextField para os parâmetros não vazio, não nulo
     * Números devem ser maiores do que zero
     *
     * @param campo
     * @return
     */
    public static boolean validar(TextField campo) {
        if(campo.getText() == null || campo.getText().isEmpty() || campo.getText().isBlank()) {
            return false;
        } else {
            try{
                double valorNumerico = Double.parseDouble(campo.getText());
                if (valorNumerico <= 0.0) {
                    System.out.println("Números devem ser maiores do que 0");
                    campo.setText("");
                    return false;
                }
            }
            catch (NumberFormatException ignored) {
            }
        }
        return true;
    }

    /**
     * Valida um ChoiceBox se há algum valor selecionado
     *
     * @param choiceBox
     * @return
     */
    public static boolean validar(ChoiceBox<?> choiceBox) {
        return choiceBox.getValue() != null;
    }

    /**
     * Valida um ComboBoxBase se há algum valor selecionado
     *
     * @param comboBoxBase
     * @return
     */
    public static boolean validar(ComboBoxBase<?> comboBoxBase) {
        return comboBoxBase.getValue() != null;
    }

}
