# Dev Log 
## Methods 

- `Solve()` in [Solve.java](src/main/java/com/example/gcalc/advancedCalculations/Solve.java) `L# 3`
- `Expand()` in [Expand.java](src/main/java/com/example/gcalc/advancedCalculations/Expand.java) `L# 3`
- `Factor()` in [Factor.java](src/main/java/com/example/gcalc/advancedCalculations/Factor.java) `L# 3`
## Known Bugs

- Tan graphs are inaccurate when the y value approaches +/- ∞
- The -x of parabolas are inverted and approach -∞ instead of ∞ making the x domain (-∞, ∞) instead of [y.intercept, +/-∞)
## Features In Progress

- Basic Calculator 
  - A simplified version of the physics calculator using the stack to efficiently evaluate simple mathematical problems, with a more  simplistic GUI than the Physics calculator and intended for more general purpose use cases.
- Scientific 
  - A simplified version of the physics calculator with some of the more general methods rather than the hyper spesific ones that the physics calculator offers
## Equation Implementations

* ### For fully implemented equations please look at [Equation List](supportedEquationList.md)

- The Schrödinger Equation `∇2ψ+8π2 m(E – U)ψ/h 2=0`
## Classes

- `Solve` file [Solve.java](src/main/java/com/example/gcalc/advancedCalculations/Solve.java)
- `Expand` file [Expand.java](src/main/java/com/example/gcalc/advancedCalculations/Expand.java)
- `Factor` file [Factor.java](src/main/java/com/example/gcalc/advancedCalculations/Factor.java)