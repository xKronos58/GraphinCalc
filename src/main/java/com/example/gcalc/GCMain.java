package com.example.gcalc;

import com.example.gcalc.Calculator.HandleStack;
import com.example.gcalc.Calculator.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

public class GCMain extends Application {
    public static boolean isGraphingCalc = true;
    static TextField tf = new TextField();
    static VBox equations = new VBox();
    static Group gp = new Group();
    static Scene main = drawGraphingCalc();
    static Stage _stage = null;

    @Override
    public void start(Stage stage) {

        _stage = stage;

        //Setting title to the Stage
        stage.setTitle("Graphing Calculator");
        stage.getIcons().add(new Image(String.valueOf(GCMain.class.getResource("Images/Icon.png"))));

        //Adding scene to the stage
        stage.setScene(main);

        //Displaying the contents of the stage
        stage.show();

        tf.setOnKeyPressed( event -> {
            switch (event.getCode()) {
                case ENTER -> {
                    if (isGraphingCalc)
                    {
                        Line[] pPoints = positive(tf.getText()), nPoints = negative(tf.getText());
                        Text graph = new Text(tf.getText());
                        equations.getChildren().add(graph);
                        for (Line point : pPoints) gp.getChildren().add(point);
                        for (Line point : nPoints) gp.getChildren().add(point);
                        tf.setText("y=");
                    }
                    else
                        gp.getChildren().add(addAns(Main.tfEval(tf.getText()), tf.getText()));
                }
                case P -> {
                    try {
                        ShowPhysicsCalc();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } );

    }
    public static void main(String[] args){
        launch(args);
    }

    public static Line[] positive(String equation) {

        Line[] out = new Line[150];
        double[] x = new double[300], y = new double[300];
        x[0] = 450.0; // X 0 point on the graph (in pixels)
        y[0] = 150.0; // Y 0 point on the graph (in pixels)

        for(int i = 0; i < out.length; i++){
            if(i == 0)
                out[i] = new Line(x[0], y[0], x[0], y[0]);
            else {
                x[i] = x[0] + (i * 2);
                y[i] = y[0] - (HandleStack.evaluateGraph(equation, (double) i/5) * 10);
                makeLine(out, x, y, i);
//                if(offGrid(x[i], y[i])) TODO modify so that it cuts lines that pass off the screen to be removed for efficiency
//                    return out;
            }
        }

        return out;
    }

    public static Line[] negative(String equation) {

        Line[] out = new Line[150];
        double[] x = new double[300], y = new double[300];
        x[0] = 450.0; // X 0 point on the graph (in pixels)
        y[0] = 150.0; // Y 0 point on the graph (in pixels)

        for(int i = 0; i < out.length; i++){
            if(i == 0)
                out[i] = new Line(x[0], y[0], x[0], y[0]);
            else {
                x[i] = x[0] + (i * 2) * -1;
                y[i] = y[0] - (HandleStack.evaluateGraph(equation, (double) i/5) * 10) * -1;
                makeLine(out, x, y, i);
//                if(offGrid(x[i], y[i])) TODO modify so that it cuts lines that pass off the screen to be removed for efficiency
//                    return out;
            }
        }

        return out;
    }

    private static boolean offGrid(double x, double y) {
        return x < 150 || x > 750 || y > 300 || y < 0;
    }

    private static void makeLine(Line[] out, double[] x, double[] y, int i) {
        out[i] = new Line(
                x[i], y[i],
                x[i-1], y[i-1]);

        out[i].setStroke(Color.RED);
        out[i].setStrokeWidth(2);

        System.out.println("(" + x[i] + ", " + y[i] + ")");
    }

    public static void ShowGraphingCalc() {
        _stage.setScene(main);
        _stage.setTitle("Graphing Calculator");
    }

    //NOTE Change a public stage rather than parse it through the methods as it can not be parsed into the controller where
    //     the methods are called.

    public static void ShowPhysicsCalc() throws IOException {
        FXMLLoader loader = new FXMLLoader(GCMain.class.getResource("PhysicsCalc.fxml"));
        Parent PhysicsCalc = loader.load();
        _stage.setTitle("Physics Calculator");
        Scene mainCallWindow = new Scene (PhysicsCalc, 750, 300);
        _stage.setScene(mainCallWindow);
    }

    public static void ShowScientificCalc() throws IOException {
        FXMLLoader loader = new FXMLLoader(GCMain.class.getResource("ScientificCalc.fxml"));
        Parent ScientificCalc = loader.load();
        _stage.setTitle("Scientific Calculator");
        Scene mainCallWindow = new Scene(ScientificCalc, 200, 600);
        _stage.setScene(mainCallWindow);
    }

    public static void ShowBasicCalc() throws IOException {
        FXMLLoader loader = new FXMLLoader(GCMain.class.getResource("BasicCalc.fxml"));
        Parent BasicCalc = loader.load();
        _stage.setTitle("Calculator");
        Scene mainCallWindow = new Scene(BasicCalc, 200, 600);
        _stage.setScene(mainCallWindow);
    }

    public static Text addAns(double ans, String eq) {
        Text text = new Text();
        text.setX(1);
        text.setText(eq + " = " + ans);
        return text;
    }

    private static Scene drawGraphingCalc() {
        // DRAW BACKGROUND

        Line[] BG_grid = new Line[48];
        for (int i = 0; i < BG_grid.length - 18; i++){
            BG_grid[i] = new Line(150 + 20 * i, 0,150 +  20 * i, 300);
            BG_grid[i].setStroke(Color.rgb(143, 147, 187));
        }

        for(int i = 30; i < BG_grid.length -3; i++){
            BG_grid[i] = new Line(150, 10+ 20 * (i -30), 750, 10+ 20 * (i -30));
            BG_grid[i].setStroke(Color.rgb(143, 147, 187));
        }

        BG_grid[45] = new Line(450, 0, 450, 300);
        BG_grid[46] = new Line(150, 150, 750, 150);
        BG_grid[45].setStrokeWidth(2);
        BG_grid[46].setStrokeWidth(2);

        BG_grid[47] = new Line(150, 0, 150, 300);
        BG_grid[47].setStrokeWidth(3);

        tf.setMaxWidth(150);
        tf.setPrefWidth(150);
        tf.setMaxHeight(50);
        tf.setPrefHeight(50);
        tf.snapPositionX(600);
        tf.setText("y=");
        equations.setPrefWidth(150);
        equations.setLayoutY(50);

        gp.getChildren().add(tf);
        gp.getChildren().add(equations);
        //Creating a Group object
        for (Line line : BG_grid) gp.getChildren().add(line);

        //Creating a scene object
        return new Scene(gp, 750, 300);
    }
}