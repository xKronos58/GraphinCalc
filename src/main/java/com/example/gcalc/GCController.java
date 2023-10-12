package com.example.gcalc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class GCController {
    public Button BCalcBtn;
    public Button SCalcBtn;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private Button gCalcBtn;

    @FXML
    protected void changeCalc(ActionEvent actionEvent) {
        GCMain.ShowGraphingCalc();
    }

    public void changeCalcS(ActionEvent actionEvent) throws IOException {
        GCMain.ShowScientificCalc();
    }

    public void changeCalcB(ActionEvent actionEvent) throws IOException {
        GCMain.ShowBasicCalc();
    }

    public void changeCalcP(ActionEvent actionEvent) throws IOException {
        GCMain.ShowPhysicsCalc();
    }
}