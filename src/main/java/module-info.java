module Main {
    requires java.sql;
    requires javafx.fxml;
    requires atlantafx.base;
    requires jasperreports;
    requires com.ctc.wstx;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.slf4j;
//    requires org.kordamp.ikonli.materialdesign2;
 //   requires org.kordamp.ikonli.fontawesome;

    opens ifsc.poo.lavacao to javafx.fxml;
    opens ifsc.poo.lavacao.controller to javafx.fxml;
    opens ifsc.poo.lavacao.model.domain to javafx.base;
    opens ifsc.poo.lavacao.service to javafx.fxml;

    exports ifsc.poo.lavacao;
}