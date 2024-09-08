package ifsc.poo.lavacao.utils;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

// Não lembro mais como isso aqui funcionava =(
class CriadorDeFiltro {

    /**
     * Cria um filtro a partir de uma expressão regular
     * @param regex
     * @return
     */
    public UnaryOperator<TextFormatter.Change> fromRegex(Pattern regex) {
        return change -> {
            String newText = change.getControlNewText();
            if (regex.matcher(newText).matches()) {
                return change;
            } else {
                return null;
            }
        };
    }

    /**
     * Cria um filtro a partir de uma expressão regular, com range (min e max)
     * @param regex
     * @param minValue
     * @param maxValue
     * @return
     */
    public UnaryOperator<TextFormatter.Change> fromRegex(Pattern regex, double minValue, double maxValue) {
        return change -> {
            String newText = change.getControlNewText();
            if(newText.isEmpty()) {
                return change;
            }
            if (regex.matcher(newText).matches()) {
                var numericValue = tryToParseNumber(newText);
                if (numericValue != null) {
                    if (checkIfNumberIsWithinRange(numericValue, minValue, maxValue))
                        return change;
                }
            }
            return null;
        };
    }

    private Double tryToParseNumber(String numberAsText) {
        try {
            return Double.parseDouble(numberAsText);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private <T extends Number> boolean checkIfNumberIsWithinRange(T newValue, T min, T max) {
        return  (newValue.doubleValue() >= min.doubleValue() && newValue.doubleValue() <= max.doubleValue());
    }


}
