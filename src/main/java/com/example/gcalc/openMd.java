package com.example.gcalc;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.*;
import javafx.stage.Stage;


public class openMd extends Application
{
    String inpType = "";

    private URL changeHtmlPage(String type) {
        return switch (type){
            case "readme" -> getClass().getResource("/com/example/gcalc/html/readme.html");
            case "eqhelp" -> getClass().getResource("/com/example/gcalc/html/eqhelp.html");
            case "about" -> getClass().getResource("/com/example/gcalc/html/about.html");
            case "solveSupported" -> getClass().getResource("/com/example/gcalc/html/solveSupported.html");
            case "constantsSupported" -> getClass().getResource("/com/example/gcalc/html/constantsSupported.html");
            case "functionsSupported" -> getClass().getResource("/com/example/gcalc/html/functionsSupported.html");
            default -> null;
        };
    }

    @Override
    public void start(final Stage stage)
    {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();

        URL url = changeHtmlPage(inpType);
        webEngine.load(url.toExternalForm());

        webEngine.setJavaScriptEnabled(true);

        browser.setPrefHeight(600);
        browser.setPrefWidth(1000);

        stage.setTitle("HELP");
        final Group rootGroup = new Group();
        rootGroup.prefWidth(1000);
        rootGroup.prefHeight(600);
        rootGroup.getChildren().add(browser);
        final Scene scene = new Scene(rootGroup, 1000, 600, Color.WHITE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] arguments)
    {
        Application.launch(arguments);
    }
}