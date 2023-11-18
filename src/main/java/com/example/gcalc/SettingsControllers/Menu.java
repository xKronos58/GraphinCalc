package com.example.gcalc.SettingsControllers;

import com.example.gcalc.GCMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu extends Application {
    public static int type = -1;
    public static boolean loaded = false;
    @Override
    public void start(Stage stage) throws Exception {
        if(loaded)
            return;

        FXMLLoader loader = switch (type) {
            case 0 -> new FXMLLoader(GCMain.class.getResource("menus/constants.fxml"));
            case 1 -> new FXMLLoader(GCMain.class.getResource("menus/functions.fxml"));
            default -> throw new IllegalArgumentException("FUCK");
        };
        System.out.println(loader);

        Parent p = loader.load();

        Scene s = new Scene(p, 500, 250);

        stage.setScene(s);
        stage.show();
        loaded = true;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}