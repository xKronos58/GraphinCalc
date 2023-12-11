package com.example.gcalc.Calculator;

public class Constants {

    //region mathematicsConstants

    /**Stores the value of pi (π)*/
    public static final double pi = 3.141592653589793238462643383279;

    /**Stores the value of Tau (tu)*/
    public static final double tau = 2 * pi;

    /**Stores the value of the golden ratio / phi*/
    public static final double phi = (1 + Math.sqrt(5))/2;

    /** Stores the value of e, which is used for calculating exponential */
    public static final double e =  2.718281828459045235360287471352;

    /**Stores the value of the super golden ratio */
    public static final double superGoldenRatio = 1.46557123187676802665;

    /**Stores the value for the Connective constant for a hexagonal lattice*/
    public static final double CCHL = Math.sqrt(2 + Math.sqrt(2));

    /**Stores the Kepler–Bouwkamp constant*/
    public static final double KBC = 0.11494204485329620070;

    /**Stores the Wallis Constant*/
    public static final double WC = 2.09455148154232659148;

    //region physicsConstants
    /**
     * Magnetic Constant inside a vacuum (µ0)
     * */
    public static final double pico0 = 0.00000125663766212;

    /**
     * Mass of proton in Kg
     * */
    public static final double protonMass = 1.67262192 * Math.pow(10, -27);
    public static final double neutronMass = 1.6749286 * Math.pow(10, -27);

    /**
     * Charge of a proton in coulombs
     * */
    public static final double protonCharge = 1.602176634 * Math.pow(10, -19);

    /**
     * Gravitational Constant
     * */
    public static final double gravity = 9.80665;
}
