package ifsc.poo.lavacao.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class DialogFormService<T> {

    public FormController<T, ?> showAddFormAndWaitResponse(String fxml, T model, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        AnchorPane page = loader.load();

        FormController<T, ?> controller = loader.getController();
        controller.setModel(model);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setPannable(true);
        scrollPane.setContent(page);
        Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UNDECORATED);
        //dialogStage.setTitle(title);

        Scene scene = new Scene(scrollPane);
        dialogStage.setScene(scene);
//        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        controller.setStage(dialogStage);

        dialogStage.showAndWait();

        // Talvez aqui pudesse dar outro retorno
        return controller;

    }

    public FormController<T, ?> showEditFormAndWaitResponse(String fxml, T model, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        AnchorPane page = loader.load();

        FormController<T, ?> controller = loader.getController();
        controller.setEditModel(true);
        controller.setModel(model);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setPannable(true);
        scrollPane.setContent(page);
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);

        Scene scene = new Scene(scrollPane);
        dialogStage.setScene(scene);
//        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        controller.setStage(dialogStage);

        dialogStage.showAndWait();

        return controller;

    }


    public boolean showDialogAndWaitResponse(String fxml, T model, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        AnchorPane page = loader.load();

        FormController<T, ?> controller = loader.getController();
        controller.setModel(model);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setPannable(true);
        scrollPane.setContent(page);
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);

        Scene scene = new Scene(scrollPane);
        dialogStage.setScene(scene);
//        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        controller.setStage(dialogStage);

        dialogStage.showAndWait();

        return controller.isModelDeleted() || controller.isModelUpdated();

    }


}
