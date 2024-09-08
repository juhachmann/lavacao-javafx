package ifsc.poo.lavacao.model.exceptions;

/**
 * Exceções geradas na camada DAO
 */
public class DAOException extends Exception {

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

}
