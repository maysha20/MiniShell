public class RootParserFix {

    public static String preprocessRootFunction(String expression) {
        // مثال: root(3,27) => (27)^(1/3)
        while (expression.contains("root(")) {
            int start = expression.indexOf("root(");
            int end = findClosingParenthesis(expression, start + 5);
            if (end == -1) break;

            String fullRoot = expression.substring(start, end + 1);
            String inside = expression.substring(start + 5, end);
            String[] parts = inside.split(",");
            if (parts.length == 2) {
                String degree = parts[0].trim();
                String value = parts[1].trim();
                String replacement = "(" + value + ")^(1/" + degree + ")";
                expression = expression.replace(fullRoot, replacement);
            } else {
                break; // تنسيق خاطئ، تجاهل
            }
        }
        return expression;
    }

    private static int findClosingParenthesis(String str, int openIndex) {
        int count = 0;
        for (int i = openIndex; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') count++;
            else if (c == ')') {
                if (count == 0) return i;
                else count--;
            }
        }
        return -1;
    }
}