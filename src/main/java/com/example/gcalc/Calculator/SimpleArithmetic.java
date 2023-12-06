package com.example.gcalc.Calculator;

import java.util.*;

public class SimpleArithmetic /*This class name is a lie lol, it is the most complicated so far */ {

    private static final Map<String, Double> ConversionRateLength = Map.of(
            "tm", 1000000000000.0,
            "gm", 1000000000.0,
            "Mm", 1000000.0,
            "km", 1000.0,
            "cm", 0.01,
            "mm", 0.001,
            "um", 0.000001,
            "nm", 0.000000001,
            "pm", 0.00000000001
    );

    private static final Map<String, Double> ConversionRateWeight = Map.of();

    private static final Map<String, Double> ConversionRateTime = Map.of();

    private static final Map<String, Double> ConversionRateCurrent = Map.of();

    private static final Map<String, Double> ConversionRateTemperature = Map.of();

    private static final Map<String, Double> ConversionRateSubstance = Map.of();

    private static final Map<String, Double> ConversionRateLight = Map.of();

    public static double Convert(Object type, Object unitsOne, Object unitsTwo, String value) {
        String _type = type.toString(), _unitsOne = unitsOne.toString(), _unitsTwo = unitsTwo.toString();

        return switch (_type) {
            case "Length" -> (Double.parseDouble(value) * ConversionRateLength.get(_unitsOne)) / ConversionRateLength.get(_unitsTwo);
            case "Weight" -> convertWeight(_unitsOne, _unitsTwo, value);
            case "Time" -> convertTime(_unitsOne, _unitsTwo, value);
            case "Current" -> convertCurrent(_unitsOne, _unitsTwo, value);
            case "Temperature" -> convertTemperature(_unitsOne, _unitsTwo, value);
            case "Substance" -> convertSubstance(_unitsOne, _unitsTwo, value);
            case "Light" -> convertLight(_unitsOne, _unitsTwo, value);
            default -> 0;
        };
    }

    private static double convertLength(String unitsOne, String unitsTwo, String value) {
        return (Double.parseDouble(value) * ConversionRateLength.get(unitsOne)) / ConversionRateLength.get(unitsTwo);
    }

    private static double convertWeight(String unitsOne, String unitsTwo, String value) {
        return (Double.parseDouble(value) * ConversionRateWeight.get(unitsOne)) / ConversionRateWeight.get(unitsTwo);
    }

    private static double convertTime(String unitsOne, String unitsTwo, String value) {
        return (Double.parseDouble(value) * ConversionRateTime.get(unitsOne)) / ConversionRateTime.get(unitsTwo);
    }

    private static double convertCurrent(String unitsOne, String unitsTwo, String value) {
        return (Double.parseDouble(value) * ConversionRateCurrent.get(unitsOne)) / ConversionRateCurrent.get(unitsTwo);
    }

    private static double convertTemperature(String unitsOne, String unitsTwo, String value) {
        return (Double.parseDouble(value) * ConversionRateTemperature.get(unitsOne)) / ConversionRateTemperature.get(unitsTwo);
    }

    private static double convertSubstance(String unitsOne, String unitsTwo, String value) {
        return (Double.parseDouble(value) * ConversionRateSubstance.get(unitsOne)) / ConversionRateSubstance.get(unitsTwo);
    }

    private static double convertLight(String unitsOne, String unitsTwo, String value) {
        return (Double.parseDouble(value) * ConversionRateLight.get(unitsOne)) / ConversionRateLight.get(unitsTwo);
    }

    public static double findMass(String amount) {
        String[] split = amount.split(" ");
        return Double.parseDouble(split[0]) /*Protons*/ * Constants.protonMass + Double.parseDouble(split[1]) /*Neutrons*/ * Constants.neutronMass;
    }
}
