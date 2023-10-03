module com.example.projectgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;
    requires itextpdf;
    requires jbcrypt;

    opens com.example.projectgui.Models to javafx.base;
    opens com.example.projectgui to javafx.fxml;
    opens com.example.projectgui.Controller to javafx.fxml; // Agrega esta línea
    exports com.example.projectgui;
    exports com.example.projectgui.Controller;
}
