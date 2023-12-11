package com.example.gcalc;

import com.example.gcalc.Calculator.ConvertCoPx;
import com.example.gcalc.Calculator.HandleStack;
import com.example.gcalc.Calculator.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class GCMain extends Application {
    public static boolean isGraphingCalc = true;

    /** Main text field for calculation input */
    static TextField tf = new TextField();
    static VBox equations = new VBox();
    static Group gp = new Group();
    static Scene main = drawGraphingCalc();

    /** Main stage should be used when outside main class */
    static Stage _stage = null;

    /**
     * List of all the graphs that have been saved.
     * */
    public static List<String> graphs;

    int currentGraph = 0;

    /**
     * The main method to launch the calculator
     * run from the <code>this.main</code> class with no args
     * */
    @Override
    public void start(Stage stage) {

        _stage = stage;

        // Setting title & icon of the Stage
        stage.setTitle("Graphing Calculator");
        try { stage.getIcons().add(new Image(String.valueOf(GCMain.class.getResource("Images/Icon.png")))); } catch (NullPointerException e) {System.out.println("Icon not found");}

        // Adds the scene to the stage
        stage.setScene(main);

        // Displaying the contents of the stage
        stage.show();

        //Paths to history files.
        Path equationsPath = Path.of(System.getProperty("user.dir"), "SavedEquations", "equations.txt");
        Path graphsPath = Path.of(System.getProperty("user.dir"), "SavedEquations", "graphs.txt");

        //Creates the history files if they don't exist
        try {
            if(!Files.exists(graphsPath))
                Files.createFile(graphsPath);
            if(!Files.exists(equationsPath))
                Files.createFile(equationsPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Loads / Refreshes the history list
        refreshGraphsList();

        // Default accuracy is 0.01; once settings menus are
        // implemented it will be pulled from the performance
        // tab inside the graphing section.
        double accuracy = 0.01;
        tf.setOnKeyPressed( event -> {
            switch (event.getCode()) {
                case ENTER -> {
                    if (!isGraphingCalc)
                        gp.getChildren().add(addAns(Main.tfEval(tf.getText()), tf.getText()));

                    //Saves the graph
                    try {
                        util.writeFile("graphs.txt", tf.getText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    refreshGraphsList();
                    // When enter is pressed, it creates a line array
                    // which essentially acts as a node array but
                    // Has a better renderer for the user.
                    // From there it calculates the x & y pos of the
                    // point in the graph and adds a line with those
                    // positions.
                    boolean xy = tf.getText().charAt(2) == 'x';
                    //TODO: Optimize Memory Usage
                    Line[] graphLine = (xy ? populateGraphX(graph(accuracy, true), tf.getText().substring(5), accuracy)
                            : populateGraphY(graph(accuracy, false), tf.getText().substring(5), accuracy));
                    Text graphText = new Text(tf.getText());
                    equations.getChildren().add(graphText);
                    Group graph = new Group();

                    // Sets the id of the graph and the respective equation to the current graph number
                    // Note:
                    // The id number is not respective to the current index of graphs on the grid
                    // as it is just an uuid for each graph.
                    currentGraph++;
                    graph.setId(currentGraph + "");
                    graphText.setId(currentGraph + "");

                    // Adds all the lines to the group to be added to the main pane.
                    for(Line x : graphLine) graph.getChildren().add(x);

                    // Using the id of the graph, it removes the graph from the grid and the equation from the list.
                    gp.getChildren().add(graph);
                    graphText.setOnMouseClicked(e -> {
                        for(Node x : gp.getChildren()) {
                            if(x.getId() == null) continue;
                            if(!x.getId().equals(graphText.getId())) continue;
                            gp.getChildren().remove(x);
                            equations.getChildren().remove(graphText);
                            break;
                        }
                    });

                    // Resets the text field to the default value.
                    tf.setText("f(x)=");
                }
                case P ->
                    // In the case the p is pressed, it changes view to
                    // the physics calculator.
                    // This may be changed in future implementations
                {
                    try {
                        displayCalc.PHYSICS.showCalc();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                // Allows the user to cycle through past graphs.
                case UP ->
                    loadLastGraphEquation();
                case DOWN ->
                    loadNextGraphEquation();
                // Displays the current accuracy of the graph.
                case ALT ->
                    util.infoMessage("Accuracy is " + accuracy, "Accuracy");
            }
        } );

    }

    /**
     * Refreshes the list of graphs that have been saved.
     * */
    static void refreshGraphsList() {
        try {
            graphs = util.readFile("graphs.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Current point in the list of graphs.
     * */
    private int currentPos = graphs == null ? 0 : graphs.size() - 1;
    private void loadLastGraphEquation() {
        if(graphs.isEmpty()) return;
        if(currentPos < 0) currentPos = graphs.size() - 1;
        tf.setText(graphs.get(currentPos));
        currentPos--;
    }

    private void loadNextGraphEquation() {
        if(graphs.isEmpty()) return;
        if(currentPos > graphs.size() - 1) currentPos = 0;
        tf.setText(graphs.get(currentPos));
        currentPos++;
    }

    /** Main method */
    public static void main(String[] args){
        launch(args);
    }

    /** Builds the main line array when a graph is added to the grid.
     * @param accuracy double of 0.01d by default, modifies how smooth the line generated by the program is*/
    public static Line[] graph(double accuracy, boolean xy) {
        // XY : true = f(x), false = f(y)
        return new Line[(int)((xy ? 30 : 15)/accuracy)];
    }

    /**
     * Uses the provided equation and calculates where all the lines should go on the grid.
     * @param graph takes the graph method which builds a graph array based of the accuracy
     * @param accuracy double of 0.01d by default, modifies how smooth the line generated by the program is
     * @param equation String of the graph equation provided by the user
     * */
    public static Line[] populateGraphX(Line[] graph, String equation, double accuracy) {
        Color color = RandomColor();
        for(int i = 0; i < graph.length; i++) {

            //Used variables,
            // x = raw x value of the graph,
            // y = raw y value of the graph,
            // xConverted = the px value of the position of x
            // yConverted = the px value of the position of y
            // Scale X(Y) = the scale of the graph in raw values
            double ScaleX = 15, ScaleY = ScaleX/2;
            double x = i*accuracy-ScaleX, y = HandleStack.evaluateGraphX(equation, (i * accuracy) - ScaleX),
                    xConverted = ConvertCoPx.convertX(x, ScaleX), yConverted = ConvertCoPx.convertY(y, ScaleY);

            // Creates the line with the calculated points
            graph[i] = new Line(xConverted, yConverted, xConverted, yConverted);

            // Console output for the raw values as using breakpoints takes too long depending on accuracy
//            System.out.printf("(raw)(x = %s, y = %s)%n",String.format("%.2f",x),String.format("%.2f",y));

            // Visual customization for the graph
            graph[i].setStrokeWidth(2);
            graph[i].setStroke(color);
        }
        // Returns the graph array to be added to the group pane.
        return graph;
    }

    public static Line[] populateGraphY(Line[] graph, String equation, double accuracy) {
        Color color = RandomColor();
        for(int i = 0; i < graph.length; i++) {
            double ScaleX = 15, ScaleY = ScaleX/2;
            double x = HandleStack.evaluateGraphY(equation, (i * accuracy) - ScaleY), y = i*accuracy-ScaleY,
                    xConverted = ConvertCoPx.convertX(x, ScaleX), yConverted = ConvertCoPx.convertY(y, ScaleY);

            if(xConverted < 150){
                graph[i] = new Line(-1, -1, -1, -1);
                continue;
            }
            graph[i] = new Line(xConverted, yConverted, xConverted, yConverted);

            System.out.printf("(raw)(x = %s, y = %s)%n",String.format("%.2f",x),String.format("%.2f",y));

            graph[i].setStrokeWidth(2);
            graph[i].setStroke(color);
        }
        return graph;
    }

    public static Color RandomColor() {
        Random r = new Random();
        return switch (r.nextInt(5)) {
            case 1 -> Color.RED;
            case 2 -> Color.BLUE;
            case 3 -> Color.GREEN;
            case 4 -> Color.ORANGE;
            default -> Color.BLACK;
        };
    }

    public static Text addAns(double ans, String eq) {
        Text text = new Text();
        text.setX(1);
        text.setText(eq + " = " + ans);
        return text;
    }

    /** Uses iteration to create a grid of lines with an obvious x and y-axis. */
    private static Scene drawGraphingCalc() {
        // DRAW BACKGROUND

        int MaxX = 750, MinX = 300;

        //Creating Lines
        Line[] BG_grid = new Line[48];

        //Creates the vertical lines
        for (int i = 0; i < BG_grid.length - 18; i++){
            BG_grid[i] = new Line(150 + 20 * i, 0,150 +  20 * i, MinX);
            BG_grid[i].setStroke(Color.rgb(143, 147, 187));
        }

        //Creates the horizontal lines
        for(int i = 30; i < BG_grid.length -3; i++){
            BG_grid[i] = new Line(150, 10+ 20 * (i -30), MaxX, 10+ 20 * (i -30));
            BG_grid[i].setStroke(Color.rgb(143, 147, 187));
        }

        BG_grid[45] = new Line(450, 0, 450, MinX);
        BG_grid[46] = new Line(150, 150, MaxX, 150);
        BG_grid[45].setStrokeWidth(2);
        BG_grid[46].setStrokeWidth(2);

        //Creates separation line
        BG_grid[47] = new Line(150, 0, 150, MinX);
        BG_grid[47].setStrokeWidth(3);

        //Adds the text box for input
        tf.setMaxWidth(150);
        tf.setPrefWidth(150);
        tf.setMaxHeight(50);
        tf.setPrefHeight(50);
        tf.snapPositionX(600);
        tf.setText("f(x)=");
        equations.setPrefWidth(150);
        equations.setLayoutY(50);

        gp.getChildren().add(tf);
        gp.getChildren().add(equations);
        //Creating a Group object
        for (Line line : BG_grid) gp.getChildren().add(line);

        //Creating a scene object
        return new Scene(gp, MaxX, MinX);
    }

    //NOTE Change a public stage rather than parse it through the methods as it can not be parsed into the controller where
    //     the methods are called. As it would have to be parsed through several override methods.

    /**Changes active calculator UI
     * @param calc Either "Physics", "Scientific" or "Calculus" sets which calculator ui is to be shown*/
    public static void showCalc(displayCalc calc) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                switch (calc) {
                    case PHYSICS -> GCMain.class.getResource("PhysicsCalc.fxml");
                    case SCIENTIFIC -> GCMain.class.getResource("ScientificCalc.fxml");
                    case CALCULUS -> GCMain.class.getResource("CalculusCalc.fxml");
                    case GRAPHING -> null; // Will never be reached.
                });
        Parent parent = loader.load();
        Scene mainCallWindow = new Scene(parent, 770, 406);
        _stage.setScene(mainCallWindow);
        _stage.setTitle(calc + " Calculator");
    }

    /** Enum for the different calculators that can be displayed */
    public enum displayCalc {
        PHYSICS {
            @Override
            public void showCalc() throws IOException {
                GCMain.showCalc(displayCalc.PHYSICS);
            }
        }, SCIENTIFIC {
            @Override
            public void showCalc() throws IOException {
                GCMain.showCalc(displayCalc.SCIENTIFIC);
            }
        }, CALCULUS {
            @Override
            public void showCalc() throws IOException {
                GCMain.showCalc(displayCalc.CALCULUS);
            }
        }, GRAPHING {
            @Override
            public void showCalc() {
                _stage.setScene(main);
                _stage.setTitle("Graphing Calculator");
            }
        };

        public abstract void showCalc() throws IOException;
    }
}