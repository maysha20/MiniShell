package TeacherTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GradeEntryFrame extends JFrame {
    private GradeManager gradeManager = new GradeManager();
    private JTextArea outputArea = new JTextArea();

    public GradeEntryFrame() {
        setTitle("Student Grades - Teacher Panel");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextField nameField = new JTextField(10);
        JTextField gradeField = new JTextField(5);
        JButton addBtn = new JButton("Add Grade");
        JButton showStatsBtn = new JButton("Show Statistics");
        JButton showChartBtn = new JButton("Show Chart");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Student:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(addBtn);

        addBtn.addActionListener((ActionEvent e) -> {
            String name = nameField.getText().trim();
            try {
                double grade = Double.parseDouble(gradeField.getText().trim());
                gradeManager.addGrade(name, grade);
                outputArea.append("✔ Added: " + name + " → " + grade + "\n");
                nameField.setText("");
                gradeField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid grade format.");
            }
        });

        showStatsBtn.addActionListener(e -> {
            double avg = gradeManager.getAverage();
            double max = gradeManager.getMax();
            double min = gradeManager.getMin();
            double std = gradeManager.getStandardDeviation();

            outputArea.append("\n📊 Statistics:\n");
            outputArea.append("➤ Average: " + String.format("%.2f", avg) + "\n");
            outputArea.append("➤ Max: " + max + "\n");
            outputArea.append("➤ Min: " + min + "\n");
            outputArea.append("➤ Std Dev: " + String.format("%.2f", std) + "\n");
        });

        showChartBtn.addActionListener(e -> {
            new GradeDistributionChartFrame(gradeManager.getGrades());
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(showStatsBtn);
        bottomPanel.add(showChartBtn);

        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
