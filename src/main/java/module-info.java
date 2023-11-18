module com.example.gcalc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.web;

    opens com.example.gcalc to javafx.fxml;
    exports com.example.gcalc;
    exports com.example.gcalc.Launchers;
    opens com.example.gcalc.Launchers to javafx.fxml;
    exports com.example.gcalc.SettingsControllers;
    opens com.example.gcalc.SettingsControllers to javafx.fxml;
}