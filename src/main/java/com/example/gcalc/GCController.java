package com.example.gcalc;

import com.example.gcalc.Calculator.EquationList;
import com.example.gcalc.Calculator.HandleStack;
import com.example.gcalc.Launchers.openMd;
import com.example.gcalc.Launchers.openPopup;
import com.example.gcalc.Launchers.openSettingsMenu;
import com.example.gcalc.advancedCalculations.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GCController implements Initializable {
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
    public Button convBtn;
    public Button solveBtn;
    public Button expandBtn;
    public Button factorBtn;
    public Button braoBtn;
    public Button bracBtn;

    public String type = "equation";
    @FXML
    public static int scrollHeight = 181;
    public ScrollPane scrollViewMain;
    public AnchorPane apScroolView;
    public MenuItem CloseMenuItem;
    public Group generalSceneMid;
    public SubScene _generalSceneMid;
    public static boolean invalidEquation = false;
    public ChoiceBox<String> langBox;
    public List<String> SupportedLang = List.of(new String[]{"English", "Finnish", "Italian"});
    public Button power;
    public Button logNBtn;
    public Button functionsBtn;
    public Button ConstantsBtn;
    public Button MatrixBtn;
    public Button ArrayBtn;
    public Button AbsoluteBtn;
    public Button FractionBtn;
    public Button PercentBtn;
    public Button InequalityBtn;
    public Button SimplifyBtn;
    public Button HCFBtn;
    public Button LCFBtn;
    public Button power10;
    public Button cubeRoot;
    public Button plus;
    public Button minus;
    public Button multi;
    public Button div;
    public Button InfinityBtn;
    public Button clearType;
    public Button clearBtm;
    public Button poeBtn;
    public Button cDeriveBtn;
    public Button DeriveBtn;
    public Button aFxBtn;
    public Button integralBtn;
    public Button integralIZBtn;
    public Button sqrdBtn;
    public static List<String> loadedEquations = readEqs();
    public Button letBtn;
    public Button TrigBtn;
    public Button empty;

    @FXML
    protected void showGraphing(ActionEvent actionEvent) throws IOException {
        GCMain.displayCalc.GRAPHING.showCalc();
    }


    public void showScientific(ActionEvent actionEvent) throws IOException {
        GCMain.displayCalc.SCIENTIFIC.showCalc();
    }

    public void showCalculus(ActionEvent actionEvent) throws IOException {
        GCMain.displayCalc.CALCULUS.showCalc();
    }

    public void showPhysics(ActionEvent actionEvent) throws IOException {
        GCMain.displayCalc.PHYSICS.showCalc();
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
        if(EquationField.getText().isEmpty())
            return;

        try {
            util.writeFile("equations.txt", EquationField.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        refreshEquationList();
        currentEquation = loadedEquations.size();

        switch (type) {
            case "solve" -> {
                double sol = Solve.solve(EquationField.getText());
                t = new Text(EquationField.getText() + "\n    = " +
                        (Solve.has2Sol ? Solve.sol2 : sol ));
            } case "expand" -> t = new Text(EquationField.getText() + "\n    = " +
                    Expand.expand(EquationField.getText()));
            case "factor" -> t = new Text(EquationField.getText() + "\n    = " +
                    Factor.factor(EquationField.getText()));
            case "simplify" -> t= new Text(EquationField.getText() + "\n    = " +
                    Simplify.simplifyRaw(EquationField.getText()));
            case "mfd", "spd", "mas", "sop", "tev", "acl", "kne", "wrk" -> t = new Text(EquationField.getText() + "\n    = " +
                    EquationList.predefinedEquation(EquationField.getText(), type));
            case "hcf" -> t = new Text(EquationField.getText() + "\n    = " +
                    CommonFactor.HCF(EquationField.getText()));
            case "lcf" -> t = new Text(EquationField.getText() + "\n    = " +
                    CommonFactor.LCM(EquationField.getText()));
            default -> t = new Text(EquationField.getText() + "\n    = " +
                    HandleStack.evaluate(EquationField.getText()));
        }

        if(invalidEquation)
            util.errorMessage("Invalid expression", "Invalid Expression");


        t.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        t.prefHeight(20);
        t.prefWidth(600);
        if(equationNum > 4)
            scrollHeight += 38;
        t.prefWidth(600);
        textScreen.getChildren().add(t);
        EquationField.clear();
        textScreen.setPrefHeight(scrollHeight);
        scrollViewMain.setPrefHeight(scrollHeight);
        apScroolView.setPrefHeight(scrollHeight);

        _clearType();

        invalidEquation = false;
    }

    public void conv(ActionEvent actionEvent) throws Exception {
        openPopup op = new openPopup();
        op.popupType = "convert";
        op.start(new Stage());
    }

    public void solve(ActionEvent actionEvent) {
        EquationField.setText("solve(");
        type = "solve";
    }

    public void expand(ActionEvent actionEvent) {
        EquationField.setText("expand(");
        type = "expand";
    }

    public void factor(ActionEvent actionEvent) {
        EquationField.setText("factor(");
        type = "factor";
    }

    public void close(ActionEvent actionEvent) {
        GCMain._stage.close();
    }

    public void openReadme(ActionEvent actionEvent) {
        openMd wbe = new openMd();
        Stage test = new Stage();
        wbe.inpType = "readme";
        wbe.start(test);
    }

    public void openEqHelp(ActionEvent actionEvent) {
        openMd wbe = new openMd();
        Stage test = new Stage();
        wbe.inpType = "eqhelp";
        wbe.start(test);
    }

    public void openAbout(ActionEvent actionEvent) throws Exception {
        openMd wbe = new openMd();
        Stage test = new Stage();
        wbe.inpType = "about";
        wbe.start(test);
    }

    public void openSolveSupported(ActionEvent actionEvent) {
        openMd wbe = new openMd();
        Stage test = new Stage();
        wbe.inpType = "solveSupported";
        wbe.start(test);
    }

    public void openFunctionsSupported(ActionEvent actionEvent) {
        openMd wbe = new openMd();
        Stage test = new Stage();
        wbe.inpType = "functionsSupported";
        wbe.start(test);
    }

    public void openConstantsSupported(ActionEvent actionEvent) {
        openMd wbe = new openMd();
        Stage test = new Stage();
        wbe.inpType = "constantsSupported";
        wbe.start(test);
    }

    public void openPhysicsSettings(ActionEvent actionEvent) throws Exception {
        openSettingsMenu opsm = new openSettingsMenu();
        Stage s = new Stage();
        opsm.menuType = "physics";
        opsm.start(s);
    }

    public void openGeneralSettings(ActionEvent actionEvent) throws Exception {
        openSettingsMenu opsm = new openSettingsMenu();
        Stage s = new Stage();
        opsm.menuType = "general";
        opsm.start(s);
    }

    public void openGraphingSettings(ActionEvent actionEvent) throws Exception {
        openSettingsMenu opsm = new openSettingsMenu();
        Stage s = new Stage();
        opsm.menuType = "graphing";
        opsm.start(s);
    }

    public static Parent LoadScene(String type) throws IOException {
        FXMLLoader x = switch (type) {
            case "general" -> new FXMLLoader(GCMain.class.getResource("Settings/SettingsSubPanes/generalSettings/general.fxml"));
            case "style" -> new FXMLLoader(GCMain.class.getResource("Settings/SettingsSubPanes/generalSettings/style.fxml"));
            case "performance" -> new FXMLLoader(GCMain.class.getResource("Settings/SettingsSubPanes/generalSettings/performance.fxml"));
            case "units" -> new FXMLLoader(GCMain.class.getResource("Settings/SettingsSubPanes/generalSettings/units.fxml"));
            case "import" -> new FXMLLoader(GCMain.class.getResource("Settings/SettingsSubPanes/generalSettings/import.fxml"));
            default -> throw new IllegalArgumentException("Settings-Sub-Class does not exist");
        };

        return x.load();
    }

    public void openGeneralSettingsMenu(ActionEvent actionEvent) throws IOException {
        Parent general = LoadScene("general");
        generalSceneMid.getChildren().setAll(general);
    }

    public void openStyleSettingsMenu(ActionEvent actionEvent) throws IOException {
        Parent style = LoadScene("style");
        generalSceneMid.getChildren().setAll(style);
    }

    public void openPerformanceSettingsMenu(ActionEvent actionEvent) throws IOException {
        Parent performance = LoadScene("performance");
        generalSceneMid.getChildren().setAll(performance);
    }

    public void openUnitsSettingsMenu(ActionEvent actionEvent) throws IOException {
        Parent units = LoadScene("units");
        generalSceneMid.getChildren().setAll(units);
    }

    public void openImportSettingsMenu(ActionEvent actionEvent) throws IOException {
        Parent _import = LoadScene("import");
        generalSceneMid.getChildren().setAll(_import);
    }

    public void AutoUpdate(ActionEvent actionEvent) {

    }

    private void initializeLangChoices() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(SupportedLang);
        langBox.getItems().addAll(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(String.valueOf(url).contains("general.fxml"))
            initializeLangChoices();
    }

    public void showFunctionsMenu(ActionEvent actionEvent) {
        remapFunctions();
    }

    public void showConstantMenu(ActionEvent actionEvent) throws Exception {
        remapConstants();
    }

    public void Inequality(ActionEvent actionEvent) {
        remapInequalities();
    }

    public void simplify(ActionEvent actionEvent) {
        EquationField.setText("simplify(");
        type = "simplify";
    }

    public void HCF(ActionEvent actionEvent) {
        EquationField.setText("HCF(");
        type = "hcf";
    }

    public void LCF(ActionEvent actionEvent) {
        EquationField.setText("LCF(");
        type = "lcf";
    }

    public void clearType(ActionEvent actionEvent) {
        _clearType();
    }

    public void clear(ActionEvent actionEvent) {
        EquationField.setText("");
        type = "equation";
    }

    public void chainDerive(ActionEvent actionEvent) {
        EquationField.setText("derive{t=chain,f(x)=");
    }

    public void Derive(ActionEvent actionEvent) {
        EquationField.setText("derive{f(x)=");
    }

    public void addFx(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "f(x)=");
    }

    public void addIntegralIZ(ActionEvent actionEvent) {
        EquationField.setText("integrate[inf][0]{f(x)=");
    }

    public void addIntegral(ActionEvent actionEvent) {
        EquationField.setText("integrate[][]{f(x)=");
    }

    private void initializeButton(Button button, String text, EventHandler<ActionEvent> eventHandler) {
        button.setText(text);
        button.setOnAction(eventHandler);
    }

    private void setText(TextField t, String text) {
        t.setText(t.getText() + text);
    }

    public void remapConstants() {
        initializeButton(sqrdBtn, "Tau", actionEvent -> setText(EquationField, "tu"));
        initializeButton(poeBtn, "Phi", actionEvent -> setText(EquationField, "ph"));
        initializeButton(power, "i", actionEvent -> setText(EquationField, "i"));
        initializeButton(logNBtn, "Sgr", actionEvent -> setText(EquationField, "sg"));
        initializeButton(logBtn, "Cchl", actionEvent -> setText(EquationField, "mc"));
        initializeButton(TrigBtn, "Kbc", actionEvent -> setText(EquationField, "kb"));
        initializeButton(letBtn, "Wc", actionEvent -> setText(EquationField, "wc"));
        initializeButton(ConstantsBtn, "µ0", actionEvent -> setText(EquationField, "_m"));
        initializeButton(MatrixBtn, "Pm", actionEvent -> setText(EquationField, "pm"));
        initializeButton(ArrayBtn, "Nm", actionEvent -> setText(EquationField, "nm"));
        initializeButton(functionsBtn, "Pc", actionEvent -> setText(EquationField, "pc"));
        initializeButton(convBtn, "Gravity", actionEvent -> setText(EquationField, "g"));
        initializeButton(plus, "X", actionEvent -> remapKeys());

        List<Object> empty = List.of(AbsoluteBtn, PercentBtn, FractionBtn, InequalityBtn, SimplifyBtn, HCFBtn, LCFBtn, power10, cubeRoot, minus, multi, div, InfinityBtn, solveBtn, expandBtn, factorBtn, rootBtn, braoBtn, bracBtn);
        setElementToEmpty(empty);
    }

    public void remapInequalities() {
        initializeButton(piBtn, "<", actionEvent -> EquationField.setText(EquationField.getText() + " <"));
        initializeButton(eBtn, ">", actionEvent -> EquationField.setText(EquationField.getText() + " >"));
        initializeButton(sqrdBtn, "<=", actionEvent -> EquationField.setText(EquationField.getText() + " <="));
        initializeButton(poeBtn, ">=", actionEvent -> EquationField.setText(EquationField.getText() + " >="));
        initializeButton(power, "!=", actionEvent -> EquationField.setText(EquationField.getText() + " !="));
        initializeButton(plus, "X", actionEvent -> remapKeys());

        List<Object> empty = List.of(logBtn, logNBtn, TrigBtn, letBtn, ConstantsBtn, MatrixBtn, ArrayBtn, AbsoluteBtn, FractionBtn, PercentBtn, InequalityBtn, SimplifyBtn, HCFBtn, LCFBtn, power10, cubeRoot, minus, multi, div, InfinityBtn, functionsBtn, convBtn, solveBtn, expandBtn, factorBtn, rootBtn, braoBtn, bracBtn);
        setElementToEmpty(empty);
    }

    public void remapFunctions() {
        initializeButton(piBtn, "MFD", actionEvent -> {
            setText(EquationField, "mfd0(");
            type = "mfd";
        });
        initializeButton(eBtn, "SPD", actionEvent -> {
            setText(EquationField, "spd0(");
            type = "spd";
        });
        initializeButton(sqrdBtn, "MAS", actionEvent -> {
            setText(EquationField, "mas0(");
            type = "mas";
        });
        initializeButton(poeBtn, "SOP", actionEvent -> {
            setText(EquationField, "sop0(");
            type = "sop";
        });
        initializeButton(power, "TEV", actionEvent -> {
            setText(EquationField, "tev0(");
            type = "tev";
        });
        initializeButton(logNBtn, "ACL", actionEvent -> {
            setText(EquationField, "acl0(");
            type = "acl";
        });
        initializeButton(logBtn, "KNE", actionEvent -> {
            setText(EquationField, "kne0(");
            type = "kne";
        });
        initializeButton(TrigBtn, "WRK", actionEvent -> {
            setText(EquationField, "wrk0(");
            type = "wrk";
        });
        initializeButton(plus, "X", actionEvent -> remapKeys());

        List<Object> empty = List.of(letBtn, ConstantsBtn, MatrixBtn, ArrayBtn, AbsoluteBtn, FractionBtn, PercentBtn, InequalityBtn, SimplifyBtn, HCFBtn, LCFBtn, power10, cubeRoot, minus, multi, div, InfinityBtn, functionsBtn, convBtn, solveBtn, expandBtn, factorBtn, rootBtn, braoBtn, bracBtn);
        setElementToEmpty(empty);
    }

    @SuppressWarnings("WriteOnlyObject")
    public static void setElementToEmpty(List<Object> objects) {
        var ref = new Object() {
            int temp = 0;
        };
        for (Object object : objects)
            if (object instanceof Button b) {
                b.setText("");
                b.setOnAction(actionEvent -> ref.temp = 0); // Does nothing
            }

    }

    private void remapKeys() {
        initializeButton(piBtn, "π", actionEvent -> addText(new ActionEvent(piBtn, piBtn)));
        initializeButton(eBtn, "e", actionEvent -> addText(new ActionEvent(eBtn, eBtn)));
        initializeButton(sqrdBtn, "x^2", actionEvent -> addText(new ActionEvent(sqrdBtn, sqrdBtn)));
        initializeButton(poeBtn, "e^", actionEvent -> addText(new ActionEvent(poeBtn, poeBtn)));
        initializeButton(power, "x^y", actionEvent -> addText(new ActionEvent(power, power)));
        initializeButton(logNBtn, "ln()", actionEvent -> addText(new ActionEvent(logNBtn, logNBtn)));
        initializeButton(logBtn, "log()", actionEvent -> addText(new ActionEvent(logBtn, logBtn)));
        initializeButton(TrigBtn, "TRIG", actionEvent -> addText(new ActionEvent(TrigBtn, TrigBtn)));
        initializeButton(letBtn, "let[x]=", actionEvent -> addText(new ActionEvent(letBtn, letBtn)));
        initializeButton(empty, "-", actionEvent -> addText(new ActionEvent(empty, empty)));
        initializeButton(functionsBtn, "FUNCTIONS", actionEvent -> showFunctionsMenu(null));
        initializeButton(convBtn, "CONVERT", actionEvent -> {
            try {
                conv(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        initializeButton(ConstantsBtn, "CONSTANTS", actionEvent -> {
            try {
                showConstantMenu(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        initializeButton(MatrixBtn, "MATRIX", actionEvent -> addText(new ActionEvent(MatrixBtn, MatrixBtn)));
        initializeButton(ArrayBtn, "ARRAY", actionEvent -> addText(new ActionEvent(ArrayBtn, ArrayBtn)));
        initializeButton(AbsoluteBtn, "|x|", actionEvent -> addText(new ActionEvent(AbsoluteBtn, AbsoluteBtn)));
        initializeButton(FractionBtn, "FRAC", actionEvent -> addText(new ActionEvent(FractionBtn, FractionBtn)));
        initializeButton(PercentBtn, "%", actionEvent -> addText(new ActionEvent(PercentBtn, PercentBtn)));
        initializeButton(InequalityBtn, "INEQUALITY", actionEvent -> Inequality(null));
        initializeButton(InfinityBtn, "∞", actionEvent -> addText(new ActionEvent(InfinityBtn, InfinityBtn)));
        initializeButton(SimplifyBtn, "SIMPLIFY", actionEvent -> simplify(null));
        initializeButton(solveBtn, "SOLVE", actionEvent -> solve(null));
        initializeButton(expandBtn, "EXPAND", actionEvent -> expand(null));
        initializeButton(factorBtn, "FACTOR", actionEvent -> factor(null));
        initializeButton(HCFBtn, "HCF", actionEvent -> HCF(null));
        initializeButton(LCFBtn, "LCF", actionEvent -> LCF(null));
        initializeButton(power10, "10^x", actionEvent -> addText(new ActionEvent(power10, power10)));
        initializeButton(cubeRoot, "∛", actionEvent -> addText(new ActionEvent(cubeRoot, cubeRoot)));
        initializeButton(rootBtn, "√", actionEvent -> addText(new ActionEvent(rootBtn, rootBtn)));
        initializeButton(braoBtn, "(", actionEvent -> addText(new ActionEvent(braoBtn, braoBtn)));
        initializeButton(plus, "+", actionEvent -> addText(new ActionEvent(plus, plus)));
        initializeButton(minus, "-", actionEvent -> addText(new ActionEvent(minus, minus)));
        initializeButton(multi, "*", actionEvent -> addText(new ActionEvent(multi, multi)));
        initializeButton(div, "/", actionEvent -> addText(new ActionEvent(div, div)));
        initializeButton(bracBtn, ")", actionEvent -> addText(new ActionEvent(bracBtn, bracBtn)));
    }

    public void addText(ActionEvent actionEvent) {
        final Node sender = (Node) actionEvent.getSource();
        switch (sender.getId()) {
            case "piBtn" -> addText(EquationField, "pi");
            case "eBtn" -> addText(EquationField, "e");
            case "sqrdBtn" -> addText(EquationField, "^2");
            case "poeBtn" -> addText(EquationField, "^e");
            case "power" -> addText(EquationField, "^");
            case "logNBtn" -> addText(EquationField, "_ln(");
            case "logBtn" -> addText(EquationField, "log(");
            case "sinBtn" -> addText(EquationField, "sin(");
            case "cosBtn" -> addText(EquationField, "cos(");
            case "tanBtn" -> addText(EquationField, "tan(");
            case "MatrixBtn" -> addText(EquationField, "|[][]|;|[][]|" );
            case "ArrayBtn" -> addText(EquationField, "[{},{}]");
            case "AbsoluteBtn" -> addText(EquationField, "|x|");
            case "FractionBtn" -> addText(EquationField, "/");
            case "PercentBtn" -> addText(EquationField, "%");
            case "InfinityBtn" -> addText(EquationField, "inf");
            case "power10" -> addText(EquationField, "*10^");
            case "cubeRoot" -> addText(EquationField, "cqrt[]{");
            case "rootBtn" -> addText(EquationField, "r(");
            case "braoBtn" -> addText(EquationField, "(");
            case "plus" -> addText(EquationField, "+");
            case "minus" -> addText(EquationField, "-");
            case "multi" -> addText(EquationField, "*");
            case "div" -> addText(EquationField, "/");
            case "bracBtn" -> addText(EquationField, ")");
            case "letBtn" -> addText(EquationField, "let[x]=");
            default -> throw new IllegalStateException("Unexpected value: " + actionEvent.getSource());
        }
    }

    private void addText(TextField field, String text) {
        field.setText(field.getText() + text);
    }

    public void emptyEquations(ActionEvent actionEvent) {
        try {
            util.clearFile("equations.txt");
            util.infoMessage("File Cleared Successfully", "Success");
        } catch (IOException e) {
            util.errorMessage("File could not be cleared", "Error");
        }
    }

    int currentEquation = loadedEquations.size();
    public void loadEquation(KeyEvent keyEvent) {
        if(!keyEvent.getCode().isArrowKey()) return;
        if(loadedEquations.isEmpty()) return;

        if(keyEvent.getCode() == KeyCode.UP) {
            if(currentEquation == 0) return;
            currentEquation--;
            EquationField.setText(loadedEquations.get(currentEquation));
        } else if(keyEvent.getCode() == KeyCode.DOWN) {
            if(currentEquation == loadedEquations.size() - 1) return;
            currentEquation++;
            EquationField.setText(loadedEquations.get(currentEquation));
        }
    }

    public static List<String> readEqs() {
        try {
            return util.readFile("equations.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshEquationList() {
        loadedEquations = readEqs();
    }

    public void _clearType() {
        type = "equation";
    }

    public void showTrig(ActionEvent actionEvent) {
        initializeButton(piBtn, "sin()", actionEvent1 -> setText(EquationField, "sin("));
        initializeButton(eBtn, "cos()", actionEvent1 -> setText(EquationField, "cos("));
        initializeButton(sqrdBtn, "tan()", actionEvent1 -> setText(EquationField, "tan("));
        initializeButton(poeBtn, "arcsin()", actionEvent1 -> setText(EquationField, "arcsin("));
        initializeButton(power, "arccos()", actionEvent1 -> setText(EquationField, "arccos("));
        initializeButton(logNBtn, "arctan()", actionEvent1 -> setText(EquationField, "arctan("));
        initializeButton(plus, "X", actionEvent1 -> remapKeys());

        List<Object> empty = List.of(logBtn, TrigBtn, letBtn, ConstantsBtn, MatrixBtn, ArrayBtn, AbsoluteBtn, FractionBtn, PercentBtn, InequalityBtn, SimplifyBtn, HCFBtn, LCFBtn, power10, cubeRoot, minus, multi, div, InfinityBtn, functionsBtn, convBtn, solveBtn, expandBtn, factorBtn, rootBtn, braoBtn, bracBtn);
        setElementToEmpty(empty);
    }
}