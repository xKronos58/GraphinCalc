package com.example.gcalc;

import com.example.gcalc.Calculator.EquationList;
import com.example.gcalc.Calculator.HandleStack;
import com.example.gcalc.Calculator.SimpleArithmetic;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

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
    public Button sqrtBtn;
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

    public static TextField State;
    public Button cDeriveBtn;
    public Button DeriveBtn;
    public Button aFxBtn;
    public Button integralBtn;
    public Button integralIZBtn;
    boolean menuOpen = false;

    @FXML
    protected void changeCalc(ActionEvent actionEvent) throws IOException {
        GCMain.displayCalc.GRAPHING.showCalc();
    }


    public void changeCalcS(ActionEvent actionEvent) throws IOException {
        GCMain.displayCalc.SCIENTIFIC.showCalc();
    }

    public void changeCalcB(ActionEvent actionEvent) throws IOException {
        GCMain.displayCalc.CALCULUS.showCalc();
    }

    public void changeCalcP(ActionEvent actionEvent) throws IOException {
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
            case "mfd", "spd", "mas", "sop", "tev" -> t = new Text(EquationField.getText() + "\n    = " +
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
        if(equationNum > 4 && !invalidEquation)
            scrollHeight += 38;
        t.prefWidth(600);
        textScreen.getChildren().add(t);
        EquationField.clear();
        textScreen.setPrefHeight(scrollHeight);
        scrollViewMain.setPrefHeight(scrollHeight);
        apScroolView.setPrefHeight(scrollHeight);

        invalidEquation = false;
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
        EquationField.setText(EquationField.getText() + "mfd(");
        type = "mfd";
    }

    public void spd(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "spd(");
        type = "spd";
    }

    public void mas(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "mas(");
        type = "mas";
    }

    public void sop(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "sop(");
        type = "sop";
    }

    public void conv(ActionEvent actionEvent) throws Exception {
        openPopup op = new openPopup();
        op.popupType = "convert";
        op.start(new Stage());
    }

    public void tev(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "tev(");
        type = "tev";
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

    public void openBrace(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "(");
    }

    public void closeBrace(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + ")");
    }

    public void arthsymb(ActionEvent actionEvent) throws Exception {
        openPopup op = new openPopup();
        Stage s = new Stage();
        op.popupType = "operator";
        op.start(s);
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

    public void addPlus(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "+");
    }

    public void addMinus(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "-");
    }

    public void addDivide(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "/");
    }

    public void addMult(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "*");
    }

    public void addAbsol(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "|");
    }

    public void addPower(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "^");
    }

    public void addEquals(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "=");
    }

    public static Parent LoadScene(String type) throws IOException {
        FXMLLoader x = switch (type) {
            case "general" -> new FXMLLoader(GCMain.class.getResource("Settings/SettingsSubPanes/general.fxml"));
            case "style" -> new FXMLLoader(GCMain.class.getResource("Settings/SettingsSubPanes/style.fxml"));
            case "performance" -> new FXMLLoader(GCMain.class.getResource("Settings/SettingsSubPanes/performance.fxml"));
            case "units" -> new FXMLLoader(GCMain.class.getResource("Settings/SettingsSubPanes/units.fxml"));
            case "import" -> new FXMLLoader(GCMain.class.getResource("Settings/SettingsSubPanes/import.fxml"));
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

    public void matrix(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "|[][]|;|[][]|");
    }

    public void array(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "[{},{}]");
    }

    public void absolute(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "|0|");
    }

    public void fraction(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "/");
    }

    public void percent(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "%");
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

    public void power10(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "*10^");
    }

    public void cubeRoot(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "cqrt[]{");
    }

    public void add(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "+");
    }

    public void minus(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "-");
    }

    public void mult(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "*");
    }

    public void div(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "/");
    }

    public void infinity(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "inf");
    }

    public void pow(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "^");
    }

    public void logn(ActionEvent actionEvent) {
        EquationField.setText(EquationField.getText() + "_ln(");
    }

    public void clearType(ActionEvent actionEvent) {
        type = "equation";
    }

    public void clear(ActionEvent actionEvent) {
        EquationField.setText("");
        type = "equation";
    }

    public void constantStartTunnel(ActionEvent actionEvent) {
        System.out.println(State.getText());
        State.setText(State.getText() + "pi");
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

    public void remapConstants() {
        initializeButton(sqrtBtn, "Tau", actionEvent -> EquationField.setText(EquationField.getText() + "tu"));
        initializeButton(poeBtn, "Phi", actionEvent -> EquationField.setText(EquationField.getText() + "ph"));
        initializeButton(power, "i", actionEvent -> EquationField.setText(EquationField.getText() + "i"));
        initializeButton(logNBtn, "Sgr", actionEvent -> EquationField.setText(EquationField.getText() + "sg"));
        initializeButton(logBtn, "Cchl", actionEvent -> EquationField.setText(EquationField.getText() + "mc"));
        initializeButton(sinBtn, "Kbc", actionEvent -> EquationField.setText(EquationField.getText() + "kb"));
        initializeButton(cosBtn, "Wc", actionEvent -> EquationField.setText(EquationField.getText() + "wc"));
        initializeButton(plus, "X", actionEvent -> remapKeys());

        List<Object> empty = List.of(tanBtn, ConstantsBtn, MatrixBtn, ArrayBtn, AbsoluteBtn, FractionBtn, PercentBtn, InequalityBtn, SimplifyBtn, HCFBtn, LCFBtn, power10, cubeRoot, minus, multi, div, InfinityBtn, functionsBtn, convBtn, solveBtn, expandBtn, factorBtn, rootBtn, braoBtn, bracBtn);
        setElementToEmpty(empty);
    }

    public void remapInequalities() {
        initializeButton(piBtn, "<", actionEvent -> EquationField.setText(EquationField.getText() + " <"));
        initializeButton(eBtn, ">", actionEvent -> EquationField.setText(EquationField.getText() + " >"));
        initializeButton(sqrtBtn, "<=", actionEvent -> EquationField.setText(EquationField.getText() + " <="));
        initializeButton(poeBtn, ">=", actionEvent -> EquationField.setText(EquationField.getText() + " >="));
        initializeButton(power, "!=", actionEvent -> EquationField.setText(EquationField.getText() + " !="));
        initializeButton(plus, "X", actionEvent -> remapKeys());

        List<Object> empty = List.of(logBtn, logNBtn, sinBtn, cosBtn, tanBtn, ConstantsBtn, MatrixBtn, ArrayBtn, AbsoluteBtn, FractionBtn, PercentBtn, InequalityBtn, SimplifyBtn, HCFBtn, LCFBtn, power10, cubeRoot, minus, multi, div, InfinityBtn, functionsBtn, convBtn, solveBtn, expandBtn, factorBtn, rootBtn, braoBtn, bracBtn);
        setElementToEmpty(empty);
    }

    public void remapFunctions() {
        initializeButton(piBtn, "MFD", actionEvent -> EquationField.setText(EquationField.getText() + "mfd("));
        initializeButton(eBtn, "SPD", actionEvent -> EquationField.setText(EquationField.getText() + "spd("));
        initializeButton(sqrtBtn, "MAS", actionEvent -> EquationField.setText(EquationField.getText() + "mas("));
        initializeButton(poeBtn, "SOP", actionEvent -> EquationField.setText(EquationField.getText() + "sop("));
        initializeButton(power, "TEV", actionEvent -> EquationField.setText(EquationField.getText() + "tev("));
        initializeButton(logNBtn, "ACL", actionEvent -> EquationField.setText(EquationField.getText() + "acl("));
        initializeButton(plus, "X", actionEvent -> remapKeys());

        List<Object> empty = List.of(logNBtn, sinBtn, cosBtn, tanBtn, ConstantsBtn, MatrixBtn, ArrayBtn, AbsoluteBtn, FractionBtn, PercentBtn, InequalityBtn, SimplifyBtn, HCFBtn, LCFBtn, power10, cubeRoot, minus, multi, div, InfinityBtn, functionsBtn, convBtn, solveBtn, expandBtn, factorBtn, rootBtn, braoBtn, bracBtn);
        setElementToEmpty(empty);
    }

    @SuppressWarnings("WriteOnlyObject")
    public static void setElementToEmpty(List<Object> objects) {
        var ref = new Object() {
            int temp = 0;
        };
        for (Object object : objects) {
            if (object instanceof Button b) {
                b.setText("");
                b.setOnAction(actionEvent -> ref.temp = 0); // Does nothing
            }
        }

    }

    private void remapKeys() {
        initializeButton(piBtn, "π", actionEvent -> addPi(null));
        initializeButton(eBtn, "e", actionEvent -> addE(null));
        initializeButton(sqrtBtn, "x^2", actionEvent -> sqrd(null));
        initializeButton(poeBtn, "e^", actionEvent -> poe(null));
        initializeButton(power, "x^y", actionEvent -> addPower(null));
        initializeButton(logNBtn, "ln()", actionEvent -> logn(null));
        initializeButton(logBtn, "log()", actionEvent -> log(null));
        initializeButton(sinBtn, "sin()", actionEvent -> sin(null));
        initializeButton(cosBtn, "cos()", actionEvent -> cos(null));
        initializeButton(tanBtn, "tan()", actionEvent -> tan(null));
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
        initializeButton(MatrixBtn, "MATRIX", actionEvent -> matrix(new ActionEvent(MatrixBtn, MatrixBtn)));
        initializeButton(ArrayBtn, "ARRAY", actionEvent -> array(null));
        initializeButton(AbsoluteBtn, "|x|", actionEvent -> absolute(null));
        initializeButton(FractionBtn, "FRAC", actionEvent -> fraction(null));
        initializeButton(PercentBtn, "%", actionEvent -> percent(null));
        initializeButton(InequalityBtn, "INEQUALITY", actionEvent -> Inequality(null));
        initializeButton(InfinityBtn, "∞", actionEvent -> infinity(null));
        initializeButton(SimplifyBtn, "SIMPLIFY", actionEvent -> simplify(null));
        initializeButton(solveBtn, "SOLVE", actionEvent -> solve(null));
        initializeButton(expandBtn, "EXPAND", actionEvent -> expand(null));
        initializeButton(factorBtn, "FACTOR", actionEvent -> factor(null));
        initializeButton(HCFBtn, "HCF", actionEvent -> HCF(null));
        initializeButton(LCFBtn, "LCF", actionEvent -> LCF(null));
        initializeButton(power10, "10^x", actionEvent -> power10(null));
        initializeButton(cubeRoot, "∛", actionEvent -> cubeRoot(null));
        initializeButton(rootBtn, "√", actionEvent -> root(null));
        initializeButton(braoBtn, "(", actionEvent -> openBrace(null));
        initializeButton(plus, "+", actionEvent -> addPlus(null));
        initializeButton(minus, "-", actionEvent -> addMinus(null));
        initializeButton(multi, "*", actionEvent -> addMult(null));
        initializeButton(div, "/", actionEvent -> addDivide(null));
        initializeButton(bracBtn, ")", actionEvent -> closeBrace(null));
    }

    public void addText(ActionEvent actionEvent) {
        final Node sender = (Node) actionEvent.getSource();
        switch (sender.getId()) {
            case "piBtn" -> addText(EquationField, "pi");
            case "eBtn" -> addText(EquationField, "e");
            default -> throw new IllegalStateException("Unexpected value: " + actionEvent.getSource());
        }
    }

    private void addText(TextField field, String text) {
        field.setText(field.getText() + text);
    }
}