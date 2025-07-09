public class EquationSolver {




    // حل معادلات من الدرجة الأولى
    public String solveLinearEquation(double a, double b) {
        if (a == 0) {
            return b == 0 ? "The equation has an infinite number of solutions." : "The equation has no solution.";
        }
        double solution = -b / a;
        return "The solution of the equation is: x = " + solution;
    }

    // حل معادلات من الدرجة الثانية
    public String solveQuadraticEquation(double a, double b, double c) {
        if (a == 0) {
            return solveLinearEquation(b, c);
        }

        double discriminant = b * b - 4 * a * c;
        String steps = "Calculating the discriminant: Δ = b² - 4ac\nStep 1: Δ = " + discriminant;

        if (discriminant > 0) {
            double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return steps + "\nStep 2: The discriminant is positive; there are two real solutions:\n" +
                    "x1 = " + x1 + "\n" +
                    "x2 = " + x2;
        } else if (discriminant == 0) {
            double x = -b / (2 * a);
            return steps + "\nStep 2: The discriminant is zero; there is one real solution:\n" +
                    "x = " + x;
        } else {
            double realPart = -b / (2 * a);
            double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);
            return steps + "\nStep 2: The discriminant is negative; there are two complex solutions:\n" +
                    "x1 = " + realPart + " + " + imaginaryPart + "i\n" +
                    "x2 = " + realPart + " - " + imaginaryPart + "i";
        }
    }
}