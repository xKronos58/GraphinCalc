package com.example.gcalc;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GCController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}