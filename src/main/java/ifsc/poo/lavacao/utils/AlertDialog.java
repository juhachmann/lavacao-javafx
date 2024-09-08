package ifsc.poo.lavacao.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Classe utilitária para lidar com modais de confirmação e avisos de erros ao usuário
 *
 * @author Marcos Pisching
 */
public class AlertDialog {

    public static boolean confirmarExclusao(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Você deseja excluir este registro?");
        alert.setContentText(msg);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    public static void exceptionMessage(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ooops, houve um erro!");
        alert.setHeaderText(ex.getMessage());
        try {
            alert.setContentText("Detalhes: \n" + ex.getCause().toString());
        } finally {
            alert.show();
        }

    }

}
