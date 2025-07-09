import java.util.Scanner;
import TeacherTools.GradeEntryFrame;

public class CalculatorUI {
    private AuthManager authManager;

    private EquationSolver equationSolver;
    private ExpressionEvaluator evaluator;
    private Scanner scanner;
    private HistoryManager historyManager;

    public CalculatorUI() {
        authManager = new AuthManager();

        equationSolver = new EquationSolver();

        scanner = new Scanner(System.in);
        evaluator = new ExpressionEvaluator();
    }

    public void start() {
        while (true) {
            authManager.welcomeScreen();
            historyManager = new HistoryManager(authManager.getCurrentUser());

            boolean running = true;
            while (running) {
                System.out.println("\nui.Main Menu:");
                System.out.println("1. Evaluate Expression");
                System.out.println("2. Solve Equations");
                System.out.println("3. Graph Mathematical Expression");
                System.out.println("4. Solve System of Linear Equations");
                System.out.println("5. Teacher Tools");
                System.out.println("6. Show History");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int mainChoice = scanner.nextInt();
                scanner.nextLine(); // clear buffer

                switch (mainChoice) {
                    case 1:
                        handleExpressionEvaluation();
                        break;

                    case 2:
                        handleEquations();
                        break;



                    case 3:
                        handleGraphExpression();
                        break;
                    case 4:
                        handleLinearSystem();
                        break;
                    case 5:
                        new GradeEntryFrame();
                        break;

                    case 6:
                        historyManager.showHistory();
                        break;
                    case 7:
                        System.out.println("Thank You For Using The Smart Calculator, Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Incorrect choice, Please Try Again.");
                }

                if (!running) {
                    System.out.println("\nReturning to login screen...\n");
                }
            }
        }
    }

