public class InputParser {

    public static Calculation parse(String input) {
        Calculation calculation = new Calculation();

        if (input.contains("+")) {
            calculation.operation = Operation.ADD;
            String[] parts = input.split("\\+");
            calculation.operands = parseOperands(parts);
            // نفس الشي للعمليات الاخرى
        } else if (input.startsWith("sqrt(")){
            calculation.operation = Operation.SQRT;
            String numStr = input.substring(5 , input.length() -1);
            calculation.operands = new double[]{Double.parseDouble(numStr)};
        }



        return calculation;
    }

    private static double[] parseOperands(String[] parts) {
        double[] operands = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            operands[i] = Double.parseDouble(parts[i].trim());
        }
        return operands;
    }
}

class Calculation {
    Operation operation;
    double[] operands;
}

enum Operation {
    ADD, SUBTRACT, MULTIPLY, DIVIDE, POWER, SQRT, SIN, COS, TAN, LOG
}