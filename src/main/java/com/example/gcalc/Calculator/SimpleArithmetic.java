package com.example.gcalc.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SimpleArithmetic /*This class name is a lie lol, it is the most complicated so far */ {

    private static final Map<String, Double> ConversionRate = Map.of(
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

    private static final Set<String> _validUnitsLength = new HashSet<>(Arrays.asList("tm", "gm", "Mm", "km", "m", "cm", "mm", "um", "nm", "pm")); // 1 = m
    private static final Set<String> _validUnitsWeight = new HashSet<>(Arrays.asList("pg", "ng", "ug", "mg", "g", "kg", "t", "mt", "gt")); // 1 = kg
    private static final Set<String> _validUnitsTime = new HashSet<>(Arrays.asList("", "")); // 1 = s
    private static final Set<String> _validUnitsCurrent = new HashSet<>(Arrays.asList("", "")); // 1 = A
    private static final Set<String> _validUnitsTemperature = new HashSet<>(Arrays.asList("", "")); // 1 = K
    private static final Set<String> _validUnitsSubstance = new HashSet<>(Arrays.asList("", "")); // 1 = mol
    private static final Set<String> _validUnitsLight = new HashSet<>(Arrays.asList("", "")); // 1 = cd


    public static double Convert() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("please a value followed by type 2 units to be converted,\n seperated by a space (in for of cm m, etc) or a type (micro giga): ");
        String unitsJoined = reader.readLine();

        int i, j = 0, l = 0;
        for(i = 0; i < unitsJoined.length() -1; i++)
            if(unitsJoined.charAt(i) == ' ') {
                j++;
                if(j == 1)
                    l = i;
                else
                    break;
            }

        String[] units = { unitsJoined.substring(l + 1, i), unitsJoined.substring(i + 1) };
        double value = Double.parseDouble(unitsJoined.substring(0, l)) * ConversionRate.get(units[0]);

        System.out.println(units[0] + ", " + units[1]);

        if(convertible(units)) {
            double value2 = ConversionRate.get(units[1]);
            System.out.println(value);
            return value / value2;
        }

        return 0;
    }

    public static boolean convertible(String[] units) {
        return ((_validUnitsLength.contains(units[0]) && _validUnitsLength.contains(units[1])) ||
                (_validUnitsWeight.contains(units[0]) && _validUnitsWeight.contains(units[1])) ||
                (_validUnitsTime.contains(units[0]) && _validUnitsTime.contains(units[1])) ||
                (_validUnitsCurrent.contains(units[0]) && _validUnitsCurrent.contains(units[1])) ||
                (_validUnitsTemperature.contains(units[0]) && _validUnitsTemperature.contains(units[1])) ||
                (_validUnitsSubstance.contains(units[0]) && _validUnitsSubstance.contains(units[1])) ||
                (_validUnitsLight.contains(units[0]) && _validUnitsLight.contains(units[1]))) && !Objects.equals(units[0], units[1]);
    }

    public static double findMass(String amount) {
        String[] split = amount.split(" ");
        return Double.parseDouble(split[0]) /*Protons*/ * Constants.protonMass + Double.parseDouble(split[1]) /*Neutrons*/ * Constants.neutronMass;
    }
}
