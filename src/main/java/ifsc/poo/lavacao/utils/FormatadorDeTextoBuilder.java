package ifsc.poo.lavacao.utils;

import javafx.scene.control.TextFormatter;

import java.util.regex.Pattern;

public class FormatadorDeTextoBuilder {

    CriadorDeFiltro filter = new CriadorDeFiltro();

    public TextFormatter<String> fromRegex(Pattern regex) {
        var filter = this.filter.fromRegex(regex);
        return new TextFormatter<>(filter);
    }

    public TextFormatter<String> fromRegexWithRange(Pattern regex, double min, double max) {
        var filter = this.filter.fromRegex(regex, min, max);
        return new TextFormatter<>(filter);
    }

}
