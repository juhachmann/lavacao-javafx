package ifsc.poo.lavacao;

import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        // Temas: https://mkpaz.github.io/atlantafx/#themes
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
      //  Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
      //  Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());


        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema de Lavação (JavaFX MVC)");
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

