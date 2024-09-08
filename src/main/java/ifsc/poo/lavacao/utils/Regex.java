package ifsc.poo.lavacao.utils;

import java.util.regex.Pattern;

public class Regex {

    /**
     * REGEX para filtrar um nome, válido em várias línguas
     */
    public static final Pattern NOME_HUMANO_GLOBAL = Pattern.compile("^([\\p{L}'-]+(\\s?))*+$");

    /**
     * REGEX para identificar se um número é um inteiro positivo
     */
    public static final Pattern INTEIRO_POSITIVO = Pattern.compile("([0-9]*)?");

    /**
     * REGEX para identificar se um número é decimal e positivo
     */
    public static final Pattern DECIMAL_POSITIVO = Pattern.compile("([0-9]*([.][0-9]*)?)");

    /**
     * REGEX para email
     */
    public static final Pattern EMAIL = Pattern.compile(".+@.+\\.+.+");

}
