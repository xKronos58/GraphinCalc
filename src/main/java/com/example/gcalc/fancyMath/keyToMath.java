package com.example.gcalc.fancyMath;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class keyToMath {
    public KeyCode key;
    public String currentEquation = "";
    public Integer positionSinceLastUpdate = 0;
    public KeyCode lastKey;
    public boolean statementCompleted = false;
    List<enclosingTag> tags = new ArrayList<>();


    public keyToMath(KeyCode key) {
        List<supportedSymbols> mappable = new ArrayList<>();
        this.key = key;
        this.currentEquation += key.getChar();

        mappable = keyPressed(this.key, mappable, lastKey);
        if(mappable.size() == 1)
            convertStringToChars(currentEquation, mappable.get(0), List.of());
    }

    public void convertStringToChars(String currentEquation, supportedSymbols equation, List<?> subObjects) {
        positionSinceLastUpdate = 0;
        enclosingTag current = switch (equation) {
            case FRACTION -> new enclosingTag("<mfrac>", List.of(), "</mfrac>");
            case CUBE_ROOT -> new enclosingTag("", List.of(), "");
            default -> null;
        };
    }

    public List<supportedSymbols> keyPressed(KeyCode key, List<supportedSymbols> possible, KeyCode lastKey) {

        /*
        * Boilerplate code :
        * Add if missing
        *   if(!possible.contains(supportedSymbols.x)) possible.add(supportedSymbols.x);
        * remove nonPossible
        *   possible.removeIf(s -> !s.equals(supportedSymbols.x));
        * */
        this.lastKey = key;
        positionSinceLastUpdate++;
        return switch (key){
            case A, C, D, E, F, G, H, I, J, K, L, M, N, O, P, U, V, W, Y, X, Z -> {
                if (!possible.contains(supportedSymbols.VARIABLE)) possible.add(supportedSymbols.VARIABLE);
                possible.removeIf(s -> !s.equals(supportedSymbols.VARIABLE));
                statementCompleted = true;
                yield possible;
            } case B -> {
                if(lastKey.equals(KeyCode.C) && possible.contains(supportedSymbols.CUBE_ROOT)) possible.removeIf(s -> !s.equals(supportedSymbols.CUBE_ROOT));
                else if (!possible.contains(supportedSymbols.VARIABLE)) possible.add(supportedSymbols.VARIABLE);
                else possible.removeIf(s -> !s.equals(supportedSymbols.VARIABLE));
                statementCompleted = false;
                yield possible;
            } case S -> {
                if (!possible.contains(supportedSymbols.SQUARE_ROOT)) possible.add(supportedSymbols.SQUARE_ROOT);
                else if (!possible.contains(supportedSymbols.VARIABLE)) possible.add(supportedSymbols.VARIABLE);
                possible.removeIf(s -> !s.equals(supportedSymbols.SQUARE_ROOT));
                statementCompleted = false;
                yield possible;
            } case Q -> {
                if(lastKey == KeyCode.S && possible.contains(supportedSymbols.SQUARE_ROOT)) possible.removeIf(s -> !s.equals(supportedSymbols.SQUARE_ROOT));
                else if (!possible.contains(supportedSymbols.VARIABLE)) possible.add(supportedSymbols.VARIABLE);
                else possible.removeIf(s -> !s.equals(supportedSymbols.VARIABLE));
                statementCompleted = false;
                yield possible;
            } case R -> {
                if(lastKey == KeyCode.Q && possible.contains(supportedSymbols.SQUARE_ROOT)) possible.removeIf(s -> !s.equals(supportedSymbols.SQUARE_ROOT));
                else if (!possible.contains(supportedSymbols.VARIABLE)) possible.add(supportedSymbols.VARIABLE);
                else possible.removeIf(s -> !s.equals(supportedSymbols.VARIABLE));
                statementCompleted = false;
                yield possible;
            } case T -> {
                if(lastKey == KeyCode.R && possible.contains(supportedSymbols.SQUARE_ROOT)) {
                    possible.removeIf(s -> !s.equals(supportedSymbols.SQUARE_ROOT));
                    statementCompleted = true;
                    yield possible;
                }
                else if (!possible.contains(supportedSymbols.VARIABLE)) possible.add(supportedSymbols.VARIABLE);
                else possible.removeIf(s -> !s.equals(supportedSymbols.VARIABLE));
                statementCompleted = false;
                yield possible;
            } case OPEN_BRACKET -> {

                yield possible;
            }
            default -> possible;
        };
    }

    public enum supportedSymbols {
        FRACTION,
        SQUARE_ROOT,
        POWER,
        CUBE_ROOT,
        E,
        PI,
        UNDER,
        OVER,
        BRACKET,
        MATRIX,
        SQUARE_BRACKET,
        PLUS_MINUS,
        INTEGRAL,
        VARIABLE,
        NONE
    }

    public record enclosingTag(String openingTag, List<?> content, String closingTag) {}

    public record singleTag(String openingTag, String Content, String closingTag) {};


}


