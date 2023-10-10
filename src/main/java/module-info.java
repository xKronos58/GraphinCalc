module com.example.gcalc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.gcalc to javafx.fxml;
    exports com.example.gcalc;
}