package com.example.gcalc.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EquationList {


    // Physics equations
    public static double LoadMFD(char type) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        switch (type){
            case '0' ->  {  // Calculate the flux
                System.out.print("Input I : ");
                double I = isExpression(reader.readLine());
                System.out.print("Input r : ");
                double r = isExpression(reader.readLine());
                return (Constants.pico0 / (2 * Constants.pi)) * (I/r);
            }
            case '1' -> {   // Calculate the distance
                System.out.print("Input I : ");
                double I = isExpression(reader.readLine());
                System.out.print("Input B : ");
                double B = isExpression(reader.readLine());
                return (Constants.pico0 / (2 * Constants.pi)) * (I/B);
            }
            case '2' -> {   // Calculate the current
                System.out.print("NOTE: This equation does not currently work and will give an incorrect answer\nInput r : ");
                double r = isExpression(reader.readLine());
                System.out.print("Input B : ");
                double B = isExpression(reader.readLine());
                return (Constants.pico0 / (2 * Constants.pi)) * (r/B);
            }
        }
        return 0;
    }

    public static double terminalVelocity() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        /*  EQUATION ($ = below power)
        *
        *   QU = 1/2 mv^2
        *   v = Sqrt(2QU/m)
        *   v = Sqrt(2 * constants.e * U / m$a)
        * */
        System.out.print("Please input the mass : ");
        double m = isExpression(reader.readLine());
        System.out.print("Please input the voltage : ");
        double U = isExpression(reader.readLine());

        return Math.sqrt((2 * Constants.protonCharge * U) / m);

    }

    public static double speed(char type) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        switch (type){
            //speed
            case '0' -> {
                System.out.print("Input distance (∆d) (meters) : ");
                double d = isExpression(reader.readLine());
                System.out.print("Input time (∆t) (seconds) : ");
                double t = isExpression(reader.readLine());
                return d/t;}
            //acceleration
            case '1' -> {
                System.out.print("Input change in velocity (∆v) (m/s) : ");
                double v = isExpression(reader.readLine());
                System.out.print("Input time (∆t) (seconds) : ");
                double t = isExpression(reader.readLine());
                return v/t; }
        }
        return 0;
    }

    public static double speedOfParticleInVacuum(char type) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //F=ma^n
        //QvB=mv^2/r
        //r=mv/QB

        switch (type){
            //Orbit Size of electron
            case '0' -> {
                System.out.print("Input speed (v) : ");
                double v = isExpression(reader.readLine());
                System.out.print("Input the magnetic flux (B) : ");
                double B = isExpression(reader.readLine());
                return ((Constants.protonMass * v) / (Constants.protonCharge * B))/4; }
            case '1' -> {}
        }

        return 0;
    }

    public static double mass(char type) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        switch (type){
            //Density
            case '0' -> {
                System.out.print("Input the mass of the object (kg) : ");
                double m = isExpression(reader.readLine());
                System.out.print("Input the Volume of the object (m^3) : ");
                double v = isExpression(reader.readLine());
                return m/v;
            }
            //Weight
            case '1' -> {
                System.out.print("Input the mass of the object (kg) : ");
                double m = isExpression(reader.readLine());
                System.out.print("Input the gravitational strength of the object (N) : ");
                double g = isExpression(reader.readLine());
                return m*g;
            }
        }
        return 0;
    }

    public static double isExpression(String element) {
        if(element.charAt(0) == 'e')
            try {
                return HandleStack.evaluate(element.substring(2, element.length() - 1));
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        else if(element.charAt(0) == 'm')
            try {
                return SimpleArithmetic.findMass(element.substring(2, element.length() - 1));
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        return Double.parseDouble(element);
    }
}
