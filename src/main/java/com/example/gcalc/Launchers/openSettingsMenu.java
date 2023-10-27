package com.example.gcalc.Launchers;

import com.example.gcalc.GCMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class openSettingsMenu extends Application {
    public String menuType = "";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = switch (menuType) {
            case "general" -> new FXMLLoader(GCMain.class.getResource("settings/generalSettings.fxml"));
            case "physics" -> new FXMLLoader(GCMain.class.getResource("settings/physicsSettings.fxml"));
            case "graphing" -> new FXMLLoader(GCMain.class.getResource("settings/graphingSettings.fxml"));
            default -> throw new IllegalArgumentException("Settings class does not exist");
        };

        Parent menu = loader.load();
        Scene mainWindow = new Scene(menu, 500, 250);
        String css = String.valueOf(GCMain.class.getResource("css/Style.css"));
        mainWindow.getStylesheets().add(css);
        stage.setScene(mainWindow);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
