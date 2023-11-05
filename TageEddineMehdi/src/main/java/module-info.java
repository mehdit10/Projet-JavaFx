module com.example.tageeddinemehdi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.tageeddinemehdi to javafx.fxml;
    exports com.example.tageeddinemehdi;
    exports com.example.tageeddinemehdi.Controlleurs;
    opens com.example.tageeddinemehdi.Controlleurs to javafx.fxml;
}