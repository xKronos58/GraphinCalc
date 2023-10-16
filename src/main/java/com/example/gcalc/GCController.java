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
    public Button Equals;
    public Button piBtn;
    public Button eBtn;
    public Button logBtn;
    public Button sinBtn;
    public Button cosBtn;
    public Button tanBtn;
    public Button rootBtn;
    public Button sqrtBtn;
    public Button poeBtn;
    public Button mfdBtn;
    public Button spdBtn;
    public Button masBtn;
    public Button sopBtn;
    public Button convBtn;
    public Button tevBtn;
    public Button solveBtn;
    public Button expandBtn;
    public Button factorBtn;
    public Button braoBtn;
    public Button bracBtn;
    public Button arthBtn;

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

    public void addPi(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "pi");
    }

    public void addE(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "e");
    }

    public void log(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "log(");
    }

    public void sin(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "sin(");
    }

    public void cos(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "cos(");
    }

    public void tan(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "tan(");
    }

    public void root(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "r(");
    }

    public void sqrd(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "^2");
    }

    public void poe(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "^e");
    }

    public void mfd(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "mfd0(");
    }

    public void spd(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "spd0(");
    }

    public void mas(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "mas0(");
    }

    public void sop(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "sop0(");
    }

    public void conv(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "conv(");
    }

    public void tev(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "tev(");
    }

    public void solve(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "solve(");
    }

    public void expand(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "expand(");
    }

    public void factor(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "factor(");
    }

    public void openBrace(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "(");
    }

    public void closeBrace(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + ")");
    }

    public void arthsymb(ActionEvent actionEvent) {
        //TODO handle this
    }
}