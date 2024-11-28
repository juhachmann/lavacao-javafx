package ifsc.poo.lavacao;

import atlantafx.base.theme.*;
import ifsc.poo.lavacao.utils.LoggedUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main extends Application {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static final LoggedUser loggedUser = new LoggedUser(null, null);

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


    
}

