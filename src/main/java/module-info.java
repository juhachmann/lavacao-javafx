module Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.sql;
    opens ifsc.poo.lavacao to javafx.fxml;
    opens ifsc.poo.lavacao.controller to javafx.fxml;
    exports ifsc.poo.lavacao;
}