package com.example.gcalc.Launchers;

import com.example.gcalc.GCMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class openPopup extends Application {

    public String popupType = "";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = switch (popupType) {
            case "operator" -> new FXMLLoader(GCMain.class.getResource("popups/operatorMenu.fxml"));
            case "constant" -> new FXMLLoader(GCMain.class.getResource("popups/constantsMenu.fxml"));
            case "equation" -> new FXMLLoader(GCMain.class.getResource("popups/prefEquationMenu.fxml"));
            default -> throw new IllegalArgumentException("Popup does not exist");

        };

        Parent popup = loader.load();
        Scene mainCallWindow = new Scene(popup, 100, 100);
        stage.setScene(mainCallWindow);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
