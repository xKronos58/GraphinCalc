package com.example.gcalc;

import com.example.gcalc.Calculator.ConvertCoPx;
import com.example.gcalc.Calculator.HandleStack;
import com.example.gcalc.Calculator.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GCMain extends Application {
    public static boolean isGraphingCalc = true;

    /** Main text field for calculation input */
    static TextField tf = new TextField();
    static Group equationsParent = new Group();
    static VBox equations = new VBox();
    static Group gp = new Group();
    static Button help = new Button("?");
    static Scene main = drawGraphingCalc();

    /** Main stage should be used when outside main class */
    static Stage _stage = null;

    /**
     * List of all the graphs that have been saved.
     * */
    public static List<String> graphs;

    /** uuid for graphs, used for connection between equation and group*/
    int currentGraph = 0;

    /** Based off the keybindings.txt allows the user to change default keybindings*/
    public static List<KeyCode> keyBinds = getKeyBinds();

    public static List<intercepts> intercepts = new ArrayList<>();

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

        KeyCode changePhysics = keyBinds.get(0);
        KeyCode cycleUp = keyBinds.get(1);
        KeyCode cycleDown = keyBinds.get(2);
        KeyCode acc = keyBinds.get(3);

        for(KeyCode k : keyBinds)
            System.out.println(k);

        // Default accuracy is 0.01; once settings menus are
        // implemented it will be pulled from the performance
        // tab inside the graphing section.
        double accuracy = 0.001;
        tf.setOnKeyPressed( event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!isGraphingCalc)
                    gp.getChildren().add(addAns(Main.tfEval(tf.getText()), tf.getText()));

                //Saves the graph
                try {
                    util.writeFile("graphs.txt", tf.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                refreshGraphsList();
                currentGraph++;
                // When enter is pressed, it creates a line array
                // which essentially acts as a node array but
                // Has a better renderer for the user.
                // From there it calculates the x & y pos of the
                // point in the graph and adds a line with those
                // positions.
                boolean xy = tf.getText().charAt(2) == 'x';
                Line[] graphLine = (xy ? populateGraphX(graph(accuracy, true), tf.getText().substring(5), accuracy, currentGraph + "")
                        : populateGraphY(graph(accuracy, false), tf.getText().substring(5), accuracy));
                Text graphText = new Text(tf.getText());
                equations.getChildren().add(graphText);
                Group graph = new Group();

                // Sets the id of the graph and the respective equation to the current graph number
                // Note:
                // The id number is not respective to the current index of graphs on the grid
                // as it is just an uuid for each graph.
                graph.setId(currentGraph + "");
                graphText.setId(currentGraph + "");

                // Adds all the lines to the group to be added to the main pane.
                for (Line x : graphLine) graph.getChildren().add(x);

                // Using the id of the graph, it removes the graph from the grid and the equation from the list.
                gp.getChildren().add(graph);
                graphText.setOnMouseClicked(e -> {
                    graphOptions(graphText);
                });

                // Resets the text field to the default value.
                tf.setText("f(x)=");
            } else if (event.getCode() == changePhysics) {
                // In the case the p is pressed, it changes view to
                // the physics calculator.
                // This may be changed in future implementations
                    try {
                        displayCalc.PHYSICS.showCalc();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            } else if (event.getCode() == cycleUp)
                    loadLastGraphEquation();     // Allows the user to cycle through past graphs.
            else if (event.getCode() == cycleDown)
                    loadNextGraphEquation();
            else if (event.getCode() == acc) // Displays the current accuracy of the graph.
                    util.infoMessage("Accuracy is " + accuracy + " px/x", "Accuracy");
        });
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

    static void findZeros(String id) {
        for(intercepts x : intercepts) {
            if(!Objects.equals(x.id, id)) continue;

            for(point y : x.zeros) {
                if (Math.round(y.rawY) == 0 && Math.round(y.rawX) == 0) continue;

                Line line = new Line(y.x, y.y, y.x, y.y);
                line.setStroke(Color.RED);
                line.setStrokeWidth(5);

                Text text = new Text(String.format("(%.2f, %.2f)", y.rawX, y.rawY));
                text.setX(y.x);
                text.setY(y.y);
                text.setId(id);
                text.setFont(new javafx.scene.text.Font(16));

                gp.getChildren().addAll(line, text);

            }

        }
    }

    public static void graphOptions(Text graphText) {
        Button RemoveBtn = new Button("Remove");
        RemoveBtn.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));
        Button ZeroBtn = new Button("Find");
        ZeroBtn.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));
        VBox main = new VBox(
                new Group(new HBox(new Text("Remove Graph"), RemoveBtn)),
                new Group(new HBox(new Text("Find intersects"), ZeroBtn))
        );

        Scene graphOptions = new Scene(main, 200, 200);
        Stage stage = new Stage();
        stage.setScene(graphOptions);
        stage.show();

        RemoveBtn.setOnAction(actionEvent -> {
            for (Node x : gp.getChildren()) {
                if (x.getId() == null) continue;
                if (!x.getId().equals(graphText.getId())) continue;
                gp.getChildren().remove(x);
                equations.getChildren().remove(graphText);
                stage.close();
                break;
            }
        });

        ZeroBtn.setOnAction(actionEvent -> {
            findZeros(graphText.getId());
        });
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
    public static Line[] populateGraphX(Line[] graph, String equation, double accuracy, String id) {
        List<point> points = new ArrayList<>();
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

            if(String.format("%.2f", y).equals("0.00"))
                points.add(new point(xConverted, yConverted, x, y));
            else if(String.format("%.2f", x).equals("0.00"))
                points.add(new point(xConverted, yConverted, x, y));

            if(yConverted > 300 || yConverted < 0) {
                graph[i] = new Line(-1, -1, -1, -1);
                continue;
            }

            // Creates the line with the calculated points
            graph[i] = new Line(xConverted, yConverted, xConverted, yConverted);

            // Console output for the raw values as using breakpoints takes too long depending on accuracy
//            System.out.printf("(raw)(x = %s, y = %s)%n",String.format("%.2f",x),String.format("%.2f",y));

            // Visual customization for the graph
            graph[i].setStrokeWidth(2);
            graph[i].setStroke(color);
        }
        intercepts.add(new intercepts(points, id , equation));
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

    /** Randomly generates a color for the graph
     * @return Randomized color of five options*/
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

    /** Adds the answer to the group pane
     * @param ans double of the answer to the equation
     * @param eq String of the equation provided by the user
     * */
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

        tf.setMaxSize(150, 50);
        tf.setPrefSize(150, 50);
        tf.setText("f(x)=");
        help.setOnAction(GCMain::showHelp);
        help.setPrefSize(15, 15);
        help.setMaxSize(15, 15);
        help.setLayoutY(260);
        help.setLayoutX(15);
        equations.setPrefWidth(150);
        equations.setLayoutY(50);
        equationsParent.getChildren().addAll(equations, help);
        gp.getChildren().addAll(tf, equationsParent);
        //Creating a Group object
        for (Line line : BG_grid) gp.getChildren().add(line);

        //Creating a scene object
        return new Scene(gp, MaxX, MinX);
    }

    public static void showHelp(ActionEvent ae) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        Hyperlink test = new Hyperlink("https://github.com/xKronos58/GraphinCalc.git");
        alert.setContentText("""
                Welcome to the Graphing Calculator!
                To use the calculator, type in an equation in the text box and press enter.
                The equation must be in the form of f(x) = x^2 + 2x + 1.
                The calculator will then graph the equation.
                To change the accuracy of the graph, press the 'ALT' or 'Option' key.
                To cycle through past graphs, press the 'up' and 'down' arrow keys.
                To change to the physics calculator, press the 'p' key.
                To change the keybindings, press the 'k' key.
                Note: For the key inputs to work you must click on the input box.
                For more information or help go to GraphingCalc.git and read the README.md.
                """);
        // TODO Make custom fxml file for the help menu rather than an alert.
        alert.show();

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

    public static List<KeyCode> getKeyBinds() {
        try {
            return util.readFile("keybindings.txt").stream().map(KeyCode::valueOf).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public record point(double x, double y, double rawX, double rawY) {

    }

    public record intercepts(List<point> zeros, String id, String equation) {

    }
}