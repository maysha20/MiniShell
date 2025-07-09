import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ExpressionEvaluator {

    public double evaluate(String expression) {
        try {
            Expression exp = new ExpressionBuilder(expression)
                    .variables("x")  // لو تم استخدام x في التعبير
                    .build()
                    .setVariable("x", 0); // قيمة افتراضية لـ x إذا لم تُستخدم
            return exp.evaluate();
        } catch (Exception e) {
            throw new RuntimeException("Error evaluating expression: " + e.getMessage());
        }
    }
}