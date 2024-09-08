module Main {
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.sql;
    requires jasperreports;
    requires com.ctc.wstx;
    requires org.controlsfx.controls;
    opens ifsc.poo.lavacao to javafx.fxml;
    opens ifsc.poo.lavacao.controller to javafx.fxml;
    opens ifsc.poo.lavacao.model.domain to javafx.base;
    exports ifsc.poo.lavacao;
}