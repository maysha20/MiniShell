public class BasicCalculator {

    public double calculateRoot(double number, int rootDegree) {
        if (rootDegree <= 0) {
            throw new IllegalArgumentException("The order of the root must be a positive integer.");
        }

        if (number < 0 && rootDegree % 2 == 0) {
            throw new IllegalArgumentException("Cannot compute the square root of a negative number in real numbers.");
        }

        return Math.pow(number, 1.0 / rootDegree);
    }
}