    private void handleExpressionEvaluation() {

        System.out.println("1.  Evaluate Mathematical Expression");
        System.out.println("\n Instructions for Entering a Mathematical Expression:");
        System.out.println("=================================================================");
        System.out.println(" - You can use basic operations: + , - , * , / , ^");
        System.out.println(" - You can use parentheses: ( and ) to group expressions");
        System.out.println(" - Supported functions: sin(e), cos(e), tan(e), sqrt(e), log(e)");
        System.out.println("    (2 + 3) * 4^3");
        System.out.println("    root(degree,number)  + sin(30)");
        System.out.println("    log(base, value) + cos(45)");
        System.out.println("=================================================================");
        System.out.println(" Note: Make sure parentheses are balanced and no invalid characters are used.");
        System.out.println("=================================================================");
        System.out.println("  Type your expression below:");

        String expression = scanner.nextLine();
        String originalExpression = expression;

        if (expression == null || expression.trim().isEmpty()) {
            System.out.println("[ERROR] Expression is empty.");
            return;
        }

        if (!areParenthesesBalanced(expression)) {
            System.out.println("[ERROR] Parentheses are not balanced.");
            return;
        }

        expression = expression.trim().toLowerCase();


        expression = RootParserFix.preprocessRootFunction(expression);
        expression = LogParserFix.preprocessLog(expression);


        if (!expression.matches("[0-9a-zA-Z+\\-*/().,^sqrt ]+")) {
            System.out.println("[ERROR] Expression contains invalid characters.");
            return;
        }

        if (expression.contains("sqrt")) {
            expression = expression.replace("sqrt", "root(2, ");
            expression += ")";
        }

        expression = replaceTrigFunction(expression, "sin", Math::sin);
        expression = replaceTrigFunction(expression, "cos", Math::cos);
        expression = replaceTrigFunction(expression, "tan", Math::tan);



        while (expression.contains("root(")) {
            try {
                int start = expression.indexOf("root(");
                int end = expression.indexOf(")", start);
                if (end == -1) break;

                String fullRoot = expression.substring(start, end + 1);
                String inside = expression.substring(start + 5, end);
                String[] parts = inside.split(",");

                if (parts.length == 2) {
                    int degree = Integer.parseInt(parts[0].trim());
                    double number = Double.parseDouble(parts[1].trim());

                } else {
                    System.out.println("[ERROR] Invalid root format.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("[ERROR] Error in root parsing: " + e.getMessage());
                return;
            }
        }

        try {
            double result = evaluator.evaluate(expression);
            System.out.printf("The Result: %.2f\n", result);
            historyManager.addToHistory(originalExpression, result);

            System.out.print("Do you want to see a graph of this expression? (yes/no): ");
            String graphAnswer = scanner.nextLine().trim().toLowerCase();



        } catch (Exception e) {
            System.out.println("An Error Occurred While Parsing The Expression: " + e.getMessage());
        }

    }

    private void handleEquations() {
        int choice = showEquationsMenu(scanner);
        if (choice == 3) return;

        scanner.nextLine(); // تنظيف الإدخال السابق

        if (choice == 1) {
            double a, b;
            try {
                System.out.print("Enter The Coefficient a for x: ");
                a = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("Enter The Constant Number b: ");
                b = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid input. Please enter numeric values.");
                return;
            }

            if (a == 0) {
                System.out.println("[ERROR] Coefficient 'a' cannot be zero in a linear equation.");
                return;
            }

            String operation = a + "x + " + b + " = 0";
            String result = equationSolver.solveLinearEquation(a, b);
            System.out.println(result);

            try {
                double x = Double.parseDouble(result.split("=")[1].trim());
                historyManager.addToHistory(operation, x);
            } catch (Exception e) {
                System.out.println("[NOTE] Could not log result to history.");
            }

        } else if (choice == 2) {
            double a, b, c;
            try {
                System.out.print("Enter The Coefficient a for x²: ");
                a = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("Enter The Coefficient b for x: ");
                b = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("Enter The Constant Number c: ");
                c = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid input. Please enter numeric values.");
                return;
            }

            String operation = a + "x^2 + " + b + "x + " + c + " = 0";
            String result = equationSolver.solveQuadraticEquation(a, b, c);
            System.out.println(result);

            // حفظ العملية في السجل (بدون نتيجة رقمية لأن الحلول ممكن تكون مركبة)
            historyManager.addToHistory(operation, 0);

            // إنشاء التعبير الرياضي للرسم
            String expression = a + "*x^2 + " + b + "*x + " + c;

            System.out.print("Do you want to plot this equation? (yes/no): ");
            String plotChoice = scanner.nextLine().trim().toLowerCase();
            if (plotChoice.equals("yes") || plotChoice.equals("y")) {
                GraphPlotter.showGraph(expression);
            }
        }
    }



    private void handleLinearSystem() {
        System.out.print("Enter number of variables (e.g., 2 or 3): ");
        int n = scanner.nextInt();
        double[][] matrix = new double[n][n + 1];

        System.out.println("Enter coefficients and constants for each equation:");
        for (int i = 0; i < n; i++) {
            System.out.println("Equation " + (i + 1) + ":");
            for (int j = 0; j <= n; j++) {
                if (j == n) {
                    System.out.print("Constant term: ");
                } else {
                    System.out.print("Coefficient of x" + (j + 1) + ": ");
                }
                matrix[i][j] = scanner.nextDouble();
            }
        }

        try {
            double[] solution = LinearSystemSolver.solveByGauss(matrix);
            System.out.println("The solution is:");
            for (int i = 0; i < solution.length; i++) {
                System.out.printf("x%d = %.4f\n", (i + 1), solution[i]);
            }

            // حفظ في السجل
            StringBuilder summary = new StringBuilder("System: ");
            for (int i = 0; i < matrix.length; i++) {
                summary.append("[");
                for (int j = 0; j <= n; j++) {
                    summary.append(matrix[i][j]);
                    if (j != n) summary.append(", ");
                }
                summary.append("] ");
            }
            historyManager.addToHistory(summary.toString(), 0); // نتيجة رمزية

        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void handleGraphExpression() {
        scanner.nextLine(); // لتنظيف الإدخال السابق
        System.out.print("Enter a mathematical expression in terms of x (e.g. x^2 - 3x + 2 or sin(x)): ");
        String expression = scanner.nextLine();

        // تحقق إذا كان المستخدم يريد الرجوع
        if (expression.equalsIgnoreCase("back")) {
            return;
        }

        if (expression == null || expression.trim().isEmpty()) {
            System.out.println("[ERROR] Expression is empty.");
            return;
        }

        try {
            GraphPlotter.showGraph(expression);
        } catch (Exception e) {
            System.out.println("[ERROR] Could not plot expression: " + e.getMessage());
        }
    }

    private String replaceTrigFunction(String expr, String func, java.util.function.DoubleUnaryOperator op) {
        while (expr.contains(func + "(")) {
            int start = expr.indexOf(func + "(") + func.length() + 1;
            int end = expr.indexOf(")", start);
            if (end == -1) break;

            String inside = expr.substring(start, end).trim();
            try {
                double angle = Double.parseDouble(inside);
                double value = op.applyAsDouble(Math.toRadians(angle));
                String full = func + "(" + inside + ")";
                expr = expr.replace(full, String.valueOf(value));
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid number inside " + func + "(): " + inside);
                break;
            }
        }
        return expr;
    }

    private boolean areParenthesesBalanced(String expr) {
        int count = 0;
        for (char ch : expr.toCharArray()) {
            if (ch == '(') count++;
            else if (ch == ')') count--;
            if (count < 0) return false;
        }
        return count == 0;
    }


    public static int showEquationsMenu(Scanner scanner){
        System.out.println(" \n { Solving Equations }");
        System.out.println("1-Equations Level One (ax + b = 0)");
        System.out.println("2-Equations Level Tow (ax² + bx + c = 0)");
        System.out.println("3-Back To The ui.Main Menu ");
        System.out.print("Choose The Operation (1-3)");

        return scanner.nextInt();
    }

    private int findClosingParen(String expr, int start) {
        int count = 1;
        for (int i = start; i < expr.length(); i++) {
            if (expr.charAt(i) == '(') count++;
            else if (expr.charAt(i) == ')') count--;
            if (count == 0) return i;
        }
        return -1;
    }












}