package com.example.gcalc;

import com.example.gcalc.Calculator.EvalEquation;
import com.example.gcalc.Calculator.HandleStack;
import com.example.gcalc.advancedCalculations.Expand;
import com.example.gcalc.advancedCalculations.Factor;
import com.example.gcalc.advancedCalculations.Solve;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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
    public Boolean solve = false;
    public Boolean expand = false;
    public Boolean factor = false;
    @FXML
    public static int scrollHeight = 181;
    public ScrollPane scrollViewMain;
    public AnchorPane apScroolView;
    public MenuItem CloseMenuItem;

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

    public static String combineArrays(double[] ans) {
        if(ans.length == 1)
            return ans[0] + " ";
        else
        {
            // For the case of a quadratic or other type of equation where more than one ans will be returned,
            // specifically when calculating x intersects within graphing
            StringBuilder sb = new StringBuilder();
            for(double _ans : ans) sb.append(_ans).append(", ");
            return sb.toString();
        }
    }

    public static int equationNum = 0;
    public void onEnter(ActionEvent actionEvent) throws IOException {
        Text t;
        equationNum++;

        // Checks the type of method being used
        if(EquationField.getText().equals("") || HandleStack.handlePredefinedEquation(EquationField.getText()))
            return;
        else if (solve)
            t = new Text(EquationField.getText() + "\n    = " +
                        Solve.solve(EquationField.getText()) );
        else if (expand)
            t = new Text(EquationField.getText() + "\n    = " +
                        Expand.expand(EquationField.getText()));
        else if (factor)
            t = new Text(EquationField.getText() + "\n    = " +
                        Factor.factor(EquationField.getText()));
        else
            t = new Text(EquationField.getText() + "\n    = " +
                        HandleStack.evaluate(EquationField.getText()));

        t.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        t.prefHeight(20);
        if(equationNum > 4)
            scrollHeight += 38;
        textScreen.getChildren().add(t);
        EquationField.clear();
        textScreen.setPrefHeight(scrollHeight);
        scrollViewMain.setPrefHeight(scrollHeight);
        apScroolView.setPrefHeight(scrollHeight);
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
        solve = true;
    }

    public void expand(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "expand(");
        expand = true;
    }

    public void factor(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "factor(");
        factor = true;
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

    public void close(ActionEvent actionEvent) {
        GCMain._stage.close();
    }

    public void openReadme(ActionEvent actionEvent) {

    }

    public void openEqHelp(ActionEvent actionEvent) {

    }

    public void openAbout(ActionEvent actionEvent) {

    }
}