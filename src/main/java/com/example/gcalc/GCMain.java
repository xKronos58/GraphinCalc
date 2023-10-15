package com.example.gcalc;

import com.example.gcalc.Calculator.ConvertCoPx;
import com.example.gcalc.Calculator.EvalEquation;
import com.example.gcalc.Calculator.HandleStack;
import com.example.gcalc.Calculator.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
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

/*    public static Line[] evalGraph(String tfText) {
        System.out.println(tfText);

        // Theory :
        // Draw 10 points on the line of the graph
        // Then draw points between to save on
        // calculation time
        // then draw a line between the dots

        // Though, using y i can go through and plot a point at the position of the graph
        // This will require going through the stack and evaluating the equation with x being substituted

        Line[] positive = new Line[150];
        Line[] negative = new Line[150];

        for(int i = 0; i < 150; i++){
            initialPoints[i] = ConvertCoPx.convertX(HandleStack.evaluateGraph(tfText, i) - 15);
            points[i] = new Line(initialPoints[i], (i * 60) - 150 , initialPoints[i] + 2, (i * 60) - 150 ); // - This is the points
            points[i] = new Line(initialPoints[i - 1 == -1 ? 0 : i - 1], ConvertCoPx.convertY(i - 1), initialPoints[i], ConvertCoPx.convertY(i));
            System.out.println("(" + initialPoints[i] + ", " + i + ")");
            points[i].setStrokeWidth(2);
            points[i].setStroke(Color.RED);


            // Spawn the zero point
            // Spawn the next 2 points in 2 diff arrays one for pos 1 for neg

            double yp = f(x (i)) = eq HandleStack.evaluateGraph(tfText, i), yp2 = HandleStack.evaluateGraph(tfText, i + 1); //TODO cache for better performance
            double yn = f(x (i)) = eq HandleStack.evaluateGraph(tfText, i + -1), yn2 = HandleStack.evaluateGraph(tfText, (i * -1) + 1);

            if(i == 0) {
                positive[i] = new Line(300, 150, 320, yp2);
                negative[i] = new Line(300, 150, 280, yn2);
            } else {
                positive[i] = new Line((300 - i * 2), yp, (300 - (i+1) * 2), yp2);
                positive[i] = new Line((300 - (i * 2) * -1), yn, (300 - ((i+1) * 2) * -1), yn2);
            }



            //Reworking required with co-ordinates and graph evaluation

            // 1 : Add handling for negative numbers and multiplication without the need for '*'
            // 2 : Calculate the position in the graph with the fixed eval
            // 3 : change those values to correspond to the pixel values where they can be used to draw the graph
        }

        Line[] combined = new Line[positive.length + negative.length];

        System.arraycopy(positive, 0, combined, 0, positive.length);
        System.arraycopy(negative, 0, combined, positive.length, negative.length);

        return combined;

    }*/


    public static Line[] positive(String equation) {

        Line[] out = new Line[75];
        double[] x = new double[150], y = new double[150];
        x[0] = 450.0; // X 0 point on the graph (in pixels)
        y[0] = 150.0; // Y 0 point on the graph (in pixels)

        for(int i = 0; i < out.length; i++){
            if(i == 0)
                out[i] = new Line(x[0], y[0], x[0], y[0]);
            else {
                x[i] = x[0] + (i * 2);
                y[i] = y[0] - (HandleStack.evaluateGraph(equation, (double) i/5) * 10);
                makeLine(out, x, y, i);
            }
        }

        return out;
    }

    public static Line[] negative(String equation) {

        Line[] out = new Line[75];
        double[] x = new double[150], y = new double[150];
        x[0] = 450.0; // X 0 point on the graph (in pixels)
        y[0] = 150.0; // Y 0 point on the graph (in pixels)

        for(int i = 0; i < out.length; i++){
            if(i == 0)
                out[i] = new Line(x[0], y[0], x[0], y[0]);
            else {
                x[i] = x[0] + (i * 2) * -1;
                y[i] = y[0] - (HandleStack.evaluateGraph(equation, (double) i/5) * 10) * -1;
                makeLine(out, x, y, i);
            }
        }

        return out;
    }

    private static void makeLine(Line[] out, double[] x, double[] y, int i) {
        out[i] = new Line(
                x[i], y[i],
                x[i-1], y[i-1]);

        out[i].setStroke(Color.RED);
        out[i].setStrokeWidth(1);

        System.out.println("(" + x[i] + ", " + y[i] + ")");
    }

    public static void ShowGraphingCalc() {
        _stage.setScene(main);
    }

    //NOTE Change a public stage rather than parse it through the methods as it can not be parsed into the controller where
    //     the methods are called.

    public static void ShowPhysicsCalc() throws IOException {
        FXMLLoader loader = new FXMLLoader(GCMain.class.getResource("PhysicsCalc.fxml"));
        Parent PhysicsCalc = loader.load();

        Scene mainCallWindow = new Scene (PhysicsCalc, 800, 600);
        _stage.setScene(mainCallWindow);
    }

    public static void ShowScientificCalc() throws IOException {
        FXMLLoader loader = new FXMLLoader(GCMain.class.getResource("ScientificCalc.fxml"));
        Parent ScientificCalc = loader.load();

        Scene mainCallWindow = new Scene(ScientificCalc, 200, 600);
        _stage.setScene(mainCallWindow);
    }

    public static void ShowBasicCalc() throws IOException {
        FXMLLoader loader = new FXMLLoader(GCMain.class.getResource("BasicCalc.fxml"));
        Parent BasicCalc = loader.load();

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