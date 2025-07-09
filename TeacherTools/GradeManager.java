package TeacherTools;

import java.util.ArrayList;
import java.util.List;

public class GradeManager {
    private List<StudentGrade> grades = new ArrayList<>();

    public void addGrade(String name, double grade) {
        grades.add(new StudentGrade(name, grade));
    }

    public double getAverage() {
        return grades.stream().mapToDouble(StudentGrade::getGrade).average().orElse(0);
    }

    public double getMax() {
        return grades.stream().mapToDouble(StudentGrade::getGrade).max().orElse(0);
    }

    public double getMin() {
        return grades.stream().mapToDouble(StudentGrade::getGrade).min().orElse(0);
    }

    public double getStandardDeviation() {
        double avg = getAverage();
        double variance = grades.stream()
                .mapToDouble(g -> Math.pow(g.getGrade() - avg, 2))
                .average()
                .orElse(0);
        return Math.sqrt(variance);
    }

    public List<StudentGrade> getGrades() {
        return grades;
    }
}
