# CHANGE LOG 
### How to use 
See B-0.01.A, B = beta (version), 0 = major iteration, 01 = minor iteration, A = First in patch order

## B-0.07.B 
- Cleaned up and updated `READNE.md`
- Provided new screenshots for application after reworks 
- Fixed minor bug that was not caught with last commit inside the `cqrt[]{}` method as the `()` were changed to `{}` and the application was still searching for `()` to finish the equation.
## B-0.07.A
-  Reworked graph plotting, graphs are now added accurately.
  - NOTE: Graphs that have r() or ^2 are not evaluated correctly in the negatives. Due to an unknown cause 
-  Added detailed comments to the `GCMain.java` class
-  Did some general code cleanup
-  Reworked the `EvaluateGraph()` function now efficiently replaces x and runs the function with updated x value
-  Added accuracy implementations, so izt can now be modified for performance or accuracy. Will be added to settings menu in future, for now is defaulted to `0.01d`
-  Made progress on `Simplify.java` class
## B-0.06.A
-  Added `HCF()` & `LCM()` & `SimplifyQuadratic()`
-  Re-did physics calc button layout. 
- - NOTE functions and constants menus have not been worked on 
-  Added alerts for invalid equations and `errorMessage()` for a predefined error 

## B-0.05.A
-  Added type definition for solve function
-  Added functioning quadratic solving when `solve(t=quadratic(equation=0))`
-  Did work on `Expand.java`
-  Added application icon
#### Below:
' <img src="src/main/resources/com/example/gcalc/Images/Icon.png" width="100px">


## B-0.04.C
-  Started expand rework

## B-0.04.B 
-  Started work on settings
-  General code cleanup

## B-0.04.A
-  Did some general cleanup of code 
-  Removed unused classes
-  Added Base for menus and button popup screens 
-  Added support for equations inside the physics calculator

## B-0.03.C
-  Added inbuilt supported actions for user reference

## B-0.03.B
-  Added support for simultaneous equation solving through the `solve()` function. 
  - Form = `solve(a1x + b1y = c1; a2x + b2y = c2, x)`

## B-0.03.A 
-  Added help menus inside the physics calculations
-  Added ability to render MD and Html code inside application
-  Fixed missing java fx libs

## B-0.02.A
-  Continued with Expand() 
-  Fixed physics calculator equation output rendering

## B-0.01.B
-  Added start to Expand Class
-  Added `HasChar(x, sp)` method

## B-0.01.A
-  Added change log
- ### Extended Constants Lib
    Added Constants.md (List of supported / planned lib constants)

    Added Constants: 
  - Tau
  - phi (Golden Ratio) 
  - Super golden ratio
  - CCHL
  - KBC (Kepler–Bouwkamp constant)
  - WC (Wallis Constant)

-  Added use of Log10 and not just LN
-  Re-Wrote Solve.java

