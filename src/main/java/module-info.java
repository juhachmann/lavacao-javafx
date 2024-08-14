module ImcApplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    opens com.github.juhachmann to javafx.fxml;
    opens com.github.juhachmann.controller to javafx.fxml;
    exports com.github.juhachmann;
}