package ifsc.poo.lavacao.controller;

import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerLight;
import ifsc.poo.lavacao.Main;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignE;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    private double x, y;

    public TextField userInput;
    public PasswordField pwdInput;
    public HBox pwdHBox;
    public FontIcon pwdIcon;

    private final TextField pwdTextInput = new TextField();
    private final SimpleBooleanProperty passwordVisible = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pwdIcon.setIconCode(MaterialDesignE.EYE_OFF);
        bindPwdTextInput();
        passwordVisible.addListener((source, oldValue, isVisible) -> {
            togglePasswordVisibility(isVisible);
        });
    }

    private void bindPwdTextInput() {
        pwdTextInput.styleProperty().bind(pwdInput.styleProperty());
        pwdTextInput.prefHeightProperty().bind(pwdInput.prefHeightProperty());
        pwdTextInput.prefWidthProperty().bind(pwdInput.prefWidthProperty());
        pwdTextInput.textProperty().bindBidirectional(pwdInput.textProperty());
    }

    private void togglePasswordVisibility(boolean isVisible) {
        if(isVisible) {
            pwdHBox.getChildren().set(0, pwdTextInput);
            pwdIcon.setIconCode(MaterialDesignE.EYE);
        } else {
            pwdHBox.getChildren().set(0, pwdInput);
            pwdIcon.setIconCode(MaterialDesignE.EYE_OFF);
        }
    }

    public void togglePassword(MouseEvent mouseEvent) {
        System.out.println(pwdIcon.getIconColor());
        passwordVisible.setValue(!passwordVisible.getValue());
    }

    public void cancelLogin(ActionEvent actionEvent) {
        closeCurrentStage(actionEvent);
    }

    private void closeCurrentStage(ActionEvent actionEvent) {
        Control eventSource = (Control) actionEvent.getSource();
        Stage currentStage = (Stage) eventSource.getScene().getWindow();
        currentStage.close();
    }

    public void login(ActionEvent actionEvent) {
        Main.loggedUser.setName(userInput.getText());
        Main.loggedUser.setLoginDateTime(LocalDateTime.now());
        closeCurrentStage(actionEvent);



        // Carrega o próximo Stage
//        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Application.setUserAgentStylesheet(getClass().getResource("/css/style.css").toExternalForm());

        Stage mainStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setTitle("Sistema de Lavação");
        mainStage.setResizable(true);
        mainStage.setMaximized(true);
        mainStage.setMinWidth(800);
        mainStage.setMinHeight(600);
        mainStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            mainStage.setX(event.getScreenX() - x);
            mainStage.setY(event.getScreenY() - y);
        });

        mainStage.show();

    }


}
