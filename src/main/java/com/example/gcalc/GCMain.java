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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GCMain extends Application {
    public static boolean isGraphingCalc = true;
    static TextField tf = new TextField();
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
                        Line[] points = evalGraph(tf.getText());
                        for (Line point : points) gp.getChildren().add(point);
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
    public static void main(String args[]){
        launch(args);
    }

    public static Line[] evalGraph(String tfText) {
        System.out.println(tfText);

        // Theory :
        // Draw 10 points on the line of the graph
        // Then draw points between to save on
        // calculation time
        // then draw a line between the dots

        // Though, using f(x) i can go through and plot a point at the position of the graph
        // This will require going through the stack and evaluating the equation with x being substituted

        double[] initialPoints = new double[30];
        Line[] points = new Line[30];

        for(int i = 0; i < initialPoints.length; i++){
            initialPoints[i] = ConvertCoPx.convertX(HandleStack.evaluateGraph(tfText, i) - 15);
            System.out.print(initialPoints[i] + ", ");
//            points[i] = new Line(initialPoints[i], (i * 60) - 150 , initialPoints[i] + 2, (i * 60) - 150 ); // - This is the points
            points[i] = new Line(initialPoints[i - 1 == -1 ? 0 : i - 1], ConvertCoPx.convertY(i - 1), initialPoints[i], ConvertCoPx.convertY(i));
            points[i].setStrokeWidth(2);
            points[i].setStroke(Color.RED);

            //Reworking required with co-ordinates and graph evaluation

            // 1 : Add handling for negative numbers and multiplication without the need for '*'
            // 2 : Calculate the position in the graph with the fixed eval
            // 3 : change those values to correspond to the pixel values where they can be used to draw the graph
        }
        return points;
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

        gp.getChildren().add(tf);

        //Creating a Group object
        for (Line line : BG_grid) gp.getChildren().add(line);

        //Creating a scene object
        return new Scene(gp, 750, 300);
    }
}