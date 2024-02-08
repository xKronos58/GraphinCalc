package com.example.gcalc;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class util {

    /**
     * Takes start index and finds the nex point at which a car occurs and returns the position
     * */
    public static int until(int startIndex, String x, char until) {
        for(int i = startIndex; i < x.length(); i++)
            if(x.charAt(i) == until) return i;

        errorMessage("'" + until + "' not found inside string", "Error");
        throw new IllegalArgumentException("Char not found inside string");
    }

    public static int untilInequality(int startIndex, String x) {
        for(int i = startIndex; i < x.length(); i++)
            if(isInequality(x.charAt(i))) return i;

        errorMessage( "' inequality' not found inside string", "Error");
        throw new IllegalArgumentException("Char not found inside string");
    }

    /**
     * Takes a `Start index` and returns the position of the next operator inside the string.
     * */
    public static int untilOperator(int startIndex, String x) {
        for(int i = startIndex; i < x.length(); i++)
            if(isOperator(x.charAt(i))) return i;

        errorMessage("' operator" + "' not found inside string", "Error");
        throw new IllegalArgumentException("Char not found inside string");
    }

    /**
     * Takes a start index and then returns the position of the next digit inside the string.
     * */
    public static int untilNum(int startIndex, String x) {
        for(int i = startIndex; i < x.length(); i++)
            if(Character.isDigit(x.charAt(i))) return i;

        errorMessage("' Number" + "' not found inside string", "Error");
        throw new IllegalArgumentException("Char not found inside string");
    }

    /**
     * Takes a start index and then returns the position of the next english letter inside the string.
     * */
    public static int untilLetter(int startIndex, String x) {
        for(int i = startIndex; i < x.length(); i++)
            if(Character.isLetter(x.charAt(i))) return i;

        errorMessage("' letter" + "' not found inside string", "Error");
        throw new IllegalArgumentException("Char not found inside string");
    }

    public static int untilBasicOp(int startIndex, String x) {
        for (int i = startIndex; i < x.length(); i++)
            if(isBasicOperator(x.charAt(i)))
                return i;

        errorMessage("' + | - " + "' not found inside string", "Error");
        throw new IllegalArgumentException("Char not found inside string");
    }

    public static Predicate<String> contains(Character c) {
        return s -> s.indexOf(c) != -1;
    }

    public static boolean charCheck(int startIndex, String x) {
        for (int i = startIndex; i < x.length(); i++)
            if(Character.isLetter(x.charAt(i))) return true;
        return false;
    }

    /**
     * checks if the char is classified as an operator (+, -, *, /)
     * */
    public static boolean isOperator(char x) {
        return x == '+' || x == '-' || x == '*' || x == '/';
    }

    public static boolean isBasicOperator(char x) { return x == '+' || x == '-'; }

    public static boolean isInequality(char x) {
        return x == '>' || x == '<' || x == '=' || x == '!';
    }

    public static int countChar(int startIndex, String x, char c) {
        int o = 0;
        for(int i = startIndex; i < x.length(); i++)
            if(x.charAt(i) == c) o++;
        return o;
    }

    /**
     * Takes a start index and counts all remaining operators inside the string.
     * */
    public static int countOperator(int startIndex, String x) {
        int o = 0;
        for(int i = startIndex; i < x.length(); i++)
            if(isOperator(x.charAt(i))) o++;
        return o;
    }


    public static char[] pullVars(String equation, String rawEquation) {
        boolean isTerm = equation.charAt(0) == 't' && equation.charAt(1) == '=', c2 = has2Chars(rawEquation, isTerm);
        char[] temp = new char[c2 ? 2 : 1];
        for(int i = 0; i < temp.length; i++)
            temp[i] = rawEquation.charAt(rawEquation.length() - (isTerm ? 3 + (c2 ? i : i - 1) : 2 + (c2 ? i : i - 1))); // Need to swap positions inside the array
        // if there are 2 terms it should set number 1 to length - isTerm ? 3 : 2 and then number 2 in the array to length - isTErm ? 4 : 3

        return temp;
    }

    // Sample raw term = solve(t=quadratic(x^2+2x-4,x)) || solve(x^2+2x-4,x)
    // Sample with 2 t = solve(t=system(6x+3y=8;8x+1y=3,xy)) || solve(6x+3y=8;8x+1y=3,xy)
    public static boolean has2Chars(String rawEquation, boolean isTerm) {
        return (Character.isLetter(rawEquation.charAt(
                    isTerm ? rawEquation.length() - 3 : rawEquation.length() - 2)) &&
                Character.isLetter(rawEquation.charAt(
                    isTerm ? rawEquation.length() - 4 : rawEquation.length() - 3)));
    }

    /**
     * checks if the provided string as the `^` operator
     * @param startIndex index to start searching from
     * @param x string to search
     * @return true if the string has the `^` operator
     * */
    public static boolean hasPower(int startIndex, String x) {
        for(int i = startIndex; i < x.length(); i++)
            if(x.charAt(i) == '^') return true;

        return false;
    }

    /**
     * Takes a message and builds an error message with the message and title.
     * @param message message to be displayed
     * @param title title of the error message
     * */
    public static void errorMessage(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    public static void infoMessage(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Takes a file name and returns a list of all the lines inside the file.
     * @param fileName name of the file to read
     * @return List of all the lines inside the file
     * @throws IOException if the file does not exist
     */
    public static List<String> readFile(String fileName) throws IOException {
        Path filePath = Path.of(System.getProperty("user.dir"), "SavedEquations", fileName);
        if(!filePath.toFile().exists())
            throw new IllegalArgumentException("File does not exist");

        return Files.readAllLines(filePath);
    }

    public static Path convertFileToPath(String fileName) {
        return Path.of(System.getProperty("user.dir"), "SavedEquations", fileName);
    }

    /**
     * Takes a file name and a line and appends the line to the file.
     * @param  fileName name of the file to write to
     * @param  line String to be appended to the file
     * @throws IOException if the file does not exist
     * */
    public static void writeFile(String fileName, String line) throws IOException {
        Path filePath = Path.of(System.getProperty("user.dir"), "SavedEquations", fileName);
        Files.writeString(filePath, line + "\n", StandardOpenOption.APPEND);
    }

    /**
     * Takes a file name and clears the file.
     * @param  fileName name of the file to clear
     * @throws IOException if the file does not exist
     * */
    public static void clearFile(String fileName) throws IOException {
        Path filePath = Path.of(System.getProperty("user.dir"), "SavedEquations", fileName);
        Files.writeString(filePath, "", StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void replaceLine(Path filePath, int line) throws IOException {
        String t = Files.readString(filePath);
        System.out.println(t);
    }
}
