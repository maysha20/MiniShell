public class LinearSystemSolver {

    // حل نظام معادلات خطية باستخدام طريقة جاوس
    public static double[] solveByGauss(double[][] augmentedMatrix) {
        int n = augmentedMatrix.length;

        for (int pivot = 0; pivot < n; pivot++) {
            // البحث عن الصف بأكبر قيمة مطلقة في العمود الحالي
            int maxRow = pivot;
            for (int i = pivot + 1; i < n; i++) {
                if (Math.abs(augmentedMatrix[i][pivot]) > Math.abs(augmentedMatrix[maxRow][pivot])) {
                    maxRow = i;
                }
            }

            // تبديل الصفوف
            double[] temp = augmentedMatrix[pivot];
            augmentedMatrix[pivot] = augmentedMatrix[maxRow];
            augmentedMatrix[maxRow] = temp;

            // جعل القطر = 1
            double pivotValue = augmentedMatrix[pivot][pivot];
            if (pivotValue == 0) throw new ArithmeticException("No unique solution.");

            for (int j = 0; j <= n; j++) {
                augmentedMatrix[pivot][j] /= pivotValue;
            }

            // جعل باقي الأعمدة = 0
            for (int i = 0; i < n; i++) {
                if (i != pivot) {
                    double factor = augmentedMatrix[i][pivot];
                    for (int j = 0; j <= n; j++) {
                        augmentedMatrix[i][j] -= factor * augmentedMatrix[pivot][j];
                    }
                }
            }
        }

        // استخراج الحلول
        double[] solution = new double[n];
        for (int i = 0; i < n; i++) {
            solution[i] = augmentedMatrix[i][n];
        }
        return solution;
    }
}