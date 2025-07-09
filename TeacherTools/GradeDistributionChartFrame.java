package TeacherTools;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GradeDistributionChartFrame extends JFrame {

    private final int[] bins = new int[9]; // 9 فئات

    public GradeDistributionChartFrame(List<StudentGrade> grades) {
        setTitle("Grade Distribution Chart");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        countGrades(grades); // ملء الفئات

        JPanel chartPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                int width = getWidth();
                int height = getHeight();
                int barWidth = width / bins.length - 20;

                int max = 0;
                for (int count : bins) max = Math.max(max, count);

                for (int i = 0; i < bins.length; i++) {
                    int barHeight = (int) ((bins[i] / (double) max) * (height - 100));
                    int x = 30 + i * (barWidth + 20);
                    int y = height - barHeight - 50;

                    g2.setColor(new Color(0, 180, 220));
                    g2.fillRect(x, y, barWidth, barHeight);

                    g2.setColor(Color.BLACK);
                    g2.drawString(String.valueOf(bins[i]), x + barWidth / 4, y - 5); // العدد
                    g2.drawRect(x, y, barWidth, barHeight); // إطار

                    g2.drawString(getBinLabel(i), x, height - 30); // عنوان الفئة
                }
            }
        };

        add(chartPanel);
        setVisible(true);
    }

    private void countGrades(List<StudentGrade> grades) {
        for (StudentGrade sg : grades) {
            double grade = sg.getGrade();
            int index = getBinIndex(grade);
            if (index >= 0 && index < bins.length) {
                bins[index]++;
            }
        }
    }

    private int getBinIndex(double grade) {
        if (grade < 55) return 0;
        else if (grade < 65) return 1;
        else if (grade < 70) return 2;
        else if (grade < 75) return 3;
        else if (grade < 80) return 4;
        else if (grade < 85) return 5;
        else if (grade < 90) return 6;
        else if (grade < 95) return 7;
        else return 8; // 95–100
    }

    private String getBinLabel(int index) {
        return switch (index) {
            case 0 -> "0–54";
            case 1 -> "55–64";
            case 2 -> "65–69";
            case 3 -> "70–74";
            case 4 -> "75–79";
            case 5 -> "80–84";
            case 6 -> "85–89";
            case 7 -> "90–94";
            case 8 -> "95–100";
            default -> "";
        };
    }
}
