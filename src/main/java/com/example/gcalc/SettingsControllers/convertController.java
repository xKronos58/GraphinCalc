package com.example.gcalc.SettingsControllers;

import com.example.gcalc.Calculator.SimpleArithmetic;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

public class convertController {
    public ChoiceBox<String> Type;
    public ChoiceBox<String> unitOne;
    public ChoiceBox<String> unitTwo;
    public TextField valueOne;
    public Label output;

    public void compute(InputMethodEvent inputMethodEvent) {
        output.setText(String.valueOf(SimpleArithmetic.Convert(Type.getValue(), unitOne.getValue(), unitTwo.getValue(), valueOne.getText())));
    }
}
