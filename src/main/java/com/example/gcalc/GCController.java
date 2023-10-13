package com.example.gcalc;

import com.example.gcalc.Calculator.EvalEquation;
import com.example.gcalc.Calculator.HandleStack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GCController {
    public Button BCalcBtn;
    public Button SCalcBtn;
    public Button gCalcBtn;
    public Button PCalcBtn;
    public TextField EquationField;
    public VBox textScreen;

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

    public void onEnter(ActionEvent actionEvent) throws IOException {
        if(EquationField.getText().equals("") || HandleStack.handlePredefinedEquation(EquationField.getText()))
            return;
        Text t = new Text(EquationField.getText() + '=' + HandleStack.evaluate(EquationField.getText()));
        textScreen.getChildren().add(t);
        EquationField.clear();
    }
}