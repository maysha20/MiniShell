public class LogParserFix {

    public static String preprocessLog(String expression) {
        StringBuilder result = new StringBuilder();
        int index = 0;

        while (index < expression.length()) {
            if (expression.startsWith("log(", index)) {
                int start = index + 4;
                int end = findClosingParenthesis(expression, start);
                if (end == -1) break;

                String inside = expression.substring(start, end);
                String[] parts = inside.split(",");

                if (parts.length == 2) {
                    // ✅ log(base, value) ← صحيح: value بالبسط
                    String base = parts[0].trim();
                    String value = parts[1].trim();
                    result.append("(log(").append(value).append(")/log(").append(base).append("))");
                } else if (parts.length == 1) {
                    // log(x) → log(x)/log(10)
                    String value = parts[0].trim();
                    result.append("(log(").append(value).append(")/log(10))");
                } else {
                    // إذا التنسيق غير صحيح
                    result.append("log(").append(inside).append(")");
                }

                index = end + 1;
            } else {
                result.append(expression.charAt(index));
                index++;
            }
        }

        return result.toString();
    }

    private static int findClosingParenthesis(String str, int openIndex) {
        int count = 1;
        for (int i = openIndex; i < str.length(); i++) {
            if (str.charAt(i) == '(') count++;
            else if (str.charAt(i) == ')') count--;
            if (count == 0) return i;
        }
        return -1;
    }
}